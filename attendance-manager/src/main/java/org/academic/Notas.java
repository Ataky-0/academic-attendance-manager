package org.academic;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Notas {
    public static void Create(String codigo) { // cria uma tupla Notas
        String sql = String.format("INSERT INTO Notas (codigo) VALUES ('%s')",
                codigo);
        Database.updateDB(sql);
    }

    public static void Delete(String codigo) throws SQLException { // deleta uma tupla Notas
        Frequencia.Delete(codigo, null);
        String sqlDisciplina = String.format("DELETE FROM Notas WHERE codigo = '%s'", codigo);
        Database.updateDB(sqlDisciplina);
    }

    public static void Update(String codigo, int Pos, float Nota) throws SQLException {
        String notaName = getNotaByPos(Pos);
        ResultSet result = Database.consultarResulta(String.format(
                "SELECT * FROM Notas WHERE codigo = '%s'", codigo));
        if (result.next()) { // verifica se existe
            String sqlFrequencia = String.format(
                    "UPDATE Notas SET '%s' = '%f' WHERE codigo = '%s'", notaName, Nota,
                    codigo);
            Database.updateDB(sqlFrequencia);
        }
        result.close();
    }

    public static String getNotaByPos(int Pos) {
        String notaName = null;
        switch (Pos) {
            case 1:
                notaName = "nota01";
                break;
            case 2:
                notaName = "nota02";
                break;
            case 3:
                notaName = "nota03";
                break;
            case 4:
                notaName = "nota04";
                break;
            default:
                System.err.println("Não existe esta unidade ou avaliação.");
                break;
        }
        return notaName;
    }
}
