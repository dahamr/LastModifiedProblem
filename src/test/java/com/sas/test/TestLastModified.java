package com.sas.test;

import static com.jayway.restassured.RestAssured.given;

import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.response.Response;
import com.sas.test.representation.TestObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@SpringBootTest(value = {"spring.config.name=optiontest",
                         "security.ignored=/**"}, 
                webEnvironment=WebEnvironment.RANDOM_PORT)
public class TestLastModified
{
    @Value("${local.server.port}")
    public int port;

    private String baseUrl;

    @Before
    public void setup()
    {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void testLastModified()
    {
        TestObject testObject = new TestObject();
        testObject.setName("TestObject1");
        testObject.setDescription("An object to test with.");
        Response response =
        given()
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(testObject)
        .when()
            .post(baseUrl + "/test/objects")
        .then()
            .log().all()
            .statusCode(HttpStatus.CREATED.value())
            .extract().response();

        TestObject newObject = response.as(TestObject.class);
        String lastModified = response.getHeader(HttpHeaders.LAST_MODIFIED);
        try{Thread.sleep(1500);}catch(InterruptedException ex){}
        Response response2 = given()
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.IF_UNMODIFIED_SINCE, lastModified)
            .body(newObject)
        .when()
            .put(baseUrl + "/test/objects/" + newObject.getId())
        .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().response();
        String newLastModified = response2.getHeader(HttpHeaders.LAST_MODIFIED);
        System.out.println("newvalue:" + newLastModified);
    }
}
