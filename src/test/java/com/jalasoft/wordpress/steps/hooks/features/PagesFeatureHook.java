package com.jalasoft.wordpress.steps.hooks.features;

import api.http.HttpResponse;
import api.methods.APIPagesMethods;
import api.methods.APIPostsMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Objects;

public class PagesFeatureHook {
    private final HttpResponse response;

    public PagesFeatureHook(HttpResponse response) {
        this.response = response;
    }

    @Before("@RetrieveAPage or @UpdateAPage or @DeleteAPage")
    public void beforeRetrieveAPageFeature() {
        String content = "TestNG WordPress Page content";
        String title = "TestNG WordPress Page title";
        String excerpt = "TestNG WordPress Page excerpt";

        Response requestResponse = APIPagesMethods.createAPage(content, title, excerpt);

        if (Objects.nonNull(requestResponse)) {
            response.setResponse(requestResponse);
        } else {
            Assert.fail("page was not created");
        }
    }

    @After("@CreateAPage or @RetrieveAPage or @UpdateAPage")
    public void afterCreateAPageFeature() {
        String id = response.getResponse().jsonPath().getString("id");
        String status = APIPagesMethods.deletePageById(id);

        Assert.assertEquals(status, "trash", "page was not deleted");
    }
}
