package TestCases;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import PageObjects.HotelResultsPage;
import PageObjects.Homepage;
import BaseTest.BaseTestClass;
import Utilities.ExcelUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HotelCostsTest extends BaseTestClass {

    List<WebElement> hotelCards;
    List<String> afterSearch;
    List<String> afterPool;
    List<String> afterTopReviewed;

    @Test (priority = 1)
    public void WarningMsg(){
      //  Homepage hp = new Homepage(driver);
        String CurrentTime = "ID-" + java.time.LocalTime.now();
        logger.info("{} --Starting HotelCostsTest -- Capture warning message ", CurrentTime);
//        hp.clickSearch();
//        String warningMessage = hp.WarningMSG();
//        System.out.println("Captured warning message is "+warningMessage);
    }

    @Test (priority = 2)
    public void HomepageDashboard () {
        logger.info("*****  Starting HotelCostsTest - HomepageDashboard  ****");
        Homepage hp = new Homepage(driver);
        hp.closePopup();
        hp.enterDestination("Nairobi");
        hp.openCheckInDate();
        LocalDate checkIn = LocalDate.now().plusDays(1);
        hp.selectDate(checkIn.getDayOfMonth());
        hp.openCheckOutDate();
        LocalDate checkOut = LocalDate.now().plusDays(6);
        hp.selectDate(checkOut.getDayOfMonth());
        hp.openGuestSelector();
        hp.addAdults(2);
        hp.confirmGuests();
        hp.clickSearch();
        driver.manage().deleteAllCookies();
        logger.info("***** Searched for required hotel details  ****");
    }

    @Test (priority = 3 , dependsOnMethods = {"HomepageDashboard"})
    public void hotelResultsPage() {
        logger.info("***** HotelResultsPage Started  ****");
        HotelResultsPage hr = new HotelResultsPage(driver);
        String resultsCount = hr.getResultsCount();
        System.out.println("Total hotel results: " + resultsCount);
        hotelCards = hr.getHotelCards();
        afterSearch = new ArrayList<>();
        for (WebElement card : hotelCards) {
            afterSearch.add(card.getText());
        }
    }
    @Test (priority = 4 ,dependsOnMethods = {"hotelResultsPage"})
    public void hotelResultsAfterPoolFilter() {
        HotelResultsPage hr = new HotelResultsPage(driver);
        hr.applyPoolFilter();
        hr.refreshPage();
        hotelCards = hr.getHotelCards();
        afterPool = new ArrayList<>();
        for (WebElement element : hotelCards) {
            afterPool.add(element.getText());
        }
        hotelCards = hr.getHotelCards();
        if (hotelCards.isEmpty()) {
            System.out.println("No hotel results found after applying Pool filter.");
        }
        String resultsCountAfterPool = hr.getResultsCount();
        System.out.println("Total hotel results after Pool filter: " + resultsCountAfterPool);
    }

    @Test (priority = 5 ,dependsOnMethods = {"hotelResultsAfterPoolFilter"})
    public void TopReviewedHotelResults() throws IOException {
        HotelResultsPage hr = new HotelResultsPage(driver);
        hr.sortByTopReviewed();
        hr.refreshPage();
        hotelCards = hr.getHotelCards();
        afterTopReviewed = new ArrayList<>();
        for (WebElement element : hotelCards) {
            afterTopReviewed.add(element.getText());
        }
        hotelCards = hr.getHotelCards();
        ExcelUtil.writeHotelData(hotelCards, configProp.getProperty("Hotel-Details"));
        List<List<String>> allLists = List.of(afterSearch, afterPool, afterTopReviewed);
        ExcelUtil.writeHotelNames(allLists, configProp.getProperty("Hotels-Sort-Verification-Excel"));
        driver.navigate().back();

        logger.info("***** HotelCostsTest Completed  ****");
    }
}
