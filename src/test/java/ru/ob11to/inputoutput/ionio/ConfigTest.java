package ru.ob11to.inputoutput.ionio;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    @Disabled
    void whenPairWithoutComment() {
        String path = "data/app.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.connection.password=password")).isEqualTo("hibernate.connection.password=password");
    }
}