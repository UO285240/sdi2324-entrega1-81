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
     * Método para listar las publicaciones
     */
    static public void doClickListPosts(WebDriver driver){

        //Pinchamos en la opción de menú de publicaciones: //li[contains(@id, 'post-menu')]/a
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='my-navbarColor02']/ul[1]/li[3]");
        elements.get(0).click();

        //Pinchamos en la opción de lista de publicaciones
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/list')]");
        elements.get(0).click();
    }


    /**
     * Método para acceder a crear las publicaciones
     */
    static public void doClickAddPost(WebDriver driver){

        //Pinchamos en la opción de menú de publicaciones: //li[contains(@id, 'post-menu')]/a
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='my-navbarColor02']/ul[1]/li[3]");
        elements.get(0).click();

        //Pinchamos en la opción de lista de publicaciones
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/add')]");
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
     * Método para contar el total de publicaciones de un usuario en un listado con paginación
     */
    public static int getPostsOfUser(WebDriver driver, int numOfPages, String email) {
        int totalPosts = 0;

        // Recorrer cada página
        for (int i = 1; i <= numOfPages; i++) {
            // Ir a la página correspondiente
            irAPagina(driver, i);

            // Contar las publicaciones de la página actual
            List<WebElement> posts = driver.findElements(By.cssSelector(".card.mb-4.shadow-sm"));
            for (WebElement post : posts) {
                // Verificar si el autor del post es "pedro@example.com"
                String author = post.findElement(By.xpath(".//p[contains(text(), 'Autor: ')]")).getText().replace("Autor: ", "").trim();
                if (author.equals(email)) {
                    totalPosts++;
                }
            }
        }

        return totalPosts;
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

    /**
     * Envía una solicitud de amistad
     */
    public static void sendFriendshipRequest(WebDriver driver, String receiver) {
        //WebElement sendRequestButton = driver.findElement(By.xpath("//tr[contains(td, '" + receiver + "')]/td/a/button[contains(text(), 'Enviar solicitud')]"));
        WebElement sendRequestButton = driver.findElement(By.id(receiver));
        sendRequestButton.click();
    }


    /**
     * Buscar un texto de búsqueda
     */
    public static void doSearch(WebDriver driver, String text){
        WebElement searchText = driver.findElement(By.name("searchText"));
        // borrar texto
        searchText.clear();

        // hacer consulta
        searchText.sendKeys(text);
        driver.findElement(By.cssSelector("button[type='submit'].btn.btn-primary")).click();


    }



    /**
     * Método para listar a los amigos
     */
    static public void doClickListFriends(WebDriver driver){

        //Pinchamos en la opción de menú de usuarios: //li[contains(@id, 'users-menu')]/a
        doClickMenuUsers(driver);

        //Pinchamos en la opción de lista de usuarios
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/list')]");
        elements.get(0).click();
    }

    static public void checkNumberOfFriends(WebDriver driver, int number){
        List<WebElement> friendshipList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//table[@id='friendshipList']//tbody/tr", PO_View.getTimeout());
        Assertions.assertEquals(number, friendshipList.size());
    }

    static public void checkDate(WebDriver driver, String fecha){
        List<WebElement> result = PO_View.checkElementBy(driver, "text", fecha);
        Assertions.assertEquals(fecha, result.get(0).getText());
    }

    static public void checkLastPost(WebDriver driver,String titulo, String xpath){
        By enlace = By.xpath(xpath);
        driver.findElement(enlace).click();
        List<WebElement> result = PO_View.checkElementBy(driver, "text", titulo);
        Assertions.assertEquals(titulo, result.get(0).getText());
    }


    /**
     * Método para comprobar si aparecen administradores en un listado paginado
     */
    public static boolean getListAdminAppears(WebDriver driver, int numOfPages) {
        String adminEmail = "admin@email.com";

        // Recorrer cada página
        for (int i = 1; i <= numOfPages; i++) {
            // Ir a la página correspondiente
            irAPagina(driver, i);

            List<WebElement> userRows = driver.findElements(By.xpath("//table[@id='usersTable']//tbody//tr"));
            for (WebElement row : userRows) {
                String userEmail = row.findElement(By.tagName("td")).getText();
                if (userEmail.equals(adminEmail)) {
                    return true;
                }
            }
        }

        return false;
    }

}

