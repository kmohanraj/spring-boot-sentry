package com.example.demo;

import io.sentry.spring.SentryServletContextInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Controller
@SpringBootApplication
public class SpringErrorApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringErrorApplication.class);
	@Bean
	public HandlerExceptionResolver sentryExceptionResolver() {
		return new io.sentry.spring.SentryExceptionResolver();
	}

	@Bean
	public SentryServletContextInitializer sentryServletContextInitializer() {
		return new io.sentry.spring.SentryServletContextInitializer();
	}

	@GetMapping("/")
	String home(@RequestParam(required = false) String name) {
		logger.info("SENTRY ERROR INFO");
		return "Hello " + name.toUpperCase();
	}

	@RequestMapping("/handled")
	@ResponseBody
	String handleError() {
		String someLocalVariable = "stack locals";

		try {
			int number = 1/0;
		} catch (Exception e) {
			logger.error("Caught exception!", e);
		}
		return "SUCCESS";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringErrorApplication.class, args);
	}
}
