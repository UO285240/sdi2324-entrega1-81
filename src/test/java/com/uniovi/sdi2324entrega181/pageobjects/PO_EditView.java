package com.uniovi.sdi2324entrega181.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_EditView extends PO_View{

    static public void fillForm(WebDriver driver, String emailp, String namep, String lastnamep) {
        WebElement email = driver.findElement(By.name("email"));
        email.click();
        email.clear();
        email.sendKeys(emailp);
        WebElement name = driver.findElement(By.name("name"));
        name.click();
        name.clear();
        name.sendKeys(namep);
        WebElement lastname = driver.findElement(By.name("lastName"));
        lastname.click();
        lastname.clear();
        lastname.sendKeys(lastnamep);
        //Pulsar el boton de Confirmar.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
}
