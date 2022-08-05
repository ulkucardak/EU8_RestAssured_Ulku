package com.cydeo.day4;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ORDSApiTestWithPath extends HRTestBase {

    @DisplayName("GET request to countries with Path method")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(200,response.statusCode());

        //print limit response
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        //print hasMore

        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        //print first country_id
        String firstCountry_id = response.path("items[0].country_id");
        System.out.println(firstCountry_id);

        //print second country_name

        System.out.println("response.path(\"items[1].country_name\") = " + response.path("items[1].country_name"));

        //print third href that belongs Canada

        System.out.println("response.path(\"items[2].links[0].href\") = " + response.path("items[2].links[0].href"));

        // get me all country names

        List<String> countryNames= response.path("items.country_name");
        System.out.println(countryNames);


        //assert that all regions ids are equal to 2

        List<Integer> allRegionIds = response.path("items.region_id");

        for (Integer eachRegionId : allRegionIds) {

            System.out.println(eachRegionId);

            assertEquals(2,eachRegionId);

        }

    }

    @DisplayName("GET request to /employees with Param")
    @Test()
    public void test2() {

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"job_id\": \"IT_PROG\"}")
                .when().get("/employees");

        Assertions.assertEquals(200,response.statusCode());

        Assertions.assertEquals("application/json",response.contentType());

        Assertions.assertTrue(response.body().asString().contains("IT_PROG"));

        // make sure we have only IT_PROG as a job_id


       List<String> allJobIds =response.path("items.job_id");
        for (String eachJobId : allJobIds) {
            assertEquals("IT_PROG",eachJobId);

        }

        // Task
        // Print each name of IT_PROGs

       List<String> allNamesOfITProg = response.path("items.first_name");
        for (String eachName : allNamesOfITProg) {
            System.out.println(eachName);

        }


    }





}
