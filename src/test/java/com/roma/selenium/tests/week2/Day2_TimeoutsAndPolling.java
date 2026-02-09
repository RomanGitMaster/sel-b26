package com.roma.selenium.tests.week2;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Day2_TimeoutsAndPolling {


    /**
     * Goal:
     * Learn how the timeout works
     * Use poling
     * do anti-flaki pattern: right conditions(locators + correct wait) + readable logs
     */


    private static final String URL1 = "https://the-internet.herokuapp.com/dynamic_loading/2";
    private static final String URL2 = "https://the-internet.herokuapp.com/add_remove_elements/";

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Duration defaultTimeout = Duration.ofSeconds(8);
    private static Duration polling = Duration.ofMillis(500);
    private static Duration shortTimeout = Duration.ofSeconds(3);


    public static void main(String[] args) {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            //Part A
            driver.get(URL1);
            By startBtn = By.cssSelector("div#start button");
            By hello = By.cssSelector("div#finish h4");

            waitClickable(startBtn, defaultTimeout, polling).click();
            String text = waitVisible(hello, defaultTimeout, polling).getText();
            log("dynamic2 loaded in time, text= " + text);

            //Part B
            try {
                By nonExisting = By.id("does-not-exist");
                waitVisible(nonExisting, shortTimeout, polling);
            } catch (TimeoutException e) {
                log("timeout occurred as expected in Part B ");
            }


            //Part C
            driver.navigate().to(URL2);
            By addBtn = By.xpath("//button[text()='Add Element']");
            for (int i = 1; i <= 5; i++) {
                waitClickable(addBtn, defaultTimeout, polling).click();
            }
            By deleteBtn = By.xpath("//button[text()='Delete']");
            List<WebElement> deletes = driver.findElements(deleteBtn);
            int startSize = deletes.size();
            log("delete buttons count = " + startSize);
            while (driver.findElements(deleteBtn).size() > 0) {
                waitClickable(deleteBtn, defaultTimeout, polling).click();
            }
            int afterSize = driver.findElements(deleteBtn).size();
            log("delete buttons count after cleanup = " + afterSize);


        } finally {
            if (driver != null) driver.quit();
        }
    }


    private static void log(String msg) {
        System.out.println("[W2D2] " + msg);
    }

    private static WebElement waitVisible(By locator, Duration timeout, Duration polling) {
        wait = new WebDriverWait(driver, timeout);
        wait.pollingEvery(polling);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static WebElement waitClickable(By locator, Duration timeout, Duration polling) {
        wait = new WebDriverWait(driver, timeout);
        wait.pollingEvery(polling);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));

    }
}
