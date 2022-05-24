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
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginCourierTests {

    private CourierHelper courierHelper;
    private int courierId;

    @BeforeEach
    @Step("setUp")
    public void setUp () {
        courierHelper = new CourierHelper();
        // Проброс запросов и ответов в консоль, аналог log().all()
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Description("Login courier with correct credentials")
    public void loginCourierCorrectCredentials() {
        // Get body for new courier
        Courier courier = Courier.getRandom();
        // Create courier
        courierHelper.createCourier(courier);
        // Login courier
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(CourierCredentials.getCourierCredentials(courier));

        courierId = responseLoginCourier.extract().path("id");

        responseLoginCourier.assertThat().statusCode(SC_OK);
        assertThat("Номер курьера не присвоен", courierId, notNullValue());
    }

    @Test
    @Description("Login courier with only login")
    public void loginCourierOnlyLogin() {
        Courier courier = Courier.getRandom();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials(courier.login, ""));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для входа";

        responseLoginCourier.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Фактическое сообщение в ответе отличается от ожидаемого");

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(CourierCredentials.getCourierCredentials(courier));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Description("Login courier with only password")
    public void loginCourierOnlyPassword() {
        Courier courier = Courier.getRandom();
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials("", courier.password));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Недостаточно данных для входа";

        responseLoginCourier.assertThat().statusCode(SC_BAD_REQUEST);
        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Фактическое сообщение в ответе отличается от ожидаемого");

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(CourierCredentials.getCourierCredentials(courier));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @Test
    @Description("Login courier with wrong login")
    public void loginCourierWrongLogin() {
        Courier courier = Courier.getRandom();
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(CourierCredentials.getCourierCredentials(courier));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        responseLoginCourier.assertThat().statusCode(SC_NOT_FOUND);
        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Фактическое сообщение в ответе отличается от ожидаемого");
    }

    @Test
    @Description("Login courier with wrong password")
    public void loginCourierWrongPassword() {
        Courier courier = Courier.getRandom();
        String wrongPassword = "121445324523seg1D*";
        courierHelper.createCourier(courier);
        ValidatableResponse responseLoginCourier =
                courierHelper.loginCourier(new CourierCredentials(courier.login, wrongPassword));

        String actualErrorMessage = responseLoginCourier.extract().path("message");
        String expectedErrorMessage = "Учетная запись не найдена";

        responseLoginCourier.assertThat().statusCode(SC_NOT_FOUND);
        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Фактическое сообщение в ответе отличается от ожидаемого");

        ValidatableResponse responseLoginCourierForID =
                courierHelper.loginCourier(CourierCredentials.getCourierCredentials(courier));
        courierId = responseLoginCourierForID.extract().path("id");
    }

    @AfterEach
    @Step("Teardown - delete courier")
    public void tearDown() {
        if (courierId != 0)
            courierHelper.deleteCourier(courierId);
    }
}