package org.academic;
import java.util.Date;

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