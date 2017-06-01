package com.sfeir.testant;

import com.example.Operation;
import com.example.ws.Response;
import com.example.ws.WebserviceAPI;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

    }

    @Test
    public void testCalculator() throws Exception {

        Operation op = new Operation();
        assertEquals(3, op.addition());


        /////////////////
        /////////////////
        //MOCK METHOD RETURN
        Operation mock = Mockito.mock(Operation.class);
        Mockito.when(mock.addition()).thenReturn(4);
        assertEquals(4, mock.addition());

        /////////////////
        /////////////////
        //MOCK SPECIFIC BEHAVIOUR FOR METHOD
        Operation test = Mockito.spy(Operation.class);
        Mockito.when(test.addition(1,1)).thenReturn(3);
        assertEquals(3, test.addition(1,1));

        //2,2 not spy
        assertEquals(4, test.addition(2,2));

        assertEquals(2, test.operation1());

        /////////////////
        /////////////////
        //mock submethods
        Operation s = Mockito.spy(Operation.class);
        Mockito.doReturn(0).when(s).addition(); // utilisable que pour spy
        Mockito.when(s.substraction()).thenReturn(0);
        assertEquals(0, s.operation1());

        /////////////////
        /////////////////
        //mock result of a main method
        Operation m = Mockito.mock(Operation.class);
        Mockito.when(m.addition()).thenReturn(1);
        Mockito.when(m.substraction()).thenReturn(1);
        //mocks below has no effects because only result count
        assertNotSame(2, m.operation1());

        //good way to use mock
        Mockito.when(m.operation1()).thenReturn(-1);
        assertEquals(-1, m.operation1());

    }


    @Test
    public void testWebservice(){

        WebserviceAPI ws = new WebserviceAPI();

        List<Response> test = ws.getCountry("FraNCE");
        assertEquals("France", test.get(0).getName());

        test = ws.getCountryFromISO2("FR");
        assertEquals("France", test.get(0).getName());

        test = ws.getCountryFromISO3("FRA");
        assertEquals("France", test.get(0).getName());


        //return USA when france is called
        WebserviceAPI mock = Mockito.spy(new WebserviceAPI());
        Mockito.doReturn(mock.getCountry("USA")).when(mock).getCountry("FRANCE");

        assertEquals("United States of America", mock.getCountry("FRANCE").get(0).getName());


    }

}