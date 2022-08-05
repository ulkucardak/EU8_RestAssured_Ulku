package com.cydeo.day8;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookItTest {

    @BeforeAll
    public static void init(){

        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }

    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjAyIiwiYXVkIjoic3R1ZGVudC10ZWFtLW1lbWJlciJ9.UQnmL58LBoFW-Opm5OPIv7AgFvupRq4cANOIBQdOlpI";

    @DisplayName("GET all campuses")
    @Test
    public void testAuth1(){
        given().header("Authorization",accessToken)
                .and().accept(ContentType.JSON)
                .when().get("/api/campuses")
                .then().statusCode(200)
                .log().all();

    }

}
