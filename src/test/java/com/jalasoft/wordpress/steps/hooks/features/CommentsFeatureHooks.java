package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APICommentsMethods;
import io.cucumber.java.After;
import org.testng.Assert;

public class CommentsFeatureHooks {
    private final HttpResponse response;

    public CommentsFeatureHooks(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateAComment")
    public void afterCreateAComment() {
        String id = response.getResponse().jsonPath().getString("id");
        boolean deleted = APICommentsMethods.deleteCommentById(id);

        Assert.assertTrue(deleted, "Comment was not deleted");
    }
}
