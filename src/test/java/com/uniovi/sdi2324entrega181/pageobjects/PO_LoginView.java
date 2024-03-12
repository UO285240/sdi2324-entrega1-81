package com.uniovi.sdi2324entrega181.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_LoginView extends PO_View{

    static public void fillLoginForm(WebDriver driver, String usernamep, String passwordp) {
        WebElement username = driver.findElement(By.name("username"));
        username.click();
        username.clear();
        username.sendKeys(usernamep);
        WebElement password = driver.findElement(By.name("password"));
        password.click();
        password.clear();
        password.sendKeys(passwordp);

        //Pulsar el boton de Login.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }


}
