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

public class CategoriesSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParamsCategory;

    public CategoriesSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^I make a request to retrieve all categories$")
    public void getAllCategories() {
        Response requestResponse = apiManager.get(credentialsManager.getCategoriesEndpoint(), headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a category with the following query params$")
    public void createCategory(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParamsCategory = queryParamsList.get(0);

        String categoryEndpoint = credentialsManager.getCategoriesEndpoint();

        Response requestResponse = apiManager.post(categoryEndpoint, queryParamsCategory, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a category$")
    public void getCategoryById() {
        String id = response.getResponse().jsonPath().getString("id");
        String name = response.getResponse().jsonPath().getString("name");

        queryParamsCategory = new HashMap<>();
        queryParamsCategory.put("id", id);
        queryParamsCategory.put("name", name);

        String categoryByIdEndpoint = credentialsManager.getCategoriesByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.get(categoryByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of categories$")
    public void checkCategoriesAmount() {
        int expectedAmountOfCategories = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfCategories = response.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(expectedAmountOfCategories, actualAmountOfCategories, "wrong amount of categories returned");
    }

    @Then("^name should be correct$")
    public void checkCategoryName() {
        String categoryName = response.getResponse().jsonPath().getString("name");
        Assert.assertEquals(queryParamsCategory.get("name"), categoryName, "wrong category name returned");
    }

    @Then("^proper category id should be returned$")
    public void checkProperCategoryId() {
        String categoryId = queryParamsCategory.get("id").toString();
        Assert.assertEquals(response.getResponse().jsonPath().getString("id"), categoryId);
    }
}
