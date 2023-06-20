package org.platanus.webboard;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringIntegratedTest {
	
	
	@Autowired
	private DatabaseCleanup databaseCleanup;
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	void setUp() {
		if(RestAssured.port == RestAssured.UNDEFINED_PORT) {
			RestAssured.port = port;
			databaseCleanup.afterPropertiesSet();
		}
		databaseCleanup.execute();
	}
}
