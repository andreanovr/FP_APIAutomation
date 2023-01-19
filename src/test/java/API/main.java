package API;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;
import io.cucumber.java.en.Given;
import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class main extends config {

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
                .when().post(baseUrlBE+"signup")
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
                .when().post(baseUrlBE+"send-otp")
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
                .when().post(baseUrlBE+"login")
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
                .when().post(baseUrlBE+"forget")
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
                .header("Content-Type", "application/json") // butuh get otp dari email
                .body("{\n" +
                        "  \"email\": \"andreanovr@gmail.com\",\n" +
                        "  \"otp\": \"883531\",\n" +
                        "  \"newPassword\": \"andreanovr\"\n" +
                        "}")
                .when().post(baseUrlBE+"change-password")
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/changePassword.json")))
                .extract().response().asString();
    }

    @Given("API confirm email")
    public void confirmEmail() {
        given().log().all()
                .header("Content-Type", "application/json")
                .when().get(baseUrlBE+"index/112")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
    }

    @Given("API Get All Room")
    public void getAllRoom() {
        given().log().all()
                .header("Content-Type", "application/json")
                .when().get(baseUrlFSW)
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/getAllRoom.json")))
                .extract().response().asString();
    }

    @Given("API Get Room By ID")
    public void getRoomByID() {
        given().log().all()
                .header("Content-Type", "application/json")
                .when().get(baseUrlFSW+"detail?id=3")
                .then().assertThat().statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/getRoomByID.json")))
                .extract().response().asString();
    }

    @Given("API Insert Room Data")
    public void insertRoomData() {
        given().log().all()
                .header("Content-Type", "multipart/form-data")
                .header("authorization", "bearer " + token)
                .multiPart("title","Kos Nusa")
                .multiPart("type","Putri")
                .formParam("description","Kos Nusa ialah kos yang menerapkan aturan dalam kosnya")
                .formParam("electricity","1")
                .formParam("wide","10x10")
                .formParam("street","Jalan Nangka")
                .when().post(baseUrlFSW)
                .then().assertThat().statusCode(201)
                .body("message", equalTo("Add room succes"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/insertRoomData.json")))
                .extract().response().asString();
    }

    @Given("API Delete Room")
    public void deleteRoom() {
        given().log().all()
                .header("Content-Type", "application/json")
                .header("authorization", "bearer " + token)
                .when().delete(baseUrlFSW+"3")
                .then().assertThat().statusCode(200)
                .body("success", equalTo(true))
                .body("message", equalTo("Room  deleted"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/deleteRoom.json")))
                .extract().response().asString();
    }

    @Given("API Delete Room Picture")
    public void deleteRoomPicture() {
        given().log().all()
                .header("Content-Type", "multipart/form-data")
                .header("authorization", "bearer " + token)
                .multiPart("id_image","Image/qvmup32xycuoquw3gh44")  // butuh file config
                .when().put(baseUrlFSW+"image/remove/21")
                .then().assertThat().statusCode(200)
                .body("success", equalTo(true))
                .body("message", equalTo("Successfuly delete picture"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/deleteRoom.json")))
                .extract().response().asString();
    }

    @Given("API Update Room Data")
    public void updateRoomData() {
        given().log().all()
                .header("Content-Type", "multipart/form-data")
                .header("authorization", "bearer " + token)
                .multiPart("title","Kos Nusa")
                .multiPart("type","Putri")
                .formParam("description","[Updated] kostan Nusa ialah kos yang menerapkan aturan dalam kosnya")
                .formParam("electricity","1")
                .formParam("wide","15x15")
                .formParam("street","Jalan Nangka")
                .when().put(baseUrlFSW+"18")
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Room update"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/updateRoomData.json")))
                .extract().response().asString();
    }

    @Given("API Update Room Picture")
    public void updateRoomPicture() {
        given().log().all()
                .header("Content-Type", "multipart/form-data")
                .header("authorization", "bearer " + token)
                .formParam("id_image","Image/iy0khjrv6ewah2qsnlal")
                .multiPart("imageUrl","http://res.cloudinary.com/dtv8k90ub/image/upload/v1674129304/Image/iy0khjrv6ewah2qsnlal.jpg")
                .when().put(baseUrlFSW+"image/19")
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Successfuly update Picture"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/updateRoomPicture.json")))
                .extract().response().asString();
    }

    @Given("API Login By Google")
    public void loginByGoogle() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"role\":\"admin\",\n" +
                        "    \"accessToken\":\"ya29.a0AX9GBdXqhr0WTIMAUUhOwni7HyPyhYNC5eh_yAbcMRNLQSvFw-cYCpcghRQJzJXrThDeHlTz_SUzgXQHlXcDEHH3u8KeXJaSJoL3MC_LwoFPxG_iEs9XSbisf86pzHU5wpr3lyvoiYtbRc5Cb6YbKA5JOFzPaCgYKAewSARISFQHUCsbC0TMkbQi5zx-ut-TpGgMfPw0163\"\n" +
                        "}")
                .when().post(baseUrlBE+"google-login")
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("read write"))
                .body("token_type", equalTo("bearer"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/login.json")))
                .extract().response().asString();
    }
}
