package com.jalasoft.wordpress.steps;

import api.http.HttpResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import org.testng.Assert;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

    @Then("^response should be invalid and have a body with the following fields$")
    public void verifyInvalidResponseAndBodyValues(DataTable table) {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");

        Assert.assertEquals(response.getResponse().jsonPath().get("status").toString(), queryParams.get("status"), "wrong status value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("error"), queryParams.get("error"), "wrong error value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("code"), queryParams.get("code"), "wrong code value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("error_description"), queryParams.get("error_description"), "wrong error description value returned");
    }

    @Then("^response should be invalid and have a body with the following keys and values$")
    public void verifyInvalidResponseAndBodyWithValues(DataTable table) {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");

        Assert.assertEquals(response.getResponse().jsonPath().get("data.status").toString(), queryParams.get("status"), "wrong status value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("error"), queryParams.get("error"), "wrong error value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("code"), queryParams.get("code"), "wrong code value returned");
    }

    @Then("^the response should be invalid and have a body with the following keys and values$")
    public void verifyTheInvalidResponseAndBodyWithValues(DataTable table) {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");

        Assert.assertEquals(response.getResponse().jsonPath().getString("status"), queryParams.get("status"), "wrong status value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("error"), queryParams.get("error"), "wrong error value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("code"), queryParams.get("code"), "wrong code value returned");
        Assert.assertEquals(response.getResponse().jsonPath().get("error_description"), queryParams.get("error_description"), "wrong error description value returned");
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

    @Then("^response should be valid and have a body with the following fields$")
    public void verifyInvalidResponseAndBody(DataTable table) {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");

        Assert.assertEquals(response.getResponse().jsonPath().getString("code"), queryParams.get("code"), "wrong code value returned");
        Assert.assertEquals(response.getResponse().jsonPath().getString("message"), queryParams.get("message"), "wrong message value returned");
    }

    @Then("^response should be invalid and have a body with the following values$")
    public void verifyInvalidResponseWithBodyValues(DataTable table) {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");

        Assert.assertEquals(response.getResponse().jsonPath().getString("code"), queryParams.get("code"), "wrong code value returned");
        Assert.assertTrue(response.getResponse().jsonPath().getString("message").contains(queryParams.get("message").toString()), "wrong message value returned");
    }
    @Then("^response should be invalid and have a body similar to the following values$")
    public void verifyInvalidResponseToTheBodyValues(DataTable table) {
        String expectedContentType = ContentType.JSON.withCharset(StandardCharsets.UTF_8);
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        Map<String, Object> queryParams = queryParamsList.get(0);

        Assert.assertTrue(Status.FAILURE.matches(response.getResponse().getStatusCode()), "invalid status code returned");
        Assert.assertFalse(response.getResponse().getBody().asString().isEmpty(), "response body is empty");
        Assert.assertEquals(response.getResponse().getContentType(), expectedContentType, "wrong content type returned");

        Assert.assertEquals(response.getResponse().jsonPath().getString("code"), queryParams.get("code"), "wrong code value returned");
        Assert.assertEquals(response.getResponse().jsonPath().getString("message"), queryParams.get("message"), "wrong message value returned");
    }
}
