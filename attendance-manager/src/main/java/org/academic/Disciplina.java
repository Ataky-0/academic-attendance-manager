package org.academic;

// import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Disciplina {
    public static Boolean existeDisciplinas() throws SQLException { // retorna caso exista ou não quais disciplinas no banco
        Boolean existe = false;

            ResultSet result = Database.consultarResulta("SELECT COUNT(*) FROM Disciplina");
            if (result.next()) {
                if (result.getInt(1) > 0) {
                    existe = true;
                }
            }
            result.close();

        return existe;
    }

    public static void printParcialDisciplinas() throws SQLException { // imprime quase todas informações de todas as disciplinas
            ResultSet result = Database.consultarResulta("SELECT codigo,nome FROM Disciplina");
            while (result.next()) {
                String nome = result.getString("nome");
                String codigo = result.getString("codigo");
                System.out.printf("\n%s - %s\n\n", codigo, nome);
            }
            result.close();

    }

    public static ResultSet getDisciplinaByCode(String codigoConexao) throws SQLException { // obtém tupla Disciplina através de codigo
        PreparedStatement consult = Database.consultarPuro(String.format("SELECT * FROM Disciplina WHERE codigo = '%s'",codigoConexao));
        ResultSet result = null;
        result = consult.executeQuery();
        consult.closeOnCompletion();
        return result;
    }

    public static void Create(String nome, String codigo, int cargaHoraria) { // cria uma tupla Disciplina 
        String sql = String.format("INSERT INTO Disciplina (nome,codigo,cargaHoraria) VALUES ('%s','%s','%d')", nome,
                codigo, cargaHoraria);
        Database.updateDB(sql);
        Notas.Create(codigo);
    }

    public static void Delete(String codigo) throws SQLException { // deleta uma tupla Disciplina
        Frequencia.Delete(codigo, null);
        Notas.Delete(codigo);

        String sqlDisciplina = String.format("DELETE FROM Disciplina WHERE codigo = '%s'", codigo);
        Database.updateDB(sqlDisciplina);
    }

    public static int getCargaHoraria(String codigo) throws SQLException { // obtém cargaHoraria de uma tupla
        int cargaHoraria = 0;
            ResultSet result = Database
                    .consultarResulta(String.format("SELECT cargaHoraria FROM Disciplina WHERE codigo = '%s'", codigo));
            result.next();
            try {
                cargaHoraria = result.getInt("cargaHoraria");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            result.close();
        return cargaHoraria;
    }
}
