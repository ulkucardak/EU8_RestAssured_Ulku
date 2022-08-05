package com.cydeo.day11;

import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CsvFileSourceParametrizedTest {

    // Write a parameterized test for this request
    // Get the data from csv source
    // GET https://api.zippopotam.us/us/{state}/{city}
    @ParameterizedTest
    @CsvFileSource(resources = "/postalcode.csv",numLinesToSkip = 1)
    public void zipCodeTestWithFile(String state, String city, int zipCount){
        //send a request and verify place number matches with zipCount
      int placeNumber = given().accept(ContentType.JSON)
                .and().pathParam("state",state)
                .and().pathParam("city",city)
                .baseUri("https://api.zippopotam.us")
                .when().get("/us/{state}/{city}")
                .then().statusCode(200)
                .extract().jsonPath().getList("places").size();

      assertThat(zipCount,equalTo(placeNumber));






    }



}
