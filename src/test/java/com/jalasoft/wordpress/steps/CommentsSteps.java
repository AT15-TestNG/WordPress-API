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

public class CommentsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParamsComments;

    public CommentsSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }
    @Given("^I make a request to retrieve all comments$")
    public void getAllComments() {
        Response requestResponse = apiManager.get(credentialsManager.getCommentsEndpoint(), headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a comment with the following query params$")
    public void createAComment(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParamsComments = queryParamsList.get(0);

        String commentsEndpoint = credentialsManager.getCommentsEndpoint();
        Response requestResponse = apiManager.post(commentsEndpoint, queryParamsComments, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of comments$")
    public void checkCommentsAmount() {
        int expectedAmountOfComments = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        int actualAmountOfComments = response.getResponse().jsonPath().getList("$").size();
        Assert.assertEquals(actualAmountOfComments, expectedAmountOfComments, "wrong amount of comments returned");
    }

    @Then("^proper postID should be returned$")
    public void checkPostID() {
        String expectedPostID = response.getResponse().jsonPath().getString("post");
        Assert.assertEquals(queryParamsComments.get("post"), expectedPostID, "wrong postID was returned");
    }
    @Then("^proper author_name should be returned$")
    public void checkAuthorName() {
        String author_name = response.getResponse().jsonPath().getString("author_name");
        Assert.assertEquals(queryParamsComments.get("author_name"), author_name, "wrong author_name was returned");
    }

    @Then("^proper author_email should be returned$")
    public void checkAuthorEmail() {
        String author_email = response.getResponse().jsonPath().getString("author_email");
        Assert.assertEquals(queryParamsComments.get("author_email"), author_email, "wrong author_email was returned");
    }

    @Then("^proper content should be returned$")
    public void checkContent() {
        String content = "<p>" + queryParamsComments.get("content") + "</p>\n";
        Assert.assertEquals(response.getResponse().jsonPath().getString("content.rendered"), content, "wrong content was returned");
    }
}
