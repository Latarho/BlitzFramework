package api.tests;

import api.helpers.CourierHelper;
import api.pojo.courier.Courier;
import api.pojo.courier.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCourierTests {

    private CourierHelper courierHelper;
    private int courierId;

    @BeforeEach
    @Step("setUp")
    public void setUp() {
        courierHelper = new CourierHelper();
        // Проброс запросов и ответов в консоль, аналог log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Tag("api")
    @Description("Create courier with all fields (login, password, firstname)")
    public void createCourierLoginPasswordAndFirstName() {
        Courier courier = Courier.getRandom();
        ValidatableResponse response = courierHelper.createCourier(courier);

        boolean isCourierCreated = response.extract().path("ok");

        response.assertThat().statusCode(SC_CREATED);
        assertTrue(isCourierCreated);

        CourierCredentials courierCredentials = CourierCredentials.getCourierCredentials(courier);
        ValidatableResponse responseLoginCourierForID = courierHelper.loginCourier(courierCredentials);
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Tag("api")
    @Description("Create courier with required fields (login, password)")
    public void createCourierLoginAndPassword() {
        Courier courier = Courier.getRandomWithLoginAndPassword();
        ValidatableResponse response = courierHelper.createCourier(courier);

        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertThat(statusCode, equalTo(SC_CREATED));
        assertTrue(isCourierCreated);

        CourierCredentials courierCredentials = CourierCredentials.getCourierCredentials(courier);
        ValidatableResponse responseLoginCourierForID = courierHelper.loginCourier(courierCredentials);
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Tag("api")
    @Description("Create courier with only login")
    public void createCourierLoginOnly() {
        Courier courier = Courier.getRandomWithLogin();
        ValidatableResponse response = courierHelper.createCourier(courier);

        int statusCode = response.extract().statusCode();
        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для создания учетной записи";

        assertThat(statusCode, equalTo(SC_BAD_REQUEST));
        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Фактическое сообщение в ответе отличается от ожидаемого");
    }

    @Test
    @Tag("api")
    @Description("Create courier with same login")
    public void createCourierSameLogin() {
        Courier courier = Courier.getRandom();
        new CourierHelper().createCourier(courier);
        ValidatableResponse response = courierHelper.createCourier(courier);

        int statusCode = response.extract().statusCode();
        String actualErrorMessage = response.extract().path("message");
        String expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";

        assertThat(statusCode, equalTo(SC_CONFLICT));
        assertEquals(expectedErrorMessage, actualErrorMessage,
                "Фактическое сообщение в ответе отличается от ожидаемого");

        CourierCredentials courierCredentials = CourierCredentials.getCourierCredentials(courier);
        ValidatableResponse responseLoginCourierForID = courierHelper.loginCourier(courierCredentials);
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @AfterEach
    @Step("Teardown - delete courier")
    public void tearDown() {
        if (courierId != 0)
            courierHelper.deleteCourier(courierId);
    }
}