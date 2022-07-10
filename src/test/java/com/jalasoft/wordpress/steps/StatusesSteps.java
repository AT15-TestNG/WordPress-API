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

    @Then("^response body has to have the following status$")
    public void verifyStatusInResponseBody(DataTable table) {
        statusesList = table.asMaps(String.class, Object.class);
        for (Object status : statusesList.get(0).values()) {
            Assert.assertTrue(response.getResponse().getBody().asString().contains(status.toString()), "wrong response body returned");
        }
    }
}
