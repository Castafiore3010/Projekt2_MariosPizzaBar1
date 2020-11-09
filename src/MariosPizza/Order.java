package MariosPizza;

import Tools.PizzaReader;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Scanner;

public class Order {
    private LocalTime time;
    private LocalTime pickUptime;
    private ArrayList<Pizza> pizzas;
    private double totalPrice;



    public Order() {
        super();
    }



    public Order(LocalTime time, LocalTime pickUptime, ArrayList<Pizza> pizzas, double totalPrice ) {
        this.time = time;
        this.pickUptime = pickUptime;
        this.pizzas = pizzas;
        this.totalPrice = totalPrice;
    }



    public ArrayList<Pizza> getPizzas() { return pizzas; }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public void showOrder() {
        String timeFormat = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        String timeFormat2 = pickUptime.format((DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        System.out.println("*******");
        System.out.println(timeFormat + " -Bestilt");
        System.out.println(timeFormat2 + " -Afhentes");
        for (Pizza element : pizzas) {
            if (element.getNumOfPizza() > 0) {
                System.out.println(element.getNumOfPizza() + "X " + element.getName().toUpperCase());
                ArrayList<Pizza> tempPizzas = new PizzaReader().loadMenu();
                int pizzaNr = new PizzaReader().searchForPizza(element.getName());
                System.out.println(tempPizzas.get(pizzaNr).ingredientsToString());
            }
        }
        String priceFormat = String.format("%,.2f", getTotalPrice());
        System.out.println(priceFormat);
        System.out.println("*******");
    }


    ArrayList<Pizza> inputOrder() {
        Scanner in = new Scanner(System.in);
        pizzas = new PizzaReader().loadMenu();

        while (true) {

            System.out.println("Indtast pizzanavn");
            String name = in.nextLine();
            while (new PizzaReader().searchForPizza(name) == -1) {
                System.out.println("Indtast venligst pizza på menuen: ");
                name = in.nextLine();
            }
            System.out.println("Antal af denne pizza?");
            int antal = in.nextInt();

            int pizzaNr = new PizzaReader().searchForPizza(name);
            double price = pizzas.get(pizzaNr).getPrice();

            pizzas.add(new Pizza(name, price, antal));


            System.out.println("Er ordren færdig? (n for at tilføje mere til ordren, y for at afslutte ordren)");
            in.nextLine();
            String orderDone = in.nextLine();

            if (orderDone.equalsIgnoreCase("y")) {
                return pizzas; 
            }
        }
    }



    double totalPrice() {
        double sum = 0.0;
        for (Pizza element : pizzas) {
            if (element.getNumOfPizza() > 0)
            sum += element.getPrice() * element.getNumOfPizza();
        }
        return sum;
    }


    public LocalTime getPickUptime() {
        return pickUptime;
    }

    public void setPickUptime(LocalTime pickUptime) {
        this.pickUptime = pickUptime;
    }


}
