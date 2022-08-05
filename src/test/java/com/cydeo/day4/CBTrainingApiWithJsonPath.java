package com.cydeo.day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class CBTrainingApiWithJsonPath {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://api.cybertektraining.com";

    }

    @DisplayName("GET Request to individual student")
    @Test
    public void test1(){
        //send a get request to student id 23401 as a path parameter and accept header application/json
        //verify status code=200 /content type=application/json;charset=UTF-8 /Content-Encoding = gzip
        //verify Date header exists
        //assert that
            /*
                firstName Vera
                batch 14
                section 12
                emailAddress aaa@gmail.com
                companyName Cybertek
                state IL
                zipCode 60606

                using JsonPath
             */

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("studentId",32881)
                .when().get("http://api.cybertektraining.com/student/{studentId}");

        Assertions.assertEquals("application/json;charset=UTF-8", response.header("Content-Type"));



        JsonPath jsonPath = response.jsonPath();

        String firstName = jsonPath.getString("students[0].firstName");
        Assertions.assertEquals("Vera",firstName);

        int batchNumber = jsonPath.getInt("students[0].batch");
        Assertions.assertEquals(14,batchNumber);

        String section = jsonPath.getString("students[0].section");
        Assertions.assertEquals("12",section);

        String email = jsonPath.getString("students[0].contact.emailAddress");
        Assertions.assertEquals("aaa@gmail.com",email);

        String companyName = jsonPath.getString("students[0].company.companyName");
        Assertions.assertEquals("Cybertek",companyName);

        String state = jsonPath.getString("students[0].company.address.state");
        Assertions.assertEquals("IL",state);

        int zipcode = jsonPath.getInt("students[0].company.address.zipCode");
        Assertions.assertEquals(60606,zipcode);







    }






}
