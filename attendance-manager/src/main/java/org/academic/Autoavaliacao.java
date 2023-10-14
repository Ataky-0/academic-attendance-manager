package org.academic;

// import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Autoavaliacao {
    public static void Create(int frequencia_id, String comentario) { // criar tupla de Autoavalicao
        String sql = String.format("INSERT INTO Autoavaliacao (frequencia_id, comentario) VALUES ('%d','%s')",
                frequencia_id,
                comentario);
        Database.updateDB(sql);
    }

    public static void Update(int frequencia_id, String comentario) throws SQLException { // atualiza tupla de Autoavaliacao
            ResultSet result = Database.consultarResulta(String.format(
                    "SELECT * FROM Autoavaliacao WHERE frequencia_id = '%d'", frequencia_id));
            if (result.next()) { // verifica se existe
                String sqlFrequencia = String.format(
                        "UPDATE Autoavaliacao SET comentario = '%s' WHERE frequencia_id = '%d'", comentario,
                        frequencia_id);
                Database.updateDB(sqlFrequencia);
            }
            result.close();
    }

    public static Boolean existByFrequencia(int frequencia_id) throws SQLException { // retorna caso exista ou não Autoavaliacao baseado em sua frequencia_id
        Boolean exist = false;
            ResultSet result = Database.consultarResulta(
                    String.format("SELECT * FROM Autoavaliacao WHERE frequencia_id = '%s'", frequencia_id));
            if (result.next())
                exist = true;
            result.close();
        return exist;
    }

    public static void Delete(int frequencia_id) { // deleta uma tupla de Autoavaliacao
        String sqlAutoavaliacao = String.format("DELETE FROM Autoavaliacao WHERE frequencia_id = '%s'", frequencia_id);
        Database.updateDB(sqlAutoavaliacao);
    }

    public static String getComentario(int frequencia_id) throws SQLException { // obtém a coluna comentário de uma tupla Autoavaliacao
        String comentario = "Comentário não encontrado.";
            ResultSet result = Database.consultarResulta(
                    String.format("SELECT comentario FROM Autoavaliacao WHERE frequencia_id = '%s'", frequencia_id));
            if (result.next())
                comentario = result.getString("comentario");
            result.close();
        return comentario;
    }
}
