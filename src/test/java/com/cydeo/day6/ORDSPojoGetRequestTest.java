package com.cydeo.day6;

import com.cydeo.pojo.Employee;
import com.cydeo.pojo.Link;
import com.cydeo.pojo.Region;
import com.cydeo.pojo.Regions;
import com.cydeo.utilities.HRTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ORDSPojoGetRequestTest extends HRTestBase {

    @Test
    public void regionTest(){

        JsonPath jsonPath = get("/regions").then().statusCode(200).extract().response().jsonPath();

       Region region1 = jsonPath.getObject("items[0]", Region.class);

        System.out.println(region1);
        System.out.println(region1.getRegion_name());
        System.out.println(region1.getLinks().get(0).getHref());
        System.out.println(region1.getRegionid());

        Link link1 = region1.getLinks().get(0);

        System.out.println(link1.getRel());

    }

    @DisplayName(("Get request to /employees and only get couple of values as a Pojo class"))
    @Test
    public void employeeTest(){

        Employee employee1 = get("/employees").then().statusCode(200)
                .extract().response().jsonPath().getObject("items[0]", Employee.class);

        System.out.println(employee1);

    }

/* send a get request to regions
        verify that region ids are 1,2,3,4
        verify that regions names Europe ,Americas , Asia, Middle East and Africa
        verify that count is 4
        try to use pojo as much as possible
        ignore non-used fields

     */


    @Test
    public void test(){


        Regions regions = get("/regions").then().statusCode(200)
                .extract().jsonPath().getObject("", Regions.class);

        assertThat(regions.getCount(),is(4));

        List<String> regionNames = new ArrayList<>();

        List<Integer> regionIds = new ArrayList<>();

        for (Region region : regions.getItems()) {

            regionNames.add(region.getRegion_name());
            regionIds.add(region.getRegionid());

        }

        List<Integer> expectedRegionIds = Arrays.asList(1,2,3,4);
        List<String> expectedRegionNames = Arrays.asList("Europe", "Americas", "Asia", "Middle East and Africa");

        assertThat(regionNames,equalTo(expectedRegionNames));
        assertThat(regionIds,equalTo(expectedRegionIds));


    }


}
