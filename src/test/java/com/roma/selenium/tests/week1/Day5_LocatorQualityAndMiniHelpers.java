package com.roma.selenium.tests.week1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Day5_LocatorQualityAndMiniHelpers {

    /**
     * Goal:
     * to write stable unique locators.
     * to be able to prove uniqueness of locators through findElements().size();
     * move out repeating things into minimal helper methods
     */

    private static final String WIKI_URL = "https://www.wikipedia.org/";
    private static final String HEROKUAPP_URL = "https://the-internet.herokuapp.com/checkboxes";
    private static final String EXPECTED_DATA = "Selenium (software)";
    private static WebDriver driver;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        try {
            driver.get(WIKI_URL);

            //Task A
            By searchBox = By.cssSelector("input#searchInput");
            printCount(searchBox);
            By searchButton = By.cssSelector("button[class*='pure-button']");
            printCount(searchButton);

            driver.findElement(searchBox).sendKeys(EXPECTED_DATA);
            driver.findElement(searchButton).click();

            By topicTitle = By.cssSelector("h1#firstHeading span");
            wait.until(ExpectedConditions.visibilityOfElementLocated(topicTitle));
            printCount(topicTitle);

            String topic = driver.findElement(topicTitle).getText();
            System.out.println(topic.trim().equalsIgnoreCase(EXPECTED_DATA) ? "Topic verified: " + topic : "Topic is not verified: " + topic);

            //Task B:

            driver.navigate().to(HEROKUAPP_URL);
            By checkboxes = By.cssSelector("input[type='checkbox']");
            List<WebElement> boxes = driver.findElements(checkboxes);
            WebElement checkbox1 = boxes.get(0);
            WebElement checkbox2 = boxes.get(1);

            System.out.println("Before checkbox 1 " + (ensureSelected(checkbox1) ? " is selected" : " is not selected"));
            System.out.println("Before checkbox 2 " + (ensureSelected(checkbox2) ? " is selected" : " is not selected"));
            checkbox1.click();
            System.out.println("After checkbox1 is selected? " + ensureSelected(checkbox1));
            checkbox2.click();
            System.out.println("After checkbox2 is deselected? " + ensureDeselected(checkbox2));
        } finally {
            if (driver != null) driver.quit();
        }
    }

    private static void printCount(By locator) {
        if (driver != null) {
            int size = driver.findElements(locator).size();
            System.out.println((size != 0 && size < 2) ? "Locator is unique:" + size : "Locator is not unique:" + size);
        }
    }

    private static boolean ensureSelected(WebElement el) {
        return el.isSelected();
    }

    private static boolean ensureDeselected(WebElement el) {
        boolean state = false;
        if (!el.isSelected()) state = true;
        return state;
    }

}
