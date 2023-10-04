package org.academic;

public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private String nomeDoProfessor;
    private Status status;
    private RelatorioDisciplina relatorioDisciplina;
    private RelatorioRisco relatorioRisco;
    private Frequencia frequencia;

    // Construtor
    public Disciplina(String nome, String codigo, int cargaHoraria, String nomeDoProfessor,
                      Status status, RelatorioDisciplina relatorioDisciplina,
                      RelatorioRisco relatorioRisco, Frequencia frequencia) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.nomeDoProfessor = nomeDoProfessor;
        this.status = status;
        this.relatorioDisciplina = relatorioDisciplina;
        this.relatorioRisco = relatorioRisco;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RelatorioDisciplina getRelatorioDisciplina() {
        return relatorioDisciplina;
    }

    public void setRelatorioDisciplina(RelatorioDisciplina relatorioDisciplina) {
        this.relatorioDisciplina = relatorioDisciplina;
    }

    public RelatorioRisco getRelatorioRisco() {
        return relatorioRisco;
    }

    public void setRelatorioRisco(RelatorioRisco relatorioRisco) {
        this.relatorioRisco = relatorioRisco;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }
}
