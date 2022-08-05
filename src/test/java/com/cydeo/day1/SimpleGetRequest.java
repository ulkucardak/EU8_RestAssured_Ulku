package com.cydeo.day1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class SimpleGetRequest {

    String url = "http://34.238.239.116:8000/api/spartans";

    @Test
    public void test1(){
        // send a get request and save inside the response object
        Response response = RestAssured.get(url);

        // to print response status code
        System.out.println(response.statusCode());

        // to print response body
        response.prettyPrint();


    }







}
