package com.jalasoft.wordpress.steps.hooks.features;

import api.methods.APIPostsMethods;
import api.http.HttpResponse;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class PostsFeatureHook {
    private final HttpResponse response;

    public PostsFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAPost or @UpdateAPost or @DeleteAPost")
    public void beforeRetrieveAPostFeature() {
        String content = "TestNG WordPress Post content";
        String title = "TestNG WordPress Post title";
        String excerpt = "TestNG WordPress Post excerpt";

        Response requestResponse = APIPostsMethods.createAPost(content, title, excerpt);

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("post was not created");
        }
    }

    @After("@CreateAPost or @RetrieveAPost or @UpdateAPost")
    public void afterCreateAPostFeature() {
        String id = response.getResponse().jsonPath().getString("id");
        String status = APIPostsMethods.deletePostById(id);

        Assert.assertEquals(status, "trash", "post was not deleted");
    }
}