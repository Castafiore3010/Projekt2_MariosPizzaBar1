package Tools;

import MariosPizza.Order;
import MariosPizza.Pizza;
import Menu.GeneriskMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Statistics {
    private FileWriter fileWriter;
    private Scanner fileReader;
    private ArrayList<String> earlierProcessedOrdersPizzaNames = new ArrayList<>();
    private ArrayList<String> earlierProcessedOrdersRevenue = new ArrayList<>();
    private ArrayList<Order> processedOrders = new ArrayList<>();
    private File accumRevenue;
    private File pizzasSoldByName;

    // Konstruktører
    public Statistics() {
        super();
    }
    public Statistics(ArrayList<Order> processedOrders) {
        this.processedOrders = processedOrders;
    }

    // Run metode der håndterer stastistik menuen.
    public void run() {
        GeneriskMenu menu = new GeneriskMenu("Statistik for Marios PizzaBar", "Vælg menupunkt: ",
                new String[]{"1. Samlet omsætning", "2. Top 3 mest solgte", "3. Omsætning pr pizza",
                        "9. Returnér til hovedmenu"});

        while (true) {

            menu.printGeneriskMenu();
            int choice = menu.readChoice();

            switch (choice) {
                case 1:
                    System.out.println(totalRevenue());
                    break;
                case 2:
                    mostSold();
                    break;
                case 3:
                    mostSoldByRevenue();
                    break;

                case 9:
                    return;

            }
        }
    }

    // Indlæser total omsætning fra filen accumRevenue.txt
    public ArrayList<String> loadAccumRevenue() {
        try {
            fileReader = new Scanner(new File("accumRevenue.txt"));
            while (fileReader.hasNext()) {
                earlierProcessedOrdersRevenue.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return earlierProcessedOrdersRevenue;
    }


    // Indlæser Pizza og antal af denne, og returnerer en ArrayList af strings.
    public ArrayList<String> loadPizzasByName() {
        try {
            fileReader = new Scanner(new File("pizzasByName.txt"));
            while (fileReader.hasNext()) {
                earlierProcessedOrdersPizzaNames.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return earlierProcessedOrdersPizzaNames;

    }

    // Skriver pizza og antal af pizza til filen PizzasByName.txt
    public void writeToPizzasByName() {
        pizzasSoldByName = new File("pizzasByName.txt");
        earlierProcessedOrdersPizzaNames = loadPizzasByName();
        try {
            fileWriter = new FileWriter(pizzasSoldByName);

            for (String names : earlierProcessedOrdersPizzaNames) {
                fileWriter.write(names + "\n");
            }
            for (Order element : processedOrders) {
                ArrayList<Pizza> pizzas = element.getPizzas();
                for (Pizza pizza : pizzas) {
                    if (pizza.getNumOfPizza() > 0)
                    fileWriter.write(pizza.getName().toUpperCase() + "\n" + pizza.getNumOfPizza() + "\n");
                }
            }
            fileWriter.flush();
            fileWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Skriver omsætning pr ordre til filen accumRevenue.txt
    public void writeToRevenue() {
        accumRevenue = new File("accumRevenue.txt");
        earlierProcessedOrdersRevenue = loadAccumRevenue();
        try {
            fileWriter = new FileWriter(accumRevenue);

            for (String revenues : earlierProcessedOrdersRevenue) {
                fileWriter.write(revenues + "\n");
            }
            for (Order element : processedOrders) {
                fileWriter.write(String.valueOf(element.getTotalPrice()) + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sorterer en given liste efter numOfSales
    public void sortAfterNumOfSales(ArrayList<Pizza> pizzas) {
        for (int i = 1; i < pizzas.size(); i++) {
            for (int j = 0; j < pizzas.size(); j++) {
                Pizza temp = pizzas.get(j);
                if (temp.getNumOfPizza() < pizzas.get(i).getNumOfPizza()) {
                    temp = pizzas.get(i);
                    pizzas.set(i, pizzas.get(j));
                    pizzas.set(j, temp);
                }
            }
        }
    }

    // Modtager en liste af pizze, og en String - og returnerer antallet af salg af det givne pizzanavn.
    public int totalNumOfSales(ArrayList<Pizza> pizzas, String name) {
        int count = 0;
        for (Pizza element : pizzas) {
            if (element.getName().equalsIgnoreCase(name))
                count += element.getNumOfPizza();
        }
        return count;
    }

    // Viser top 3 mest solgte pizze
    public void mostSold() {
        PizzaReader pizzaReader = new PizzaReader();
        ArrayList<Pizza> pizzaMenu = pizzaReader.loadMenu();
        ArrayList<Pizza> totalOrders = new ArrayList<>();
        ArrayList<Pizza> totalPrPizza = new ArrayList<>();


        pizzasSoldByName = new File("pizzasByName.txt");
        try {
            fileReader = new Scanner(pizzasSoldByName);
            while (fileReader.hasNext()) {
                String name = fileReader.nextLine();
                String number = fileReader.nextLine();
                totalOrders.add(new Pizza(name, number));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Pizza element : pizzaMenu) {
            int pizzaNr = pizzaReader.searchForPizza(element.getName());
            totalPrPizza.add(pizzaNr, pizzaMenu.get(pizzaNr));
        }

        for (int k = 0; k < totalOrders.size(); k++) {
            int pizzaNr = pizzaReader.searchForPizza(totalOrders.get(k).getName());
            totalPrPizza.get(pizzaNr).setNumOfPizza(totalNumOfSales(totalOrders, totalOrders.get(k).getName()));
        }

       sortAfterNumOfSales(totalPrPizza);
        for (int i = 0; i < 3; i++) {
            if (totalPrPizza.get(i).getNumOfPizza() > 0)
            System.out.println(totalPrPizza.get(i).getName() + " x" + totalPrPizza.get(i).getNumOfPizza());
        }

    }

    // Viser alle solgte pizze, mest solgte øverst etc. Minder meget om ovenstående metode,
    // denne metode tilføjer dog også omæstning pr pizza.
    //
    public void mostSoldByRevenue() {
        PizzaReader pizzaReader = new PizzaReader();
        ArrayList<Pizza> pizzaMenu = pizzaReader.loadMenu();
        ArrayList<Pizza> totalOrders = new ArrayList<>();
        ArrayList<Pizza> totalPrPizza = new ArrayList<>();


        pizzasSoldByName = new File("pizzasByName.txt");
        try {
            fileReader = new Scanner(pizzasSoldByName);
            while (fileReader.hasNext()) {
                String name = fileReader.nextLine();
                String number = fileReader.nextLine();
                totalOrders.add(new Pizza(name, number));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Pizza element : pizzaMenu) {
            int pizzaNr = pizzaReader.searchForPizza(element.getName());
            totalPrPizza.add(pizzaNr, pizzaMenu.get(pizzaNr));
        }

        for (int k = 0; k < totalOrders.size(); k++) {
            int pizzaNr = pizzaReader.searchForPizza(totalOrders.get(k).getName());
            totalPrPizza.get(pizzaNr).setNumOfPizza(totalNumOfSales(totalOrders, totalOrders.get(k).getName()));
        }

        sortAfterNumOfSales(totalPrPizza);
        for (Pizza pizzas : totalPrPizza) {
            if (pizzas.getNumOfPizza() > 1)
                System.out.println(String.format("%s x%d: %,.2f,-", pizzas.getName(), pizzas.getNumOfPizza(),
                        pizzas.getNumOfPizza() * pizzas.getPrice()));
        }

    }

    // Læser filen accumRevenue og adderer alle værdierne i denne og returnerer denne værdi.
    public double totalRevenue() {
        accumRevenue = new File("accumRevenue.txt");
        double sum = 0.0;
        try {
            fileReader = new Scanner(accumRevenue);
            while (fileReader.hasNext()) {
                double sumHelp= Double.parseDouble(fileReader.nextLine());
               sum += sumHelp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sum;
    }





}
