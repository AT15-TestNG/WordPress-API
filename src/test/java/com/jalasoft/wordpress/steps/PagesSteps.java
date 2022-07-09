package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

public class PagesSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams;

    public PagesSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^(?:I make|the user makes) a request to retrieve all pages$")
    public void getAllPosts() {
        String pagesEndpoint = credentialsManager.getPagesEndpoint();
        Response requestResponse = apiManager.get(pagesEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of pages$")
    public void verifyPostsAmount() {
        int expectAmountOfPages = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        Assert.assertEquals(response.getResponse().jsonPath().getList("$").size(), expectAmountOfPages, "wrong amount of pages returned");
    }
}
