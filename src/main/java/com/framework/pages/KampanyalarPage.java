package com.framework.pages;

import com.framework.base.TestBase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class KampanyalarPage extends TestBase {

    @FindBy(xpath = "//SECTION[contains(@class,'group category')]")
    List<WebElement> secCategories;




    public KampanyalarPage() {
        PageFactory.initElements(getDriver(), this);
    }



    public KampanyalarPage getCategoriesAndCampaignsAndWriteToExcel() {
        try {
            Workbook wb = new XSSFWorkbook();

            HashMap<String, WebElement> categoryAndCampaigns = new HashMap<>();

            for (WebElement category : secCategories) {
                String categoryTitle = category.findElement(By.xpath("./h2[@class='unlineTitle']")).getText();

                List<WebElement> campaigns = category.findElements(By.xpath("./ul/li/a/img"));

                categoryAndCampaigns.put(categoryTitle, campaigns.get(campaigns.size() - 2));

            }

            for (Map.Entry<String, WebElement> entry : categoryAndCampaigns.entrySet()) {
                String categoryTitle = entry.getKey();

                while(!isImageLoaded(entry.getValue()))
                    ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView(false);arguments[0].focus();", entry.getValue());

                String campaignName = entry.getValue().getAttribute("alt");
                String campaignImageUrl = entry.getValue().getAttribute("src");

                Sheet sheet = wb.createSheet(categoryTitle + " - " + campaignName);

                InputStream inputStream = new URL(campaignImageUrl).openStream();
                byte[] bytes = IOUtils.toByteArray(inputStream);
                int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                inputStream.close();
                CreationHelper helper = wb.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();

                ClientAnchor anchor = helper.createClientAnchor();

                anchor.setCol1(1);
                anchor.setRow1(2);
                anchor.setCol2(2);
                anchor.setRow2(3);

                Picture pict = drawing.createPicture(anchor, pictureIdx);

                pict.resize();

                Cell cell = sheet.createRow(2).createCell(1);

            }

            FileOutputStream fileOut = null;
            fileOut = new FileOutputStream("output.xlsx");
            wb.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            System.out.println(e);
        }


        return this;
    }

    private Boolean isImageLoaded(WebElement element) {
        Boolean status = (Boolean) ((JavascriptExecutor)getDriver()).executeScript("return arguments[0].complete && arguments[0].src != \"https://n11scdn.akamaized.net/static/css/jquery/img/blank.gif\"", element);

        return status;
    }

}
