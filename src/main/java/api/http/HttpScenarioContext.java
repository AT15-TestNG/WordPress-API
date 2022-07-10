package api.http;

import io.restassured.http.Header;

import java.util.HashMap;
import java.util.Map;

public class HttpScenarioContext {
    private final Map<String, Object> scenarioContext = new HashMap<String, Object>();

    public Map<String, Object> getScenarioContext() {
        return scenarioContext;
    }

    public void addScenarioContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

}
