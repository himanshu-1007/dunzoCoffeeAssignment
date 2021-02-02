package dunzo.entities;

import java.util.Objects;

/**
 * All access to amount property should be synchronized as it could be alter by multiple threads, so need
 * to handle this race condition
 */
public class Ingredient {
    //name is unique
    private String name;

    private int amount;
    //maximum amount up to which it can be filled
    private int maxCapacity;
    // minimum amount below or at which the ingredient amount is considered low
    private int minCapacityAlarm;

    public String getName() {
        return name;
    }

    public Ingredient() {
    }

    public Ingredient(String name, int amount, int maxCapacity, int minCapacityAlarm) {
        this.name = name;
        this.amount = amount;
        this.maxCapacity = maxCapacity;
        this.minCapacityAlarm = minCapacityAlarm;
    }

    public void setName(String name) {
        this.name = name;
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized void setAmount(int amount) {
        this.amount = amount;
    }
    public synchronized void incrementAmount(int amountToBeIncremented){
        this.amount +=amountToBeIncremented;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMinCapacityAlarm() {
        return minCapacityAlarm;
    }

    public void setMinCapacityAlarm(int minCapacityAlarm) {
        this.minCapacityAlarm = minCapacityAlarm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", maxCapacity=" + maxCapacity +
                ", minCapacityAlarm=" + minCapacityAlarm +
                '}';
    }
}
