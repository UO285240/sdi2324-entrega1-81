package com.uniovi.sdi2324entrega181.pageobjects;

import com.uniovi.sdi2324entrega181.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_PostView extends PO_NavView{

    /**
     * Método para obtener una publicación a partir del usuario actual, titulo y texto
     */
    public static boolean getPost(WebDriver driver, String userEmail, String titlePost, String textPost) {
        // buscar entre las publicaciones
        for (WebElement card : driver.findElements(By.cssSelector(".card.mb-4.shadow-sm"))) {
            String cardTitle = card.findElement(By.className("card-title")).getText();
            String cardText = card.findElement(By.className("card-text")).getText();
            String cardAuthor = card.findElement(By.xpath(".//p[contains(text(), 'Autor: ')]")).getText();

            if (cardTitle.contains(titlePost) && cardText.contains(textPost) && cardAuthor.contains(userEmail)) {
                // se encuentra la publicación
                return true;
            }
        }
        return false;
    }






}
