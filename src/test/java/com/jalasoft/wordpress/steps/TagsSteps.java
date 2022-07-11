package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
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
    public void getAllCategories() {
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
}