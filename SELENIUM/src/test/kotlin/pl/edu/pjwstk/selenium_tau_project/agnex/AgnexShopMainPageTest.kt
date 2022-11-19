package pl.edu.pjwstk.selenium_tau_project.agnex

import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AgnexShopMainPageTest {

    lateinit var mainPage: AgnexShopMainPage
    lateinit var driver: WebDriver
    lateinit var driverService: ChromeDriverService

    @BeforeAll
    fun setUpAll() {
        driverService = ChromeDriverService.Builder()
                .usingDriverExecutable(File("/Users/cezarygraban/Downloads/chromedriver"))
                .usingAnyFreePort()
                .build()
                .apply {
                    start()
                }

        driver = RemoteWebDriver(driverService.url, ChromeOptions())
        mainPage = AgnexShopMainPage(driver)
        driver.manage().window().maximize()

    }

    // refresh main page before each test
    @BeforeEach
    fun refreshPage() {
        mainPage.refreshPage()
    }

    @Test
    fun navigating_to_login_works() {
        driver.findElement(By.className("login")).click()

        assertTrue { driver.currentUrl == AgnexShopMainPage.LOGIN_URL }
    }

    @Test
    fun mail_field_input_is_type_text() {
        driver.navigate().to(AgnexShopMainPage.LOGIN_URL)

        val mailInput = driver.findElement(By.id("mail_input_long"))

        val inputType = mailInput.getAttribute("type")

        assertTrue { inputType == "text" }
    }

    @Test
    fun logging_works() {
        driver.navigate().to(AgnexShopMainPage.LOGIN_URL)

        driver.findElement(By.id("mail_input_long")).sendKeys(AgnexShopMainPage.USERNAME)
        driver.findElement(By.id("pass_input_long")).sendKeys(AgnexShopMainPage.PASSWORD)

        driver.findElement(By.xpath("//*[@id=\"box_login\"]/div[2]/div/form/fieldset/button")).click()

        assertTrue { driver.findElement(By.className("myaccount")).isDisplayed }
    }

    @Test
    fun menu_navigation_is_working() {
        mainPage.howToBuyButton.click()

        assertTrue { driver.title.contains("Jak kupować?", ignoreCase = true) }
    }

    @Test
    fun multiple_new_items_are_present_in_shop() {
        mainPage.newItemsButton.click()

        WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.className("product-main-wrap")))

        val numberOfItems = driver.findElements(By.className("product-main-wrap")).size

        assertTrue { numberOfItems > 1 }
    }

    @Test
    fun can_add_product_to_basket() {
        mainPage.newItemsButton.click()

        WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.className("product-main-wrap")))

        val itemCountBeforeAddingToBasket = driver.findElement(By.className("countlabel")).findElement(By.xpath("//*/b")).text

        val addToBasketButton = driver.findElement(By.className("products")).findElements(By.className("addtobasket")).first()

        val actions = Actions(driver)
        actions.moveToElement(addToBasketButton).click().perform()

        WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.className("modal-visible")))

        val continueBtn = driver.findElement(By.xpath("/html/body/div[12]/div[3]/div/a[1]"))

        actions.moveToElement(continueBtn).click().perform()

        val itemCountAfterAddingToBasket = driver.findElement(By.className("countlabel")).findElement(By.xpath("//*/b")).text

        assertNotEquals(itemCountBeforeAddingToBasket, itemCountAfterAddingToBasket)

    }

    @AfterAll
    fun cleanUpAll() {
        driver.quit()
        driverService.stop()
    }
}