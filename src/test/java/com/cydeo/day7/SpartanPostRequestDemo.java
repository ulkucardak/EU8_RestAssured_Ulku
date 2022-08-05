package com.cydeo.day7;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanPostRequestDemo extends SpartanTestBase {

     /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"Severus",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */

    @Test
    public void test1(){

        String requestJsonBody = "{\n" +
                "  \"gender\": \"Male\",\n" +
                "  \"name\": \"HaroldFinch\",\n" +
                "  \"phone\": 2346170972\n" +
                "}";

        Spartan spartan = new Spartan();
        spartan.setName("HaroldFinch");
        spartan.setGender("Male");
        spartan.setPhone(2346170972L);
        Response response = given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .body(spartan).log().all()
                .when().post("/api/spartans");

        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";

        assertThat(response.path("success"),is(expectedResponseMessage));
        assertThat(response.path("data.gender"),is("Male"));
        assertThat(response.path("data.name"),is("HaroldFinch"));
        assertThat(response.path("data.phone"),is(2346170972L));

    }

    @Test
    public void postMethod4(){

        Spartan spartan = new Spartan();
        spartan.setName("HaroldFinch");
        spartan.setGender("Male");
        spartan.setPhone(2346170972L);

        String expectedResponseMessage = "A Spartan is Born!";

        int idFromPost = given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .body(spartan).log().all()
                .when().post("/api/spartans")
                .then().statusCode(201)
                .contentType("application/json").log().all()
                .body("success",is(expectedResponseMessage))
                .extract().jsonPath().getInt("data.id");


        Spartan spartanPosted = given().accept(ContentType.JSON)
                .and().pathParam("id", idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .contentType(ContentType.JSON).log().all()
                .extract().response().as(Spartan.class);

        assertThat(spartanPosted.getName(),is(spartan.getName()));
        assertThat(spartanPosted.getId(),is(spartan.getId()));



    }
}
