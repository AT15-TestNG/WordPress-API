package com.jalasoft.wordpress.steps;

import api.http.HttpResponse;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import org.testng.Assert;

import java.nio.charset.StandardCharsets;

public class APISteps {
    private final HttpResponse response;
    public APISteps(HttpResponse response) {
        this.response = response;
    }
    @Then("^response should be \"(.*?)\"$")
    public void verifyResponseStatusLine(String expectedStatusLine) {
        Assert.assertEquals(response.getResponse().statusLine(), expectedStatusLine, "wrong status line returned");
    }
    @Then("^response should be valid and have a body$")
    public void verifyValidResponseAndBody() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);

        Assert.assertTrue(Status.SUCCESS.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");
    }
    @Then("^response to the app password request should be invalid and have a body$")
    public void verifyInvalidResponseAndBodyAppPassword() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");
    }

    @Then("^response to the statuses request should be invalid and have a body$")
    public void verifyInvalidResponseAndBodyStatuses() {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");
    }
}
