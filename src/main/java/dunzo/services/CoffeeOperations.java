package dunzo.services;

import dunzo.entities.Beverage;
import dunzo.entities.Ingredient;
import dunzo.entities.Recipe;
import dunzo.enums.ExceptionEnum;
import dunzo.exceptions.InsufficientIngredient;
import dunzo.exceptions.InvalidDataException;
import dunzo.exceptions.MaxCapicityBreachedException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoffeeOperations {

    /**
     * This method is user for serving beverages.
     * As there could be more than one beverage to be served parallelly,
     * so ingredient object is synchronized to have consistent state
     * @param beverage
     * @return
     */
    public String makeBeverage(Beverage beverage) {
        if (null == beverage)
            throw new InvalidDataException(ExceptionEnum.INVALID_DATA);
        Recipe recipe = beverage.getRecipe();
        Set<Map.Entry<Ingredient, Integer>> entrySet = recipe.getIngredientAmountMap().entrySet();
        Set<Map.Entry<Ingredient, Integer>> entrySetVisited = new HashSet<>();
        for (Map.Entry<Ingredient, Integer> entry : entrySet) {
            synchronized (entry.getKey()) {

                int amountAvailable = entry.getKey().getAmount();
                int amountToBeDeducted = entry.getValue();
                if (amountToBeDeducted <= amountAvailable)
                    entry.getKey().incrementAmount(-1 * amountToBeDeducted);
                else
                    break;


            }

            entrySetVisited.add(entry);


        }
        if (entrySet.size() != entrySetVisited.size()) {
            for (Map.Entry<Ingredient, Integer> entry : entrySetVisited) {
                synchronized (entry.getKey()) {

                    int amountAvailable = entry.getKey().getAmount();
                    int amountToBeAdded = entry.getValue();
                    entry.getKey().incrementAmount(amountToBeAdded);


                }

            }
            throw new InsufficientIngredient(ExceptionEnum.INSUFFICIENT_INGREDIENT);
        }
        return beverage.getName()+" is prepared";
    }

    /**
     * This method is used to refill the ingredient by a specfic amount
     * @param ingredient
     * @param amountToBeRefilled
     */
    public void refillIngredient(Ingredient ingredient,int amountToBeRefilled){
        if(null == ingredient)
            throw new InvalidDataException(ExceptionEnum.INVALID_DATA);
        synchronized (ingredient){
            int newAmount = ingredient.getAmount()+amountToBeRefilled;
            if(newAmount> ingredient.getMaxCapacity())
                throw new MaxCapicityBreachedException(ExceptionEnum.MAX_CAPACITY_BREACHED_EXCEPTION);
            ingredient.setAmount(newAmount);
        }
    }
}
