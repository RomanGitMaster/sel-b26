package com.roma.selenium.tests.week2;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//Website is not suiting the task for demonstration...Part A is not matching  the website functionality
public class Day3_ExceptionsAndRetry {

    /**
     * Goal:
     * - catch typical Selenium Exceptions
     * - do retry without while(true)
     * - understand when retry is needed and when it is not
     */


    private static final String URL1 = "https://the-internet.herokuapp.com/entry_ad";
    private static final String URL2 = "https://the-internet.herokuapp.com/dynamic_controls";
    private static WebDriverWait wait;
    private static final Duration defaultTimeout = Duration.ofSeconds(8);
    private static WebDriver driver;


    public static void main(String[] args) throws InterruptedException {


        driver = new ChromeDriver();
        driver.manage().window().maximize();
        try {
//Part A
            driver.get(URL1);
            By es = By.xpath("//a[text()='Elemental Selenium']");
            By modalClose = By.xpath("//div[@class='modal-footer']/p");
            try {
                waitClickable(es, defaultTimeout).click();


            } catch (ElementClickInterceptedException e) {
                log("click intercepted, closing modal");
                driver.findElement(modalClose).click();

                waitClickable(es, defaultTimeout).click();

                log("retry click succeeded");
            }

            //Part B/
            driver.navigate().to(URL2);
            By cbLocator1 = By.cssSelector("div#checkbox input");
            By cbLocator2 = By.cssSelector("input#checkbox");
            By removeBtn = By.xpath("//button[text()='Remove']");
            By addBtn = By.xpath("//button[text()='Add']");
            WebElement cb = driver.findElement(cbLocator1);
            waitClickable(removeBtn, defaultTimeout).click();
            waitInVisiable(cbLocator1);
            try {
                cb.isDisplayed();
            } catch (StaleElementReferenceException s) {
                log("stale element detected, re-locating");
                waitClickable(addBtn, defaultTimeout).click();
                waitVisiable(cbLocator2);
                cb = driver.findElement(cbLocator2);
                log("retry action succeeded,checkbox selected = " + cb.isSelected());

            }
        } finally {
            if (driver != null) driver.quit();
        }
    }


    private static void log(String msg) {
        System.out.println("[W2D3] " + msg);
    }

    private static WebElement waitClickable(By locator, Duration timeout) {
        wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private static void safeClick(By locator, int retries) {
        try {
            waitClickable(locator, defaultTimeout).click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            if (retries < 0) {
                log("retrying click, retries left = " + retries);
                safeClick(locator, retries - 1);
            } else {
                throw e;
            }
        }
    }

    private static WebElement waitVisiable(By locator) {
        wait = new WebDriverWait(driver, defaultTimeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static boolean waitInVisiable(By locator) {
        wait = new WebDriverWait(driver, defaultTimeout);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
