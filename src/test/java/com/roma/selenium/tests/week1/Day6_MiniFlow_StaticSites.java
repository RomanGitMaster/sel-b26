package com.roma.selenium.tests.week1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Day6_MiniFlow_StaticSites {
    /**
     * Build a clean, readable “mini test flow” that:
     * navigates two sites
     * verifies state using isSelected() / getText()
     * prints clear logs
     * never relies on timing
     */


    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {

            //Part #1
            String url1 = "https://the-internet.herokuapp.com/checkboxes";
            driver.get(url1);
            By checkboxes = By.xpath("//form[@id='checkboxes']/input[@type='checkbox']");
            List<WebElement> boxes = driver.findElements(checkboxes);
            WebElement cb1 = boxes.get(0);
            WebElement cb2 = boxes.get(1);

            log("Checkbox 1 selected : " + cb1.isSelected());
            log("Checkbox 2 selected : " + cb2.isSelected());
            setCheckbox(cb1, true);
            setCheckbox(cb2, false);
            // after click Dom might rerender, and for not to catch the stale we simply refind the element
            log("Checkbox 1 selected : " + boxes.get(0).isSelected());
            log("Checkbox 2 selected : " + boxes.get(1).isSelected());

            //Part #2
            String url2 = "https://the-internet.herokuapp.com/dropdown";
            driver.get(url2);
            Select s = new Select(driver.findElement(By.cssSelector("select#dropdown")));
            s.selectByVisibleText("Option 2");
            //String option = driver.findElement(By.xpath("//select[@id='dropdown']/option[@selected='selected']")).getText();
            String option = s.getFirstSelectedOption().getText();//more readable option for checks
            log("Dropdown selected : " + option);

        } finally {
            if (driver != null) driver.quit();
        }
    }


    private static void setCheckbox(WebElement el, boolean shouldBeSelected) {
        if (el.isSelected() != shouldBeSelected) {
            el.click();
        }
    }


    private static void log(String message) {
        System.out.println("[Day 6] " + message);
    }
}
