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

    public CategoriesFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @After("@CreateACategory or @RetrieveACategory or @UpdateACategory")
    public void afterCreateACategory() {
        String id = response.getResponse().jsonPath().getString("id");
        Boolean deleted = APICategoriesMethods.deleteCategoryById(id);

        Assert.assertTrue(deleted, "Category was not deleted");
    }

    @Before("@RetrieveACategory or @UpdateACategory")
    public void beforeRetrieveACategory() {
        String name = "Category Name Example";

        Response requestResponse = APICategoriesMethods.createACategory(name);

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("Category was not created");
        }
    }
}
