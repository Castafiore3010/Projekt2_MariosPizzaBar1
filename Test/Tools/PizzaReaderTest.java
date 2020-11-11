package Tools;

import MariosPizza.Pizza;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PizzaReaderTest {

    @Test
    void searchForPizza() {
        String name = "Vesuvio";
        String name2 = "Amerikaner";
        String name3 = "HejMedDig";
        PizzaReader reader = new PizzaReader();

        var result = reader.searchForPizza(name);
        var result2 = reader.searchForPizza(name2);
        var result3 = reader.searchForPizza(name3);

        assertTrue(result3 == -1);
        assertTrue(result == 0);

        assertEquals(1, result2);


    }
}