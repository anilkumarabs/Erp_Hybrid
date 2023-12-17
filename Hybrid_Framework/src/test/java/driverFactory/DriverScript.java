package driverFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLiabrary;
import utilities.ExcelFileUtil;

public class DriverScript
{
	public static WebDriver driver;
	String inputpath ="./FileInput/controller.xlsx";
	String outputpath ="./FileOutpput/Hybrid_Results.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String testcases="MasterTestCases";
	public void StartTest() throws Throwable
	{
	String 	Module_Status="";
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	for(int i=1;i<=xl.Rowcount(testcases);i++)
	{
		String Execution_Status=xl.getCellData(testcases, i, 2);
		if(Execution_Status.equalsIgnoreCase("y"))
		{
			String Tcmodule=xl.getCellData(	testcases, i, 1) ;
			report=new ExtentReports("./target/extentreports/"+Tcmodule+FunctionLiabrary.generateDate()+".html");
			logger=report.startTest(Tcmodule);
			for(int j=1;j<=xl.Rowcount(Tcmodule);j++)
			{
				String Description=xl.getCellData(Tcmodule, j, 0);
				String Object_Type=xl.getCellData(Tcmodule, j, 1);
				String Locator_Type=xl.getCellData(Tcmodule, j, 2);
				String Locator_Value=xl.getCellData(Tcmodule, j, 3);
				String Test_Data= xl.getCellData(Tcmodule, j, 4);
				try {
				if(Object_Type.equalsIgnoreCase("startBrowser"))
				{
					driver=FunctionLiabrary.startBrowser();
					logger.log(LogStatus.INFO, Description);
				}
				if(Object_Type.equalsIgnoreCase("openUrl")) 
				{
					FunctionLiabrary.openUrl(driver);
					logger.log(LogStatus.INFO, Description);
				}
				if(Object_Type.equalsIgnoreCase("waitForElement")) {
					FunctionLiabrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("typeAction")) {
					FunctionLiabrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("clickAction")) {
					FunctionLiabrary.clickAction(driver, Locator_Type, Locator_Value);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("validateTitle")) {
					FunctionLiabrary.validateTitle(driver, Test_Data);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("closeBrowser")) {
					FunctionLiabrary.closeBrowser(driver);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("mouseClick")) {
					FunctionLiabrary.mouseClick(driver);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("categoryTable")) {
					FunctionLiabrary.categoryTable(driver, Test_Data);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("stocktable")) {
					FunctionLiabrary.stocktable(driver);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("dropdownaction")) {
					FunctionLiabrary.dropdownaction(driver, Locator_Type, Locator_Value, Test_Data);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("captureStockNumber")) {
					FunctionLiabrary.captureStockNumber(driver, Locator_Type, Locator_Value);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("suppiartable")) {
					FunctionLiabrary.suppiartable(driver);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("captureSupplierNumber")) {
					FunctionLiabrary.captureSupplierNumber(driver, Locator_Type, Locator_Value);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("captureCustomerNumber")) {
					FunctionLiabrary.captureCustomerNumber(driver, Locator_Type, Locator_Value);
					logger.log(LogStatus.INFO, Description);

				}
				if(Object_Type.equalsIgnoreCase("Customertable")) {
					FunctionLiabrary.Customertable(driver);
					//logger.log(LogStatus.INFO, Description);

				}
				
				xl.setCellData(Tcmodule, j, 5, "pass", outputpath);
				Module_Status="true";
				logger.log(LogStatus.PASS, Description);
		}catch (Exception e)
				{
			xl.setCellData(Tcmodule, j, 5, "fail", outputpath);
			Module_Status="false";
			System.out.println(e.getMessage());
			logger.log(LogStatus.FAIL, Description);
				}
			if(Module_Status.equalsIgnoreCase("true"))
			{
				xl.setCellData(testcases, i, 3, "pass", outputpath);
				
			}
			if(Module_Status.equalsIgnoreCase("false"))
			{
				xl.setCellData(testcases, i, 3, "fail", outputpath);
			}}
			report.endTest(logger);
			report.flush();
			
		}
		else{
			xl.setCellData(testcases, i, 3, "blocked", outputpath);
		
		}
	}
	}
	}
