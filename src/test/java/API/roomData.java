package API;

import com.jayway.restassured.module.jsv.JsonSchemaValidator;
import io.cucumber.java.en.Given;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class roomData extends config{
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
                .when().delete(baseUrlFSW+idFlow)
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
                .multiPart("id_image",urlImageupdateIdFlow)
                .when().put(baseUrlFSW+"image/remove/"+updateIdFlow)
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
                .when().put(baseUrlFSW+idFlow)
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
                .when().put(baseUrlFSW+"image/"+idFlow)
                .then().assertThat().statusCode(200)
                .body("message", equalTo("Successfuly update Picture"))
                .body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/schema/updateRoomPicture.json")))
                .extract().response().asString();
    }
}
