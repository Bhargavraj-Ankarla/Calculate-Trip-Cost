package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import Utilities.Waits;

import java.util.ArrayList;
import java.util.List;

public class CruiseResultsPage extends BasePage {
    public CruiseResultsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath="//div[contains(@class,'product-name')]")
    List<WebElement> cruiseProducts;
    @FindBy(xpath="//div[@class='ship-parames']/span[contains(text(),'Guest capacity:')]")
    WebElement guestCapacityElem;
    @FindBy(xpath="//div[@class='ship-parames']/span[contains(text(),'Entered service')]")
    WebElement renovatedYear;
    @FindBy(xpath="//div[contains(@class,'cruise-ids')]/span[contains(@class,'cruise-id') and contains(text(),'Cruise ID:')]")
    WebElement cruiseId;
    @FindBy(xpath="//span[@class='sort-name-text' and contains(text(),\"Best reviews\")]")
    WebElement BestReviewed;
    @FindBy(xpath="//i[contains(@class,'fi-close')]")
    List<WebElement> closeButtons;
    @FindBy(xpath="//div[@class='atlas-inner']")
    WebElement ScrollUpTo;
    @FindBy(xpath="//div[@class='ship-parames']")
    WebElement ShipDetails;
    @FindBy(xpath="//div[contains(@class,'product-name')]")
    WebElement ShipsListByName;

    public void switchToLastWindow() {
        List<String> windowList = new ArrayList<>(driver.getWindowHandles());
        if (windowList.size() > 1) {
            driver.switchTo().window(windowList.getLast());
        }
    }
    public void SelectTopReviewed(){
        Waits.waitForElementToBeClickable(driver, BestReviewed, 5).click();
    }
    public void selectFirstCruiseProduct() {
        if (!cruiseProducts.isEmpty()) {
            WebElement firstCruise = cruiseProducts.getFirst();
            Waits.waitForElementToBeClickable(driver, firstCruise, 5).click();
        }
    }
    public String getCruiseId() {
        try {
            String cruiseIdText = cruiseId.getText();
            return cruiseIdText.split(":")[1];
        } catch (Exception e) {
            System.out.println("Cruise ID not found: " + e.getMessage());
            return "N/A";
        }
    }
    public String getGuestCapacity() {
        try {
            Waits.waitForVisibility(driver, guestCapacityElem, 5);
            String GuestCapacityText = guestCapacityElem.getText();
            return GuestCapacityText.split(":")[1];
        } catch (Exception e) {
            System.out.println("GuestCapacity not found: " + e.getMessage());
            return "N/A";
        }
    }

    public String getRenovatedYear() {
        try {
            Waits.waitForVisibility(driver, renovatedYear, 5);
            String RenovatedYearText = renovatedYear.getText();
            return  RenovatedYearText.split(":")[1];
        } catch (Exception e) {
            System.out.println("RenovatedYear not found: " + e.getMessage());
            return "N/A";
        }
    }

    public void Scrolling(){
        Actions action = new Actions(driver);
        action.scrollToElement(ScrollUpTo).perform();
    }
    public void waitForCruiseProductList() {
        Waits.waitForVisibility(driver,ShipsListByName, 5);
    }
    public void waitForCruiseDetailsPage() {
        Waits.waitForVisibility(driver,ShipDetails, 5);
    }
    public void closeAllPopups() {
        for (WebElement closeButton : closeButtons) {
            if (closeButton.isDisplayed() && closeButton.isEnabled()) {
                closeButton.click();
            }
        }
    }
}
