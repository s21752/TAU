package pl.edu.pjwstk.selenium_tau_project.agnex

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import pl.edu.pjwstk.selenium_tau_project.utils.PageToTest
import java.lang.Exception

class AgnexShopMainPage(driver: WebDriver) : PageToTest(driver) {

    companion object {
        const val USERNAME = "dbuzoxcnfwhelcstsd@tmmwj.com"
        const val PASSWORD = "cokolwiek"
        const val LOGIN_URL = "https://sklepagnex.pl/pl/login"
    }

    val logOutButton: WebElement
        get() = driver.findElement(By.className("logout"))

    val howToBuyButton: WebElement
        get() = driver.findElement(By.id("headlink8"))

    val newItemsButton: WebElement
        get() = driver.findElement(By.id("headlink26"))

    override val mainUrl: String
        get() = "https://sklepagnex.pl/"


    // go to main page and logout if needed
    override fun refreshPage() {
        super.refreshPage()

        try {
            logOutButton.click()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}