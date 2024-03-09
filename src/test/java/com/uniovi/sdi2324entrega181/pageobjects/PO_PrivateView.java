package com.uniovi.sdi2324entrega181.pageobjects;

import com.uniovi.sdi2324entrega181.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_PrivateView extends PO_NavView {


    /**
     * Método para loguearse. Va al formulario de login y rellena el formulario
     */
    static public void doLogin(WebDriver driver, String usernamep, String passwordp){
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, usernamep, passwordp);
    }

    /**
     * Método para que el usuario autenticado se desconecte
     */
    static public void doLogout(WebDriver driver){
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }


    /**
     * Método para pinchar y desplegar el menú de usuarios
     */
    static public void doClickMenuUsers(WebDriver driver){

        //Pinchamos en la opción de menú de usuarios: //li[contains(@id, 'users-menu')]/a
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='my-navbarColor02']/ul[1]/li[2]");
        elements.get(0).click();
    }

    /**
     * Método para listar a los usuarios
     */
    static public void doClickListUsers(WebDriver driver){

        //Pinchamos en la opción de menú de usuarios: //li[contains(@id, 'users-menu')]/a
        doClickMenuUsers(driver);

        //Pinchamos en la opción de lista de usuarios
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'user/list')]");
        elements.get(0).click();
    }


    /**
     * Método para contar el total de usuarios en un listado con pagincación
     */
    public static int getNumOfUsers(WebDriver driver, int numOfPages) {

        // por cada página, coger el número de elementos
        int elements = 0;

        // recorrer el resto de páginas e ir añadiendo los elementos de cada una
        for (int i = 1; i <= numOfPages; i++){
            irAPagina(driver, i);
            elements += contarElementosEnPagina(driver);
        }

        return elements;
    }

    /**
     * Toma un WebDriver y un número de página y hace clic en el enlace de paginación correspondiente para ir a esa página
     */
    static public void irAPagina(WebDriver driver, int numPag){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.get(numPag).click();
    }

    /**
     * Espera a que se carguen los elementos en la página actual y devuelve su número
     */
    static public int contarElementosEnPagina(WebDriver driver){
        List<WebElement> elementsList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        System.out.println(elementsList.size());
        return elementsList.size();
    }

}

