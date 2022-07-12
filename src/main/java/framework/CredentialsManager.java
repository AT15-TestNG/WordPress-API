package framework;

import utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CredentialsManager {
    private Properties properties;
    private static final LoggerManager log = LoggerManager.getInstance();
    private static final String envFilePath = System.getProperty("user.dir") + File.separator + "environment.properties";
    private static final String usersFilePath = System.getProperty("user.dir") + File.separator + "user.properties";
    private static final String apiFilePath = System.getProperty("user.dir") + File.separator + "api.properties";
    private static CredentialsManager instance;

    private String envId;

    private CredentialsManager() {
        initialize();
    }

    private void initialize() {
        log.info("Reading credentials");
        String wpEnvironmentId = System.getProperty("envId");
        if ((wpEnvironmentId == null) || (wpEnvironmentId.isEmpty())) {
            envId = "QA01";
        } else {
            envId = wpEnvironmentId;
        }
        log.info("WordPress Environment Id --> " + envId);

        properties = new Properties();
        Properties envProperties = new Properties();
        Properties usersProperties = new Properties();
        Properties apiProperties = new Properties();
        try {
            envProperties.load(new FileInputStream(envFilePath));
            usersProperties.load(new FileInputStream(usersFilePath));
            apiProperties.load(new FileInputStream(apiFilePath));
        } catch (IOException e) {
            log.error("unable to load properties file");
        }
        properties.putAll(envProperties);
        properties.putAll(usersProperties);
        properties.putAll(apiProperties);
    }

    public static CredentialsManager getInstance() {
        if (instance == null) {
            instance = new CredentialsManager();
        }
        return instance;
    }

    private String getEnvironmentSetting(String setting) {
        return (String) getInstance().properties.get(setting);
    }

    public String getEnvId() {
        return envId;
    }

    public String getBaseURL() {
        return getEnvironmentSetting("envId.baseURL".replace("envId", getEnvId().toLowerCase()));
    }

    public String getBasePath() {
        return getEnvironmentSetting("api.basePath");
    }

    public String getTokenEndpoint() {
        return getEnvironmentSetting("api.endpoint.token");
    }

    public String getPostsEndpoint() {
        return getEnvironmentSetting("api.endpoint.posts");
    }

    public String getPostsByIdEndpoint() {
        return getEnvironmentSetting("api.endpoint.postsById");
    }

    public String getUserName(String userRole) {
        return getEnvironmentSetting("userRole.username".replace("userRole", userRole));
    }

    public String getPassword(String userRole) {
        return getEnvironmentSetting("userRole.password".replace("userRole", userRole));
    }

    public int getAPIServicePort() {
        return Integer.parseInt(getEnvironmentSetting("api.service.port"));
    }
    public String getTaxonomiesEndpoint() {
        return getEnvironmentSetting("api.endpoint.taxonomies");
    }

    public String getTaxonomiesByIdEndpoint() {
        return getEnvironmentSetting("api.endpoint.taxonomiesById");
    }
}
