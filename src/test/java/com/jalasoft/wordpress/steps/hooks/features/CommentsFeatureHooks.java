package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APICommentsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class CommentsFeatureHooks {
    private final HttpResponse response;

    public CommentsFeatureHooks(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAComment or @UpdateAComment")
    public void beforeRetrieveAComment() {
        Response requestResponse = APICommentsMethods.createAComment();

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Comment was not created");
        }
    }

    @After("@CreateAComment or @RetrieveAComment or @UpdateAComment")
    public void afterCreateAComment() {
        String id = response.getResponse().jsonPath().getString("id");
        boolean deleted = APICommentsMethods.deleteCommentById(id);

        Assert.assertTrue(deleted, "Comment was not deleted");
    }
}
