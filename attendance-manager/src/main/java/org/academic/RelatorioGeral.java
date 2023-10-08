package org.academic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatorioGeral {
    private float porcentagemDeFrequencia;
    private int indicacaoDePerformance;
    private boolean reprovaPorFalta;
    private String[] overview;

    // C
    public RelatorioGeral(float porcentagemDeFrequencia, int indicacaoDePerformance, String[] overview) {
        this.porcentagemDeFrequencia = porcentagemDeFrequencia;
        this.indicacaoDePerformance = indicacaoDePerformance;
        this.overview = overview;
    }

    public static void printDisciplinasFrequencias() {
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta("SELECT codigo,nome FROM Disciplina");
            while (result.next()) {
                String nome = result.getString("nome");
                String codigo = result.getString("codigo");
                int faltas = Frequencia.getFaltas(codigo);
                int cargaHoraria = Disciplina.getCargaHoraria(codigo);
                if (cargaHoraria == 0) {
                    System.err.println("Carga horária não encontrada.");
                    return;
                }
                // float porcentagemDeFalta = 100/Disciplina.getCargaHoraria(codigo)*(faltas*2);
                float porcentagemDeFalta = (faltas) * 100 / Disciplina.getCargaHoraria(codigo);
                System.out.printf("\n%s - %s\n\tFaltas: %d de %d (%.2f%% | 100%%)\n\n", codigo, nome, faltas,
                        cargaHoraria, porcentagemDeFalta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getPorcentagemDeFrequencia() {
        return porcentagemDeFrequencia;
    }

    public void setPorcentagemDeFrequencia(float porcentagemDeFrequencia) {
        this.porcentagemDeFrequencia = porcentagemDeFrequencia;
    }

    public int getIndicacaoDePerformance() {
        return indicacaoDePerformance;
    }

    public void setIndicacaoDePerformance(int indicacaoDePerformance) {
        this.indicacaoDePerformance = indicacaoDePerformance;
    }

    public boolean isReprovaPorFalta() {
        return reprovaPorFalta;
    }

    public void setReprovaPorFalta(boolean reprovaPorFalta) {
        this.reprovaPorFalta = reprovaPorFalta;
    }

    public String[] getOverview() {
        return overview;
    }

    public void setOverview(String[] overview) {
        this.overview = overview;
    }
}
