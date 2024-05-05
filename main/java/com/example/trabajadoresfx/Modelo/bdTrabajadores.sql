create database bdTrabajadores;
use bdTrabajadores;

create table Trabajador(
id int primary key auto_increment,
nombre varchar(50) not null,
puesto varchar (50) not null,
sueldo int not null,
fecha date not null
);
