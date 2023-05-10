package org.example;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static org.example.Main.logger;

public class CreateTranscript {

    private static boolean newTranscript = false;
    public static void CreateTranscript() {
        // Start the program and log in
        Main.startProgramLogIn();

        // Click on Intyg
        $(byXpath("//a[contains(text(),' Intyg »')]")).shouldBe(visible).click();

        // Switch to the new tab
        switchTo().window(1);

        // Choose to log in with your institution
        $(By.xpath("//span[text()='Access through your institution']")).shouldBe(visible).click();

        // Search for Luleå
        $(By.id("searchinput")).setValue("Luleå").pressEnter();

        // Click on Luleå tekniska universitet
        $(By.cssSelector("a.institution.identityprovider.bts-dynamic-item[data-href='https://idp.ltu.se/idp/shibboleth'] li")).shouldBe(visible).click();

        // Choose Swedish
        try {
            $(By.linkText("På svenska")).shouldBe(visible).click();
        } catch (ElementNotFound e) {
            logger.info("Browser already in Swedish" + e.getMessage());
        }

        // Click on Intyg
        $(By.linkText("Intyg")).shouldBe(visible).click();


        // Click on Skapa Intyg
        if ($(By.cssSelector("button.btn.btn-ladok-brand")).shouldBe(visible).isDisplayed()) {
            $(By.cssSelector("button.btn.btn-ladok-brand")).shouldBe(visible).click();
            logger.info("The button Skapa Intyg is there");
        } else {
            logger.warn("The button Skapa Intyg is not there");
        }

        // Choose Registreringsintyg in the dropdown menu
        $(By.id("intygstyp")).selectOption("Registreringsintyg");

        // Click on Skapa
        $(By.xpath("//span[text()='Skapa']")).shouldBe(visible).click();

        // Get today's date in the desired format
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = today.format(formatter);

        // Compares transcript date with today's date
        if (Selenide.$(By.xpath("//*[contains(text(), '" + todayDate + "')]")).shouldBe(visible).exists()) {
            logger.info("Transcript created on " + todayDate + " is found on the page.");
            newTranscript = true;
            System.out.println(newTranscript);
        } else {
            logger.error("Transcript not found");
        }
        switchTo().window(0);
        Main.logOut();
    }


    // Getter for the boolean newTranscript
    public static boolean getNewTranscript() {
        return newTranscript;
    }
}

