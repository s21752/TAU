package pl.edu.pjwstk.selenium_tau_project.jetbrains

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import pl.edu.pjwstk.selenium_tau_project.utils.PageToTest

// page_url = https://www.jetbrains.com/
class JetBrainsMainPage(driver: WebDriver) : PageToTest(driver) {

    override val mainUrl: String
        get() = "https://www.jetbrains.com/"

    val searchButton: WebElement
        get() = driver.findElement(By.cssSelector("[data-test='site-header-search-action']"))

    val developerToolsButton: WebElement
        get() = driver.findElement(By.cssSelector("[data-test='main-menu-item-action']"))

    val allToolsButton: WebElement
        get() = driver.findElement(By.partialLinkText("products"))
}
