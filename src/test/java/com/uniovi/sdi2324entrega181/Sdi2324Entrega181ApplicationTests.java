package com.uniovi.sdi2324entrega181;

import com.uniovi.sdi2324entrega181.pageobjects.PO_HomeView;
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

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2324Entrega181ApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "geckodriver-v0.30.0-win64.exe";


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

    // [Prueba21] Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario.
    // Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente).
    @Test
    @Order(4)
    void PR21() {
        //login - inicio sesión con un usuario estándar (pedri@example.com) que no es admin
        PO_PrivateView.doLogin(driver, "pedro@example.com", "123456");

        // listamos las usuarios
        PO_PrivateView.doClickListUsers(driver);

    }

    // [Prueba22] Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario al
    // que ya le habíamos enviado la invitación previamente. No debería dejarnos enviar la invitación. Se podría
    // ocultar el botón de enviar invitación o notificar que ya había sido enviada previamente.
    @Test
    @Order(5)
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





}
