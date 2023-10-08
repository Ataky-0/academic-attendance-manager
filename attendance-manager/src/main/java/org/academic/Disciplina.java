package org.academic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Disciplina {
    public static Boolean existeDisciplinas() { // retorna caso exista ou não quais disciplinas no banco
        Boolean existe = false;

        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta("SELECT COUNT(*) FROM Disciplina");
            if (result.next()) {
                if (result.getInt(1) > 0) {
                    existe = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static void printParcialDisciplinas() { // imprime quase todas informações de todas as disciplinas
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta("SELECT codigo,nome FROM Disciplina");
            while (result.next()) {
                String nome = result.getString("nome");
                String codigo = result.getString("codigo");
                System.out.printf("\n%s - %s\n\n", codigo, nome);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getDisciplinaByCode(String codigo) { // obtém tupla Disciplina através de codigo
        try (Connection Conexao = Database.conectar()) {
            PreparedStatement consult = Database.consultarPuro("SELECT * FROM Disciplina WHERE codigo = ?");
            consult.setString(1, codigo);
            ResultSet result = consult.executeQuery();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void Create(String nome, String codigo, int cargaHoraria) { // cria uma tupla Disciplina 
        String sql = String.format("INSERT INTO Disciplina (nome,codigo,cargaHoraria) VALUES ('%s','%s','%d')", nome,
                codigo, cargaHoraria);
        Database.updateDB(sql);
    }

    public static void Delete(String codigo) { // deleta uma tupla Disciplina
        Frequencia.Delete(codigo, null);

        String sqlDisciplina = String.format("DELETE FROM Disciplina WHERE codigo = '%s'", codigo);
        Database.updateDB(sqlDisciplina);
    }

    public static int getCargaHoraria(String codigo) { // obtém cargaHoraria de uma tupla
        int cargaHoraria = 0;
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database
                    .consultarResulta(String.format("SELECT cargaHoraria FROM Disciplina WHERE codigo = '%s'", codigo));
            result.next();
            try {
                cargaHoraria = result.getInt("cargaHoraria");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
        }
        return cargaHoraria;
    }
}
