package org.academic;

public class Aluno {
    private String nome;
    private String curso;
    private String semestre;
    private int matricula;

    // Construtor
    public Aluno(String nome, String semestre, String curso, int matricula) {
        this.nome = nome;
        this.curso = curso;
        this.semestre = semestre;
        this.matricula = matricula;
    }

    // Métodos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }


    // Método para exibir informações do aluno
    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("Curso: " + curso);
        System.out.println("Semestre: " + semestre);
        System.out.println("Matrícula: " + matricula);
    }
}