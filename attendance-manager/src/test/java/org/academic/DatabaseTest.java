package org.academic;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;

import junit.framework.Assert;

import static org.junit.gen5.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatabaseTest {
    Random random = new Random();
    int intCod = random.nextInt(10000);
    String codigo = "Teste"+intCod;
    ResultSet result;

    @BeforeEach
    public void setUp() {
        Database.getConnection();
        result = null;
    }

    @Test
    public void testVerificaPrincipal() throws SQLException {
        String SQL = "DESCRIBE Disciplina";

        result = Database.consultarResulta(SQL);

        assertNotNull(result);
    }

    @Test
    public void testGetConnection() {
        Connection connection = Database.getConnection();

        assertNotNull(connection);
    }

    @Test
    public void testPresencaFaltas() throws ParseException, SQLException{
        Connection connection = Database.getConnection();
        assertNotNull(connection);
        // disciplina
        Disciplina.Create("TesteTesteTeste", codigo, 1);
        
        // atribuir uma frequencia à esta disciplina
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        String dateSimulado = "2002/07/29";
        Date date = formatoData.parse(dateSimulado);
        Frequencia.Create(date, 0, codigo, 2);
        // em 29 do 7 de 2002, foi registrado uma falta completa na disciplina TesteTesteTeste

        // verificar se a frequencia corresponde com os valores inseridos
        String sql = String.format("SELECT data, presencaAusencia, faltas FROM Frequencia WHERE codigo = '%s'",codigo);
        ResultSet result = Database.consultarResulta(sql);
        try {
            if (result.next()){
                Assert.assertEquals(date, result.getDate("data"));
                Assert.assertEquals(0, result.getInt("presencaAusencia"));
                Assert.assertEquals(2, result.getInt("faltas"));
            } else
                Assert.fail("Criação de uma frequência não foi possível.");
        } catch (SQLException e) {
            System.err.println(e);
        } catch (AssertionError e) {
            System.err.println(e);
        } finally {
            Disciplina.Delete(codigo);
        }
    }

    @Test
    public void testAutoavaliacao() throws ParseException, SQLException {
        Connection connection = Database.getConnection();
        assertNotNull(connection);
        // disciplina
        Disciplina.Create("TesteTesteTeste", codigo, 1);
        
        // atribuir uma frequencia à esta disciplina
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        String dateSimulado = "2002/07/29";
        Date date = formatoData.parse(dateSimulado);
        Frequencia.Create(date, 0, codigo, 2);
        // em 29 do 7 de 2002, foi registrado uma falta completa na disciplina TesteTesteTeste

        // atribuir uma autoavaliacao á esta frequencia
        int frequencia_id = Frequencia.getId(codigo, date);
        Autoavaliacao.Create(frequencia_id, "TesteTeste");

        // verificar se a autoavalicao corresponde com os valores inseridos
        String sql = String.format("SELECT comentario FROM Autoavaliacao WHERE frequencia_id = '%s'", frequencia_id);
        ResultSet result = Database.consultarResulta(sql);
        try {
            if (result.next()){
                Assert.assertEquals(frequencia_id, result.getInt("frequencia_id"));
                Assert.assertEquals("TesteTeste", result.getString("comentario"));
            } else
                Assert.fail("Criação de uma autoavaliaçã não foi possível.");
        } catch (SQLException e) {
            System.err.println(e);
        } catch (AssertionError e) {
            System.err.println(e);
        } finally {
            Disciplina.Delete(codigo);
        }    
    }

}
