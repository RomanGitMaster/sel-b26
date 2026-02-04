package com.roma.selenium.tests.week1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Day4_ExplicitWaits {
    /**
     * Goal: learn how to wait properly
     * visibility
     * clickable
     * presence
     * text/attribute change
     * trying to understand  Thread.sleep() — bad option, а implicit wait — trap.
     */
    public static void main(String[] args) {
        //testing webpage
        String url1 = "https://the-internet.herokuapp.com/dynamic_loading/1";
        String url2 = "https://the-internet.herokuapp.com/dynamic_loading/2";

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.manage().window().maximize();
            driver.get(url1);

            //#1 Get onfirst url provided and Press on Start button, use wait and print the displayed message
            //(Notice: using visibilityOfElementLocated)
            By startButton = By.xpath("//button[text()='Start']");
            wait.until(ExpectedConditions.elementToBeClickable(startButton));
            driver.findElement(startButton).click();
            By message = By.xpath("//div[@id='finish']/h4");
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(message)).getText();
            System.out.println("Page 1 message text: " + text);

            //#2 Navigate to second url provided and Press on Start button, use wait and print the displayed message
            //(Notice: element renders in DOM after fact of pressing the button - using presenceOfElementLocated +
            // visibilityOfElementLocated)
            driver.navigate().to(url2);
            wait.until(ExpectedConditions.elementToBeClickable(startButton));
            driver.findElement(startButton).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(message));
            //System.out.println(driver.findElement(message).getText());
            String text2 = wait.until(ExpectedConditions.visibilityOfElementLocated(message)).getText();
            System.out.println("Page 2 message text: " + text2);
        } finally {
            if (driver != null) driver.quit();
        }
/***
 * Why not Thread.sleep()
 * -is not waiting for elements to be found
 * -is not waiting for conditions to be met for these elements
 * -just slows the tests and not solving possible root cause of the exception that might appear
 */
    }
}
