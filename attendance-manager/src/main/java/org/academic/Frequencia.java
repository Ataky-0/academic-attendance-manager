package org.academic;

import java.util.Date;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Frequencia {
    private Date data;
    private Boolean presencaAusencia;
    private Autoavaliacao autoavaliacao;

    // Construtor
    public Frequencia(Date data, Boolean presencaAusencia, Autoavaliacao autoavaliacao) {
        this.data = data;
        this.presencaAusencia = presencaAusencia;
        this.autoavaliacao = autoavaliacao;
    }

    public static void printParcialFrequencias(String codigoDisciplina) {
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

    public static void Create(Date data, int presencaAusencia, String codigo, int faltas) {
        String sql = String.format(
                "INSERT INTO Frequencia (data,presencaAusencia,codigo,faltas) VALUES ('%tF','%d','%s','%d')", data,
                presencaAusencia, codigo, faltas);
        Database.updateDB(sql);
    }

    public static void Delete(String codigo, Date data) {
        String sqlFrequencia;
        if (data != null) {
            Autoavaliacao.Delete(Frequencia.getId(codigo, data));
            sqlFrequencia = String.format("DELETE FROM Frequencia WHERE data = '%tF' and codigo = '%s'", data,
                    codigo);
            Database.updateDB(sqlFrequencia);
        } else {
            // Autoavaliacao.Delete(Frequencia.getId(codigo, null));
            // sqlFrequencia = String.format("DELETE FROM Frequencia WHERE codigo = '%s'", codigo);
            try (Connection Conexao = Database.conectar()) {
                ResultSet result = Database.consultarResulta(String.format("SELECT data,frequencia_id FROM Frequencia WHERE codigo = '%s'",codigo));
                while (result.next()){
                    Autoavaliacao.Delete(result.getInt("frequencia_id"));
                    Frequencia.Delete(codigo, result.getDate("data"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Alternar(String codigo, Date data) { // Quebrado, tem que corrigir
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta(String.format(
                    "SELECT presencaAusencia FROM Frequencia WHERE data = '%tF' and codigo = '%s'", data, codigo));
            if (result.next()) {
                Boolean presencaAusencia = result.getBoolean("presencaAusencia");
                int novoPresencaAusencia = (presencaAusencia == true ? 0 : 1);
                String sqlFrequencia = String.format(
                        "UPDATE Frequencia SET presencaAusencia = '%d' WHERE codigo = '%s'", novoPresencaAusencia,
                        codigo);
                Database.updateDB(sqlFrequencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getFaltas(String codigo) {
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

    public static int getId(String codigo, Date data) {
        int id = 0;
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta("SELECT codigo,frequencia_id FROM Frequencia");
            result.next();
            id = result.getInt("frequencia_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static Boolean doesExist(int frequenca_id) {
        Boolean doesExist = false;
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta("SELECT frequencia_id FROM Frequencia");
            if (result.next())
                doesExist = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doesExist;
    }

    // Getters e Setters para os atributos data, presencaAusencia e autoavaliacao
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getPresencaAusencia() {
        return presencaAusencia;
    }

    public void setPresencaAusencia(Boolean presencaAusencia) {
        this.presencaAusencia = presencaAusencia;
    }

    public Autoavaliacao getAutoavaliacao() {
        return autoavaliacao;
    }

    public void setAutoavaliacao(Autoavaliacao autoavaliacao) {
        this.autoavaliacao = autoavaliacao;
    }
}