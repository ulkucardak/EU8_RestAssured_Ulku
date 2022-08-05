package com.cydeo.day7;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PutAndPatchRequestDemo extends SpartanTestBase {

    @DisplayName("PUT equest to one spartan for update with Map")
    @Test
    public void PutRequest(){

        Map<String,Object> putRequestMap = new LinkedHashMap<>();
        putRequestMap.put("name","Lorenza");
        putRequestMap.put("gender","Female");
        putRequestMap.put("phone",3312820936L);

        given().and().contentType(ContentType.JSON)
                .and().pathParam("id",10)
                .body(putRequestMap)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204);


        Object genderFromResponse = given().and().pathParam("id", "10")
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .contentType(ContentType.JSON).extract().response().jsonPath().getString("gender");

        assertThat(putRequestMap.get("gender"),is(genderFromResponse));

    }


}
