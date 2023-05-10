package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        //Starts the program
        startProgramLogIn();
        //Logs out
        logOut();
    }

    public static void chromeDriver() {
        try {
            //Locates the chromedriver.exe file
            System.setProperty("webdriver.chrome.driver", "src/main/java/chromedriver.exe");
            //Maximizes the window
            Configuration.startMaximized = true;
            //Opens the website
            open("https://www.ltu.se");

            //Accepts the cookies
            $(By.id("CybotCookiebotDialogBodyButtonDecline")).shouldBe(visible).click();

            logger.info("Driver initialized and cookies accepted");
        } catch (Exception e) {
            logger.error("Error while initializing driver: " + e.getMessage());
        }
    }

    public static void startProgramLogIn() {
        // Initialize the variables for email and password
        String email = "";
        String password = "";

        // Import the credentials from the json file
        try {
            // Locate the json file
            File jsonFile = new File("C:\\temp\\ltu.json");
            ObjectMapper objectMapper = new ObjectMapper();
            // Read the json file
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            // Get the email and password from the json file
            email = jsonNode.get("ltuCredentials").get("email").asText();
            password = jsonNode.get("ltuCredentials").get("password").asText();
            logger.info("Credentials successfully imported");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        // Initialize the chrome driver
        chromeDriver();

        try {
            // Go to student portal
            $(byXpath("//a[@href='/student' and @onclick=\"gaClickEvent('First page Extra links', 'Click', '/student');\"]")).shouldBe(visible).click();
            $(byXpath("//a[@href='https://portal.ltu.se/group/student/start' and @onclick=\"gaClickEvent('First page Extra links', 'Click', 'https://portal.ltu.se/group/student/start');\"]")).click();

            // Enter username and password
            $("input[name='username']").shouldBe(visible).setValue(email);
            $("input[name='password']").setValue(password);

            // Click the login button
            $(byValue("LOGGA IN")).should(be(enabled)).click();
            logger.info("Logged in successfully");
        }
        catch (Exception e) {
            logger.error("Error while logging in: " + e.getMessage());
        }
    }

    public static void logOut () {
        try {
            // Click the avatar dropdown
            $(By.id("_145_userAvatar")).click();
            // Click the logout button
            Selenide.$(By.cssSelector("a[href='/c/portal/logout']")).click();
            logger.info("Logged out successfully");
        }
        catch (Exception e) {
            logger.error("Error while logging out: " + e.getMessage());
        }
    }
}
