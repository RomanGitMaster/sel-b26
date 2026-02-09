package com.roma.selenium.tests.week1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Day7_MiniProject {
    /**
     * Goal:
     * Summary of week1
     * -proper structure
     * -locators (show uniqueness at least once with findElements().size())
     * WebElement methods (isDisplayed, isEnabled, isSelected, getText, getAttribute)
     * explicit waits
     * state-aware clicks
     */

    private static final String URL_A = "https://the-internet.herokuapp.com/dynamic_loading/2";
    private static final String URL_B = "https://the-internet.herokuapp.com/checkboxes";

    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        try {
            //Part A
            driver.get(URL_A);
            By startButton = By.cssSelector("div#start button");
            printCount(startButton);
            wait.until(ExpectedConditions.elementToBeClickable(startButton)).click();
            By msg = By.cssSelector("div#finish");
            wait.until(ExpectedConditions.presenceOfElementLocated(msg));
            By msgText = By.cssSelector("div#finish h4");
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(msgText)).getText();
            log("dynamic loading text: " + text);

            //Part B
            driver.navigate().to(URL_B);
            List<WebElement> checkboxes = driver.findElements(By.xpath("//form[@id='checkboxes']/input[@type='checkbox']"));
            WebElement cb1 = checkboxes.get(0);
            WebElement cb2 = checkboxes.get(1);
            log("checkbox 1 selected: " + cb1.isSelected());
            log("checkbox 2 selected: " + cb2.isSelected());
            setCheckbox(cb1, true);
            setCheckbox(cb2, false);
            log("checkbox 1 selected: " + checkboxes.get(0).isSelected());
            log("checkbox 2 selected: " + checkboxes.get(1).isSelected());

            //Part C
            WebElement title = driver.findElement(By.cssSelector("h3"));
            log("tagName is : " + title.getTagName());
            log("checkboxes title displayed: " + title.isDisplayed());
            log("text is : " + title.getText());
        } finally {
            if (driver != null) driver.quit();
        }
    }

    private static void log(String msg) {
        System.out.println("[Day7] " + msg);
    }

    private static void setCheckbox(WebElement cb, boolean shouldBeSelected) {
        if (cb.isSelected() != shouldBeSelected) {
            cb.click();
        }
    }


    private static void printCount(By locator) {
        if (driver != null) {
            int size = driver.findElements(locator).size();
            if (size == 0) {
                log("wrong locator : " + size);
            } else if (size == 1) {
                log("locator is unique : " + size);
            } else {
                log("locator is not unique : " + size);
            }
        }
    }
}
