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

public class UsersSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams;

    public UsersSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^I make a request to retrieve all users$")
    public void getAllUsers() {
        String usersEndpoint = credentialsManager.getUsersEndpoint();
        Response requestResponse = apiManager.get(usersEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a user with the following query params$")
    public void createAUser(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String usersEndpoint = credentialsManager.getUsersEndpoint();

        Response requestResponse = apiManager.post(usersEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of users")
    public void checkUsersAmount() {
        int expectAmountOfPosts = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualUsersAmount = response.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualUsersAmount, expectAmountOfPosts);
    }

    @Then("^username should be correct$")
    public void checkUsername() {
        String username = response.getResponse().jsonPath().getString("username");
        Assert.assertEquals(username, queryParams.get("username"));
    }

    @Then("^email should be correct$")
    public void checkEmail() {
        String email = response.getResponse().jsonPath().getString("email");
        Assert.assertEquals(email, queryParams.get("email"));
    }

    @Then("^role should be correct$")
    public void checkRole() {
        String role = response.getResponse().jsonPath().getString("role");
        Assert.assertEquals(role, queryParams.get("role"));
    }
}
