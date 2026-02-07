package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import Utilities.Waits;

import java.util.List;

public class HotelResultsPage extends BasePage {
    public HotelResultsPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(@class,'hotel-count-tip') and @role='heading']")
    WebElement resultsCount;
    @FindBy(xpath="//div[contains(@class,'style_radio-item__6Kt3T') and @role='checkbox' and @aria-label='Pool']")
    WebElement poolFilter;
    @FindBy(xpath="//div[contains(@class,'hotel-card')]")
    List<WebElement> hotelCards;
    @FindBy(xpath="//span[contains(@class,'style_dropdown-selector__iZHJ2') and .//span[contains(text(),'Trip.com recommended')]]")
    WebElement sortDropdown;
    @FindBy(xpath="//span[contains(@class,'style_dropdown-selector-options__BelBO') and normalize-space(text())='Top reviewed']")
    WebElement topReviewedOption;
    @FindBy(xpath = "//div[contains(@class,'style_choosen-item__XppJT')]/span")
    WebElement filterResultText;

    public String getResultsCount() {
        try {
            return resultsCount.getAttribute("aria-label");
        } catch (Exception e) {
            System.out.println("Hotel results count element not found: " + e.getMessage());
            return "N/A";
        }
    }
    public void applyPoolFilter() {
        Waits.waitForElementToBeClickable(driver, poolFilter, 5).click();
    }
    public void refreshPage() {
        driver.navigate().refresh();
    }
    public List<WebElement> getHotelCards() {
        return hotelCards;
    }

    public void sortByTopReviewed() {
        Waits.waitForVisibility(driver, sortDropdown, 5);
        Waits.waitForElementToBeClickable(driver, sortDropdown, 5).click();
        Waits.waitForVisibility(driver, topReviewedOption, 5);
        Waits.waitForElementToBeClickable(driver, topReviewedOption, 5).click();
    }

    public String getSearchResultText() {
        try {
            Waits.waitForVisibility(driver, filterResultText, 10);
            System.out.println("Search result text: " + filterResultText.getText());
            return filterResultText.getText();
        } catch (Exception e) {
            System.out.println("Search result text not found: " + e.getMessage());
            return "";
        }
    }
}
