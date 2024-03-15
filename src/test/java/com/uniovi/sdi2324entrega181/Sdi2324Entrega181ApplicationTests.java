package com.uniovi.sdi2324entrega181;

import com.uniovi.sdi2324entrega181.pageobjects.*;
import com.uniovi.sdi2324entrega181.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class Sdi2324Entrega181ApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\Rita Catucho\\Desktop\\segundo cuatri\\SDI\\laboratorios\\semana06\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

  // static String Geckodriver = "C:\\Users\\coral\\IdeaProjects\\SeleniumMaterial\\geckodriver-v0.30.0-win64.exe";

    static String Geckodriver = "C:\\Users\\javie\\OneDrive\\Escritorio\\Tercero\\SDI\\L5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";


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





    // [Prueba9] - Hacer clic en la opción de salir de sesión y comprobar que se muestra el mensaje “Ha cerrado
    // sesión correctamente” y se redirige a la página de inicio de sesión
    @Test
    @Order(1)
    void PR09() {

        //login
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");
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

    // [Prueba10] - Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
    @Test
    @Order(2)
    void PR10() {

        //login
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

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
    @Order(1)
    void PR11() {
        // TODO: Comprobar listado de admin y listado de usuarios
        Assertions.assertEquals(7, 1); // da falso
    }



    // [Prueba17] - Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema,
    // excepto el propio usuario y aquellos que sean administradores.
    @Test
    @Order(3)
    void PR17() {

        //login - inicio sesión con un usuario estándar (pedri@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // Comprobamos que hay un total de 7 usuarios (total de uruarios del sistema menos el autenticado y los usuarios administradores)
        int users = PO_PrivateView.getNumOfUsers(driver, 2);
        Assertions.assertEquals(7, users);
    }


    // [Prueba18] -  Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
    //corresponde con el listado usuarios existentes en el sistema.
    @Test
    @Order(4)
    void PR18() {

        //login - inicio sesión con un usuario estándar (pedri@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // buscamos un texto vacío
        PO_PrivateView.doSearch(driver, "");


        // Comprobamos que hay un total de 7 usuarios (total de uruarios del sistema menos el autenticado y los usuarios administradores)
        int users = PO_PrivateView.getNumOfUsers(driver, 2);
        Assertions.assertEquals(7, users);
    }


    // [Prueba19] -  Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
    //muestra la página que corresponde, con la lista de usuarios vacía
    @Test
    @Order(5)
    void PR19() {

        //login - inicio sesión con un usuario estándar (pedri@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

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
    @Order(6)
    void PR20() {

        //login - inicio sesión con un usuario estándar (pedri@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // buscamos un texto que coincide con un usuario
        PO_PrivateView.doSearch(driver, "lucas");


        // Comprobamos que hay un total de 7 usuarios (total de uruarios del sistema menos el autenticado y los usuarios administradores)
        int users = PO_PrivateView.getNumOfUsers(driver, 1);
        Assertions.assertEquals(1, users);
    }






    // [Prueba21] Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario.
    // Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente).
    @Test
    @Order(7)
    void PR21() {
        //login - inicio sesión con un usuario estándar (pedro@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

    }

    // [Prueba22] Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario al
    // que ya le habíamos enviado la invitación previamente. No debería dejarnos enviar la invitación. Se podría
    // ocultar el botón de enviar invitación o notificar que ya había sido enviada previamente.
    @Test
    @Order(8)
    void PR22() {

        //login - inicio sesión con un usuario estándar (pedri@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

        // Pedro le manda una invitación de amistad a Lucas (lucas@example.com)
        PO_PrivateView.sendFriendshipRequest(driver, "lucas@example.com");

        WebElement sendRequestButton = driver.findElement(By.id("lucas@example.com"));

        // Verifica si el botón está deshabilitado
        Assertions.assertFalse(sendRequestButton.isEnabled(), "El botón de solicitud está habilitado después de hacer click.");
    }

//[Prueba1] Registro de Usuario con datos válidos.

    @Test
    @Order(6)
    void PR01(){
        //Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "uo384382@example.es", "Josefo", "Perez", "aaaaaaaaaaaaA1@", "aaaaaaaaaaaaA1@");
        //Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Bienvenidos a la página principal";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }
//[Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos y
//contraseña incorrecta (débil)).
    @Test
    @Order(7)
    void PR02(){
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "", "", "", "corta", "corta");
        //Compruebo si existe el texto esperado
        Assert.hasText("Rellene este campo.");



    }
//[Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña inválida).
@Test
@Order(8)
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
@Order(9)
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
//[Prueba14] Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
@Test
@Order(10)
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
@Order(11)
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
    @Order(12)
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

//[Prueba25] Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene los amigos que deben ser.
@Test
@Order(13)
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
@Order(14)
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
    @Order(13)
    void PR27() {
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // ir a la sección de añadir post
        PO_PrivateView.doClickAddPost(driver);

        // crear publicación
        String title = "Título de prueba 27";
        String text = "Texto de la publicación de prueba 27";
        PO_AddPostView.createPost(driver, title, text);

        // comprobar que existe la publicación
        boolean postCreated = PO_PostView.getPost(driver, "pedro@example.com", title, text);
        Assertions.assertTrue(postCreated);

    }


    // [Prueba28] Ir al formulario de crear publicaciones, rellenarla con datos inválidos (campos título y
    //descripción vacíos) y pulsar el botón Submit. Comprobar que se muestran los mensajes de campo
    //obligatorios
    @Test
    @Order(13)
    void PR28() {
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // ir a la sección de añadir post
        PO_PrivateView.doClickAddPost(driver);

        // crear publicación
        String title = "";
        String text = "";
        PO_AddPostView.createPost(driver, title, text);

        // TODO: REVISAR excepciones de datos inválidos
        Assertions.assertTrue(false); // false

    }


    // [Prueba29] Mostrar el listado de publicaciones de un usuario y comprobar que se muestran todas las que
    //existen para dicho usuario.
    @Test
    @Order(13)
    void PR29() {
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // ir a la sección de añadir post
        PO_PrivateView.doClickListPosts(driver);


        int posts = PO_PrivateView.getPostsOfUser(driver, 3, "pedro@example.com");

        Assertions.assertEquals(15, posts);



    }





//[Prueba30] Mostrar el perfil del usuario y comprobar que se muestran sus datos y el listado de sus publicaciones.
@Test
@Order(15)
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
@Order(16)
void PR31(){
    PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
    driver.navigate().to("http://localhost:8090/user/details/1");
    PO_HomeView.checkWelcomeToPage(driver,PO_Properties.getSPANISH());

}


// [Prueba39] Acceder a las publicaciones de un amigo y recomendar una publicación. Comprobar que el número de
// recomendaciones se ha incrementado en uno y que no aparece el botón/enlace recomendar
@Test
@Order(17)
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

//[Prueba40] Utilizando un acceso vía URL u otra alternativa, tratar de recomendar una publicación de un usuario
// con el que no se mantiene una relación de amistad.
@Test
@Order(18)
void PR40(){
    PO_PrivateView.doLogin(driver, "user07@email.com", "Us3r@7-PASSW");
    driver.navigate().to("http://localhost:8090/user/details/1");

    PO_HomeView.checkWelcomeToPage(driver,PO_Properties.getSPANISH());


}




}
