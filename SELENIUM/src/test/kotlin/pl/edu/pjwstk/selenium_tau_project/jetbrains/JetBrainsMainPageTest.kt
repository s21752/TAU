package pl.edu.pjwstk.selenium_tau_project.jetbrains

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JetBrainsMainPageTest {

    lateinit var mainPage: JetBrainsMainPage
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
        mainPage = JetBrainsMainPage(driver)
    }

    // refresh main page before each test
    @BeforeEach
    fun open_main_page() {
        mainPage.refreshPage()
    }

    @Test
    fun test_search_input_available() {
        mainPage.searchButton.click()

        driver.findElement(By.cssSelector("[data-test='search-input']")).sendKeys("Selenium")

        assertEquals(
                "Selenium",
                driver.findElement(By.cssSelector("input[data-test='search-input']")).getAttribute("value"))
    }

    @Test
    fun test_quick_search_visible_if_input_not_blank() {
        mainPage.searchButton.click()
        driver.findElement(By.cssSelector("[data-test='search-input']")).sendKeys("Selenium")

        WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.className("quick-search__results")))

        val element = driver.findElement(By.className("quick-search__results"))

        assertEquals(true, element.isDisplayed)
    }

    @Test
    fun test_multiple_results_found_for_search_query() {
        mainPage.searchButton.click()
        driver.findElement(By.cssSelector("[data-test='search-input']")).sendKeys("Selenium")

        WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.className("quick-search__item")))

        val resultsListSize = driver.findElements(By.className("quick-search__item")).size

        assertTrue { resultsListSize > 1 }
    }

    @Test
    fun test_clear_search_query_button_quits_quick_search() {
        mainPage.searchButton.click()
        driver.findElement(By.cssSelector("[data-test='search-input']")).sendKeys("Selenium")


        driver.findElement(By.className("_wt-input__icon_action_1pdmso7_276")).click()

        assertTrue { driver.findElements(By.className("quick-search__results")).isEmpty() }
    }

    @Test
    fun tools_menu_is_showing() {
        mainPage.developerToolsButton.click()

        assertTrue { driver.findElement(By.cssSelector("div[data-test='main-submenu']")).isDisplayed }
    }

    @Test
    fun navigation_to_all_tools_page_test() {
        mainPage.allToolsButton.click()

        WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#products-page")))

        assertEquals("All Developer Tools and Products by JetBrains", driver.title)
    }

    @AfterAll
    fun cleanUpAll() {
        driver.quit()
        driverService.stop()
    }

}
