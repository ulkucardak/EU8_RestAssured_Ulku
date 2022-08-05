package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseTimeTest extends SpartanAuthTestBase{


    @Test
    public void test(){

        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin","admin")
                .when().get("/api/spartans")
                .then().statusCode(200)
                .time(both(greaterThan(500L)).and(lessThanOrEqualTo(1800L)))
                .extract().response();

        System.out.println("response.getTime() = " + response.getTime());
    }

}
