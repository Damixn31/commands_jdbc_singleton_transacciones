package org.olmedo.commands.jdbc.utils;

import java.sql.*;

public class ConexionBaseDeDatos {


    private static String url = "jdbc:mysql://localhost:3306/linux_commands?serverTimezone=America/Argentina/Buenos_Aires";
    private static String username = "root";
    private static String password = "9521";

    //el sigleton para que toda nuestra aplicacion tenga la misma conexion de base de datos
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        //preguntamos si es igual a null nos conectamos
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}

