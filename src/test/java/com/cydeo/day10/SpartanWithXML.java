package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanWithXML extends SpartanAuthTestBase {

    @DisplayName("GET request to /api/spartans and verify xml")
    @Test
    public void getSpartanXml(){

         given().accept(ContentType.XML)
                .and().auth().basic("admin","admin")
                .when().get("/api/spartans")
                .then().statusCode(200)
                .contentType("application/xml")
                .body("list.item[0].name",is("Ulku"))
                 .body("list.item[0].gender",is("Male"));
                //.extract().response().path("list.item[0].name");

        //assertThat("Ulku",is(apiName));

    }

    @DisplayName("GET request /api/spartans with xmlPath")
    @Test
    public void testXmlPath(){

        Response response = given().accept(ContentType.XML)
                .and().auth().basic("admin", "admin")
                .when().get("/api/spartans");

        XmlPath xmlPath = response.xmlPath();

        String name = xmlPath.getString("list.item[2].name");
        System.out.println(name);

        List<Object> names = xmlPath.getList("list.item.name");
        System.out.println(names);



    }

}
