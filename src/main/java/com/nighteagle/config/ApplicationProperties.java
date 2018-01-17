package com.nighteagle.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final ApplicationProperties.Storage storage = new ApplicationProperties.Storage();

    public static class Storage {
        private String location;

        public Storage() {}

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public ApplicationProperties.Storage getStorage() {
        return this.storage;
    }
}
