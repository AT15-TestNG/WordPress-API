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

public class PagesSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams;
    private String pageId;

    public PagesSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^(?:I make|the user makes) a request to retrieve all pages$")
    public void getAllPosts() {
        String pagesEndpoint = credentialsManager.getPagesEndpoint();
        Response requestResponse = apiManager.get(pagesEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a pages with the ID \"(.*)\"$")
    public void getPageById(String id) {
        String pagesByIdEndpoint = credentialsManager.getPageByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(pagesByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a post with the ID \"(.*)\"$")
    public void getPostById(String id) {
        String postsByIdEndpoint = credentialsManager.getPostsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(postsByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a page with the ID \"(.*)\"$")
    public void deletePageById(String id) {
        String pagesByIdEndpoint = credentialsManager.getPageByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(pagesByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a post with the ID \"(.*)\"$")
    public void deletePostById(String id) {
        String postByIdEndpoint = credentialsManager.getPostsByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(postByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to create a page with the following query params$")
    public void createAPage(DataTable table) {
        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams = queryParamsList.get(0);

        String pageEndpoint = credentialsManager.getPagesEndpoint();

        Response requestResponse = apiManager.post(pageEndpoint, queryParams, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a page$")
    public void getPageById() {
        String id = response.getResponse().jsonPath().getString("id");
        String content = response.getResponse().jsonPath().getString("content.raw");
        String title = response.getResponse().jsonPath().getString("title.raw");
        String excerpt = response.getResponse().jsonPath().getString("excerpt.raw");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("content", content);
        queryParams.put("title", title);
        queryParams.put("excerpt", excerpt);

        String pagesByIdEndpoint = credentialsManager.getPageByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.get(pagesByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to update a page with the following query params$")
    public void updatePageById(DataTable table) {
        String id = response.getResponse().jsonPath().getString("id");

        queryParams = new HashMap<>();
        queryParams.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams.putAll(queryParamsList.get(0));

        String pageByIdEndpoint = credentialsManager.getPageByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.put(pageByIdEndpoint, queryParamsList.get(0), authHeaders);
        response.setResponse(requestResponse);
        pageId = requestResponse.jsonPath().getString("id");
    }

    @Given("^I make a request to update a page with the ID \"(.*)\" and the following query parameters$")
    public void updatePageById(String id, DataTable table) {
        queryParams = new HashMap<>();
        queryParams.put("id", id);

        List<Map<String, Object>> queryParamsList = table.asMaps(String.class, Object.class);
        queryParams.putAll(queryParamsList.get(0));

        String pagesByIdEndpoint = credentialsManager.getPageByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.put(pagesByIdEndpoint, queryParamsList.get(0), authHeaders);
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to delete a page$")
    public void deletePageById() {
        String id = response.getResponse().jsonPath().getString("id");

        queryParams = new HashMap<>();
        queryParams.put("id", id);
        queryParams.put("status", "trash");
        queryParams.put("force", true);

        String pageByIdEndpoint = credentialsManager.getPageByIdEndpoint().replace("<id>", id);
        Headers authHeaders = headers.getHeaders();

        Response requestResponse = apiManager.delete(pageByIdEndpoint, authHeaders);
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of pages$")
    public void verifyPageAmount() {
        int expectAmountOfPages = Integer.parseInt(response.getResponse().getHeaders().getValue("X-WP-Total"));
        Assert.assertEquals(response.getResponse().jsonPath().getList("$").size(), expectAmountOfPages, "wrong amount of pages returned");
    }

    @Then("^proper page id should be returned$")
    public void verifyPageId() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("id"), queryParams.get("id"), "wrong id value returned");
    }

    @Then("^page content should be correct$")
    public void verifyContent() {
        String expectedContent = "<p>" + queryParams.get("content") + "</p>\n";
        Assert.assertEquals(response.getResponse().jsonPath().getString("content.rendered"), expectedContent, "wrong content value returned");
    }

    @Then("^page title should be correct$")
    public void verifyTitle() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("title.rendered"), queryParams.get("title"), "wrong tile value returned");
    }

    @Then("^page excerpt should be correct$")
    public void verifyExcerpt() {
        String expectedExcerpt = "<p>" + queryParams.get("excerpt") + "</p>\n";
        Assert.assertEquals(response.getResponse().jsonPath().getString("excerpt.rendered"), expectedExcerpt, "wrong excerpt value returned");
    }

    @Then("^page should be deleted$")
    public void verifyPageIsDeleted() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("status"), queryParams.get("status"), "page was not deleted");
    }
}
