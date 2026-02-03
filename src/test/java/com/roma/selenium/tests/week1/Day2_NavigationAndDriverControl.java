package com.roma.selenium.tests.week1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Day2_NavigationAndDriverControl {

    public static void main(String[] args) {

        String googleUrl = "https://google.com";
        String wikiUrl = "https://wikipedia.org";

        WebDriver driver = new ChromeDriver();

        try {
            driver.get(googleUrl);
            driver.navigate().to(wikiUrl);
            driver.navigate().back();
            driver.navigate().forward();
            driver.navigate().refresh();
            System.out.println("Current page tile is: " + driver.getTitle());
            System.out.println("Current page URL is: " + driver.getCurrentUrl());
        } finally {
            driver.quit();
        }
    }
}