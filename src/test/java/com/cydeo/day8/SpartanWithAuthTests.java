package com.cydeo.day8;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanWithAuthTests extends SpartanAuthTestBase {

    @DisplayName("GET /api/spartans as a public user(guest) expect 401 ")
    @Test
    public void test1(){
        get("/api/spartans")
                .then().statusCode(401)
                .log().all();
    }

    @DisplayName("GET /api/spartans as admin user expect 200")
    @Test
    public void testAdmin() {
        //how to pass admin admin as a username and password ?

        given().auth().basic("admin","admin")
                .and().accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then().statusCode(200)
                .log().all();

    }

    @DisplayName("DELETE /spartans/{id} as editor user expect 403")
    @Test
    public void testEditorDelete(){
        given().auth().basic("editor","editor")
                .and().accept(ContentType.JSON)
                .and().pathParam("id",1)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(403)
                .log().body();

    }

     /*
        As a homework,write a detealied test for Role Base Access Control(RBAC)
            in Spartan Auth app (7000)
            Admin should be able take all CRUD
            Editor should be able to take all CRUD
                other than DELETE
            User should be able to only READ data
                not update,delete,create (POST,PUT,PATCH,DELETE)
       --------------------------------------------------------
        Can guest even read data ? 401 for all

     */

    @Test
    public void testSpartanUser(){

        given().auth().basic("user","user")
                .accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then().statusCode(200);

        given().auth().basic("user","user")
                .accept(ContentType.JSON)
                .and().pathParam("id",1)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(403);

        String newSpartan = "{\n" +
                "        \"name\": \"Nels\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 4218971348\n" +
                "    }";

        given().auth().basic("user","user")
                .accept(ContentType.JSON)
                .and().body(newSpartan)
                .when().post("/api/spartans")
                .then().statusCode(403);

        String updateName = "{\n" +
                "        \"name\": \"Ulku\"\n" +
                "    }";
        given().auth().basic("user","user")
                .accept(ContentType.JSON)
                .and().body(updateName)
                .when().put("/api/spartans")
                .then().statusCode(403);



    }

    @Test
    public void testSpartanEditor(){

        given().auth().basic("editor","editor")
                .accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then().statusCode(200);

        given().auth().basic("editor","editor")
                .accept(ContentType.JSON)
                .and().pathParam("id",1)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(403);

        String newSpartan = "{\n" +
                "        \"name\": \"Ahmet\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 4218971348\n" +
                "    }";

        given().auth().basic("editor","editor")
                .accept(ContentType.JSON)
                .and().body(newSpartan)
                .when().post("/api/spartans")
                .then().statusCode(415)
                ;


    }


}
