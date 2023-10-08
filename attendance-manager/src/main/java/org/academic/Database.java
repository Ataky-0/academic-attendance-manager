package org.academic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://sql10.freemysqlhosting.net:3306/sql10651874";
    private static final String USUARIO = "sql10651874";
    private static final String SENHA = "PLMAzVMLbS";

    public static Connection conectar() { // conecta no banco de dados
        Connection conexao = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Não pôde se conectar à database.");
            e.printStackTrace();
        }

        return conexao;
    }

    public static ResultSet consultarResulta(String SQL) { // um atalho para realizar uma consulta e retornar seu ResultSet
        Connection conexao = Database.conectar();
        PreparedStatement consult;
        try {
            consult = conexao.prepareStatement(SQL);
            ResultSet result = consult.executeQuery();
            return result;
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
        return null;
    }
    public static PreparedStatement consultarPuro(String SQL) { // um atalho para realizar uma consulta e retornar um PreparedStatement
        Connection conexao = Database.conectar();
        PreparedStatement consult;
        try {
            consult = conexao.prepareStatement(SQL);
            return consult;
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
        return null;
    }
    public static int updateDB(String SQL){ // realiza sql do tipo create/update/delete
        Connection conexao = Database.conectar();
        PreparedStatement statement;
        try {
            statement = conexao.prepareStatement(SQL);
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL provavelmente incorreto.");
            e.printStackTrace();
        }
        return 0;
    }
}