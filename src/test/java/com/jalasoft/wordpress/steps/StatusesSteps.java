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

public class StatusesSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private List<Map<String, Object>> statusesList;

    public StatusesSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^(?:I make|the user makes) a request to retrieve all statuses$")
    public void getAllStatuses() {
        String statusesEndpoint = credentialsManager.getStatusesEndpoint();
        Response requestResponse = apiManager.get(statusesEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^(?:I make|the user makes) a request to retrieve all statuses without authentication$")
    public void getAllStatusesWithoutToken() {
        String statusesEndpoint = credentialsManager.getStatusesEndpoint();
        Response requestResponse = apiManager.get(statusesEndpoint);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a status by its \"(.*?)\"$")
    public void getStatusByName(String statusName) {
        String statusByNameEndpoint = credentialsManager.getStatusesByNameEndpoint().replace("<status>", statusName);
        Response requestResponse = apiManager.get(statusByNameEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response body has to have the following status$")
    public void verifyStatusInResponseBody(DataTable table) {
        statusesList = table.asMaps(String.class, Object.class);
        for (Object status : statusesList.get(0).values()) {
            Assert.assertTrue(response.getResponse().getBody().asString().contains(status.toString()), "wrong response body returned");
        }
    }
    @Then("^response body has to have the \"(.*?)\" sent in the request$")
    public void verifyStatusName(String statusName) {
       Assert.assertTrue(response.getResponse().getBody().asString().contains((statusName)), "wrong response status");
    }

    @Then("^response body should contain the \"(.*?)\"$")
    public void verifyErrorMessage(String errorMessage) {
        Assert.assertTrue(response.getResponse().getBody().asString().contains((errorMessage)), "wrong response status");
    }
}
