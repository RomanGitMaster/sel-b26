package com.roma.selenium.tests.week2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v144.network.model.WebTransportCreated;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Day1_WaitPatterns {

    /**
     * GOAL
     * Learn to write controolled, readable waits using two minimal helpers:
     * -waitVisibile(By)
     * -waitClickability(By)
     * and use them in real scanario
     */

    private static final String URL1 = "https://the-internet.herokuapp.com/dynamic_loading/1";
    private static final String URL2 = "https://the-internet.herokuapp.com/add_remove_elements/";
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        try {
//Part A
            driver.get(URL1);
            By startBtn = By.xpath("//div[@id='start']/button");
            waitClickable(startBtn).click();
            By hello = By.xpath("//div[@id='finish']/h4");
            String dynamicTxt = waitVisible(hello).getText();
            log("dynamic1 text : " + dynamicTxt);
//Part B
            driver.navigate().to(URL2);
            By addBtn = By.xpath("//button[text()='Add Element']");
            for (int i = 1; i <= 3; i++) {
                waitClickable(addBtn).click();
            }
            By deleteLocator = By.xpath("//button[text()='Delete']");
            List<WebElement> deletes = driver.findElements(deleteLocator);
            int size = deletes.size();
            log("delete buttons count: " + size);
            for (int i = 1; i < size; i++) {
                waitClickable(deleteLocator).click();
            }
            deletes = driver.findElements(deleteLocator);
            int newSize = deletes.size();
            log("delete buttons count after removal: " + newSize);

        } finally {
            if (driver != null) driver.quit();
        }

    }


    private static void log(String msg) {
        System.out.println("[W2D1] " + msg);
    }

    private static WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));

    }
}
