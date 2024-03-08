package com.uniovi.sdi2324entrega181;

import com.uniovi.sdi2324entrega181.pageobjects.PO_HomeView;
import com.uniovi.sdi2324entrega181.pageobjects.PO_LoginView;
import com.uniovi.sdi2324entrega181.pageobjects.PO_View;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2324Entrega181ApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\Rita Catucho\\Desktop\\segundo cuatri\\SDI\\laboratorios\\semana06\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";


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





    //[Prueba9] - Hacer clic en la opción de salir de sesión y comprobar que se muestra el mensaje “Ha cerrado
    //sesión correctamente” y se redirige a la página de inicio de sesión
    @Test
    @Order(1)
    void PR01A() {

        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "pedro@example.com", "123456");
        // click en el botón de logout
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        //Comprobamos que entramos en la pagina privada de Alumno
        String checkText = "Ha cerrado sesión correctamente";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

    }

    @Test
    @Order(2)
    void PR02B() {

    }

}
