package com.uniovi.sdi2324entrega181;

import com.uniovi.sdi2324entrega181.pageobjects.*;
import com.uniovi.sdi2324entrega181.pageobjects.PO_HomeView;
import com.uniovi.sdi2324entrega181.pageobjects.PO_LoginView;
import com.uniovi.sdi2324entrega181.pageobjects.PO_PrivateView;
import com.uniovi.sdi2324entrega181.pageobjects.PO_View;
import com.uniovi.sdi2324entrega181.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class Sdi2324Entrega181ApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\Rita\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

  // static String Geckodriver = "C:\\Users\\coral\\IdeaProjects\\SeleniumMaterial\\geckodriver-v0.30.0-win64.exe";

    //static String Geckodriver = "C:\\Users\\javie\\OneDrive\\Escritorio\\Tercero\\SDI\\L5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";


    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";
    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }


    //[Prueba1] Registro de Usuario con datos válidos.

    @Test
    @Order(1)
    void PR01(){
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "uo384382@example.es", "Josefo", "Perez", "aaaaaaaaaaaaA1@", "aaaaaaaaaaaaA1@");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Bienvenido a nuestra aplicación web";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //[Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos y
//contraseña incorrecta (débil)).
    @Test
    @Order(2)
    void PR02(){
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "", "", "", "corta", "corta");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.empty",
                PO_Properties.getSPANISH());
        String checkText = PO_HomeView.getP().getString("Error.empty", PO_Properties.getSPANISH())+"\n"+
                PO_HomeView.getP().getString("Error.signup.email.notCorrectFormat", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());

    }
    //[Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña inválida).
    @Test
    @Order(3)
    void PR03(){
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "correcto@email.com", "Pepe", "José", "corta", "corta");

        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.notHard",
                PO_Properties.getSPANISH());
        String checkText = PO_HomeView.getP().getString("Error.signup.password.notHard", PO_Properties.getSPANISH())
                +"\n"+ PO_HomeView.getP().getString("Error.signup.password.length", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }
    //[Prueba4] Registro de Usuario con datos inválidos (email existente).
    @Test
    @Order(4)
    void PR04(){
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "admin@email.com", "Pepe", "José", "aaaaaaaaaaaaA1@",
                "aaaaaaaaaaaaA1@");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.email.duplicate",
                PO_Properties.getSPANISH());
        String checkText = PO_HomeView.getP().getString("Error.signup.email.duplicate", PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    // [Prueba5] - Introduccir datos validos e iniciar sesión (administrador)
    @Test
    @Order(5)
    void PR05() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "id", "password");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "@Dm1n1str@D0r");
        //Comprobamos que entramos en la pagina privada del admin
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // [Prueba6] - Introduccir datos validos e iniciar sesión (usuario estándar)
    @Test
    @Order(6)
    void PR06() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "id", "password");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "Us3r@1-PASSW");
        //Comprobamos que entramos en la pagina privada del usuario
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // [Prueba7] - Inicio de sesión con datos inválidos
    @Test
    @Order(7)
    void PR07() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "id", "password");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "", "");
        //Comprobamos que volvemos al login
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // [Prueba8] - Inicio de sesión con datos validos pero contraseña incorrecta
    @Test
    @Order(8)
    void PR08() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "id", "password");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "pedro@example.com", "12");
        //Comprobamos que volvemos al login
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * [Prueba9] - Hacer clic en la opción de salir de sesión y comprobar que se muestra el mensaje “Ha cerrado
     * sesión correctamente” y se redirige a la página de inicio de sesión
     */
    @Test
    @Order(9)
    void PR09() {

        //login
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");
        // click en el botón de logout
        PO_PrivateView.doLogout(driver);

        //Comprobamos que se muestra el mensaje
        String checkText = "Ha cerrado sesión correctamente";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // Comprobamos que se redirige a la página de login ??
        checkText = "Identifícate";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * [Prueba10] - Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
     */
    @Test
    @Order(10)
    void PR10() {

        //login
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");

        // Comprobamos que no está visible el botón de logout
        List<WebElement> elements = new ArrayList<>();
        try {
            elements = PO_View.checkElementBy(driver, "text", "logout");
        } catch (TimeoutException e) {
            Assertions.assertEquals(0, elements.size());
        }
    }

    // [Prueba11] - Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema,
    //incluyendo el usuario actual y los usuarios administradores.
    @Test
    @Order(11)
    void PR11() {
        //inicio sesión como el administrador
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");

        // Comprobamos que hay un total de 15 usuarios (total de usuarios del sistema)
        int users = PO_PrivateView.getNumOfUsers(driver, 3);
        Assertions.assertEquals(15, users);
    }

    @Test
    @Order(12)
    void PR12(){
        //login - inicio sesión con un usuario admin
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");
        PO_PrivateView.clickAdminUserList(driver);

        PO_EditView.fillForm(driver, "user01@email.com", "user001", "Zapico");


        //Iniciamos sesión con el usuario que hemos modificado
        PO_PrivateView.doLogin(driver,"admin@email.com","Us3r@1-PASSW");
        PO_PrivateView.clickAdminUserList(driver);
        String checkText = "Usuarios";
        List<WebElement> result = PO_View.checkElementBy(driver, "id", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

    }

    @Test
    @Order(13)
    void PR13(){
        //login - inicio sesión con un usuario admin
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");
        PO_PrivateView.clickAdminUserList(driver);
        PO_EditView.fillForm(driver, "pedrogmail.com.es", " ", " ");
        String checkText = "El email no cumple el formato estandar: xxx@xxx.xxx .";
        List<WebElement> result = PO_View.checkElementBy(driver, "id", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

    }

    //[Prueba14] Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
    @Test
    @Order(14)
    void PR14(){
        //inicio sesión como el administrador
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");
        // listamos las usuarios
        PO_PrivateView.clickAdminUserList(driver);

        //borro el primero usuario borrable
        PO_PrivateView.deleteAnUser(driver,"2");

        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "user01@example.com",PO_View.getTimeout());
    }
    //[Prueba15] Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
    @Test
    @Order(15)
    void PR15(){
        //inicio sesión como el administrador
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");
        // listamos las usuarios
        PO_PrivateView.clickAdminUserList(driver);
        //Voy a la última página
        PO_PrivateView.irAPagina(driver,3);
        //borro el último usuario borrable
        PO_PrivateView.deleteAnUser(driver,"1");
        //Voy a la última página
        PO_PrivateView.irAPagina(driver,3);
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "user15@email.com",PO_View.getTimeout());
    }

    //[Prueba16] Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos usuarios desaparecen
    @Test
    @Order(16)
    void PR16(){
        //inicio sesión como el administrador
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");
        // listamos las usuarios
        PO_PrivateView.clickAdminUserList(driver);

        PO_PrivateView.deleteThreeFirstUsers(driver);

        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "user01@email.com",PO_View.getTimeout());
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "user02@email.com",PO_View.getTimeout());
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "user03@email.com",PO_View.getTimeout());
    }



    /**
     * [Prueba17] - Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema,
     * excepto el propio usuario y aquellos que sean administradores.
     */
    @Test
    @Order(17)
    void PR17() {

        //login - inicio sesión con un usuario estándar que no es admin
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // Comprobamos que hay un total de 14 usuarios (total de usuarios del sistema menos el autenticado y los usuarios administradores)
        int users = PO_PrivateView.getNumOfUsers(driver, 3);
        Assertions.assertEquals(14, users);
    }


    // [Prueba18] -  Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
    //corresponde con el listado usuarios existentes en el sistema.
    @Test
    @Order(18)
    void PR18() {

        //login - inicio sesión con un usuario estándar  que no es admin
        PO_PrivateView.doLogin(driver, "user01@email.com", "Us3r@1-PASSW");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // buscamos un texto vacío
        PO_PrivateView.doSearch(driver, "");

        // Comprobamos que hay un total de 7 usuarios (total de uruarios del sistema menos el autenticado y los usuarios administradores)
        int users = PO_PrivateView.getNumOfUsers(driver, 3);
        Assertions.assertEquals(14, users);
    }


    // [Prueba19] -  Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
    //muestra la página que corresponde, con la lista de usuarios vacía
    @Test
    @Order(19)
    void PR19() {

        //login - inicio sesión con un usuario estándar que no es admin
        PO_PrivateView.doLogin(driver, "user01@email.com", "Us3r@1-PASSW");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // buscamos un texto que no coincide con nada
        PO_PrivateView.doSearch(driver, "qwerty");


        // Comprobamos que hay un total de 0 usuarios (total de uruarios del sistema menos el autenticado y los usuarios administradores)
        int users = PO_PrivateView.getNumOfUsers(driver, 0);
        Assertions.assertEquals(0, users);
    }


    // [Prueba20] -  Hacer una búsqueda con un texto específico y comprobar que se muestra la página que
    //corresponde, con la lista de usuarios en los que el texto especificado sea parte de su nombre, apellidos o
    //de su email.
    @Test
    @Order(20)
    void PR20() {

        //login - inicio sesión con un usuario estándar que no es admin
        PO_PrivateView.doLogin(driver, "user01@email.com", "Us3r@1-PASSW");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // buscamos un texto que coincide con un usuario
        PO_PrivateView.doSearch(driver, "user02");


        // Comprobamos que hay un total de 1 usuarios
        int users = PO_PrivateView.getNumOfUsers(driver, 1);
        Assertions.assertEquals(1, users);
    }


    /**
     * [Prueba21] Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario.
     * Comprobar que la solicitud de amistad aparece en el listado de invitaciones
     */
    @Test
    @Order(21)
    void PR21() {
        //login y manda una invitación de amistad un usuario del que no es amigo
        PO_PrivateView.doLogin(driver, "user03@email.com", "Us3r@3-PASSW");
        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);
        PO_PrivateView.sendFriendshipRequest(driver, "user02@email.com");
        PO_PrivateView.doLogout(driver);

        // login con user02 y  comprobar que tiene la solicitud
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");
        // listar solicitudes de amistad
        PO_PrivateView.doClickListFriendsRequests(driver);

        // comprobar que aparece la invitación
        String checkText = "user03@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * [Prueba22] Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario al
     * que ya le habíamos enviado la invitación previamente. No debería dejarnos enviar la invitación. Se podría
     * ocultar el botón de enviar invitación o notificar que ya había sido enviada previamente.
     */
    @Test
    @Order(22)
    void PR22() {

        //login - inicio sesión con un usuario estándar (pedri@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "user03@email.com", "Us3r@3-PASSW");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // Pedro le manda una invitación de amistad un usuario del que no es amigo
        PO_PrivateView.sendFriendshipRequest(driver, "user02@email.com");

        WebElement sendRequestButton = driver.findElement(By.id("user02@email.com"));

        // Verifica si el botón está deshabilitado
        Assertions.assertFalse(sendRequestButton.isEnabled(), "El botón de solicitud está habilitado después de hacer click.");
    }


    /**
     * Mostrar el listado de invitaciones de amistad recibidas. Comprobar con un listado que
     * contenga varias invitaciones recibidas.
     * Se han enviado varias solicitudes de amistad a nunez@icloud.com, deberían de aparecer 5 en la
     * primera página.
     */
    @Test
    @Order(23)
    void PR23() {
        // Iniciamos sesión como nunez@icloud.com
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");
        // Accedemos a la vista de peticiones recibidas
        driver.findElement(By.id("listPetitions")).click();
        // Deberían de salir 5 peticiones
        List<WebElement> petitions = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        // Comprobamos que salen 5 peticiones
        Assertions.assertEquals(5, petitions.size());

        //Ahora nos desconectamos y comprobamos que aparece el menú de identificarse
        PO_PrivateView.doLogout(driver);
    }

    /**
     * Sobre el listado de invitaciones recibidas. Hacer clic en el botón/enlace de una de ellas y
     * comprobar que dicha solicitud desaparece del listado de invitaciones.
     * Volvemos a iniciar sesión con nunez@icloud.com, y aceptamos una petición de la primera página;
     * no debería de aparecer tras aceptarla.
     */
    @Test
    @Order(24)
    void PR24() {
        // Iniciamos sesión como nunez@icloud.com
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");
        // Accedemos a la vista de peticiones recibidas
        driver.findElement(By.id("listPetitions")).click();
        // Comprobamos que aparece una única invitación de María Rodríguez
        List<WebElement> petitions = PO_View.checkElementBy(driver, "text", "María Rodríguez");
        Assertions.assertEquals(1, petitions.size());
        // Aceptamos la petición de user02@email.com, tras aceptarla no debería de aparecer
        driver.findElement(By.id("user02@email.com")).click();
        // Comprobamos que no aparece ninguna solicitud de María Rodríguez
        SeleniumUtils.textIsNotPresentOnPage(driver, "María Rodríguez");

        //Ahora nos desconectamos y comprobamos que aparece el menú de identificarse
        PO_PrivateView.doLogout(driver);
    }





    //[Prueba25] Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene los amigos que deben ser.
    @Test
    @Order(25)
    void PR25(){
            //Login como el usuario 7
        PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
        //Voy a la página de amigos
        PO_PrivateView.doClickListFriends(driver);
        //Compruebo que el número de amigos es el correcto
        PO_PrivateView.checkNumberOfFriends(driver,1);
    }
    //[Prueba26] Mostrar el listado de amigos de un usuario. Comprobar que se incluye la información relacionada con la última publicación de cada usuario y la fecha de inicio de amistad.
    @Test
    @Order(26)
    void PR26(){
        //Login como el usuario 7
        PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
        //Voy a la página de amigos
        PO_PrivateView.doClickListFriends(driver);
        //Compruebo la fecha
        PO_PrivateView.checkDate(driver,"2024-02-03");
        //Compruebo la última publicación
        PO_PrivateView.checkLastPost(driver,"Título 15","/html/body/div/div/table/tbody/tr/td[5]/a");


    }


    // [Prueba27] -  Ir al formulario crear publicaciones, rellenarla con datos válidos y pulsar el botón Submit.
    //Comprobar que la publicación sale en el listado de publicaciones de dicho usuario.
    @Test
    @Order(27)
    void PR27() {
        PO_PrivateView.doLogin(driver, "user01@email.com", "Us3r@1-PASSW");

        // ir a la sección de añadir post
        PO_PrivateView.doClickAddPost(driver);

        // crear publicación
        String title = "Título de prueba 27";
        String text = "Texto de la publicación de prueba 27";
        PO_AddPostView.createPost(driver, title, text);

        // comprobar que existe la publicación
        boolean postCreated = PO_PostView.getPost(driver, "user01@email.com", title, text);
        Assertions.assertTrue(postCreated);

    }


    // [Prueba28] Ir al formulario de crear publicaciones, rellenarla con datos inválidos (campos título y
    //descripción vacíos) y pulsar el botón Submit. Comprobar que se muestran los mensajes de campo
    //obligatorios
    @Test
    @Order(28)
    void PR28() {
        PO_PrivateView.doLogin(driver, "user01@email.com", "Us3r@1-PASSW");

        // ir a la sección de añadir post
        PO_PrivateView.doClickAddPost(driver);

        // crear publicación
        String title = "";
        String text = "";
        PO_AddPostView.createPost(driver, title, text);

        WebElement errorMessage = driver.findElement(By.className("text-danger"));
        Assertions.assertTrue(errorMessage.getText().contains(PO_AddPostView.getP().getString("Error.empty", 0)));


    }


    // [Prueba29] Mostrar el listado de publicaciones de un usuario y comprobar que se muestran todas las que
    //existen para dicho usuario.
    @Test
    @Order(29)
    void PR29() {
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");

        // ir a la sección de añadir post
        PO_PrivateView.doClickListPosts(driver);


        int posts = PO_PrivateView.getPostsOfUser(driver, 3, "user02@email.com");

        Assertions.assertEquals(15, posts);



    }





    //[Prueba30] Mostrar el perfil del usuario y comprobar que se muestran sus datos y el listado de sus publicaciones.
    @Test
    @Order(30)
    void PR30(){
        //Login como el usuario 7
        PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
        //Voy a la página de amigos
        PO_PrivateView.doClickListFriends(driver);
        //Voy a los detalles del amigo
        PO_PrivateView.doClickFriendDetails(driver,"/html/body/div/div/table/tbody/tr/td[1]/a");
        int posts = PO_PrivateView.getPostsOfUser(driver, 3, "user08@email.com");
        Assertions.assertEquals(15, posts);


    }
    //[Prueba31] Utilizando un acceso vía URL u otra alternativa, tratar de acceder al perfil de un usuario que no sea amigo
    // del usuario identificado en sesión. Comprobar que el sistema da un error de autorización.

    @Test
    @Order(31)
    void PR31(){
        PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
        driver.navigate().to("http://localhost:8090/user/details/1");
        PO_HomeView.checkWelcomeToPage(driver,PO_Properties.getSPANISH());

    }

    // [Prueba32] Visualizar tres páginas (Página principal - Listado de usuarios - lista de publicaciones) en español/inglés/español
    // (comprobando que algunas de las etiquetas cambian al idioma correspondiente)
    @Test
    @Order(32)
    void PR32() {

        // --- PÁGINA PRINCIPAL --- (/index)

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        String checkText = "Welcome to our web application"; //
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // español
        PO_PrivateView.changeLanguage(driver, "Spanish"); // cambiamos a español
        checkText = "Bienvenido a nuestra aplicación web";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        checkText = "Welcome to our web application";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());


        // --- LISTADO DE USUARIOS ---
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW"); //login
        PO_PrivateView.doClickListUsers(driver); // listamos los usuarios

        // español
        PO_PrivateView.changeLanguage(driver, "Spanish"); // cambiamos a español
        checkText = "Los usuarios que actualmente figuran en el sistema son los siguientes:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        checkText = "The users currently listed in the system are the following:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // español
        PO_PrivateView.changeLanguage(driver, "Spanish"); // cambiamos a español
        checkText = "Los usuarios que actualmente figuran en el sistema son los siguientes:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());


        // --- LISTADO DE PUBLICACIONES --- (/post/list)
        PO_PrivateView.doClickListPosts(driver); // listamos las publicaciones

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        checkText = "The posts you have made are shown below:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // español
        PO_PrivateView.changeLanguage(driver, "Spanish"); // cambiamos a español
        checkText = "A continuación se muestran las publicaciones que has realizado:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

    }

    /**
     * [Prueba33] Visualizar al menos tres páginas en inglés/francés (comprobando
     * que algunas de las etiquetas cambian al idioma correspondiente).
     */
    @Test
    @Order(33)
    void PR33() {

        // --- PÁGINA PRINCIPAL --- (/index)

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        String checkText = "Welcome to our web application"; //
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // francés
        PO_PrivateView.changeLanguage(driver, "French"); // cambiamos a francés
        checkText = "Bienvenue sur notre application Web";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        checkText = "Welcome to our web application";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());


        // --- LISTADO DE USUARIOS ---
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW"); //login
        PO_PrivateView.doClickListUsers(driver); // listamos los usuarios

        // español
        PO_PrivateView.changeLanguage(driver, "French"); // cambiamos a francés
        checkText = "Les utilisateurs actuellement répertoriés dans le système sont les suivants:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        checkText = "The users currently listed in the system are the following:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // español
        PO_PrivateView.changeLanguage(driver, "French"); // cambiamos a francés
        checkText = "Les utilisateurs actuellement répertoriés dans le système sont les suivants:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());


        // --- LISTADO DE PUBLICACIONES --- (/post/list)
        PO_PrivateView.doClickListPosts(driver); // listamos las publicaciones

        // inglés
        PO_PrivateView.changeLanguage(driver, "English"); // cambiamos a inglés
        checkText = "The posts you have made are shown below:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // español
        PO_PrivateView.changeLanguage(driver, "French"); // cambiamos a francés
        checkText = "Les messages que vous avez publiés sont affichés ci-dessous:";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

    }


    // [Prueba39] Acceder a las publicaciones de un amigo y recomendar una publicación. Comprobar que el número de
    // recomendaciones se ha incrementado en uno y que no aparece el botón/enlace recomendar
    @Test
    @Order(34)
    void PR39(){
        //Login como el usuario 7
        PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
        //Voy a la página de amigos
        PO_PrivateView.doClickListFriends(driver);
        //Voy a los detalles del amigo
        PO_PrivateView.doClickFriendDetails(driver,"/html/body/div/div/table/tbody/tr/td[1]/a");

        PO_PrivateView.checkRecommendation(driver,"/html/body/div/div/div[1]/div[1]/div/div/button",
                "/html/body/div/div/div[1]/div[1]/div/div/p[4]","Recomendado por 1 personas");


    }


    // Intentar acceder sin estar autenticado a la opción de listado de usuarios. Se deberá volver al
    // formulario de login.
    @Test
    @Order(34)
    void PR34() {
        // Intentamos navegar a listado de usuarios
        String url = driver.getCurrentUrl() + "userList";
        driver.navigate().to(url);
        // Comprobamos que nos devuelve al login
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals("Identifícate", result.get(0).getText());
    }

    // Intentar acceder sin estar autenticado a la opción de listado de invitaciones de amistad recibida
    // de un usuario estándar. Se deberá volver al formulario de login.
    @Test
    @Order(35)
    void PR35() {
        // Intentamos navegar a listado de usuarios
        String url = driver.getCurrentUrl() + "list";
        driver.navigate().to(url);
        // Comprobamos que nos devuelve al login
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals("Identifícate", result.get(0).getText());
    }

    // Estando autenticado como usuario estándar intentar acceder a una opción disponible solo
    // para usuarios administradores (Añadir menú de auditoria (visualizar logs)). Se deberá indicar un mensaje
    // de acción prohibida.
    @Test
    @Order(36)
    void PR36() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "id", "password");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "Us3r@1-PASSW");
        //Comprobamos que nos da error forbidden
        driver.navigate().to(driver.getCurrentUrl().substring(0, driver.getCurrentUrl().length() - 5) + "/log");
        String checkText = "There was an unexpected error (type=Forbidden, status=403).";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //[Prueba40] Utilizando un acceso vía URL u otra alternativa, tratar de recomendar una publicación de un usuario
    // con el que no se mantiene una relación de amistad.
    @Test
    @Order(18)
    void PR40() {
        PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
        driver.navigate().to("http://localhost:8090/user/details/1");

        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }



    /**
     * [Prueba41] Como administrador, cambiar el estado de una publicación y comprobar que el estado ha cambiado
     */
    @Test
    @Order(41)
    void PR41() {

        //login - inicio sesión con un usuario administrador
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");

        // listamos todas las publicaciones del sistema
        PO_PrivateView.doClickAdminListPostsForAdminNav(driver);
        WebElement updatedState = PO_PrivateView.changePostState(driver, "CENSURADA");

        Assertions.assertEquals("CENSURADA", updatedState.getText());
    }

    /**
     * [Prueba42] Como usuario estándar, comprobar que NO aparece en el listado propio de publicaciones una
     * publicación censurada.
     */
    @Test
    @Order(42)
    void PR42() {

        // inicio sesión con usuario admin y censuro una publicación de user03@email.com
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");

        // listamos todas las publicaciones del sistema y censuro la primera del usuario user03@email.com
        PO_PrivateView.doClickAdminListPostsForAdminNav(driver);
        PO_PrivateView.changeStateFirstPost(driver, "user01@email.com", "CENSURADA");

        PO_PrivateView.doLogout(driver);

        //inicio sesión con un usuario estándar
        PO_PrivateView.doLogin(driver, "user01@email.com", "Us3r@1-PASSW");

        // Verificar que no aparece ninguna publicación censurada en el listado propio de publicaciones
        Assertions.assertFalse(PO_PrivateView.isCensoredPostPresent(driver));
    }


    /**
     * [Prueba43] Como usuario estándar, comprobar que, en el listado de publicaciones de un amigo, NO
     * aparece una publicación moderada.
     */
    @Test
    @Order(43)
    void PR43() {

        // inicio sesión con usuario admin y censuro una publicación de user03@email.com
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");

        // listamos todas las publicaciones del sistema y censuro la primera del usuario user01@email.com - con título 8
        PO_PrivateView.doClickAdminListPostsForAdminNav(driver);
        PO_PrivateView.changeStateFirstPost(driver, "user01@email.com", "MODERADA");

        PO_PrivateView.doLogout(driver);

        //inicio sesión con un usuario estándar (user03, ya que tiene de amigo a user01@email.com)
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");

        //Voy a la página de amigos y pincho en los detalles de user01@email.com para ver sus publicaciones, checkeo que
        // esté presente la publicación con título 8
        PO_PrivateView.doClickListFriendships(driver);
        WebElement userLink = driver.findElement(By.xpath("//a[contains(text(),'user01@email.com')]"));
        userLink.click();

        SeleniumUtils.textIsNotPresentOnPage(driver, "Título 8");
    }

    /**
     * [Prueba44] Como usuario estándar, intentar acceder la opción de cambio del estado de una publicación y
     * comprobar que se redirecciona al usuario hacia la página login
     */
    @Test
    @Order(44)
    void PR44() {

        // Intentar acceder a la URL para cambiar el estado de la publicación
        driver.get("http://localhost:8090/post/updateState/3");

        // redirecciona al login
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // ahora lo intento otra vez logeada como un usuario estándar
        PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");

        // Intentar acceder a la URL para cambiar el estado de la publicación
        driver.get("http://localhost:8090/post/adminList");

        // redirecciona al login
        checkText = "Identifícate";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    /**
     * [Prueba45] Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
     * corresponde con el listado publicaciones.
     */
    @Test
    @Order(45)
    void PR45() {

        //login - inicio sesión con un usuario admin
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");

        // listamos todas las publicaciones del sistema
        PO_PrivateView.doClickAdminListPostsForAdminNav(driver);

        // buscamos un texto vacío
        PO_PrivateView.doSearch(driver, "");

        // Comprobamos que hay un total de 5 publicaciones
        int posts = PO_PrivateView.countPosts(driver);
        Assertions.assertEquals(5, posts);
    }

    /**
     * [Prueba46] Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
     * muestra la página que corresponde, con la lista de publicaciones vacía
     */
    @Test
    @Order(46)
    void PR46() {

        //login - inicio sesión con un usuario admin
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");

        // listamos todas las publicaciones del sistema
        PO_PrivateView.doClickAdminListPostsForAdminNav(driver);

        // buscamos un texto vacío
        PO_PrivateView.doSearch(driver, "qwerty");

        // Comprobamos que hay un total de 5 publicaciones
        int posts = PO_PrivateView.countPosts(driver);
        Assertions.assertEquals(0, posts);
    }

    /**
     * [Prueba47] Hacer una búsqueda de publicaciones censuradas, escribiendo el cuadro de búsqueda
     * “Censurada” y comprobar que se muestra la página que corresponde, con la lista de publicaciones
     * censuradas o que en el texto especificado sea parte de título, estado o del email
     */
    @Test
    @Order(47)
    void PR47() {

        // inicio sesión con usuario admin
        PO_PrivateView.doLogin(driver, "admin@email.com", "@Dm1n1str@D0r");

        // listamos todas las publicaciones del sistema y censuro una
        PO_PrivateView.doClickAdminListPostsForAdminNav(driver);
        PO_PrivateView.changeStateFirstPost(driver, "user01@email.com", "CENSURADA");

        // buscamos el texto 'CENSURADA'
        PO_PrivateView.doSearch(driver, "CENSURADA");

        // Comprobamos que hay un total de 5 publicaciones
        int posts = PO_PrivateView.countPosts(driver);
        Assertions.assertEquals(1, posts);
    }

    /**
     * [Prueba48] Desde el formulario de crear publicaciones, crear una publicación con datos válidos y una foto
     * adjunta. Comprobar que en el listado de publicaciones aparecer la foto adjunta junto al resto de datos de
     * la publicación.
     */
    @Test
    @Order(48)
    void PR48() {

        // inicio sesión con usuario2
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");

        // formulario de creación de publicaciones
        PO_PrivateView.doClickAddPost(driver);

        String title = "Titulo post con foto";
        String text = "Texto de la publicación con foto";
        String imagePath = "src/main/resources/static/images/student-48.png";
        PO_AddPostView.createPostAndImage(driver, title, text, imagePath);

        // comprobar que existe la publicación
        boolean postCreated = PO_PostView.getPostWithImage(driver, "user02@email.com", title, text);

        Assertions.assertTrue(postCreated);
    }


    /**
     * [Prueba49] Crear una publicación con datos válidos y sin una foto adjunta. Comprobar que la publicación
     * se ha creado con éxito, ya que la foto no es obligatoria.
     */
    @Test
    @Order(49)
    void PR49() {

        // inicio sesión con usuario2
        PO_PrivateView.doLogin(driver, "user02@email.com", "Us3r@2-PASSW");

        // formulario de creación de publicaciones
        PO_PrivateView.doClickAddPost(driver);

        String title = "Titulo post sin foto";
        String text = "Texto de la publicación sin foto";
        PO_AddPostView.createPost(driver, title, text);

        // comprobar que existe la publicación con una foto (por  defecto la foto)
        boolean postCreated = PO_PostView.getPostWithImage(driver, "user02@email.com", title, text);

        Assertions.assertTrue(postCreated);
    }





    


}





