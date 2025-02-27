package ru.yandex.praktikum.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.*;
import org.junit.*;
import ru.yandex.praktikum.auth.Authentication;
import ru.yandex.praktikum.auth.user.AuthUsers;
import ru.yandex.praktikum.helper.OrdersData;
import ru.yandex.praktikum.helper.UserData;
import ru.yandex.praktikum.orders.creatingorders.CreateOrder;
import ru.yandex.praktikum.registrations.user.UsersRegistration;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@DisplayName("Проверка создания заказов")
public class CreateOrderTest {
    private final CreateOrder createOrder = new CreateOrder();
    private final AssertsOrders assertsOrders = new AssertsOrders();
    private final OrdersData ordersData = new OrdersData();
    private static final AuthUsers authUsers = new AuthUsers();
    private static final UsersRegistration usersRegistration = new UsersRegistration();
    private static final UserData userData = new UserData();

    private static ValidatableResponse creatingUser;
    private ValidatableResponse creatingOrderUser;
    private static ValidatableResponse authRandomUser;
    private static Authentication authUserData;
    private static String userToken;
    private static String randomUserEmail;

    @BeforeClass
    public static void creatingTestUser() {
        creatingUser = usersRegistration.userRegistration(userData.randomUser());
        randomUserEmail = creatingUser.extract().path("user.email");
    }

    @Test
    @DisplayName("Создание заказа c авторизацией")
    @Description("Создание заказа c авторизацией")
    public void creatingOrderWithAuthorization() {
        authUserData = new Authentication(randomUserEmail, "12345678");
        authRandomUser = authUsers.authenticationUser(authUserData);
        userToken = authRandomUser.extract().path("accessToken");
        creatingOrderUser = createOrder.creatingOrder(ordersData.orderBunFluorescent());
        assertsOrders.creatingOrderWithAuthorized(creatingOrderUser);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Создание заказа без авторизации")
    public void creatingOrderWithoutAuthorization() {
        creatingOrderUser = createOrder.creatingOrder(OrdersData.orderBunCrater());
        assertsOrders.creatingOrderWithoutAuthorized(creatingOrderUser);
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Создание заказа с ингредиентами")
    public void creatingOrderWithIngredients() {
        creatingOrderUser = createOrder.creatingOrder(OrdersData.orderBunWithIngredientsImmortalBun());
        assertsOrders.creatingOrderWithIngredientsImmortalBun(creatingOrderUser);
    }



    @Test
    @DisplayName("Создаем заказ без ингредиентов")
    @Description("Проверяем, что код ответа 400 и сообщение ошибки")
    public void createOrderWithoutIngredients() {
        creatingOrderUser = createOrder.creatingOrder(OrdersData.createOrderWithoutIngredients());
        assertsOrders.creatingOrderWithoutIngredients(creatingOrderUser);
    }


    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Создание заказа с неверным хешем ингредиентов")
    public void creatingOrderWithIncorrectHash() {
        creatingOrderUser = createOrder.creatingOrder(OrdersData.incorrectOrderBun());
        assertsOrders.creatingOrderWithIncorrectHash(creatingOrderUser);
    }
    @AfterClass
    public static void deleteUser() {
        authUserData = new Authentication(randomUserEmail, "12345678");
        authRandomUser = authUsers.authenticationUser(authUserData);
        userToken = authRandomUser.extract().path("accessToken");
        usersRegistration.deleteUser(userToken);
    }
}

