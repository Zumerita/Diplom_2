package ru.yandex.praktikum.orders;

import java.util.List;

public class Orders {

    private List<String> ingredientsIds;
    private List<String> ingredients;

    public Orders() {
    }

    public Orders(List<String> ingredients) {

        this.ingredients = ingredients;
    }

    public List<String> getIngredientsIds() {
        return ingredientsIds;
    }

    public void setIngredientsIds(List<String> ingredientsIds) {
        this.ingredientsIds = ingredientsIds;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}

