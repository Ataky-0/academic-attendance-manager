package org.academic;

public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private String nomeDoProfessor;
    private Boolean status;
    private RelatorioGeral relatorioGeral;
    private Frequencia frequencia;

    // Construtor
    public Disciplina(String nome, String codigo, int cargaHoraria, String nomeDoProfessor,
                      Boolean status, RelatorioGeral RelatorioGeral, Frequencia frequencia) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.nomeDoProfessor = nomeDoProfessor;
        this.status = status;
        this.relatorioGeral = RelatorioGeral;
        this.frequencia = frequencia;
    }

    // Métodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNomeDoProfessor() {
        return nomeDoProfessor;
    }

    public void setNomeDoProfessor(String nomeDoProfessor) {
        this.nomeDoProfessor = nomeDoProfessor;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public RelatorioGeral getRelatorioGeral() {
        return relatorioGeral;
    }

    public void setRelatorioGeral(RelatorioGeral relatorioGeral) {
        this.relatorioGeral = relatorioGeral;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }
}
