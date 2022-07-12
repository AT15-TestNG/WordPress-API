package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
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

    @Given("^I make a request to create an existent user$")
    public void createAnExistentUser() {
        String username = response.getResponse().jsonPath().getString("username");
        String email = response.getResponse().jsonPath().getString("email");
        String password = "password";

        queryParams = new HashMap<>();
        queryParams.put("username", username);
        queryParams.put("email", email);
        queryParams.put("password", password);

        String usersEndpoint = credentialsManager.getUsersEndpoint();

        Response requestResponse = apiManager.post(usersEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a user by Id$")
    public void getUserById() {
        String id = response.getResponse().jsonPath().getString("id");
        String name = response.getResponse().jsonPath().getString("name");
        String description = response.getResponse().jsonPath().getString("description");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("name", name);
        queryParams.put("description", description);

        String usersByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(usersByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve my own user$")
    public void retrieveMyUser() {
        String id = response.getResponse().jsonPath().getString("id");
        String name = response.getResponse().jsonPath().getString("name");
        String description = response.getResponse().jsonPath().getString("description");
        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("name", name);
        queryParams.put("description", description);

        String retrieveMeEndpoint = credentialsManager.getRetrieveMeEndpoint();
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(retrieveMeEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to update a user with the following query params$")
    public void updateUserById(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");
        queryParams = new HashMap<>();
        queryParams.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams.putAll(queryParamsList.get(0));

        String usersEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.put(usersEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^He makes a request to update his own user with the following query params$")
    public void updateMyUser(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");
        queryParams = new HashMap<>();
        queryParams.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams.putAll(queryParamsList.get(0));

        String updateMeEndpoint = credentialsManager.getRetrieveMeEndpoint();

        Response requestResponse = apiManager.put(updateMeEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a user by Id$")
    public void deleteUserById() {
        String id = response.getResponse().jsonPath().getString("id");
        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("reassign", 1);
        queryParams.put("force", true);

        String usersByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(usersByIdEndpoint, queryParams, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^He makes a request to delete his own user$")
    public void deleteMe() {
        String id = response.getResponse().jsonPath().getString("id");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("reassign", 1);
        queryParams.put("force", true);

        String deleteMeEndpoint = credentialsManager.getRetrieveMeEndpoint();
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(deleteMeEndpoint, queryParams, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a user with the Id \"(.*)\"$")
    public void getUserByNonExistentAndInvalidId(String id) {
        String usersByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(usersByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a user with the Id \"(.*?)\"$")
    public void deleteUserByNonExistentAndInvalidId(String id) {
        String usersByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();
        queryParams = new HashMap<>();
        queryParams.put("reassign", 1);
        queryParams.put("force", true);

        Response requestResponse = apiManager.delete(usersByIdEndpoint, queryParams, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a user by Id with missing parameters$")
    public void deleteUserByIdWithMissingParameters() {
        String id = response.getResponse().jsonPath().getString("id");
        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("reassign", 1);

        String usersByIdEndpoint = credentialsManager.getUsersByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(usersByIdEndpoint, queryParams, authHeaders);
        response.setResponse(requestResponse);
    }

    @Then("^proper user id should be returned$")
    public void checkUserId() {
        String id = response.getResponse().jsonPath().getString("id");
        Assert.assertEquals(id, queryParams.get("id"));
    }

    @Then("^proper deleted user id should be returned$")
    public void checkDeletedUserId() {
        String id = response.getResponse().jsonPath().getString("previous.id");
        Assert.assertEquals(id, queryParams.get("id"));
    }

    @Then("^name should be correct$")
    public void checkUserName() {
        String name = response.getResponse().jsonPath().getString("name");
        Assert.assertEquals(name, queryParams.get("name"));
    }

    @Then("^First Name should be correct$")
    public void checkUserFirstName() {
        String firstName = response.getResponse().jsonPath().getString("first_name");
        Assert.assertEquals(firstName, queryParams.get("first_name"));
    }

    @Then("^Last Name should be correct$")
    public void checkUserLastName() {
        String lastName = response.getResponse().jsonPath().getString("last_name");
        Assert.assertEquals(lastName, queryParams.get("last_name"));
    }

    @Then("^description should be correct$")
    public void checkUserDescription() {
        String description = response.getResponse().jsonPath().getString("description");
        Assert.assertEquals(description, queryParams.get("description"));
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
        String role = response.getResponse().jsonPath().getString("roles[0]");
        Assert.assertEquals(role, queryParams.get("roles"));
    }

    @Then("^user should be deleted$")
    public void checkUserDeleted() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("deleted"), "true");
    }
}
