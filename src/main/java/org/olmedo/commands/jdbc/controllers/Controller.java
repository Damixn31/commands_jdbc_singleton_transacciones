package org.olmedo.commands.jdbc.controllers;

import org.olmedo.commands.jdbc.models.Category;
import org.olmedo.commands.jdbc.models.Command;
import org.olmedo.commands.jdbc.repositorio.CommandRepositoryImpl;
import org.olmedo.commands.jdbc.repositorio.Repositorio;
import org.olmedo.commands.jdbc.utils.ConexionBaseDeDatos;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Date;


public class Controller {
    public static void main(String[] args) {

        // ahora ConexionBaseDeDatos.getInstance() puedo usarla en cualquier lado de mi aplicacion para hacer una peticion de cualquier tipo a la base de datos
        try (Connection conn = ConexionBaseDeDatos.getInstance()) {

            Repositorio<Command> repositorio = new CommandRepositoryImpl();

            System.out.println("================== findAll ====================");
            repositorio.findAll().forEach(System.out::println);

            System.out.println("================== byId ====================");
            System.out.println(repositorio.byId(5L));

            System.out.println("================== New Commands ====================");
            Command command = new Command();
            command.setName("netstat");
            command.setDescription("Se utiliza para mostrar estadísticas de redes y conexiones");
            command.setExample("netstat");


            Category category = new Category();
            category.setId(2L);
            command.setCategory(category);

            command.setRegistrationDate(new Date());

            repositorio.save(command);
            System.out.println("Command save succesfull");
            repositorio.findAll().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();


        }
    }
}
