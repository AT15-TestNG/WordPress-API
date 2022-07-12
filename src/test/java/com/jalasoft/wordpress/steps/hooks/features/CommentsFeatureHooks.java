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
    private String commentId;

    public CommentsFeatureHooks(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAComment or @UpdateAComment or @DeleteAComment or @DeleteATrashedCommentError410 or @RetrieveACommentAsSubscriber or @UpdateACommentAsSubscriber or @DeleteACommentAsSubscriber")
    public void beforeRetrieveAComment() {
        Response requestResponse = APICommentsMethods.createAComment();
        commentId = requestResponse.jsonPath().getString("id");

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Comment was not created");
        }
    }

    @After("@CreateAComment or @RetrieveAComment or @UpdateAComment or @DeleteAComment or @RetrieveACommentAsSubscriber or @UpdateACommentAsSubscriber or @DeleteACommentAsSubscriber or @CreateACommentAsSubscriber")
    public void afterCreateAComment() {
        if (Objects.nonNull(response.getResponse().jsonPath().getString("id"))) {
            String id = response.getResponse().jsonPath().getString("id");
            boolean deleted = APICommentsMethods.deleteCommentById(id);

            Assert.assertTrue(deleted, "Comment was not deleted");
        } else if (Objects.nonNull(commentId)) {
            boolean deleted = APICommentsMethods.deleteCommentById(commentId);

            Assert.assertTrue(deleted, "Comment was not deleted");
        } else {
            Assert.fail("Comment was not able to being deleted");
        }
    }
}
