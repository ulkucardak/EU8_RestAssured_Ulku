package com.cydeo.day12;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MockApi {

    // https://9c8afcf2-2280-4afd-83bd-20b3c254416a.mock.pstmn.io
    @Test
    public void test1(){

        given().baseUri("https://9c8afcf2-2280-4afd-83bd-20b3c254416a.mock.pstmn.io")
                .and().accept(ContentType.JSON)
                .when().get("/customer")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("customers.firstName",is("Joe"));
    }
}
