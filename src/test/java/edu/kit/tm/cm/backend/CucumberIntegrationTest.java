package edu.kit.tm.cm.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration
public abstract class CucumberIntegrationTest {

    private final String SERVER_URL = "http://localhost";
    private final String BUILDINGS_ENDPOINT = "/buildings";

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    protected String buildingstEnpoint() {
        return SERVER_URL + ":" + port + BUILDINGS_ENDPOINT;
    }

}
