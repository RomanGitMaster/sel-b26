package com.roma.selenium.tests.week1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Day1_OpenClose {
    public static void main(String[] args) {
        String url = "https://google.com";
        WebDriver driver = new ChromeDriver();
        try {
            //get on provided website
            driver.get(url);
            //print the title of the page
            System.out.println("Page title is: " + driver.getTitle());
            //close the browser and all windows
        } finally {
            driver.quit();
        }

    }
}
