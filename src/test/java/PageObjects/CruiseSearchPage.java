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
    WebElement departureCityBox;
    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;
    @FindBy(xpath="//button[contains(@class,'cru-btn') and .//span[normalize-space()='Search']]")
    WebElement searchButton;

    public void goToCruiseMenu() {
        WebElement cruiseMenu = Waits.waitForElementToBeClickable(driver, cruiseTab, 10);
        cruiseMenu.click();
    }

    public void selectDepartureCity(String city) {
        Waits.waitForVisibility(driver, departureCityBox, 10);
        Waits.waitForElementToBeClickable(driver, departureCityBox, 10).click();
        By cityOption = By.xpath("//div[contains(@class,'c-dropdown') and contains(@class,'show')]//span[normalize-space()='" + city + "']");
        WebElement cityElem = Waits.waitForElementToBeClickable(driver, cityOption, 10);
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
