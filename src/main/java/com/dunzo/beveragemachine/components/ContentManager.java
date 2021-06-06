package com.dunzo.beveragemachine.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*

 * Manages contents for the machine
 * has methods to remove/add contents
 * has methods to get contents for a beverage syncronously and subtract the quantity from the machine's content store

 */

public class ContentManager {

    //contents and their quantities present in the machine
    private final Map<String, Integer> machineContents = new HashMap<>();
    //ingredients required in ech beverage
    private final Map<String, Map<String, Integer>> ingredients = new HashMap<>();


    public void addBeverages(Map<String, Map<String, Integer>> beverages) {
        beverages.entrySet().forEach(entry -> addBeverage(entry.getKey(), entry.getValue()));
    }

    public void addBeverage(String name, Map<String, Integer> ing) {
        ingredients.put(name, new HashMap<>(ing));
    }


    public synchronized void addContents(Map<String, Integer> contents) {
        contents.entrySet().forEach(entry -> addContent(entry.getKey(), entry.getValue()));
    }


    public synchronized void addContent(String ing, int quantity) {
        machineContents.put(ing, machineContents.getOrDefault(ing, 0) + quantity);
    }

    private void removeContent(String ing, int quantity) {
        machineContents.put(ing, machineContents.get(ing) - quantity);
    }

    private void removeContents(Map<String, Integer> contents) {
        for (Map.Entry<String, Integer> entry : contents.entrySet()) {
            removeContent(entry.getKey(), entry.getValue());
        }
    }


    public IngredientAndStatusPair checkContentsSufficient(Map<String, Integer> ingredients) {

        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            IngredientAndStatusPair pair = checkContentSufficient(entry.getKey(), entry.getValue());
            if (pair.status == ContentStatus.INSUFFICIENT)
                return pair;
        }

        return new IngredientAndStatusPair("all", ContentStatus.AVAILABLE);

    }


    public IngredientAndStatusPair checkContentSufficient(String ingredient, int quantity) {

        if (machineContents.getOrDefault(ingredient, 0) >= quantity)
            return new IngredientAndStatusPair(ingredient, ContentStatus.AVAILABLE);
        else
            return new IngredientAndStatusPair(ingredient, ContentStatus.INSUFFICIENT);

    }

    public IngredientAndStatusPair checkContentsPresent(Set<String> ingredients) {

        for (String ing : ingredients) {
            IngredientAndStatusPair pair = checkContentPresent(ing);
            if (pair.status == ContentStatus.UNAVAILABLE)
                return pair;
        }

        return new IngredientAndStatusPair("all", ContentStatus.AVAILABLE);

    }

    public IngredientAndStatusPair checkContentPresent(String ingredient) {
        if (machineContents.containsKey(ingredient))
            return new IngredientAndStatusPair(ingredient, ContentStatus.AVAILABLE);
        else
            return new IngredientAndStatusPair(ingredient, ContentStatus.UNAVAILABLE);
    }


    //returns the contents required by the beverage if all the present in sufficient quantity
    public String getBeverageContents(String beverage) {

        //check if a content isnt at all in the machine, multiple threads can do this as contents are not modified
        Map<String, Integer> beverageIngredients = ingredients.get(beverage);
        if (beverageIngredients == null)
            return "No such beverage available";

        IngredientAndStatusPair allPresentOrNot = checkContentsPresent(beverageIngredients.keySet());
        if (allPresentOrNot.status == ContentStatus.UNAVAILABLE)
            return beverage + " cannot be prepared because " + allPresentOrNot.ingredient + " is " + allPresentOrNot.status;

        synchronized (ingredients) {

            //check sufficient quantity of each ingredient
            IngredientAndStatusPair allSufficientOrNot = checkContentsSufficient(beverageIngredients);
            if (allSufficientOrNot.status == ContentStatus.INSUFFICIENT)
                return beverage + " cannot be prepared because " + allSufficientOrNot.ingredient + " is " + allSufficientOrNot.status;

            //if all contents are present , remove from machine and return
            removeContents(beverageIngredients);
            return beverage + " is prepared";

        }

    }

    public int getQuantity(String ingredient) {
        return machineContents.getOrDefault(ingredient, 0);
    }


    @Override
    public String toString() {
        return "ContentManager{" +
                "machineContents=" + machineContents +
                "beverages=" + ingredients +
                '}';
    }
}
