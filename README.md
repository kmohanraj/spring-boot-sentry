##### Initialize the project
Dependency:
* Spring web

##### Integrated Sentry
in `pom.xml` add the following configuration

```
<dependency>
    <groupId>io.sentry</groupId>
    <artifactId>sentry-spring</artifactId>
    <version>1.7.30</version>
</dependency>
```
    
Add configuration file to `SpringErrorApplication`

```
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
```
##### Configure DSN
There are the following ways to configure DSN
add dsn config to `sentry.properties` file
```
dsn=https://public:private@host:port/1
```

##### Run the application

```
mvn spring-boot:run
```

THEN Call endpoint is,

```
http://localhost:8080/handled
```

AFTER show the success msg in browser and send error log to sentry.