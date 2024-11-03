package com.ogisystems.model;

import java.util.Objects;
import java.util.Set;

public class DishCategory {

    private String name;
    private Set<Dish> dishes;

    public DishCategory(String name, Set<Dish> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishCategory that = (DishCategory) o;
        return Objects.equals(name, that.name) && Objects.equals(dishes, that.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dishes);
    }
}
