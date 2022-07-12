package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParamsTag;


    public TagsSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^I make a request to retrieve all tags$")
    public void getAllTags() {
        Response requestResponse = apiManager.get(credentialsManager.getTagsEndpoint(), headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to create a tag with the following query params$")
    public void createTag(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParamsTag = queryParamsList.get(0);

        String tagsEndpoint = credentialsManager.getTagsEndpoint();

        Response requestResponse = apiManager.post(tagsEndpoint, queryParamsTag, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve a tag")
    public void getTagById() {
        String id = response.getResponse().jsonPath().getString("id");
        String name = response.getResponse().jsonPath().getString("name");

        queryParamsTag = new HashMap<>();
        queryParamsTag.put("id", id);
        queryParamsTag.put("name", name);

        String tagByIdEndpoint = credentialsManager.getTagsByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.get(tagByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to update a tag with the following query params$")
    public void updateTagById(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");

        queryParamsTag = new HashMap<>();
        queryParamsTag.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParamsTag.putAll(queryParamsList.get(0));

        String tagByIdEndpoint = credentialsManager.getTagsByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.put(tagByIdEndpoint, queryParamsList.get(0), headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to delete a tag$")
    public void deleteTagById() {
        String id = response.getResponse().jsonPath().getString("id");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        String tagByIdEndpoint = credentialsManager.getTagsByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.delete(tagByIdEndpoint, jsonAsMap, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to create a tag with invalid \"(.*?)\"$")
    public void createTagWithInvalidBody(String bodyValue) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        String invalid_json;
        Response requestResponse;

        if (bodyValue.equals("JSON with missing parameters")) {
            jsonAsMap.put("Bad Request", "Bad Request");
            requestResponse = apiManager.post(credentialsManager.getTagsEndpoint(), jsonAsMap, headers.getHeaders());
            response.setResponse(requestResponse);
        } else if (bodyValue.equals("invalid JSON format")) {
            invalid_json = "/{}/";
            requestResponse = apiManager.post(credentialsManager.getTagsEndpoint(), headers.getHeaders(), ContentType.JSON, invalid_json);
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Tag with bad body was not created");
        }
    }

    @Given("^I make a request to delete a non-existing tag$")
    public void deleteDefaultCategory() {
        String defaultCategoryEndpoint = "/wp/v2/tags/123456987";

        Response requestResponse = apiManager.delete(defaultCategoryEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a tag using an invalid id \"(.*?)\"$")
    public void retrieveCategoryWithInvalidEndpoint(String invalidEndpoint) {
        Response requestResponse = apiManager.get(invalidEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to delete a tag without using force=true$")
    public void deleteATagWithoutForcingTrue() {
        String id = response.getResponse().jsonPath().getString("id");
        String tagsByIdEndpoint = credentialsManager.getTagsByIdEndpoint().replace("<id>", id);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        Response requestResponse = apiManager.delete(tagsByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
        apiManager.delete(tagsByIdEndpoint, jsonAsMap, headers.getHeaders());
    }
    @Then("^response should have proper amount of tags$")
    public void verifyTagsAmount() {
        int expectedAmountOfTags = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfTags = response.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(expectedAmountOfTags, actualAmountOfTags);
    }
    @Then("^name should be correct$")
    public void verifyTagName() {
        String TagName = response.getResponse().jsonPath().getString("name");
        Assert.assertEquals(queryParamsTag.get("name"), TagName, "wrong tag name returned");
    }
    @Then("^proper tag id should be returned$")
    public void verifyProperTagId() {
        String tagId = queryParamsTag.get("id").toString();
        Assert.assertEquals(response.getResponse().jsonPath().getString("id"), tagId);
    }
    @Then("^proper description should be returned$")
    public void verifyProperDescription() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("description"), queryParamsTag.get("description"), "wrong description returned");
    }
    @Then("^the tag should be deleted$")
    public void checkDeletedCategory() {
        Assert.assertTrue(response.getResponse().jsonPath().get("deleted"), "tag was not deleted");
    }
}