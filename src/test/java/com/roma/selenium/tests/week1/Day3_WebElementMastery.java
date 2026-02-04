package com.roma.selenium.tests.week1;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Day3_WebElementMastery {

//Objective:Understand WebElement properties, the difference between text vs value, and how a stale element happens

    public static void main(String[] args) {
        //testing webpage
        String url = "https://www.wikipedia.org";

        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get(url);

            //Task A : search for element and print attribute values and apply isEnabled and isDisplayed methods
            WebElement search = driver.findElement(By.xpath("//input[@id='searchInput']"));
            System.out.println("Tag name of search box is: " + search.getTagName());
            System.out.println("Is search box displayed? " + search.isDisplayed());
            System.out.println("Is search box enabled? " + search.isEnabled());
            System.out.println("Value of id attribute is: " + search.getAttribute("id"));
            System.out.println("Value of name attribute is: " + search.getAttribute("name"));
            System.out.println("Value of placeholder attribute is: " + search.getAttribute("placeholder"));

            //Task B: getText() vs getAttribute()
            search.sendKeys("Selenium (software)");
            System.out.println("By applying getText() method on input element we get: " + search.getText());
            System.out.println("By applying getAttribute() method on input element we get: " + search.getAttribute("value"));
            //Remark: on input elements we can only apply getAttribute method to get the value we sent using sendKeys()

            //Task C — Trigger a Stale Element (On Purpose) : Refresh the page and try to interact with old element reference
            WebElement searchButton = driver.findElement(By.xpath("//button[contains(@class,'pure-button')]"));
            driver.navigate().refresh();
            try {
                searchButton.click();
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element detected");
            }

            //Task D — Fix It Correctly : Re-locate the element after refresh
            searchButton = driver.findElement(By.xpath("//button[contains(@class,'pure-button')]"));
            searchButton.click();

        } finally {
            if (driver != null) driver.quit();
        }


    }
}
