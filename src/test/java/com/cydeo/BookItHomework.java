package com.cydeo;

import com.cydeo.utilities.DBUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookItHomework {

    /*@BeforeAll
    public void setUp(){
        String dbUsername = "qa_user";
        String dbPassword = "Cybertek11!";
        String dbUrl = "jdbc:postgresql://room-reservation-qa2.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/room_reservation_qa2";
        DBUtils.createConnection(dbUrl,dbUsername,dbPassword);

        //baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }

    @AfterAll
    public void tearDown(){
        DBUtils.destroy();
    }*/



    @Test
    public void test() {

        // try to get name,role,batch number, campus, team name from api for one student
        // it will be multiple api request
        // responses returns batch name team name with students information
        //first make sure your student is inside the respones then get those info
        //prepare one list of information about student and compare with ui
        //UI vs API
        //verify same information vs
        //connect db and get the same information which requires joins multiple tables
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("email", "teacherilsamnickel@gmail.com")
                .and().queryParam("password", "samnickel")
                .when()
                .get("https://cybertek-reservation-api-qa2.herokuapp.com/sign")
                .then().extract().response();

        String token = response.path("accessToken");

        String teacherToken = "Bearer " + token;

        Response response2 = given()
                .and().header("Authorization", teacherToken)
                .and().pathParam("id", 46)
                .when().get("https://cybertek-reservation-api-qa2.herokuapp.com/api/students/{id}")
                .then().statusCode(200)
                .extract().response();


        String apiName = response2.path("firstName") + " " + response2.path("lastName");
        String apiRole = response2.path("role");

        System.out.println(apiName);

        String token2 = given().accept(ContentType.JSON)
                .and().queryParam("email", "nshearsby7w@uiuc.edu")
                .and().queryParam("password", "lorettebradnum")
                .when()
                .get("https://cybertek-reservation-api-qa2.herokuapp.com/sign")
                .then().extract().response().path("accessToken");

        String studentToken = "Bearer " + token2;


    }



}
