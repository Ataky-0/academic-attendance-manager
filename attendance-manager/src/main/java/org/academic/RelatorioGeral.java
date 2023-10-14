package org.academic;

// import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioGeral {
    public static void printDisciplinasFrequencias() { // imprime parcial de todas as disciplinas
            ResultSet result = Database.consultarResulta("SELECT codigo,nome FROM Disciplina");
            try {
                while (result.next()) {
                    String nome = result.getString("nome");
                    String codigo = result.getString("codigo");
                    int faltas = Frequencia.getFaltas(codigo);
                    int cargaHoraria = Disciplina.getCargaHoraria(codigo);
                    if (cargaHoraria == 0) {
                        System.err.println("Carga horária não encontrada.");
                        return;
                    }
                    float porcentagemDeFalta = (faltas) * 100 / Disciplina.getCargaHoraria(codigo); // cálculo de porcentagem
                    System.out.printf("\n%s - %s\n\tFaltas: %d de %d (%.2f%% | 100%%)\n\n", codigo, nome, faltas,
                            cargaHoraria, porcentagemDeFalta);
                }
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
