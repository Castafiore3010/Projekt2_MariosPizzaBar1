package MariosPizza;

import java.time.LocalTime;
import java.util.ArrayList;

public class Pizza {
    private String name;
    private String[] ingredients;
    private double price;
    private int numOfPizza;
    private final int TIME_TO_MAKE_PIZZA = 15;



    public Pizza(String name, String number) {
        this.name = name;
        this.numOfPizza = Integer.parseInt(number);
    }
    public Pizza(String name, String[] ingredients, double price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public Pizza(String name, double price, int numOfPizza) {
        this.name = name;
        this.price = price;
        this.numOfPizza = numOfPizza;
    }


    public String[] getIngredients() {return ingredients;}

    public String getName() {return name;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumOfPizza(int numOfPizza) {this.numOfPizza = numOfPizza; }

    public int getNumOfPizza() { return numOfPizza;}

    public String ingredientsToString() {
        String stringIngredients = "";
        for (int i = 0; i < ingredients.length; i++) {
            if (i + 1 == ingredients.length)
                stringIngredients += ingredients[i];
            else
                stringIngredients += ingredients[i] + ", ";
        }
        return stringIngredients;
    }

    public String toString() {
        String stringIngredients = "";
        for (String element : ingredients) {
            stringIngredients += element + ",";
        }
        String format = String.format("%s: %s %,.2f", name, stringIngredients, price);
        return format;
    }



}
