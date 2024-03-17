package com.uniovi.sdi2324entrega181.pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_LogView extends PO_View {

    public static void checkText(WebDriver driver, String text) {
        String checkText = text;
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    public static void checkLongText(WebDriver driver, String search, String expected) {
        List<WebElement> result = PO_View.checkElementBy(driver, "text", search);
        Assertions.assertEquals(expected, result.get(0).getText());
    }
}
