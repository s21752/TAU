package pl.edu.pjwstk.selenium_tau_project.utils

import org.openqa.selenium.WebDriver

abstract class PageToTest(val driver: WebDriver) {

    abstract val mainUrl: String

    open fun refreshPage() {
        driver.get(mainUrl)
    }
}