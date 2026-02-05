package PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import Utilities.Waits;

import java.util.*;

public class Homepage extends BasePage {

    public Homepage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(id="destinationInput")
    WebElement destinationInput;
    @FindBy(id="checkInInput")
    WebElement checkInInput;
    @FindBy(id="checkOutInput")
    WebElement checkOutInput;
    @FindBy(css = "div.c-calendar-month__title")
    WebElement calendarTitle;
    @FindBy(xpath="//div[@class='hBD91TxW6Uv2IEd2ZK_X']/i")
    WebElement guestSelector;
    @FindBy(xpath="(//i[@class='smarticon u-icon u-icon-ic_bestir_plus u-icon_ic_bestir_plus K6h5Q4uKXnll47o5TCH7'])[2]")
    WebElement addAdultsButton;
    @FindBy(xpath="//div[@class='OVZS4orBSAXrR1ijARZy']")
    WebElement confirmGuestsButton;
    @FindBy(xpath="//span[@class='tripui-online-btn-content-children ' and text()='Search']")
    WebElement finalSearch;
    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;


    public void enterDestination(String destination) {
        destinationInput.sendKeys(destination);
    }

    public void openCheckInDate() {
        Waits.waitForVisibility(driver, checkInInput, 5);
        Waits.waitForElementToBeClickable(driver, checkInInput, 5).click();
    }

    public void openCheckOutDate() {
        Waits.waitForVisibility(driver, checkOutInput, 5);
        Waits.waitForElementToBeClickable(driver, checkOutInput, 5).click();
    }

    public void selectDate(int day) {
        Waits.waitForVisibility(driver, calendarTitle, 5);
        By daySelector = By.xpath("//li[@role='button' and not(contains(@class,'is-disable'))]//span[@class='day' and text()='" + day + "']");
        WebElement dateElement = Waits.waitForElementToBeClickable(driver, daySelector, 5);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dateElement);
    }

    public void openGuestSelector() {
        Waits.waitForVisibility(driver, guestSelector, 5);
        WebElement guestElem = Waits.waitForElementToBeClickable(driver, guestSelector, 5);
        Actions action = new Actions(driver);
        action.scrollToElement(guestElem).perform();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", guestElem);
    }

    public void addAdults(int count){
        for (int i = 0; i < count; i++) {
            WebElement addAdult = Waits.waitForElementToBeClickable(driver, addAdultsButton, 5);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", addAdult);
        }
    }

    public void confirmGuests() {
        WebElement btn = Waits.waitForElementToBeClickable(driver, confirmGuestsButton, 5);
        btn.click();
        Waits.waitForInvisibility(driver, confirmGuestsButton, 5);
    }

    public void clickSearch() {
        WebElement btn = Waits.waitForElementToBeClickable(driver, finalSearch, 5);
        btn.click();
    }
    public void closePopup() {
        try {
            for (WebElement closeIcon : closeButtons) {
                if (closeIcon.isDisplayed() && closeIcon.isEnabled()) {
                    closeIcon.click();
                    break;
                }
            }
        } catch (Exception ignored) {
        }
    }
}