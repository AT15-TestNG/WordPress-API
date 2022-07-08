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

public class PostsSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams;

    public PostsSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^(?:I make|the user makes) a request to retrieve all posts$")
    public void getAllPosts() {
        String postsEndpoint = credentialsManager.getPostsEndpoint();
        Response requestResponse = apiManager.get(postsEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a post with the following query params$")
    public void createAPost(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String postsEndpoint = credentialsManager.getPostsEndpoint();

        Response requestResponse = apiManager.post(postsEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a post$")
    public void getPostById() {
        String id = response.getResponse().jsonPath().getString("id");
        String content = response.getResponse().jsonPath().getString("content.raw");
        String title = response.getResponse().jsonPath().getString("title.raw");
        String excerpt = response.getResponse().jsonPath().getString("excerpt.raw");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("content", content);
        queryParams.put("title", title);
        queryParams.put("excerpt", excerpt);

        String postsByIdEndpoint = credentialsManager.getPostsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(postsByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to update a post with the following query params$")
    public void updatePostById(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");

        queryParams = new HashMap<>();
        queryParams.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams.putAll(queryParamsList.get(0));

        String postsByIdEndpoint = credentialsManager.getPostsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.put(postsByIdEndpoint, queryParamsList.get(0), authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a post$")
    public void deletePostById() {
        String id = response.getResponse().jsonPath().getString("id");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("status", "trash");

        String postsByIdEndpoint = credentialsManager.getPostsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(postsByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of posts$")
    public void verifyPostsAmount() {
        int expectAmountOfPosts = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        Assert.assertEquals(response.getResponse().jsonPath().getList("$").size(), expectAmountOfPosts, "wrong amount of posts returned");
    }

    @Then("^content should be correct$")
    public void verifyContent() {
        String expectedContent = "<p>" + queryParams.get("content") + "</p>\n";
        Assert.assertEquals(response.getResponse().jsonPath().getString("content.rendered"), expectedContent, "wrong content value returned");
    }

    @Then("^title should be correct$")
    public void verifyTitle() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("title.rendered"), queryParams.get("title"), "wrong tile value returned");
    }

    @Then("^excerpt should be correct$")
    public void verifyExcerpt() {
        String expectedExcerpt = "<p>" + queryParams.get("excerpt") + "</p>\n";
        Assert.assertEquals(response.getResponse().jsonPath().getString("excerpt.rendered"), expectedExcerpt, "wrong excerpt value returned");
    }

    @Then("^proper post id should be returned$")
    public void verifyPostId() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("id"), queryParams.get("id"), "wrong id value returned");
    }

    @Then("^post should be deleted$")
    public void verifyPostIsDeleted() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("status"), queryParams.get("status"), "post was not deleted");
    }
}
