package dunzo.entities;


import dunzo.enums.ExceptionEnum;
import dunzo.exceptions.NoSlotsAvailableException;
import dunzo.services.CoffeeOperations;
import dunzo.store.BeveragesStore;
import dunzo.store.IngredientStore;

import java.util.List;
import java.util.Map;

/**
 * It contains all the methods by which user would interact with the system
 * Beverage has a recipe
 * Recipe contains all the ingredients with amount required
 * As there are n slots available, so slotsAvailable property maintain the number of slot available.
 * As this property would be altered by multiple threads, so this must be sunchronized
 */
public class CoffeeMachine {

    private int numberOfSlots;
    private int slotsAvailable;
    private final BeveragesStore beveragesStore;
    private final IngredientStore ingredientStore;
    private CoffeeOperations coffeeOperations;


    public CoffeeMachine(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
        this.slotsAvailable = numberOfSlots;
        beveragesStore = new BeveragesStore();
        ingredientStore = new IngredientStore();
        this.coffeeOperations = new CoffeeOperations();
    }

    public synchronized int  getSlotsAvailable() {
        return slotsAvailable;
    }

    public synchronized void decrementSlots(){
        if(getSlotsAvailable() ==0)
            throw new NoSlotsAvailableException(ExceptionEnum.NO_SLOTS_AVAILABLE);
        slotsAvailable--;
    }
    public synchronized void incrementSlots(){
    slotsAvailable++;
    }


    public List<Ingredient> getAllIngredients(){
         return ingredientStore.getAllElements();
    }
    public List<Beverage> getAllBeverages(){
        return beveragesStore.getAllElements();
    }
    public String makeBeverage(String name){
        try{
        decrementSlots();
        Beverage beverage = beveragesStore.getByKey(name);
        String result = coffeeOperations.makeBeverage(beverage);
        return result;
        }
        catch (Exception e){
            throw e;
        }
        finally {
            incrementSlots();
        }
    }
    public List<Ingredient> getAllIngredientsWhichNeedToBeRefilled(){
        return ingredientStore.getIngredientsWhichNeedToBeRefilled();
    }
    public void refillIngredient(String name,int amountToBeRefilled){
        Ingredient ingredient = ingredientStore.getByKey(name);
        coffeeOperations.refillIngredient(ingredient,amountToBeRefilled);
    }
    public void addIngredient(Ingredient ingredient){
        ingredientStore.addElement(ingredient.getName(),ingredient);
    }
    public void addBeverage(String name, Map<String,Integer> recipeMap){
        Recipe recipe = new Recipe(name+" Recipe");
        for(Map.Entry<String,Integer> entry : recipeMap.entrySet())
            recipe.addIngredientWithAmount(ingredientStore.getByKey(entry.getKey()),entry.getValue());
        beveragesStore.addElement(name,new Beverage(name,recipe));
    }
    public Ingredient getIngredientByName(String name){
        return ingredientStore.getByKey(name);
    }



}
