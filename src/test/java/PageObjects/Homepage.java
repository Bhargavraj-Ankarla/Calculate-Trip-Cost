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
    JavascriptExecutor js = (JavascriptExecutor) driver;

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
    @FindBy(xpath="//div[@class='hBD91TxW6Uv2IEd2ZK_X']/div/div[@class='KPcnGfJAicIvGoo_H8ra']")
    WebElement roomsAndGuests;

    public void enterDestination(String destination) throws Exception {
        try{
            destinationInput.sendKeys(destination);
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }

    public String getDestination()  {
        return (String) js.executeScript("return arguments[0].value;", destinationInput);
    }

    public void openCheckInDate() throws Exception {
        try{
            Waits.waitForVisibility(driver, checkInInput, 5);
            Waits.waitForElementToBeClickable(driver, checkInInput, 5).click();
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }

    public String getCheckinDate(){
        return (String) js.executeScript("return arguments[0].value;", checkInInput);
    }

    public void openCheckOutDate() throws Exception {
        try{
            Waits.waitForVisibility(driver, checkOutInput, 5);
            Waits.waitForElementToBeClickable(driver, checkOutInput, 5).click();
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }

    public String getCheckoutDate() {
        return (String) js.executeScript("return arguments[0].value;", checkOutInput);
    }

    public void selectDate(int day) throws Exception {
        try{
            Waits.waitForVisibility(driver, calendarTitle, 5);
            By daySelector = By.xpath("//li[@role='button' and not(contains(@class,'is-disable'))]//span[@class='day' and text()='" + day + "']");
            WebElement dateElement = Waits.waitForElementToBeClickable(driver, daySelector, 5);
            js.executeScript("arguments[0].click();", dateElement);
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }

    }

    public void openGuestSelector() throws Exception {
        try{
            Waits.waitForVisibility(driver, guestSelector, 5);
            WebElement guestElem = Waits.waitForElementToBeClickable(driver, guestSelector, 5);
            Actions action = new Actions(driver);
            action.scrollToElement(guestElem).perform();
            js.executeScript("arguments[0].click();", guestElem);
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }

    public void addAdults(int count) throws Exception {
        try{
            for (int i = 0; i < count; i++) {
                WebElement addAdult = Waits.waitForElementToBeClickable(driver, addAdultsButton, 5);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", addAdult);
            }
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }

    public void confirmGuests() throws Exception {
        try{
            WebElement Done = Waits.waitForElementToBeClickable(driver, confirmGuestsButton, 5);
            Done.click();
            Waits.waitForInvisibility(driver, confirmGuestsButton, 5);
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }

    public String getRoomsAndGuests() {
        return roomsAndGuests.getText();
    }

    public void clickSearch() throws Exception {
        try{
            WebElement search = Waits.waitForElementToBeClickable(driver, finalSearch, 5);
            search.click();
        }catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }
    public void closePopup() throws Exception {
        try {
            for (WebElement closeIcon : closeButtons) {
                if (closeIcon.isDisplayed() && closeIcon.isEnabled()) {
                    closeIcon.click();
                    break;
                }
            }
        } catch(Exception e){
            throw new Exception("Unable to locate enter the destination because, "+e.getMessage());
        }
    }

}