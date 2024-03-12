package com.uniovi.sdi2324entrega181.pageobjects;

import com.uniovi.sdi2324entrega181.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
     * Método para listar las todas publicaciones del sistema (solo los admins puede)
     */
    static public void doClickAdminListPosts(WebDriver driver){

        //Pinchamos en la opción de menú de publicaciones: //li[contains(@id, 'post-menu')]/a
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='my-navbarColor02']/ul[1]/li[3]");
        elements.get(0).click();

        //Pinchamos en la opción de lista de publicaciones
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'post/adminList')]");
        elements.get(0).click();
    }

    /**
     * Método para cambiar el estado de una publicación a censurada
     *
     */
    static public WebElement changePostState(WebDriver driver, String state) {

        // Encontrar el primer postId disponible en la lista de publicaciones
        WebElement postRow = driver.findElement(By.xpath("//tbody/tr[1]"));

        // Encontrar el elemento select y cambiar el estado a "Censurada"
        Select stateDropdown = new Select(postRow.findElement(By.tagName("select")));
        stateDropdown.selectByValue(state);

        // Hacer clic en el botón de cambiar estado
        postRow.findElement(By.tagName("button")).click();

        // Esperar a que se actualice la página y verificar que el estado ha cambiado correctamente
        return driver.findElement(By.xpath("//tbody/tr[1]/td[4]"));
    }


    /**
     * Método para cambiar el estado de la primera publicación del usuario pasado como parámetro
     */
    static public void changeStateFirstPost(WebDriver driver, String userEmail, String state) {
        // Navegar a la página de listado de todas las publicaciones
        doClickAdminListPosts(driver);

        // Encontrar la primera publicación del usuario con el correo electrónico especificado y censurarla
        List<WebElement> posts = driver.findElements(By.xpath("//tbody/tr"));
        for (WebElement post : posts) {
            WebElement userEmailElement = post.findElement(By.xpath("./td[2]"));
            String email = userEmailElement.getText().trim();
            if (email.equalsIgnoreCase(userEmail)) {
                // censuro la publicación
                WebElement selectElement = post.findElement(By.tagName("select"));
                selectElement.sendKeys(state);
                WebElement buttonElement = post.findElement(By.tagName("button"));
                buttonElement.click();
                break;
            }
        }
    }

    /**
     * Método para contar el número de publicaciones en la página.
     */
    static public int countPosts(WebDriver driver) {
        // Encontrar todos los elementos de la tabla que representan las publicaciones
        List<WebElement> postRows = driver.findElements(By.xpath("//table[@class='table table-hover']/tbody/tr"));

        // Contar el número de filas en la tabla
        return postRows.size();
    }



    /**
     * Método para verificar si hay alguna publicación censurada en el listado propio de publicaciones
     */
    static public boolean isCensoredPostPresent(WebDriver driver) {
        // Navegar a la página de listado de publicaciones del usuario
        doClickListPosts(driver);

        // Encontrar todas las publicaciones en la tabla
        List<WebElement> posts = driver.findElements(By.xpath("//tbody/tr"));

        // Iterar sobre cada publicación y verificar si alguna está censurada
        for (WebElement post : posts) {
            WebElement statusElement = post.findElement(By.xpath("./td[4]"));
            String status = statusElement.getText().trim();
            if (status.equalsIgnoreCase("CENSURADA")) {
                // Se encontró una publicación censurada
                return true;
            }
        }

        // Si no se encontró ninguna publicación censurada
        return false;
    }

    /**
     * Método para verificar que no aparece ninguna publicación con el estado pasado como parámetro
     * en el listado de publicaciones.
     */
    static public boolean isStatePostPresent(WebDriver driver, String state) {
        // Encontrar todos los elementos de texto que contienen el estado de la publicación
        List<WebElement> statusElements = driver.findElements(By.cssSelector("td.card-text"));

        // Iterar sobre los elementos de estado para verificar si alguno tiene el estapo moderado
        for (WebElement element : statusElements) {
            if (element.getText().contains(state)) {
                return true;
            }
        }

        return false;
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

    static public void doClickFriendDetails(WebDriver driver, String xpath){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",xpath);
        elements.get(0).click();
    }

     static public void checkRecommendation(WebDriver driver, String button, String text, String checkTest) {
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",button);
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free",text);
        String checkText = checkTest;
        Assertions.assertEquals(checkText, elements.get(0).getText());
    }

    static public void clickAdminUserList(WebDriver driver){
        //Pincho en el deslplegable del administrador
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "/html/body/nav/div/ul[1]/li[2]");
        elements.get(0).click();
        //Pinchamos en la opción de lista de usuarios
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'user/administratorList')]");
        elements.get(0).click();
    }

     static public void deleteAnUser(WebDriver driver,String number) {
        //selecciono el checkbox del primero
         List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                 "/html/body/div/div/form/table/tbody/tr["+number+"]/td[5]/input");
         elements.get(0).click();
        //doy click en el botón de borrar
         elements = PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/div/div/button");
         elements.get(0).click();

    }

    public static void deleteThreeFirstUsers(WebDriver driver) {
        //selecciono el checkbox del primero
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "/html/body/div/div/form/table/tbody/tr[2]/td[5]/input");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free",
                "/html/body/div/div/form/table/tbody/tr[3]/td[5]/input");
        elements.get(0).click();
        elements = PO_View.checkElementBy(driver, "free",
                "/html/body/div/div/form/table/tbody/tr[4]/td[5]/input");
        elements.get(0).click();
        //doy click en el botón de borrar
        elements = PO_View.checkElementBy(driver, "free", "/html/body/div/div/form/div/div/button");
        elements.get(0).click();
    }
    /**
     * Método para listar la lista de solicitudes de amistad
     */
    public static void doClickListFriendships(WebDriver driver) {
        //Pinchamos en la opción de menú de usuarios: //li[contains(@id, 'users-menu')]/a
        doClickMenuUsers(driver);

        //Pinchamos en la opción de lista de usuarios
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'friendship/list')]");
        elements.get(0).click();
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

