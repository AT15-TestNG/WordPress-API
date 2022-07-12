package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.StringManager;

import java.util.HashMap;
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

    @Given("^I make a request to retrieve a comment$")
    public void retrieveComment() {
        String id = response.getResponse().jsonPath().getString("id");
        String author_name = response.getResponse().jsonPath().getString("author_name");
        String content = response.getResponse().jsonPath().getString("content.rendered");
        String post = response.getResponse().jsonPath().getString("post");

        queryParamsComments = new HashMap<>();
        queryParamsComments.put("id", id);
        queryParamsComments.put("post", post);
        queryParamsComments.put("author_name", author_name);
        queryParamsComments.put("content", content);

        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);

        Response requestResponse = apiManager.get(commentByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to update a comment with the following query params$")
    public void updateACommentByID(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");
        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);

        queryParamsComments = new HashMap<>();
        queryParamsComments.put("id", id);
        queryParamsComments.putAll(queryParamsList.get(0));

        Response requestResponse = apiManager.put(commentByIdEndpoint, queryParamsList.get(0), headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a comment$")
    public void deleteACommentByID() {
        String id = response.getResponse().jsonPath().getString("id");
        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);

        queryParamsComments = new HashMap<>();
        queryParamsComments.put("id", id);

        Response requestResponse = apiManager.delete(commentByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a comment with invalid \"(.*?)\"$")
    public void createCategoryWithInvalidBody(String bodyValue) {
        String invalid_json;
        Response requestResponse;

        if (bodyValue.equals("invalid JSON format")) {
            invalid_json = "/{}/";
            requestResponse = apiManager.post(credentialsManager.getCommentsEndpoint(), headers.getHeaders(), ContentType.JSON, invalid_json);
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Response of a Comment with bad Body was not set");
        }
    }

    @Given("^I make a request to create a comment without specifying any post$")
    public void createCommentWithoutPost() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("author_name", "TestNG");
        jsonAsMap.put("author_email", "wapuu@wordpress.example");
        jsonAsMap.put("content", "Comment without a Post");

        Response requestResponse = apiManager.post(credentialsManager.getCommentsEndpoint(), jsonAsMap, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a comment using an invalid endpoint \"(.*?)\"$")
    public void retrieveCategoryWithInvalidEndpoint(String invalidEndpoint) {
        Response requestResponse = apiManager.get(invalidEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a duplicated comment$")
    public void createDuplicatedComment() {
        String id, commentByIdEndpoint;
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("post", 1);
        jsonAsMap.put("author_name", "A WordPress Commenter");
        jsonAsMap.put("author_email", "wapuu@wordpress.example");
        jsonAsMap.put("content", "Example");

        Response requestResponse = apiManager.post(credentialsManager.getCommentsEndpoint(), jsonAsMap, headers.getHeaders());
        id = requestResponse.jsonPath().getString("id");

        Response requestResponseDuplicated = apiManager.post(credentialsManager.getCommentsEndpoint(), jsonAsMap, headers.getHeaders());
        response.setResponse(requestResponseDuplicated);

        jsonAsMap.clear();
        jsonAsMap.put("force", true);
        commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        apiManager.delete(commentByIdEndpoint, jsonAsMap, headers.getHeaders());
    }

    @Given("^I make a request to delete a trashed comment without using force=true$")
    public void deleteTrashedCommentError410() {
        String id = response.getResponse().jsonPath().getString("id");
        String commentByIdEndpoint = credentialsManager.getCommentsByIdEndpoint().replace("<id>", id);
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("force", true);

        apiManager.delete(commentByIdEndpoint, headers.getHeaders());
        Response requestResponse = apiManager.delete(commentByIdEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);

        apiManager.delete(commentByIdEndpoint, jsonAsMap, headers.getHeaders());
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

    @Then("^proper content should be returned$")
    public void checkContent() {
        String content = "<p>" + queryParamsComments.get("content") + "</p>\n";
        Assert.assertEquals(response.getResponse().jsonPath().getString("content.rendered"), content, "wrong content was returned");
    }

    @Then("^proper content of a retrieve comment should be returned$")
    public void checkRetrieveComment() {
        String content = queryParamsComments.get("content").toString();
        Assert.assertEquals(response.getResponse().jsonPath().getString("content.rendered"), content, "wrong content was returned");
    }

    @Then("^proper comment id should be returned$")
    public void checkCommentId() {
        String id = response.getResponse().jsonPath().getString("id");
        Assert.assertEquals(id, queryParamsComments.get("id"));
    }

    @Then("^comment should be deleted$")
    public void checkDeleteComment() {
        String status = response.getResponse().jsonPath().getString("status");
        Assert.assertEquals(status, "trash");
    }
}
