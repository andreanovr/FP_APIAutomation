package API;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;
import io.cucumber.java.en.Given;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class main {
    String baseUrl = "https://binar-final-project-production.up.railway.app/api/auth/";

    @Given("API register")
    public void register() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \"ayamberbuludomba@gmail.com\",\n" +
                        "    \"username\": \"ayamberbuludomba\",\n" +
                        "    \"password\": \"ayamberbuludomba\",\n" +
                        "    \"fullname\": \"ayamberbuludomba\"\n" +
                        "}")
                .when().post(baseUrl+"signup")
                .then().assertThat().statusCode(200)
                .body("data", equalTo("Thanks, please check your email for activation."))
                .body("message", equalTo("sukses"))
                .body("status", equalTo("200"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/register.json")))
                .extract().response().asString();
    }

    @Given("API register send-otp")
    public void registerSendOTP() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \"ayamberkokok@gmail.com\",\n" +
                        "    \"username\": \"ayamberkokok\",\n" +
                        "    \"password\": \"ayamberkokok\",\n" +
                        "    \"fullname\": \"ayamberkokok\"\n" +
                        "}")
                .when().post(baseUrl+"send-otp")
                .then().assertThat().statusCode(200)
                .body("data", equalTo("Thanks, please check your email for activation."))
                .body("message", equalTo("sukses"))
                .body("status", equalTo("200"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/registerSendOTP.json")))
                .extract().response().asString();
    }

    @Given("API login")
    public void login() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \"andreanovr@gmail.com\",\n" +
                        "    \"password\": \"andreanovr\"\n" +
                        "}")
                .when().post(baseUrl+"login")
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("read write"))
                .body("token_type", equalTo("bearer"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/login.json")))
                .extract().response().asString();
    }

    @Given("API forgot password")
    public void forgotPassword() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\":\"andreanovr@gmail.com\"\n" +
                        "}")
                .when().post(baseUrl+"forget")
                .then().assertThat().statusCode(200)
                .body("status", equalTo("200"))
                .body("message", equalTo("sukses"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/forgotPassword.json")))
                .extract().response().asString();
    }

    @Given("API change password")
    public void changePassword() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"email\": \"andreanovr@gmail.com\",\n" +
                        "  \"otp\": \"883531\",\n" +
                        "  \"newPassword\": \"andreanovr\"\n" +
                        "}")
                .when().post(baseUrl+"change-password")
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/changePassword.json")))
                .extract().response().asString();
    }

    @Given("API confirm email")
    public void confirmEmail() {
        given().log().all()
                .header("Content-Type", "application/json")
                .when().get(baseUrl+"index/112")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
    }
}
