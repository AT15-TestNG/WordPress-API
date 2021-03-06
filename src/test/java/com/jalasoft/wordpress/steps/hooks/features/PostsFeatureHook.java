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
    private String postId;

    public PostsFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAPost or @UpdateAPost or @DeleteAPost or @RetrieveAPostAsSub or @UpdateAPostAsSub or @CreateAPostAsSub or @DeleteAPostAsSub or @UpdateAPostWithInvalidId " +
    "or @RetrieveAPostWithInvalidId or @RetrieveAPostWithInvalidIdAsSub or @CreateAPostWithInvalidIdAsSub or @DeleteAPostWithInvalidIdAsSub")
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
        postId = response.getResponse().jsonPath().getString("id");
    }

    @After("@CreateAPost or @RetrieveAPost or @UpdateAPost")
    public void afterCreateAPostFeature() {
        String id = response.getResponse().jsonPath().getString("id");
        boolean status = APIPostsMethods.deletePostById(id);
        Assert.assertTrue(status, "Post was not deleted");
    }

    @After("@RetrieveAPostAsSub or @UpdateAPostAsSub or @CreateAPostAsSub or @DeleteAPostAsSub or @UpdateAPostWithInvalidId " +
            "or @RetrieveAPostWithInvalidId or @RetrieveAPostWithInvalidIdAsSub " +
            "or @CreateAPostWithInvalidIdAsSub or @DeleteAPostWithInvalidIdAsSub")
    public void afterCreateAPostFeatureAsSubscriber() {
        boolean status = APIPostsMethods.deletePostById(postId);
        Assert.assertTrue(status, "Post was not deleted");
    }
}
