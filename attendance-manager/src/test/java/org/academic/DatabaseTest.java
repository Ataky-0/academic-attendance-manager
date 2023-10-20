package org.academic;

import org.junit.gen5.api.BeforeEach;
import org.junit.gen5.api.Test;
import static org.junit.gen5.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest {

    @BeforeEach
    public void setUp() {
        Database.getConnection();
    }

    @Test
    public void testVerificaPrincipal() throws SQLException {
        String SQL = "DESCRIBE Disciplina";

        ResultSet result = Database.consultarResulta(SQL);

        assertNotNull(result);
        result.close();
    }

    @Test
    public void testGetConnection() {
        Connection connection = Database.getConnection();

        assertNotNull(connection);
    }
}
