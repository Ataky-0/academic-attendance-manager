package org.academic;

import java.util.Date;

// import javax.xml.crypto.Data;

// import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Frequencia {
    public static void printParcialFrequencias(String codigoDisciplina) throws SQLException { // imprime quase todas
                                                                                              // informações de todas
                                                                                              // tuplas
        ResultSet result = Database.consultarResulta(String.format(
                "SELECT data,presencaAusencia,faltas FROM Frequencia WHERE codigo = '%s'", codigoDisciplina));
        if (!result.isBeforeFirst())
            System.out.printf("\nNão há frequências para esta Disciplina ainda.\n");
        while (result.next()) {
            Date data = result.getDate("data");
            Boolean presencaAusencia = result.getBoolean("presencaAusencia");
            int faltas = result.getInt("faltas");
            if (presencaAusencia) {
                System.out.printf("\n%tF Presente", data);
                if (faltas > 0)
                    System.out.printf(" | %d Falta(s)\n", faltas);
                else
                    System.out.println();
            } else {
                System.out.printf("\n%tF Falta | 2 Faltas\n", data);
            }
        }
        result.close();
    }

    public static void Create(Date data, int presencaAusencia, String codigo, int faltas) { // cria
                                                                                            // tupla
                                                                                            // frequencia
        String sql = String.format(
                "INSERT INTO Frequencia (data,presencaAusencia,codigo,faltas) VALUES ('%tF','%d','%s','%d')", data,
                presencaAusencia, codigo, faltas);
        Database.updateDB(sql);
    }

    public static void Delete(String codigo, Date data) throws SQLException { // deleta uma tupla
                                                                              // frequencia
        String sqlFrequencia;
        if (data != null) {
            Autoavaliacao.Delete(Frequencia.getId(codigo, data));
            sqlFrequencia = String.format("DELETE FROM Frequencia WHERE data = '%tF' and codigo = '%s'", data,
                    codigo);
            Database.updateDB(sqlFrequencia);
        } else {
            ResultSet result = Database.consultarResulta(
                    String.format("SELECT data,frequencia_id FROM Frequencia WHERE codigo = '%s'", codigo));
            while (result.next()) {
                Autoavaliacao.Delete(result.getInt("frequencia_id"));
                Frequencia.Delete(codigo, result.getDate("data"));
            }
            result.close();
        }
    }

    public static int getFaltas(String codigo) throws SQLException { // obtém todas as faltas de uma
                                                                     // disciplina
        ResultSet result = Database
                .consultarResulta(String.format("SELECT faltas FROM Frequencia WHERE codigo = '%s'", codigo));
        int returnFaltas = 0;
        while (result.next()) {
            returnFaltas += result.getInt("faltas");
        }
        result.close();
        return returnFaltas;
    }

    public static int getId(String codigo, Date data) throws SQLException { // obtém frequencia_id
                                                                            // de uma tupla
                                                                            // frequencia
        int id = 0;
        ResultSet result = Database.consultarResulta(
                String.format("SELECT codigo,frequencia_id FROM Frequencia WHERE data = '%tF'", data));
        result.next();
        id = result.getInt("frequencia_id");
        result.close();
        return id;
    }

    public static Boolean doesExist(int frequencia_id) throws SQLException { // retorna caso tupla
                                                                             // frequencia exista ou
                                                                             // não
        Boolean doesExist = false;
        ResultSet result = Database.consultarResulta(
                String.format("SELECT * FROM Frequencia WHERE frequencia_id = '%s'", frequencia_id));
        if (result.next())
            doesExist = true;
        result.close();
        return doesExist;
    }
}