package tests;

import dunzo.entities.CoffeeMachine;
import dunzo.entities.Ingredient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CoffeeMachineInstance {

private CoffeeMachine coffeeMachine;


@Before
public void init(){
int numberOfSlots = 3;
coffeeMachine = new CoffeeMachine(numberOfSlots);

// add Ingredients
    initializeIngredients();
//add Beverages
    initializeBeverages();

}

public void initializeIngredients(){
    List<Ingredient> ingredientList = new ArrayList<>();
    ingredientList.add(new Ingredient("Hot Water",500,1000,200));
    ingredientList.add(new Ingredient("Hot Milk",500,1000,100));
    ingredientList.add(new Ingredient("Ginger Syrup",100,1000,10));
    ingredientList.add(new Ingredient("Sugar Syrup",100,1000,10));
    ingredientList.add(new Ingredient("Tea Leaves Syrup",100,1000,10));
    ingredientList.add(new Ingredient("Green Mixture",100,1000,10));
    for(Ingredient ingredient: ingredientList)
        coffeeMachine.addIngredient(ingredient);
}

public void initializeBeverages(){

    //hot tea beverage added
    Map<String,Integer> map = new HashMap<>();
    map.put("Hot Water",200);
    map.put("Hot Milk",100);
    map.put("Ginger Syrup",10);
    map.put("Sugar Syrup",10);
    map.put("Tea Leaves Syrup",30);
    coffeeMachine.addBeverage("Hot Tea",map);

    map.clear();
    //Hot coffee beverage
    map.put("Hot Water",100);
    map.put("Hot Milk",400);
    map.put("Ginger Syrup",30);
    map.put("Sugar Syrup",50);
    map.put("Tea Leaves Syrup",30);
    coffeeMachine.addBeverage("Hot Coffee",map);

    map.clear();
    //Black Tea Beverage
    map.put("Hot Water",300);
    map.put("Ginger Syrup",30);
    map.put("Sugar Syrup",50);
    map.put("Tea Leaves Syrup",30);
    coffeeMachine.addBeverage("Black Tea",map);

    map.clear();
    // green tea beverage
    map.put("Hot Water",100);
    map.put("Ginger Syrup",30);
    map.put("Sugar Syrup",50);
    map.put("Green Mixture",30);
    coffeeMachine.addBeverage("Green Tea",map);

}

@Test

    public void orderingBeveragesOneByOne(){
    System.out.println("\nExecuting test  for orderingBeveragesOneByOne\n\n");

    try{

        System.out.println("Initiated Tea preparation");
        System.out.println(coffeeMachine.makeBeverage("Hot Tea"));
    }
    catch(Exception e){
        System.out.println("Tea couldn't be prepared due to error "+ e.getMessage());

    }

    System.out.println("Checking the Ingredients after preparing tea");

    for(Ingredient ingredient: coffeeMachine.getAllIngredients())
        System.out.println(ingredient);

    try{

        System.out.println("Initiated Coffee preparation");
        System.out.println(coffeeMachine.makeBeverage("Hot Coffee"));
    }
    catch(Exception e){
        System.out.println("Hot Coffee couldn't be prepared due to error "+ e.getMessage());

    }
    System.out.println("Get all ingredients which are running low");
    System.out.println(coffeeMachine.getAllIngredientsWhichNeedToBeRefilled());

    System.out.println("Hot water remaining in the system " + coffeeMachine.getIngredientByName("Hot Water").getAmount());
    System.out.println("Refilling water by 200");
    coffeeMachine.refillIngredient("Hot Water",200);
    System.out.println("Hot water remaining in the system after refill " + coffeeMachine.getIngredientByName("Hot Water").getAmount());

    System.out.println("Ordering Hot Tea again would give an exception as ingredients are not there in required amount");

    try {
        coffeeMachine.makeBeverage("Hot Tea");
    }
    catch (Exception e){
        System.out.println("Tea couldn't be prepared due to error "+ e.getMessage());
    }


}

@Test
    public void orderingBeveragesSimultaneously(){

    System.out.println("\nExecuting test for orderingBeveragesSimultaneously \n\n");
    ExecutorService executorService = Executors.newFixedThreadPool(3);
  Future task1 =  executorService.submit(()->{
        try{
            System.out.println("Preparing Tea");
            System.out.println(coffeeMachine.makeBeverage("Hot Tea"));
        }
        catch (Exception e){
            System.out.println("Tea couldn't be prepared due to error "+ e.getMessage());
        }
    });

    Future task2 = executorService.submit(()->{
        try{
            System.out.println("Preparing Hot Coffee");
            System.out.println(coffeeMachine.makeBeverage("Hot Coffee"));
        }
        catch (Exception e){
            System.out.println("Hot Coffee couldn't be prepared due to error "+ e.getMessage());
        }
    });
    Future task3 =  executorService.submit(()->{
        try{
            System.out.println("Preparing Green Tea");
            System.out.println(coffeeMachine.makeBeverage("Green Tea"));
        }
        catch (Exception e){
            System.out.println("Green Tea couldn't be prepared due to error "+ e.getMessage());
        }
    });

    Future task4 = executorService.submit(()->{
        try{
            System.out.println("Preparing Black Tea");
            System.out.println(coffeeMachine.makeBeverage("Black Tea"));
        }
        catch (Exception e){
            System.out.println("Black Tea Tea couldn't be prepared due to error "+ e.getMessage());
        }
    });
    Future task5 = executorService.submit(()->{
        try{
            System.out.println("Preparing Tea");
            System.out.println(coffeeMachine.makeBeverage("Hot Tea"));
        }
        catch (Exception e){
            System.out.println("Tea couldn't be prepared due to error "+ e.getMessage());
        }
    });

    while(!task1.isDone() || !task2.isDone() ||
         !task3.isDone() || !task4.isDone()
                 || !task5.isDone())
    {
        //System.out.println("Waiting for all the processes to be completed");
    }
    System.out.println("Ingredients left");
    for(Ingredient ingredient:coffeeMachine.getAllIngredients())
        System.out.println(ingredient);
}

}
