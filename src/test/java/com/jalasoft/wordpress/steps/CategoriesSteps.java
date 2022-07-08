package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;

public class CategoriesSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;

    public CategoriesSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^I make a request to retrieve all categories$")
    public void getAllCategories() {
        Response requestResponse = apiManager.get(credentialsManager.getCategoriesEndpoint(), headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of categories$")
    public void checkCategoriesAmount() {
        int expectedAmountOfCategories = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfCategories = response.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(expectedAmountOfCategories, actualAmountOfCategories);
    }
}
