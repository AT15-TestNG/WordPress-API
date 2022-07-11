package com.jalasoft.wordpress.steps;

import api.APIManager;
import api.http.HttpHeaders;
import api.http.HttpResponse;
import framework.CredentialsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

public class TypesSteps {
    private static final CredentialsManager credentialsManager = CredentialsManager.getInstance();
    private static  final APIManager apiManager = APIManager.getInstance();
    private final HttpHeaders headers;
    private final HttpResponse response;
    private Map<String, Object> queryParams;

    public TypesSteps(HttpHeaders headers, HttpResponse response) {
        this.headers = headers;
        this.response = response;
    }

    @Given("^I make a request to retrieve all types$")
    public void getAllTypes() {
        String typesEndpoint = credentialsManager.getTypesEndpoint();
        Response requestResponse = apiManager.get(typesEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a post type$")
    public void getPostType() {
        String typeByNameEndpoint = credentialsManager.getTypeByNameEndpoint().replace("<type>", "post");
        Response requestResponse = apiManager.get(typeByNameEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Given("^I make a request to retrieve a type by name \"(.*?)\"$")
    public void getTypeByName(String typeName) {
        String typeByNameEndpoint = credentialsManager.getTypeByNameEndpoint().replace("<type>", typeName);
        Response requestResponse = apiManager.get(typeByNameEndpoint, headers.getHeaders());
        response.setResponse(requestResponse);
    }

    @Then("^response should have proper amount of types$")
    public void verifyTypesAmount() {
        Assert.assertNotNull(response.getResponse().jsonPath().get("post"), "Response does not have any posts");
        Assert.assertNotNull(response.getResponse().jsonPath().get("page"), "Response does not have any pages");
        Assert.assertNotNull(response.getResponse().jsonPath().get("attachment"), "Response does not have any attachments");
        Assert.assertNotNull(response.getResponse().jsonPath().get("nav_menu_item"), "Response does not have any nav_menu_items");
        Assert.assertNotNull(response.getResponse().jsonPath().get("wp_block"), "Response does not have any wp_blocks");
        Assert.assertNotNull(response.getResponse().jsonPath().get("wp_template"), "Response does not have any wp_templates");
        Assert.assertNotNull(response.getResponse().jsonPath().get("wp_template_part"), "Response does not have any wp_template_part");
        Assert.assertNotNull(response.getResponse().jsonPath().get("wp_navigation"), "Response does not have any wp_navigation");
    }

    @Then("^response should be a post type$")
    public void verifyPostType() {
        Assert.assertEquals(response.getResponse().jsonPath().getString("name"), "Posts", "Response is not a post type");
    }
}
