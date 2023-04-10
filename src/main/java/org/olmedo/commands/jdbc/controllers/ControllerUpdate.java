package org.olmedo.commands.jdbc.controllers;

import org.olmedo.commands.jdbc.models.Category;
import org.olmedo.commands.jdbc.models.Command;
import org.olmedo.commands.jdbc.repositorio.CommandRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.Repositorio;
import org.olmedo.commands.jdbc.utils.ConexionBaseDeDatos;

import java.sql.Connection;
import java.sql.SQLException;


public class ControllerUpdate {
    public static void main(String[] args) {

        // ahora ConexionBaseDeDatos.getInstance() puedo usarla en cualquier lado de mi aplicacion para hacer una peticion de cualquier tipo a la base de datos
        try (Connection conn = ConexionBaseDeDatos.getInstance()) {

            Repositorio<Command> repositorio = new CommandRepositoryImpl();

            System.out.println("================== findAll ====================");
            repositorio.findAll().forEach(System.out::println);

            System.out.println("================== byId ====================");
            System.out.println(repositorio.byId(1L));

            System.out.println("================== Editar Command ====================");
            Command command = new Command();

            command.setId(18L);
            command.setName("useradd");
            command.setDescription("Agregar un nuevo usuario al sistem");
            command.setExample("useradd nombreUsuario");

            Category category = new Category();
            category.setId(4L);
            command.setCategory(category);

            repositorio.save(command);
            System.out.println("Command Edit successfull");
            repositorio.findAll().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();


        }
    }
}
