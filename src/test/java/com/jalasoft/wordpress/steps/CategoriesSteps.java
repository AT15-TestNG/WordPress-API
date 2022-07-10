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

    @Given("^I make a request to update a category with the following query params$")
    public void updateCategoryById(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");

        queryParamsCategory = new HashMap<>();
        queryParamsCategory.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParamsCategory.putAll(queryParamsList.get(0));

        String categoryByIdEndpoint = credentialsManager.getCategoriesByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.put(categoryByIdEndpoint, queryParamsList.get(0), headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a category$")
    public void deleteCategoryById() {
        String id = response.getResponse().jsonPath().getString("id");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        String categoryByIdEndpoint = credentialsManager.getCategoriesByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.delete(categoryByIdEndpoint, jsonAsMap, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a category with invalid \"(.*?)\"$")
    public void createCategoryWithInvalidBody(String bodyValue) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        String invalid_json;
        Response requestResponse;

        if (bodyValue.equals("JSON with missing parameters")) {
            jsonAsMap.put("Bad Request", "Bad Request");
            requestResponse = apiManager.post(credentialsManager.getCategoriesEndpoint(), jsonAsMap, headers.getHeaders());
            response.setResponse(requestResponse);
        } else if (bodyValue.equals("invalid JSON format")) {
            invalid_json = "/{}/";
            requestResponse = apiManager.post(credentialsManager.getCategoriesEndpoint(), headers.getHeaders(), ContentType.JSON, invalid_json);
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Category with bad body was not created");
        }
    }

    @Given("^I make a request to delete the default category$")
    public void deleteDefaultCategory() {
        String defaultCategoryEndpoint = "/wp/v2/categories/1";

        Response requestResponse = apiManager.delete(defaultCategoryEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of categories$")
    public void checkCategoriesAmount() {
        int expectedAmountOfCategories = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfCategories = response.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(expectedAmountOfCategories, actualAmountOfCategories, "wrong amount of categories returned");
    }

    @Then("^proper name should be returned")
    public void checkCategoryName() {
        String categoryName = response.getResponse().jsonPath().getString("name");
        Assert.assertEquals(queryParamsCategory.get("name"), categoryName, "wrong category name returned");
    }

    @Then("^proper category id should be returned$")
    public void checkProperCategoryId() {
        String categoryId = queryParamsCategory.get("id").toString();
        Assert.assertEquals(response.getResponse().jsonPath().getString("id"), categoryId);
    }

    @Then("^proper description should be returned$")
    public void checkProperDescription() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("description"), queryParamsCategory.get("description"), "wrong description returned");
    }

    @Then("^the category should be deleted$")
    public void checkDeletedCategory() {
        Assert.assertTrue(response.getResponse().jsonPath().get("deleted"), "category was not deleted");
    }
}
