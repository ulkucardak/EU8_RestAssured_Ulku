package com.cydeo.day12;

import com.cydeo.utilities.BookItTestBase;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class BookItSpecTest extends BookItTestBase {

    @Test
    public void test1(){
        //send a get request to /api/users/me endpoint as a teacher
        //verify status code and content type

        given().spec(teacherReqSpec)
                .when().get("api/users/me")
                .then().spec(responseSpec);

    }

    @Test
    public void test2(){
        //send a get request to /api/users/me endpoint as a student-member
        //verify status code and content type

        given().spec(studentMemberReqSpec)
                .when().get("api/users/me")
                .then()
                .spec(userCheck("Marius","Forker"));

    }

    @Test
    public void test3(){
        //send a get request to /api/users/me endpoint as a student-leader
        //verify status code and content type

        given().spec(studentLeaderReqSpec)
                .when().get("api/users/me")
                .then().spec(responseSpec);

    }


}
