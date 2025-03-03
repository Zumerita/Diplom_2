package ru.yandex.praktikum.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.auth.Authentication;
import ru.yandex.praktikum.auth.user.AuthUsers;
import ru.yandex.praktikum.helper.OrdersData;
import ru.yandex.praktikum.helper.UserData;
import ru.yandex.praktikum.orders.creatingorders.CreateOrder;
import ru.yandex.praktikum.orders.getorders.GetOrders;
import ru.yandex.praktikum.registrations.user.UsersRegistration;

@DisplayName("Проверка получения заказов пользователя")
public class GetOrdersTest {
    private final GetOrders getOrders = new GetOrders();
    private static final AuthUsers authUsers = new AuthUsers();
    private final CreateOrder createOrder = new CreateOrder();
    private final AssertsOrders assertsOrders = new AssertsOrders();
    private static final UsersRegistration usersRegistration = new UsersRegistration();
    private final UserData userData = new UserData();
    private final OrdersData ordersData = new OrdersData();
    private ValidatableResponse creatingUser;
    private static ValidatableResponse authRandomUser;
    private ValidatableResponse getOrdersForUser;
    private static Authentication authUserData;
    private static String userToken;
    private static String randomUserEmail;

    @Before
    public void creatingTestUser() {
        creatingUser = usersRegistration.userRegistration(userData.randomUser());
        randomUserEmail = creatingUser.extract().path("user.email");
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Получение заказов авторизованного пользователя")
    public void creatingOrderWithAuthorization() {
        authUserData = new Authentication(randomUserEmail, "12345678");
        authRandomUser = authUsers.authenticationUser(authUserData);
        userToken = authRandomUser.extract().path("accessToken");
        createOrder.creatingOrder(ordersData.orderBunWithIngredientsImmortalBun());
        getOrdersForUser = getOrders.GetOrdersForUserWithAuthorization(userToken);
        assertsOrders.getOrderWithAuthorized(getOrdersForUser);

    }
    @Test
    @DisplayName("Получение заказов не авторизованного пользователя")
    @Description("Получение заказов не авторизованного пользователя")
    public void creatingOrderWithoutAuthorization() {
        authUserData = new Authentication(randomUserEmail, "12345678");
        authRandomUser = authUsers.authenticationUser(authUserData);
        userToken = authRandomUser.extract().path("accessToken");
        createOrder.creatingOrder(ordersData.orderBunWithIngredientsImmortalBun());
        getOrdersForUser = getOrders.GetOrdersForUserWithAuthorization("");
        assertsOrders.getOrderWithoutAuthorized(getOrdersForUser);
    }

    @AfterClass
    public static void deleteUser() {
        authUserData = new Authentication(randomUserEmail, "12345678");
        authRandomUser = authUsers.authenticationUser(authUserData);
        userToken = authRandomUser.extract().path("accessToken");
        usersRegistration.deleteUser(userToken);
    }
}
