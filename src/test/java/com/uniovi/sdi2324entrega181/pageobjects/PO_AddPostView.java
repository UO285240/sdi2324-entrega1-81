package com.uniovi.sdi2324entrega181.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

public class PO_AddPostView extends PO_NavView{


    static public void createPost(WebDriver driver, String title, String text){
        WebElement titleInput = driver.findElement(By.id("title"));
        titleInput.click();
        titleInput.clear();
        titleInput.sendKeys(title);

        WebElement textInput = driver.findElement(By.id("text"));
        textInput.click();
        textInput.clear();
        textInput.sendKeys(text);

        By boton = By.className("btn");
        driver.findElement(boton).click();

    }

    static public void createPostAndImage(WebDriver driver, String title, String text, String imagePath){
        WebElement titleInput = driver.findElement(By.id("title"));
        titleInput.click();
        titleInput.clear();
        titleInput.sendKeys(title);

        WebElement textInput = driver.findElement(By.id("text"));
        textInput.click();
        textInput.clear();
        textInput.sendKeys(text);

        if (imagePath != null && !imagePath.isEmpty()) {
            WebElement imageInput = driver.findElement(By.id("image"));
            imageInput.sendKeys(new File(imagePath).getAbsolutePath());
        }

        By boton = By.className("btn");
        driver.findElement(boton).click();

    }


}
