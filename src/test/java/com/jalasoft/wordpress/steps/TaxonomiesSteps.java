package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class TaxonomiesSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParamsTaxonomies;

    public TaxonomiesSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^I make a request to retrieve all taxonomies$")
    public void getAllTaxonomies() {
        Response requestResponse = apiManager.get(credentialsManager.getTaxonomiesEndpoint(), headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve a taxonomy")
    public void getTaxonomyById() {
        String taxonomy = "category";
        String name = "Categories";

        queryParamsTaxonomies = new HashMap<>();
        queryParamsTaxonomies.put("taxonomy", taxonomy);
        queryParamsTaxonomies.put("name", name);

        String taxonomyByIdEndpoint = credentialsManager.getTaxonomiesByIdEndpoint().replace("<taxonomy>", taxonomy);

        Response requestResponse = apiManager.get(taxonomyByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve a taxonomy using an invalid identifier \"(.*?)\"$")
    public void retrieveTaxonomyWithInvalidEndpoint(String invalidEndpoint) {
        Response requestResponse = apiManager.get(invalidEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Then("^name should be correct$")
    public void verifyTaxonomyName() {
        String categoryName = response.getResponse().jsonPath().getString("name");
        Assert.assertEquals(queryParamsTaxonomies.get("name"), categoryName, "wrong taxonomy name returned");
    }
}
