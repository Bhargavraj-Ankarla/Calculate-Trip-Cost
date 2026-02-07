package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import PageObjects.CruiseResultsPage;
import PageObjects.CruiseSearchPage;
import BaseTest.BaseTestClass;
import Utilities.ExcelUtil;

public class CruiseDetailsTest extends BaseTestClass {
    @Test(priority = 1)
    public void cruisesDashboard(){
        logger.info("***** Starting Cruise Details Test ****");
        CruiseSearchPage cruisePage = new CruiseSearchPage(driver);
        try{
            cruisePage.goToCruiseMenu();
            cruisePage.closeAllPopups();
            cruisePage.selectDepartureCity("New York");
            cruisePage.selectDestinationCity("Europe");
            cruisePage.closeAllPopups();
            cruisePage.clickSearchButton();
            logger.info("***** Cruise Details Test Completed ****");
        }catch (Exception e){
            Assert.fail("Testcase failed due to"+e.getMessage());
        }
    }
    @Test(priority = 2 ,dependsOnMethods = {"cruisesDashboard"})
    public void cruisesResultsPage() {
        logger.info("***** Starting Cruise Results Test ****");
        try{
            CruiseResultsPage cruiseResultsPage = new CruiseResultsPage(driver);
            cruiseResultsPage.switchToLastWindow();
            cruiseResultsPage.waitForCruiseProductList();
            cruiseResultsPage.closeAllPopups();
            cruiseResultsPage.SelectTopReviewed();
            cruiseResultsPage.selectFirstCruiseProduct();
        }catch (Exception e){
            Assert.fail("Testcase failed due to"+e.getMessage());
        }

    }
    @Test(priority = 3, dependsOnMethods = {"cruisesResultsPage"})
    public void CruisesDetails(){
        logger.info("***** Selected Cruise Details ****");
        CruiseResultsPage cruiseResultsP = new CruiseResultsPage(driver);
        try{
            cruiseResultsP.switchToLastWindow();
            cruiseResultsP.waitForCruiseDetailsPage();
            cruiseResultsP.Scrolling();

            String guestCapacity = cruiseResultsP.getGuestCapacity();
            String renovated = cruiseResultsP.getRenovatedYear();
            String cruiseId = cruiseResultsP.getCruiseId();

            logger.info("Guest capacity: {}", guestCapacity);
            logger.info("Renovated: {}", renovated);
            logger.info("Cruise ID: {}", cruiseId);

            System.out.println("Cruise ID: " + cruiseId);
            System.out.println("Guest Capacity: " + guestCapacity);
            System.out.println("Renovated Year: " + renovated);

            ExcelUtil.writeCruiseData(guestCapacity, renovated, cruiseId, configProp.getProperty("Cruise-Details"));
            logger.info("***** Cruise Results Test Completed ****");
        }catch (Exception e){
            Assert.fail("Testcase failed due to"+e.getMessage());
        }

    }
}




