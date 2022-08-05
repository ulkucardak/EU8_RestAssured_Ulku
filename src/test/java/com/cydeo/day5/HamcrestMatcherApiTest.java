package com.cydeo.day5;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatcherApiTest {

    /*
       given accept type is Json
       And path param id is 15
       When user sends a get request to spartans/{id}
       Then status code is 200
       And content type is Json
       And json data has following
           "id": 15,
           "name": "Meta",
           "gender": "Female",
           "phone": 1938695106
        */

    @DisplayName("GET request to spartans/{id}")
    @Test
    public void test(){

                 given()
                         .accept(ContentType.JSON)
                         .and().pathParam("id",15)
                .when()
                         .get("http://34.238.239.116:8000/api/spartans/{id}")
                         .then()
                         .statusCode(200)
                         .and().assertThat()
                         .contentType("application/json")
                         .and()
                         .body("id",equalTo(15),
                                 "name",is("Meta"),
                                 "gender",is("Female"),
                                 "phone",is(1938695106));


    }

    @DisplayName("CBTraining Teacher request with chaining and matchers")
    @Test
    public void teacherData(){

                        given()
                                .accept(ContentType.JSON)
                                .and().pathParam("teacherId",21887)
                        .when()
                                .get("http://api.cybertektraining.com/teacher/{teacherId}")

                                .then()
                                .statusCode(200)
                                .and()
                                .contentType("application/json;charset=UTF-8")
                                .and()
                                .header("Date",notNullValue())
                                .and()
                                .header("Content-Length",is("275"))
                                .and().assertThat()
                                .body("teachers[0].firstName",is("Leonel"))
                                .body("teachers[0].lastName",is("Messi"))
                                .body("teachers[0].gender",equalTo("Male"));


    }

    @DisplayName("GET request to teacher/all and chaining")
    @Test
    public void teachersTest(){

        //verify Alexander,Darleen,Sean inside the all teachers

        given().accept(ContentType.JSON)
                .and()
                .when().get("http://api.cybertektraining.com/teacher/all")

                .then()
                .statusCode(200)
                .and()
                .body("teachers.firstName",hasItems("Leonel","Andrii"));

    }


}
