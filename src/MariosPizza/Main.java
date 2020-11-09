package MariosPizza;

import Menu.GeneriskMenu;
import Tools.PizzaReader;
import Tools.Statistics;

import java.io.File;
import java.time.LocalTime;
import java.util.*;

public class Main {
    private ArrayList<Order> pizzaQueue = new ArrayList<>();
    private ArrayList<Order> finishedOrders = new ArrayList<>();


    void sortAfterPickUpTime() {
        for (int i = 1; i < pizzaQueue.size(); i++) {
            for (int j = 0; j < pizzaQueue.size(); j++) {
                Order temp = pizzaQueue.get(j);
                if (temp.getPickUptime().isAfter(pizzaQueue.get(i).getPickUptime())) {
                    temp = pizzaQueue.get(i);
                    pizzaQueue.set(i, pizzaQueue.get(j));
                    pizzaQueue.set(j, temp);

                }
            }
        }
    }

    void nextOrder() {
        sortAfterPickUpTime();
        if (pizzaQueue.size() < 1) {
            System.out.println("Ingen nuværende ordrer\n");
        } else
            pizzaQueue.get(0).showOrder();
    }

    void orderAdder() {
        Order order = new Order();
        new PizzaReader().printMenu();
        ArrayList<Pizza> pizzas = order.inputOrder();
        LocalTime timeNow = LocalTime.now();
        double totalPriceOrder = order.totalPrice();
        LocalTime pickUpTime = timeNow.plusMinutes(60);
        System.out.println("Er ordren bestilt i butikken? (y/n)");
        String orderInStore = new Scanner(System.in).nextLine();
        if (orderInStore.equalsIgnoreCase("y"))
            pickUpTime = timeNow.plusMinutes(15);
        pizzaQueue.add(new Order(timeNow, pickUpTime, pizzas, totalPriceOrder));

    }

    void run() {
        GeneriskMenu menu = new GeneriskMenu("Marios PizzaBar", "Vælg menupunkt: ",
                new String[]{"1. Se menukort", "2. Indtast bestilling", "3. Vis bestillingskø",
                        "4. Næste ordre", "5. Færdiggør ordre", "6. Statistik menu",  "9. Exit"});






        while (true) {

            menu.printGeneriskMenu();
            int choice = menu.readChoice();

            switch (choice) {
                case 1:
                    new PizzaReader().printMenu();
                    break;
                case 2:
                    orderAdder();
                    break;
                case 3:
                    sortAfterPickUpTime();
                    for (Order element : pizzaQueue)
                        element.showOrder();
                    break;
                case 4:
                    nextOrder();
                    break;
                case 5:
                    sortAfterPickUpTime();
                    System.out.println("Afslut næste ordre i køen? (y/n)");
                    String brugerSvar = new Scanner(System.in).nextLine();

                    int i = 0;
                    if (brugerSvar.equalsIgnoreCase("y")) {
                        if (pizzaQueue.size() > i)  {
                            finishedOrders.add(pizzaQueue.get(i));
                            pizzaQueue.remove(i);
                            break;
                        }
                    }
                    break;
                case 6:
                    Statistics statistics = new Statistics(finishedOrders);
                    statistics.writeToRevenue();
                    statistics.writeToPizzasByName();
                    finishedOrders.clear();

                    statistics.run();


                    break;


                case 9:
                    return;

            }
        }
    }


    public static void main(String[] args) {

        new Main().run();


    }
}
