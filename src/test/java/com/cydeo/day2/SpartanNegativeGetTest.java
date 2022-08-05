package com.cydeo.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpartanNegativeGetTest {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        RestAssured.baseURI = "http://34.238.239.116:8000";

    }
      /*TASK
    Given Accept type application/xml
    When user send GET request to /api/spartans/10 end point
    Then status code must be 406
    And response Content Type must be application/xml;charset=UTF-8
    */

    @DisplayName("GET request to /api/spartans/10")
    @Test
    public void test1(){
        Response response = RestAssured.given().accept(ContentType.XML)
                .when().get("/api/spartans/10");

        Assertions.assertEquals(406, response.statusCode());
        Assertions.assertEquals("application/xml;charset=UTF-8",response.contentType());




    }
}
