package org.academic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// import javax.xml.crypto.Data;

public class Database {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://sql10.freemysqlhosting.net:3306/sql10651874";
    private static final String USUARIO = "sql10651874";
    private static final String SENHA = "PLMAzVMLbS";
    /*
    Desta maneira, não é preciso um close para Conexao pois finalizar o programa já o fará.
    SELECT COUNT(*) FROM information_schema.processlist WHERE DB IS NOT NULL;
    Este sql retornará quantas conexões ATIVAS existem no banco de dados.
    */

    public static ResultSet consultarResulta(String SQL) { // um atalho para realizar uma consulta e retornar seu ResultSet
        Connection Conexao = Database.getConnection();
        PreparedStatement consult;
        try {
            consult = Conexao.prepareStatement(SQL);
            ResultSet result = consult.executeQuery();
            return result;
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
        return null;
    }
    public static PreparedStatement consultarPuro(String SQL) { // um atalho para realizar uma consulta e retornar um PreparedStatement
        Connection Conexao = Database.getConnection();
        PreparedStatement consult;
        try {
            consult = Conexao.prepareStatement(SQL);
            return consult;
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
        return null;
    }
    public static void updateDB(String SQL){ // realiza sql do tipo create/update/delete
        Connection Conexao = Database.getConnection();
        PreparedStatement statement;
        try {
            statement = Conexao.prepareStatement(SQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USUARIO, SENHA);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.err.println("Conexão impedida com o banco de dados.");
            }
        }
        return connection;
    }
}