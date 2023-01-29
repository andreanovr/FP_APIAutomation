package API;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class main extends config {
    @Given("API register")
    public void register() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \"" + registerField + "@gmail.com\",\n" +
                        "    \"username\": \"" + registerField + "\",\n" +
                        "    \"role\": \"" + role + "\",\n" +
                        "    \"password\": \"" + registerField + "\",\n" +
                        "    \"fullname\": \"" + registerField + "\"\n" +
                        "}")
                .when().post(baseUrlBE + "signup")
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
                        "    \"email\": \""+superField+"\",\n" +
                        "    \"username\": \""+superField+"\",\n" +
                        "    \"role\": \"" + role + "\",\n" +
                        "    \"password\": \""+superField+"\",\n" +
                        "    \"fullname\": \""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "send-otp")
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
                        "    \"email\": \""+superField+"\",\n" +
                        "    \"password\": \""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "login")
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
                        "    \"email\":\""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "forget")
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
                        "  \"email\": \""+superField+"\",\n" +
                        "  \"otp\": " + otpChangePassword + ",\n" +
                        "  \"newPassword\": \""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "change-password")
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/changePassword.json")))
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
                .when().get(baseUrlFSW+"detail?id="+idFlow)
                .then().assertThat().statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/getRoomByID.json")))
                .extract().response().asString();
    }

    @Given("API Insert Room Data")
    public void insertRoomData() {
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \""+superField+"\",\n" +
                        "    \"password\": \""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "login")
                .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        given().log().all()
                .header("Content-Type", "multipart/form-data")
                .header("authorization", "bearer " + accessToken)
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

    @Given("API Update Room Data")
    public void updateRoomData() {
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \""+superField+"\",\n" +
                        "    \"password\": \""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "login")
                .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        given().log().all()
                .header("Content-Type", "multipart/form-data")
                .header("authorization", "bearer " +accessToken)
                .multiPart("title","Kos Nusa")
                .multiPart("type","Putri")
                .formParam("description","[Updated] kostan Nusa ialah kos yang menerapkan aturan dalam kosnya")
                .formParam("electricity","1")
                .formParam("wide","15x15")
                .formParam("street","Jalan Nangka")
                .when().put(baseUrlFSW+idFlow)
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Room update"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/updateRoomData.json")))
                .extract().response().asString();
    }

    @Given("API Get All Promo Room")
    public void getAllPromoRoom() {
        given().log().all()
                .header("Content-Type", "application/json")
                .when().get(baseUrlFSW+"promo")
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/getAllPromoRoom.json")))
                .extract().response().asString();
    }

    @Given("API Search Kos")
    public void searchKos() {
        given().log().all()
                .header("Content-Type", "application/json")
                .when().get(baseUrlFSW+"search?keyword="+keyword)
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/searchKos.json")))
                .extract().response().asString();
    }

    @Given("API Get All Approval")
    public void getAllApproval() {
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \""+superField+"\",\n" +
                        "    \"password\": \""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "login")
                .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        given().log().all()
                .header("Content-Type", "application/json")
                .header("authorization", "bearer " +accessToken)
                .when().get(baseUrlFSW+"approval")
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/getAllApproval.json")))
                .extract().response().asString();
    }

    @Given("API All Room Need Approval")
    public void allRoomNeedApproval() {
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \""+superField+"\",\n" +
                        "    \"password\": \""+superField+"\"\n" +
                        "}")
                .when().post(baseUrlBE + "login")
                .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        given().log().all()
                .header("Content-Type", "application/json")
                .header("authorization", "bearer " +accessToken)
                .when().get(baseUrlFSW+"approval/list-need-approval")
                .then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/allRoomNeedApproval.json")))
                .extract().response().asString();
    }
}
