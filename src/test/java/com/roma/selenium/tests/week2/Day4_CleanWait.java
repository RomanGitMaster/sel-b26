package com.roma.selenium.tests.week2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Day4_CleanWait {


    private static final String URL = "https://the-internet.herokuapp.com/dynamic_controls";
    private static final Duration timeout = Duration.ofSeconds(8);
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, timeout);
        driver.manage().window().maximize();
        try {
            //part A
            driver.get(URL);
            By cbLocator = By.cssSelector("div#checkbox input");
            By removeBtn = By.xpath("//button[text()='Remove']");
            By addBtn = By.xpath("//button[text()='Add']");

            waitClickable(removeBtn).click();
            log("checkbox removed");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(cbLocator));
            waitClickable(addBtn).click();
            log("checkbox added back");

            //Part B
            By enableBtn = By.xpath("//form[@id='input-example']//button");
            By input = By.cssSelector("#input-example input");
            waitClickable(enableBtn).click();
            waitClickable(input).sendKeys("Roma");
            log("input enabled and typed Roma");
        } finally {
            if (driver != null) driver.quit();
        }

    }


    private static void log(String msg) {
        System.out.println("[W2D4] " + msg);
    }

    private static WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
