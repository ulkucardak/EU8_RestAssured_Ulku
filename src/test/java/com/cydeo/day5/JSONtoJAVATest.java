package com.cydeo.day5;
import com.cydeo.utilities.SpartanTestBase;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JSONtoJAVATest extends SpartanTestBase {

    @DisplayName("GET one Spartan and deserialize to Map")
    @Test
    public void oneSpartanToMap(){

        Response response = given().pathParam("id","15")
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).extract().response();

        Map<String,Object> jsonMap = response.as(Map.class);

        System.out.println(jsonMap.toString());

       String actualName = (String)jsonMap.get("name");

    }


    @DisplayName("Get all spartans to JAVA data structure")
    @Test
    public void getAllSpartans(){

        Response response = get("/api/spartans").then().statusCode(200).extract().response();

        List< Map<String,Object> > allSpartans = response.as(List.class);

        System.out.println(allSpartans.get(1).get("name"));
        Map<String,Object> spartan3 = allSpartans.get(2);
        System.out.println(spartan3.get("name"));
    }
}
