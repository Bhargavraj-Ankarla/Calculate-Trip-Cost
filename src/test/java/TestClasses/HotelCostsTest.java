package TestClasses;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import PageClasses.HotelResultsPage;
import PageClasses.Homepage;
import BaseClass.BaseTestClass;
import Utilities.ExcelUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelCostsTest extends BaseTestClass {

    List<WebElement> hotelCards;
    List<String> afterSearch;
    List<String> afterPool;
    List<String> afterTopReviewed;

    public String getYearMonthDay(String date, String month, String dayOfWeek) {
        Map<String,String> monthMap = new HashMap<>();
        monthMap.put("1","Jan");
        monthMap.put("2","Feb");
        monthMap.put("3","Mar");
        monthMap.put("4","Apr");
        monthMap.put("5","May");
        monthMap.put("6","Jun");
        monthMap.put("7","Jul");
        monthMap.put("8","Aug");
        monthMap.put("9","Sep");
        monthMap.put("10","Oct");
        monthMap.put("11","Nov");
        monthMap.put("12","Dec");

        Map<String,String> dayConversionMap = new HashMap<>();
        dayConversionMap.put("MONDAY","Mon");
        dayConversionMap.put("TUESDAY","Tue");
        dayConversionMap.put("WEDNESDAY","Wed");
        dayConversionMap.put("THURSDAY","Thu");
        dayConversionMap.put("FRIDAY","Fri");
        dayConversionMap.put("SATURDAY","Sat");
        dayConversionMap.put("SUNDAY","Sun");

        return dayConversionMap.get(dayOfWeek)+", "+monthMap.get(month)+" "+date;

    }

    @Test (priority = 1)
    public void HomepageDashboard () {
        Homepage hp = new Homepage(driver);
        String CurrentTime = "ID-" + java.time.LocalTime.now();
        logger.info("{} --Starting HotelCostsTest - HomepageDashboard  ", CurrentTime);
        try{
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

            Assert.assertEquals(hp.getDestination(),"Nairobi","the destination cities does not match");

            String actualCheckIn = hp.getCheckinDate();
            String expectedCheckInDate  = getYearMonthDay(String.valueOf(checkIn.getDayOfMonth()), String.valueOf(checkIn.getMonthValue()), String.valueOf(checkIn.getDayOfWeek()));
            Assert.assertEquals(actualCheckIn,expectedCheckInDate,"Check-in date is not matched correctly");

            String actualCheckOut = hp.getCheckoutDate();
            String expectedCheckOutDate  = getYearMonthDay(String.valueOf(checkOut.getDayOfMonth()), String.valueOf(checkOut.getMonthValue()), String.valueOf(checkOut.getDayOfWeek()));
            Assert.assertEquals(actualCheckOut,expectedCheckOutDate);

            Assert.assertEquals(hp.getRoomsAndGuests(),"1 room, 4 adults, 0 children");

            hp.clickSearch();
            driver.manage().deleteAllCookies();
            logger.info("***** Searched for required hotel details  ****");
        }catch (Exception e){
            Assert.fail("Testcase failed due to"+e.getMessage());
        }
    }

    @Test (priority = 2 , dependsOnMethods = {"HomepageDashboard"})
    public void hotelResultsPage() {
        logger.info("***** HotelResultsPage Started  ****");
        HotelResultsPage hr = new HotelResultsPage(driver);
        try{
            String resultsCount = hr.getResultsCount();
            System.out.println("Total hotel results: " + resultsCount);
            hotelCards = hr.getHotelCards();
            afterSearch = new ArrayList<>();
            for (WebElement card : hotelCards) {
                afterSearch.add(card.getText());
            }
        }catch (Exception e){
            Assert.fail("Testcase failed due to"+e.getMessage());
        }
    }
    @Test (priority = 3 ,dependsOnMethods = {"hotelResultsPage"})
    public void hotelResultsAfterPoolFilter() {
        HotelResultsPage hr = new HotelResultsPage(driver);
        try{
            hr.applyPoolFilter();
            Assert.assertEquals(hr.getSearchResultText(),"Pool","The applied filter is not displayed in the search result text.");
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
        }catch (Exception e){
            Assert.fail("Testcase failed due to"+e.getMessage());
        }
    }

    @Test (priority = 4 ,dependsOnMethods = {"hotelResultsAfterPoolFilter"})
    public void TopReviewedHotelResults(){
        HotelResultsPage hr = new HotelResultsPage(driver);
        try{
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
        }catch (Exception e){
            Assert.fail("Testcase failed due to"+e.getMessage());
        }
    }
}
