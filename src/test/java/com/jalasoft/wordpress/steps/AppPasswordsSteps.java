package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import api.http.HttpScenarioContext;
import api.methods.APIUsersMethods;
import constants.DomainAppEnums;
import framework.CredentialsManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
public class AppPasswordsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams = new HashMap<>();
    private final HttpScenarioContext scenarioContext;
    public AppPasswordsSteps(HttpHeaders headers, HttpResponse response, HttpScenarioContext scenarioContext) {
        this.headers = headers;
        this.response = response;
        this.scenarioContext = scenarioContext;
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
    @Given("^I make a request to retrieve all app passwords from the request user$")
    public void getAllAppPasswordsById() {
        String userId = scenarioContext.getScenarioContext().get("userId").toString();
        String getAllAppPasswordsByIdEndpoint = credentialsManager.getAppPasswordsByIdEndpoint().replace("<user_id>",userId);
        Response requestResponse = apiManager.get(getAllAppPasswordsByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve an app passwords from the request user by its uuid$")
    public void getAppPasswordsByIdByUuid() {
        String userId = scenarioContext.getScenarioContext().get("userId").toString();
        String uuid = scenarioContext.getScenarioContext().get("uuid").toString();
        String getAppPasswordsByIdEndpointByUuid = credentialsManager.getAppPasswordsByIByUuidEndpoint().replace("<user_id>",userId).replace("<uuid>",uuid);
        Response requestResponse = apiManager.get(getAppPasswordsByIdEndpointByUuid, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to update an app passwords from the request user by its uuid with the following parameters$")
    public void UpdateAnAppPasswordByIdByUuid(DataTable table) {
        String userId = scenarioContext.getScenarioContext().get("userId").toString();
        String uuid = scenarioContext.getScenarioContext().get("uuid").toString();
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);
        String appPasswordsByIdByUuidEndpoint = credentialsManager.getAppPasswordsByIByUuidEndpoint().replace("<user_id>", userId).replace("<uuid>",uuid);
        Response requestResponse = apiManager.put(appPasswordsByIdByUuidEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to delete an app passwords from the request user by its uuid$")
    public void DeleteAnAppPasswordByIdByUuid() {
        String userId = scenarioContext.getScenarioContext().get("userId").toString();
        String uuid = scenarioContext.getScenarioContext().get("uuid").toString();

        String appPasswordsByIdByUuidEndpoint = credentialsManager.getAppPasswordsByIByUuidEndpoint().replace("<user_id>", userId).replace("<uuid>",uuid);

        Response requestResponse = apiManager.delete(appPasswordsByIdByUuidEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to delete all app passwords from the request user$")
    public void DeleteAllAppPasswordById() {
        String userId = scenarioContext.getScenarioContext().get("userId").toString();
        String appPasswordsByIdEndpoint = credentialsManager.getAppPasswordsByIdEndpoint().replace("<user_id>", userId);
        Response requestResponse = apiManager.delete(appPasswordsByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve all app passwords from the request user without authentication$")
    public void getAllAppPasswordsByIdWithoutToken() {
        String userId = response.getResponse().jsonPath().getString("id");
        String appPasswordsByIdEndpoint = credentialsManager.getAppPasswordsByIdEndpoint().replace("<user_id>", userId);
        Response requestResponse = apiManager.get(appPasswordsByIdEndpoint);
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to create an app password with an invalid user id and the following query params$")
    public void CreateAnAppPasswordByNonexistentId(DataTable table) {
        String userId = "10000000";
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String postsEndpoint = credentialsManager.getAppPasswordsByIdEndpoint().replace("<user_id>", userId);

        Response requestResponse = apiManager.post(postsEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to retrieve an app passwords from the request user by an non existent uuid$")
    public void getAppPasswordsByIdByNonExistentUuid() {
        String userId = response.getResponse().jsonPath().getString("id");
        String uuid = "100";
        String getAppPasswordsByIdEndpointByUuid = credentialsManager.getAppPasswordsByIByUuidEndpoint().replace("<user_id>",userId).replace("<uuid>",uuid);
        Response requestResponse = apiManager.get(getAppPasswordsByIdEndpointByUuid, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I make a request to create an app password with the same name than the already app password created$")
    public void CreateAnAppPasswordByIdWithNotUniqueName() {
        String userId = scenarioContext.getScenarioContext().get("userId").toString();
        Object name = scenarioContext.getScenarioContext().get("name");
        queryParams.put("name", name);
        String postsEndpoint = credentialsManager.getAppPasswordsByIdEndpoint().replace("<user_id>", userId);
        Response requestResponse = apiManager.post(postsEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }
    @Given("^I create a user with a subscriber role for app password$")
    public void CreateAnUserWithSubscriberRole() {
        Response requestResponse = APIUsersMethods.createAPropertyUser(DomainAppEnums.UserRole.SUBSCRIBER2.getUserRole());
        scenarioContext.addScenarioContext("subscriberUserId",requestResponse.jsonPath().get("id"));

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("User was not created");
        }
    }
    @Then("^name attribute should be the same as the value delivered$")
    public void verifyName() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("name"), queryParams.get("name"), "wrong name value returned");
    }
    @Then("^item with the name of the app-password created should be retrieved$")
    public void verifyNameRetrieved() {
        Assert.assertTrue(response.getResponse().body().asString().contains(scenarioContext.getScenarioContext().get("name").toString()), "name value not found");
    }

    @Then("^item with the uuid of the app-password created should be retrieved$")
    public void verifyUuidRetrieved() {
        Assert.assertTrue(response.getResponse().body().asString().contains(scenarioContext.getScenarioContext().get("uuid").toString()), "uuid value not found");
    }
    @Then("^returned deleted attribute should be \"(.*?)\"$")
    public void verifyDeletedRetrieved(String isDeleted) {
        Assert.assertEquals(response.getResponse().jsonPath().getString("deleted"), isDeleted, "app-password was not deleted");
    }
}
