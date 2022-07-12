package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APICategoriesMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class CategoriesFeatureHook {
    private final HttpResponse response;
    private String categoryId;

    public CategoriesFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateACategory or @RetrieveACategory or @UpdateACategory or @RetrieveACategoryAsSubscriber or @UpdateACategoryAsSubscriber or @DeleteACategoryAsSubscriber")
    public void afterCreateACategory() {
        if (Objects.nonNull(response.getResponse().jsonPath().getString("id"))) {
            String id = response.getResponse().jsonPath().getString("id");
            boolean deleted = APICategoriesMethods.deleteCategoryById(id);

            Assert.assertTrue(deleted, "Category was not deleted");
        } else if (Objects.nonNull(categoryId)) {
            boolean deleted = APICategoriesMethods.deleteCategoryById(categoryId);

            Assert.assertTrue(deleted, "Category was not deleted");
        } else {
            Assert.fail("Category was not able to being deleted");
        }
    }

    @Before("@RetrieveACategory or @UpdateACategory or @DeleteACategory or @DeleteACategoryError501 or @RetrieveACategoryAsSubscriber or @UpdateACategoryAsSubscriber or @DeleteACategoryAsSubscriber")
    public void beforeRetrieveACategory() {
        Response requestResponse = APICategoriesMethods.createACategory();
        categoryId = requestResponse.jsonPath().getString("id");

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Category was not created");
        }
    }
}
