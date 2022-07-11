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
    @Then("^response should have proper amount of tags$")
    public void checkCategoriesAmount() {
        int expectedAmountOfCategories = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfCategories = response.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(expectedAmountOfCategories, actualAmountOfCategories);
    }
    @Then("^name should be correct$")
    public void checkCategoryName() {
        String TagName = response.getResponse().jsonPath().getString("name");
        Assert.assertEquals(queryParamsTag.get("name"), TagName, "wrong tag name returned");
    }
}