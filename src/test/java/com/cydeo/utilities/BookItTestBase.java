package com.cydeo.utilities;

import org.junit.jupiter.api.BeforeAll;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.hamcrest.Matchers.is;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class BookItTestBase {
    public static RequestSpecification teacherReqSpec;
    public static RequestSpecification studentMemberReqSpec;
    public static RequestSpecification studentLeaderReqSpec;
    public static ResponseSpecification responseSpec;

    public static RequestSpecification userReqSpec;

    @BeforeAll
    public static void init(){
        baseURI = ConfigurationReader.getProperty("qa3_api_url");

        teacherReqSpec = given().accept(ContentType.JSON)
                .and().header("Authorization",getTokenByRole("teacher"));

        studentMemberReqSpec = given().accept(ContentType.JSON)
                .and().header("Authorization",getTokenByRole("student-member"));

        studentLeaderReqSpec = given().accept(ContentType.JSON)
                .and().header("Authorization",getTokenByRole("student-leader"));


        responseSpec = expect().contentType(ContentType.JSON)
                .statusCode(200)
                .logDetail(LogDetail.ALL);




    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    public static RequestSpecification userReqSpec(String role){
        return given().accept(ContentType.JSON)
                .and().header("Authorization",getTokenByRole(role));

    }

    public static ResponseSpecification getDynamicResSpec(int statusCode){
        return expect().contentType(ContentType.JSON)
                .statusCode(statusCode)
                .logDetail(LogDetail.ALL);
    }

    public static ResponseSpecification userCheck(String firstName, String lastName){
        return expect().contentType(ContentType.JSON)
                .statusCode(200)
                .body("firstName",is(firstName))
                .body("lastName",is(lastName))
                .logDetail(LogDetail.ALL);
    }

    public static String getTokenByRole(String role){
        String token;
        if(role.equalsIgnoreCase("teacher")){
           token = given().accept(ContentType.JSON)
                    .and().queryParam("email",ConfigurationReader.getProperty("teacher_email"))
                    .queryParam("password",ConfigurationReader.getProperty("teacher_password"))
                    .when().get(baseURI+"sign")
                    .then().statusCode(200)
                    .extract().jsonPath().getString("accessToken");
        }else if(role.equalsIgnoreCase("student-member")){
            token = given().accept(ContentType.JSON)
                    .and().queryParam("email",ConfigurationReader.getProperty("team_member_email"))
                    .queryParam("password",ConfigurationReader.getProperty("team_member_password"))
                    .when().get(baseURI+"sign")
                    .then().statusCode(200)
                    .extract().jsonPath().getString("accessToken");

        }else {
            token = given().accept(ContentType.JSON)
                    .and().queryParam("email",ConfigurationReader.getProperty("team_leader_email"))
                    .queryParam("password",ConfigurationReader.getProperty("team_leader_password"))
                    .when().get(baseURI+"sign")
                    .then().statusCode(200)
                    .extract().jsonPath().getString("accessToken");
        }
        return "Bearer "+token;

    }


}
