package com.example.trabajadoresfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.lang.StringTemplate.STR;

public class HelloController implements Initializable {
    @FXML
    public TextField txtFSalario;
    @FXML
    public TextField txtFNombre;
    @FXML
    public Button btnInsertar;
    @FXML
    public Label lblNombre;
    @FXML
    public Label lblPuesto;
    @FXML
    public Label lblSalario;
    @FXML
    public Label lblFecha;
    @FXML
    public Label lblID;
    @FXML
    public ComboBox<String> comboPuesto;
    @FXML
    public ListView<String> lstVerLista;
    @FXML
    public Button btnRefrescar;
    @FXML
    public Button btnEliminar;
    @FXML
    public Button btnEditar;


    public Button basesBtn;
    //@FXML
    //public boolean btnInsertarOnAction() {
    //    if (txtFNombre == null || txtFSalario == null){
    //        return false;
    //    }else {
    //        return true;
    //    }
    //}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboPuesto.setItems(FXCollections.observableArrayList("Scada Manager", "Sales Manager", "Product Owner", "Product Manager", "Analyst Programmer", "Junior Programmer"));
        lstVerLista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String empelados = lstVerLista.getSelectionModel().getSelectedItem();
                mostrarDatos(empelados);
            }
        });

    }

    public void correcto()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Mensaje");
        alert.setContentText(STR."Empleado \{txtFNombre.getText()} introducido en la base de datos correctamente");
        alert.setTitle("HECHO");
        alert.show();
    }

    public Connection conexionBBDD()  {
        String url = "jdbc:mysql://localhost:3306/bdTrabajadores";
        String usuario = "root";
        String contrasenya = "root";
        Connection connection;

        try {
            connection = DriverManager.getConnection(url, usuario, contrasenya);
            if (connection != null) {
                return connection;
            }
        } catch (SQLException e) {
            System.out.println("Error al acceder");
        }
        return null;
    }

    public void empleado()
    {
        if (txtFNombre.getText().isEmpty() || txtFNombre.getText().isEmpty() || comboPuesto.getValue() == null)
        {
            incompleto();
        }
        else
        {
            insertar();
            correcto();
        }
    }

    public void elegirDelete(String nombre)
    {
        try {
            Connection connection = conexionBBDD();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EMPLEADO WHERE NOMBRE = ?");
            preparedStatement.setString(1, nombre);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al Borrar");
        }
    }
    public void eliminar()
    {
        elegirDelete(lstVerLista.getSelectionModel().getSelectedItem());
    }


    public ArrayList<String> meterNombres()
    {
        ArrayList<String> nombres = new ArrayList<>();
        try {
            Connection connection = conexionBBDD();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT NOMBRE FROM Empleado");
            while (resultSet.next())
            {
                nombres.add(resultSet.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar");
        }
        return nombres;
    }

    public void cogerFichero() {
        File file = new File("src/main/java/Modelo/TXT/trabajadores.txt");
        try {
            Connection connection = null;
            connection = conexionBBDD();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EMPLEADO (NOMBRE, PUESTO, SALARIO, FECHA) VALUES (?,?,?,NOW())");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
            {
                String[] trabajador;
                trabajador = scanner.nextLine().split(";");
                String Nombre =trabajador[0];
                String Puesto =trabajador[1];
                int Sueldo = Integer.parseInt(trabajador[2]);
                statement.setString(1, Nombre);
                statement.setString(2, Puesto);
                statement.setInt(3, Sueldo);
                statement.executeUpdate();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incompleto()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText("Si no rellena todos lo campos no puede ser añadido a la lista de trabajadores.");
        alert.setTitle("No se puede añadir al trabajador");
        alert.show();
    }
    public void refrescar()
    {
        try
        {
            Connection connection = conexionBBDD();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT NOMBRE FROM EMPLEADO");
            lstVerLista.getItems().clear();
            while (resultSet.next())
            {
                lstVerLista.getItems().add(resultSet.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al refrescar");
        }
    }

    public void insertar() {
        Connection connection = null;
        try {
            connection = conexionBBDD();
            PreparedStatement statmen = connection.prepareStatement("INSERT INTO TRABAJADOR (NOMBRE, PUESTO, SUELDO, FECHA) VALUES (?,?,?,NOW())");
            statmen.setString(1, txtFNombre.getText());
            statmen.setString(2, comboPuesto.getValue());
            statmen.setInt(3, Integer.parseInt(txtFSalario.getText()));
            statmen.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No funciona la conexión");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar");
                }
            }
        }
    }

    public void mostrarDatos(String nombre)
    {
        try {
            Connection connection = conexionBBDD();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EMPLEADO where NOMBRE = ?");
            preparedStatement.setString(1, nombre);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                lblID.setText(resultSet.getString("ID"));
                lblNombre.setText(resultSet.getString("NOMBRE"));
                lblPuesto.setText(resultSet.getString("PUESTO"));
                lblSalario.setText(resultSet.getString("SALARIO"));
                lblFecha.setText(resultSet.getString("FECHA"));
            }
        } catch (SQLException e) {
            System.out.println("Error al seleccionar");
        }
    }
}