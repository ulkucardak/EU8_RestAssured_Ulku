package com.cydeo.day6;

import com.cydeo.pojo.Search;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanPojoGetRequestTest extends SpartanTestBase {

    @DisplayName("GET one spartan and convert it to Spartan object")
    @Test
    public void oneSpartanPojo(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .log().all()
                .extract().response();

        //De serialize --> JSON to POJO (java custom class)
        //2 different way to do this
        //1.using as() method
        //we convert json response to spartan object with the help of jackson
        //as() method uses jackson to de serialize(converting JSON to Java class)

        Spartan spartan15 = response.as(Spartan.class);
        System.out.println(spartan15);
        System.out.println("spartan15.getId() = " + spartan15.getId());
        System.out.println("spartan15.getGender() = " + spartan15.getGender());

        //2. usage

        JsonPath jsonPath = response.jsonPath();

        Spartan s15 = jsonPath.getObject("",Spartan.class);

        System.out.println(s15);
    }

    @DisplayName("Get one spartan from search endpoint result and use pojo")
    @Test
    public void spartanSearchWithPojo(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().queryParams("nameContains", "a", "gender", "Male")
                .when().get("/api/spartans/search")
                .then().statusCode(200)
                .extract().response().jsonPath();

        Spartan s1 = jsonPath.getObject("content[0]", Spartan.class);
        System.out.println("s1 = " + s1);

    }

    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParams("nameContains", "a", "gender", "Male")
                .when().get("/api/spartans/search")
                .then().statusCode(200)
                .extract().response();

        Search searchResult = response.as(Search.class);
        System.out.println(searchResult.getContent().get(0).getName());

    }

    @DisplayName("Get /spartans/search and save as List<Spartan>")
    @Test
    public void test4(){

        List<Spartan> spartanList = given().accept(ContentType.JSON)
                .and().queryParams("nameContains", "a", "gender", "Male")
                .when().get("/api/spartans/search")
                .then().statusCode(200)
                .extract().response().jsonPath().getList("content", Spartan.class);

        System.out.println(spartanList.get(0).getName());


    }

}
