package ru.yandex.praktikum.auth;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class AssertsAuth {

    @Step
    public void successfulAuthentication(ValidatableResponse response, String name, String email) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .body("name", equalTo(name))
                .body("email", equalTo(email));
    }

    @Step
    public void failedAuthentication(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step
    public void successfulUpdateUser(ValidatableResponse response, String name, String email) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .body("name", equalTo(name))
                .body("email", equalTo(email));
    }

    @Step
    public void failedUpdateUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }


}
