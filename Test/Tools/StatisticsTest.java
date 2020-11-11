package Tools;

import MariosPizza.Order;
import MariosPizza.Pizza;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {

    @Test
    void totalNumOfSales() {
        ArrayList<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza("Mafia", "2"));
        pizzas.add(new Pizza("Vesuvio", "4"));

        var result = 2;

        assertTrue(result == new Statistics().totalNumOfSales(pizzas, "Mafia"));
        assertEquals(4, new Statistics().totalNumOfSales(pizzas, "Vesuvio"));

    }

    @Test
    void totalRevenue() {

        var result = 0.0;

        assertTrue(result == new Statistics().totalRevenue());
        assertEquals(0.0, new Statistics().totalRevenue());

    }
}