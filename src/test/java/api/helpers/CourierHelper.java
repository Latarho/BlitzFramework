package api.helpers;

import api.common.EndPoints;
import api.pojo.courier.Courier;
import api.pojo.courier.CourierCredentials;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierHelper extends RestAssuredHelper {
    @Step("Create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post(EndPoints.CREATE_COURIER)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .and()
                .body(credentials)
                .when()
                .post(EndPoints.LOGIN_COURIER)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(Object courierId) {
        return given()
                .filter(new AllureRestAssured())
                .spec(getBaseSpec())
                .when()
                .delete(EndPoints.DELETE_COURIER + courierId)
                .then();
    }
}