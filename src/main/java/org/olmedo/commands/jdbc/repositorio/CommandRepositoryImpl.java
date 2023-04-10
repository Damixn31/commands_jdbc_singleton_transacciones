package org.olmedo.commands.jdbc.repositorio;

import org.olmedo.commands.jdbc.models.Category;
import org.olmedo.commands.jdbc.models.Command;
import org.olmedo.commands.jdbc.utils.ConexionBaseDeDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandRepositoryImpl implements Repositorio<Command> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDeDatos.getInstance();
    }

    @Override
    public List<Command> findAll() {
        List<Command> commands = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT c.*, ca.name as category FROM commands as c " +
                     "inner join categories as ca ON (c.category_id = ca.id)")) {

            while (rs.next()) {
                Command c = createCommand(rs);

                commands.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commands;
    }


    @Override
    public Command byId(Long id) {
        Command comand = null;

        try (PreparedStatement stmt = getConnection()
                .prepareStatement("SELECT c.*, ca.name as category FROM commands as c " +
                        "inner join categories as ca ON (c.category_id = ca.id) WHERE c.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    comand = createCommand(rs);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comand;
    }

    @Override
    public void save(Command command) {
        String sql;
        if (command.getId() != null && command.getId() > 0) {
            sql = "UPDATE commands SET name=?, description=?, example=?, category_id=? WHERE id=?";
        } else {
            sql = "INSERT INTO commands(name, description, example, category_id, registration_date) VALUES(?,?,?,?,?)";
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, command.getName());
            stmt.setString(2, command.getDescription());
            stmt.setString(3, command.getExample());
            stmt.setLong(4, command.getCategory().getId());

            if(command.getId() != null && command.getId() > 0) {
               stmt.setLong(5, command.getId());
            } else {
                stmt.setDate(5, new Date(command.getRegistrationDate().getTime()));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {

        try(PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM commands WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static Command createCommand(ResultSet rs) throws SQLException {
        Command c = new Command();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setDescription(rs.getString("description"));
        c.setExample(rs.getString("example"));


        Category category = new Category();
        category.setId(rs.getLong("category_id"));
        category.setName(rs.getString("category"));
        c.setCategory(category);

        c.setRegistrationDate(rs.getDate("registration_date"));
        return c;
    }
}
