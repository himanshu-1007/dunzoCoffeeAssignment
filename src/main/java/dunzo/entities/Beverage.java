package dunzo.entities;

import java.util.Objects;

public class Beverage {
    private String name;
    private Recipe recipe;

    public Beverage() {
    }

    public Beverage(String name, Recipe recipe) {
        this.name = name;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beverage beverage = (Beverage) o;
        return Objects.equals(name, beverage.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
