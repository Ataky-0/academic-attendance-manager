package org.academic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Autoavaliacao {
    private float autoNota;
    private String comentario;

    public Autoavaliacao(String comentario, float autoNota) {
        this.comentario = comentario;
        this.autoNota = autoNota;
    }

    public static void Create (int frequencia_id){

    }

    public static void Flush() { // Matar sem donos
        try (Connection Conexao = Database.conectar()) {
            ResultSet avaliacaoResult = Database.consultarResulta("SELECT frequenca_id FROM Autoavaliacao");
            while (avaliacaoResult.next()) {
                if(!Frequencia.doesExist(avaliacaoResult.getInt("frequencia_id")))
                    Autoavaliacao.Delete(avaliacaoResult.getInt("frequencia_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Delete(int frequencia_id) {
        String sqlAutoavaliacao = String.format("DELETE FROM Autoavaliacao WHERE frequencia_id = '%s'", frequencia_id);
        Database.updateDB(sqlAutoavaliacao);
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getAutoNota() {
        return autoNota;
    }

    public void setAutoNota(float autoNota) {
        this.autoNota = autoNota;
    }

}
