package ru.yandex.praktikum.orders;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class AssertsOrders {

    @Step
    public void creatingOrderWithAuthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step
    public void creatingOrderWithoutAuthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step
    public void creatingOrderWithIngredientsImmortalBun(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("name", equalTo("Экзо-плантаго флюоресцентный фалленианский spicy бессмертный бургер"));
    }

    @Step
    public void creatingOrderWithIncorrectHash(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("success", is(false));
    }

    @Step
    public void getOrderWithAuthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("orders._id", is(notNullValue()));
    }

    @Step
    public void getOrderWithoutAuthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }

    @Step
    public void creatingOrderWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

}

