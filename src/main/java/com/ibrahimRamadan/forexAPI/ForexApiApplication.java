package com.ibrahimRamadan.forexAPI;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

// The EnableAsync annotation should allow spring boot to do Asynchronous processing
// Meaning that multiple functions can run at the same time
// for example VariationGenerator and the rest of the server
// without this, the server will wait variationGenerator to finish
// its job before doing anything else like user login, preventing the server from working probably
// and EnableScheduling will trigger the method to run
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ForexApiApplication {



	public static void main(String[] args) {
		SpringApplication.run(ForexApiApplication.class, args);
	}

}
