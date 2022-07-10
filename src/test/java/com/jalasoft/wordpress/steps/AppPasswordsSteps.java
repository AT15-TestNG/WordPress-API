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

public class AppPasswordsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams;

    public AppPasswordsSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^I make a request to create an app password with the following query params$")
    public void CreateAnAppPasswordById(DataTable table) {
        String userId = response.getResponse().jsonPath().getString("id");
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String postsEndpoint = credentialsManager.getAppPasswordsByIdEndpoint().replace("<user_id>", userId);

        Response requestResponse = apiManager.post(postsEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^name attribute should be the same as the value delivered$")
    public void verifyName() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("name"), queryParams.get("name"), "wrong name value returned");
    }
}
