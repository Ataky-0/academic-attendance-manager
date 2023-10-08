package org.academic;

import java.util.Date;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Frequencia {
    public static void printParcialFrequencias(String codigoDisciplina) { // imprime quase todas informações de todas tuplas frequencia
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta(String.format(
                    "SELECT data,presencaAusencia,faltas FROM Frequencia WHERE codigo = '%s'", codigoDisciplina));
            while (result.next()) {
                Date data = result.getDate("data");
                Boolean presencaAusencia = result.getBoolean("presencaAusencia");
                int faltas = result.getInt("faltas");
                if (presencaAusencia) {
                    System.out.printf("\n%tF Presente", data);
                    if (faltas > 0)
                        System.out.printf(" | %d Faltas\n", faltas);
                    else
                        System.out.println();
                } else {
                    System.out.printf("\n%tF Falta | 2 Faltas\n", data);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Create(Date data, int presencaAusencia, String codigo, int faltas) { // cria tupla frequencia
        String sql = String.format(
                "INSERT INTO Frequencia (data,presencaAusencia,codigo,faltas) VALUES ('%tF','%d','%s','%d')", data,
                presencaAusencia, codigo, faltas);
        Database.updateDB(sql);
    }

    public static void Delete(String codigo, Date data) { // deleta uma tupla frequencia
        String sqlFrequencia;
        if (data != null) {
            Autoavaliacao.Delete(Frequencia.getId(codigo, data));
            sqlFrequencia = String.format("DELETE FROM Frequencia WHERE data = '%tF' and codigo = '%s'", data,
                    codigo);
            Database.updateDB(sqlFrequencia);
        } else {
            try (Connection Conexao = Database.conectar()) {
                ResultSet result = Database.consultarResulta(
                        String.format("SELECT data,frequencia_id FROM Frequencia WHERE codigo = '%s'", codigo));
                while (result.next()) {
                    Autoavaliacao.Delete(result.getInt("frequencia_id"));
                    Frequencia.Delete(codigo, result.getDate("data"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getFaltas(String codigo) { // obtém todas as faltas de uma disciplina
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database
                    .consultarResulta(String.format("SELECT faltas FROM Frequencia WHERE codigo = '%s'", codigo));
            int returnFaltas = 0;
            while (result.next()) {
                returnFaltas += result.getInt("faltas");
            }
            return returnFaltas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getId(String codigo, Date data) { // obtém frequencia_id de uma tupla frequencia
        int id = 0;
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta(
                    String.format("SELECT codigo,frequencia_id FROM Frequencia WHERE data = '%tF'", data));
            result.next();
            id = result.getInt("frequencia_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static Boolean doesExist(int frequencia_id) { // retorna caso tupla frequencia exista ou não
        Boolean doesExist = false;
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta(
                    String.format("SELECT * FROM Frequencia WHERE frequencia_id = '%s'", frequencia_id));
            if (result.next())
                doesExist = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doesExist;
    }
}