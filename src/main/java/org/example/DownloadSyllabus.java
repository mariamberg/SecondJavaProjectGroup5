package org.example;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.example.Main.logger;

public class DownloadSyllabus {

    public static void downloadSyllabus(){
        // Start the program and log in
        Main.chromeDriver();

        // Click the search button
        $(By.cssSelector("button.ltu-search-btn")).shouldBe(visible).click();

        // Search for the course
        $(By.id("cludo-search-bar-input")).shouldBe(visible).setValue("I0015N").pressEnter();

        // Click the course, has to click on Våren 2024 because it is mislabelled
        $(byText("Våren 2024")).shouldBe(visible).click();

        // Click the link to the syllabus
        $(By.linkText("Kursplan")).shouldBe(visible).click();

        // Find the link to the PDF file
        SelenideElement pdfLink = $(By.cssSelector("a.utbplan-pdf-link"));

        try {
            // Click the link to download the file
            File downloadedFile = pdfLink.download();

            // Move the downloaded file to the target/downloads directory with the name "kursplan.pdf"
            Files.move(downloadedFile.toPath(), new File("target/downloads/kursplan.pdf").toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("File downloaded successfully");
        } catch (IOException e) {
            logger.error("File download failed" + e.getMessage());
        }

    }
}
