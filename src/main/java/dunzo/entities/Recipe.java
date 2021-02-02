package dunzo.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Recipe class is linked to Beverage. An object for recipe should not exist without beverage.
 */
public class Recipe {
    private String name;
    private Map<Ingredient,Integer> ingredientAmountMap;

    public Recipe(){
        ingredientAmountMap = new HashMap<>();
    }
    public Recipe(String name){
        this.name = name;
        ingredientAmountMap = new HashMap<>();
    }
    public void addIngredientWithAmount(Ingredient ingredient,int amount){
        ingredientAmountMap.put(ingredient, amount);
    }
    public void removeIngredient(Ingredient ingredient){
        ingredientAmountMap.remove(ingredient);
    }

    public Map<Ingredient, Integer> getIngredientAmountMap() {
        return ingredientAmountMap;
    }

    public void setIngredientAmountMap(Map<Ingredient, Integer> ingredientAmountMap) {
        this.ingredientAmountMap = ingredientAmountMap;
    }
}
