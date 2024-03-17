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


    /**
     * Método para obtener una publicación a partir del usuario actual, título, texto y la presencia de una imagen adjunta.
     */
    public static boolean getPostWithImage(WebDriver driver, String userEmail, String titlePost, String textPost) {
        // buscar entre las publicaciones
        for (WebElement card : driver.findElements(By.cssSelector(".card.mb-4.shadow-sm"))) {
            String cardTitle = card.findElement(By.className("card-title")).getText();
            String cardText = card.findElement(By.className("card-text")).getText();
            String cardAuthor = card.findElement(By.xpath(".//p[contains(text(), 'Autor: ')]")).getText();

            // comprobar si el autor, título y texto coinciden
            if (cardTitle.contains(titlePost) && cardText.contains(textPost) && cardAuthor.contains(userEmail)) {
                // se encuentra la publicación, comprobar si contiene una imagen
                WebElement imageElement = card.findElement(By.tagName("img"));
                String imageURL = imageElement.getAttribute("src");
                if (imageURL != null && !imageURL.isEmpty()) {
                    // se encuentra la publicación con imagen
                    return true;
                }

            }
        }
        // no tiene imagen
        return false;
    }







}
