package Utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static BaseClass.BaseTestClass.driver;

public class Screenshot {
    public String captureScreen(String tName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath=System.getProperty("user.dir")+"\\Failed Screenshots\\" + tName + "_" + timeStamp + ".png";
        File targetFile=new File(targetFilePath);
        boolean result = sourceFile.renameTo(targetFile);
        if (!result) { throw new IOException("Failed to move screenshot to: " + targetFilePath); }
        return targetFilePath;
    }
}
