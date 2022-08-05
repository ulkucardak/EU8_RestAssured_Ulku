package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonSchemaValidationTest extends SpartanAuthTestBase {

    @DisplayName("GET request to verify one spartan against to schema")
    @Test
    public void schemaValidation(){

        given().accept(ContentType.JSON)
                .and().pathParam("id",1)
                .and().auth().basic("admin","admin")
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"))
                .log().all();

    }

    @DisplayName("GET request to all spartans and verify schema")
    @Test
    public void allSpartanSchemaTest(){

        given().accept(ContentType.JSON)
                .auth().basic("admin","admin")
                .when().get("/api/spartans")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/allSpartansSchema.json")))
                .log().all();

    }

    @DisplayName("POST request for creating one spartan")
    @Test
    public void postSpartanSchemaTest(){
        String newSpartan = "{\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"name\": \"UlkuCardak\",\n" +
                "  \"phone\": 2346170972\n" +
                "}";

        given().accept(ContentType.JSON)
                .and().auth().basic("admin","admin")
                .and().contentType(ContentType.JSON)
                .body(newSpartan)
                .when().post("/api/spartans")
                .then().statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/spartanPostJsonSchema.json")))
                .log().all();
    }

}
