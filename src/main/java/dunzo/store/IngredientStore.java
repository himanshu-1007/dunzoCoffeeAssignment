package dunzo.store;


import dunzo.entities.Ingredient;
import java.util.ArrayList;
import java.util.List;

/**
 * This is DAO object to access all the ingredients in the machine
 */
public class IngredientStore extends GeneralStore<String,Ingredient> {
    public List<Ingredient> getIngredientsWhichNeedToBeRefilled(){

        List<Ingredient> list = new ArrayList<>();
        for(Ingredient ingredient : getAllElements()){
            if(ingredient.getAmount() <= ingredient.getMinCapacityAlarm()){
                list.add(ingredient);
            }

        }
        return list;

    }

}
