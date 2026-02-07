package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import Utilities.Waits;

import java.util.List;

public class CruiseSearchPage extends BasePage {
    public CruiseSearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="header_action_nav_cruises")
    WebElement cruiseTab;
    @FindBy(xpath="//div[contains(text(),'Departure city')]/following-sibling::div//div[contains(@class,'cru-form-select-text')]")
    WebElement departureCity;
    @FindBy(xpath="//div[contains(text(),'Destination')]/following-sibling::div//div[contains(@class,'cru-form-select-text')]")
    WebElement destinationCity;
    //div[contains(text(),'Destination')]/following-sibling::div//div[contains(@class,'cru-form-select-text')]
    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;
    @FindBy(xpath="//button[contains(@class,'cru-btn') and .//span[normalize-space()='Search']]")
    WebElement searchButton;

    public void goToCruiseMenu() {
        WebElement cruiseMenu = Waits.waitForElementToBeClickable(driver, cruiseTab, 5);
        cruiseMenu.click();
    }

    public void selectDepartureCity(String city) {
        Waits.waitForVisibility(driver, departureCity, 5);
        Waits.waitForElementToBeClickable(driver, departureCity, 5).click();
        By cityOption = By.xpath("//div[contains(@class,'c-dropdown') and contains(@class,'show')]//span[normalize-space()='" + city + "']");
        WebElement cityElem = Waits.waitForElementToBeClickable(driver, cityOption, 5);
        cityElem.click();
    }

    public void selectDestinationCity(String city) {
        Waits.waitForVisibility(driver, destinationCity, 5);
        Waits.waitForElementToBeClickable(driver, destinationCity, 5).click();
        By cityOption = By.xpath("//div[contains(@class,'c-dropdown') and contains(@class,'show')]//span[normalize-space()='" + city + "']");
        WebElement cityElem = Waits.waitForElementToBeClickable(driver, cityOption, 5);
        cityElem.click();
    }

    public void closeAllPopups()  {
        for (WebElement closeButton : closeButtons) {
            if (closeButton.isDisplayed() && closeButton.isEnabled()) {
                closeButton.click();
            }
        }
    }
    public void clickSearchButton() {
        WebElement search = Waits.waitForElementToBeClickable(driver, searchButton, 10);
        search.click();
    }
}
