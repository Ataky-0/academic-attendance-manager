package org.academic;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Notas {

    public static void printTotal(String codigo) throws SQLException{
        ResultSet result = Database.consultarResulta(String.format("SELECT * FROM Notas WHERE codigo = '%s'", codigo));
        if (result.next()){
            float MP = getMediaParcial(codigo);
            for (int i=1; i<4; i++)
                System.out.printf("\nUnidade 0%d: %.1f\n",i,result.getFloat("nota0"+i));
            if (MP!=0f){ // Servirá para saber se precisa ou não da quarta prova
                System.out.printf("\nMédia Parcial: %.1f\n",MP);
                if (MP>=3.5f && MP<7f && !anyZero(codigo)){ // Questiona se quarta prova é necessária?
                    float quartaProva = result.getFloat("nota04");
                    System.out.printf("\nQuarta prova: %.1f",quartaProva);
                    if (quartaProva==0){
                        System.out.printf(" (Necessário %.1f)\n",(10*5-MP*6)/4);
                    } else System.out.printf("\n");
                    if (quartaProva!=0f){
                        float MF = getMediaFinal(codigo, quartaProva);
                        System.out.printf("\nMédia Final: %.1f\n",MF);
                        if (MF>=5f)
                            System.out.println("Aprovado. (Média final acima ou igual a 5)");
                        else
                            System.out.println("Reprovado. (Média final abaixo de 5)");
                    } else 
                        System.out.println("Porfavor, registre a nota da sua quarta prova.");
                } else if (MP<3.5f) {
                    System.out.println("Reprovado. (Média Parcial abaixo de 3.5)");
                } else if (MP>=7f) {
                    System.out.println("Aprovado. (Média Parcial acima ou igual a 7)");
                }
            }
        }

    }

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
        String notaString = String.format("%.1f",Nota).replace(",", ".");
        if (result.next()) { // verifica se existe
            String sqlFrequencia = String.format(
                    "UPDATE Notas SET %s = %s WHERE codigo = '%s'", notaName, notaString,
                    codigo);
            Database.updateDB(sqlFrequencia);
        }
        result.close();
    }

    public static Boolean anyZero(String codigo) throws SQLException {
        ResultSet result = Database.consultarResulta(String.format("SELECT * FROM Notas WHERE codigo = '%s'", codigo));
        if (result.next()){
            for (int i=1; i<4; i++)
                if (result.getFloat("nota0"+i) == 0f)
                    return true;
        }
        return false;
    }

    public static float getMediaParcial(String codigo) throws SQLException{
        ResultSet result = Database.consultarResulta(String.format("SELECT * FROM Notas WHERE codigo = '%s'", codigo));
        float Media = 0;
        if (result.next()){
            for (int i=1; i<4; i++)
                Media+=result.getFloat("nota0"+i);
        }
        return Media/3;

    }

    public static float getMediaFinal(String codigo, float quartaProva) throws SQLException{
        // MF=(MP X 6 + AF X 4) /10
        float Media = 0;
        Media = (getMediaParcial(codigo)*6+quartaProva*4)/10;
        return Media;
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
