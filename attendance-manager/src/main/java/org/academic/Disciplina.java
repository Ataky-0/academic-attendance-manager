package org.academic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static Boolean existeDisciplinas() {
        Boolean existe = false;

        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta("SELECT COUNT(*) FROM Disciplina");
            if (result.next()) {
                if (result.getInt(1) > 0) {
                    existe = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static void printParcialDisciplinas() {
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta("SELECT codigo,nome FROM Disciplina");
            while (result.next()) {
                String nome = result.getString("nome");
                String codigo = result.getString("codigo");
                System.out.printf("\n%s - %s\n\n", codigo, nome);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getDisciplinaByCode(String code) {
        try (Connection Conexao = Database.conectar()) {
            PreparedStatement consult = Database.consultarPuro("SELECT * FROM Disciplina WHERE codigo = ?");
            consult.setString(1, code);
            ResultSet result = consult.executeQuery();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void Create(String nome, String codigo, int cargaHoraria) {
        String sql = String.format("INSERT INTO Disciplina (nome,codigo,cargaHoraria) VALUES ('%s','%s','%d')", nome,
                codigo, cargaHoraria);
        Database.updateDB(sql);
    }

    public static void Delete(String codigo) {
        Frequencia.Delete(codigo, null);

        String sqlRelatorioGeral = String.format("DELETE FROM RelatorioGeral WHERE codigo = '%s'", codigo);
        Database.updateDB(sqlRelatorioGeral);

        String sqlDisciplina = String.format("DELETE FROM Disciplina WHERE codigo = '%s'", codigo);
        Database.updateDB(sqlDisciplina);
    }

    public static int getCargaHoraria(String codigo) {
        int cargaHoraria = 0;
        try (Connection Conexao = Database.conectar()) {
            ResultSet result = Database.consultarResulta(String.format("SELECT cargaHoraria FROM Disciplina WHERE codigo = '%s'",codigo));
            result.next();
            try {
                cargaHoraria = result.getInt("cargaHoraria");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
        }
        return cargaHoraria;
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
