package org.example;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.example.Main.logger;

public class ExaminationDate {
    // Create a variable to store the examination date
    public static String examinationDate = "";

    public static void checkFinalExaminationDate (){
        // Start the program and log in
        Main.startProgramLogIn();
        // Click on Tentamen
        $(byXpath("//a[contains(text(),'Tentamen')]")).click();

        // Click on Tentamensschema
        $(byXpath("//a[contains(text(),'Tentamensschema')]")).click();

        // Switch to the new tab
        switchTo().window(1);

        // Click on the search field and search for the course
        $("#enkel_sokfalt").setValue("I0015N").pressEnter();

        // Click on the course
        $(byLinkText("I0015N-VT23-47000-, Test av IT-system vt234 50")).click();

        // Switch to the new tab
        switchTo().window(2);

        // Save the examination date
        examinationDate = $(By.xpath("//td[contains(text(), '30 Maj')]")).getText();

        // Take a screenshot
        try {
            // Take a screenshot and save it to a file
            File screenshotFile = Screenshots.takeScreenShotAsFile();

            // Save the screenshot to a specific directory with a specific name
            FileUtils.copyFile(screenshotFile, new File("target/screenshots/final_examination.jpeg"));
            logger.info("Screenshot saved to: target/screenshots/final_examination.jpeg");
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage());
        }
        // Close the tab
        switchTo().window(0);
        // Log out
        Main.logOut();
    }
}
