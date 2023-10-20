package org.academic;

import static org.junit.gen5.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.gen5.api.Test;

public class MenuTest {
    @Test
    public void testGetUserFloat(){
        String floatSimulado = "5.5\n";
        InputStream inputStream = new ByteArrayInputStream(floatSimulado.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        float userFloat = Menu.getUserFloat(scanner, 1, 10);
        Float userFloatObj = userFloat;
        Float expectedFloat = 5.5f;
        assertEquals(expectedFloat, userFloatObj);
        System.setIn(System.in);
        scanner.close();
    }

    @Test
    public void testGetUserChoice(){
        String intSimulado = "1\n";
        InputStream inputStream = new ByteArrayInputStream(intSimulado.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        int userInt = Menu.getUserChoice(scanner, 1, 2);
        Integer userIntObj = userInt;
        Integer expectedInt = 1;
        assertEquals(expectedInt, userIntObj);
        System.setIn(System.in);
        scanner.close();
    }

    @Test
    public void testGetDate() throws ParseException{
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        String dateSimulado = "2002/07/29\n";
        InputStream inputStream = new ByteArrayInputStream(dateSimulado.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        Date userDate = Menu.getDate(scanner);
        Date expectedDate = formatoData.parse(dateSimulado);
        assertEquals(expectedDate, userDate);
        System.setIn(System.in);
        scanner.close();
    }
    
}
