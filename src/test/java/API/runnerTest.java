package API;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"API"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber reports room data.html",
                "junit:target/cucumber-reports/cucumber reports room data.xml",
                "json:target/cucumber-reports/cucumber reports room data.json"
        }
)

public class runnerTest extends AbstractTestNGCucumberTests {
}