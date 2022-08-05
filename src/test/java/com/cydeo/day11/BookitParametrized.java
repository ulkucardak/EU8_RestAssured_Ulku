package com.cydeo.day11;
import com.cydeo.utilities.ExcelUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookitParametrized {

    public static List<Map<String,String>> studentCredentials(){

        ExcelUtil bookItFile = new ExcelUtil("src/test/resources/BookItQa3.xlsx","QA3");

        return bookItFile.getDataList();
    }

    @ParameterizedTest
    @MethodSource("studentCredentials")
    public void getTokenTest(Map<String,String> studentInfo){

        /*given().accept(ContentType.JSON)
                .and().queryParam("email",studentInfo.get("email"))
                .and().queryParam("password",studentInfo.get("password"))
                .when().get("https://cybertek-reservation-api-qa2.herokuapp.com/sign")
                .then().statusCode(200);*/

        given().accept(ContentType.JSON)
                .queryParams(studentInfo)
                .when().get("https://cybertek-reservation-api-qa2.herokuapp.com/sign")
                .then().statusCode(200);
    }


}
