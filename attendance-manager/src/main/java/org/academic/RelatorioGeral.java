package org.academic;

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
