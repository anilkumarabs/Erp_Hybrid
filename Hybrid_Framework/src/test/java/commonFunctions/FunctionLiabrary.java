package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLiabrary 
{
public static WebDriver driver;
public static Properties conpro;
public static WebDriver startBrowser() throws Throwable
{
	conpro=new Properties();
	conpro.load(new FileInputStream("./propertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver=new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
	{
		driver=new FirefoxDriver();
	}else
	{
		Reporter.log("browser value is not matching",true);
	}
	return driver;
}
public static void openUrl(WebDriver driver)
{
	driver.get(conpro.getProperty("Url"));
	
}
public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
{
	WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
	if(Locator_Type.equalsIgnoreCase("id")) 
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
	}
		
		
}
public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).clear();
		driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).clear();
		driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
	}if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).clear();
		driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
	}
	
}
public static void clickAction(WebDriver driver,String Locator_Type,String Locator_Value)
{
	if(Locator_Type.equalsIgnoreCase("id"))
	{
	driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).click();
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).click();
	}
}
public static void closeBrowser(WebDriver driver)
{
 driver.quit();	
}
public static void validateTitle(WebDriver driver,String Exp_title)
{
	String Act_title = driver.getTitle();
	try {
	Assert.assertEquals(Act_title,Exp_title,"title is not matching");
	}catch (Throwable t) 
	{
		System.out.println(t.getMessage());
	}
}
public static String generateDate()
{
	Date date= new Date();
	DateFormat df=new SimpleDateFormat("yyyy_mm_dd hh_mm_ss");
	return df.format(date);
}
public static void mouseClick(WebDriver driver) throws Throwable
{
	Actions  ac=new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]")));
	ac.perform();
	Thread.sleep(2000);
	ac.moveToElement(driver.findElement(By.xpath("(//a[starts-with(text(),'Stock Categories')][1])[2]")));
	ac.pause(2000).click().perform();
}
public static void categoryTable(WebDriver driver,String Exp_data) throws Throwable
{
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(2000);
	String Act_data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
	Reporter.log(Exp_data+"     "+Act_data,true);
	try {
	Assert.assertEquals(Exp_data, Act_data,"category name is not matching");
	}
	catch (Throwable t) 
	{
		System.out.println(t.getMessage());
	}
}
public static void dropdownaction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
{
if(Locator_Type.equalsIgnoreCase("id"))
{
	int value=Integer.parseInt(Test_Data);
	WebElement element =driver.findElement(By.id(Locator_Value));
	Select select=new Select(element);
	select.selectByIndex(value);	
}
if(Locator_Type.equalsIgnoreCase("name"))
{
	int value=Integer.parseInt(Test_Data);
	WebElement element= driver.findElement(By.name(Locator_Value));
	Select select =new Select(element);
	select.selectByIndex(value);
}
if(Locator_Type.equalsIgnoreCase("name"))
{
	int value=Integer.parseInt(Test_Data);
	WebElement element= driver.findElement(By.name(Locator_Value));
	Select select =new Select(element);
	select.selectByIndex(value);
}}
public static void captureStockNumber(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable
{
	String Stocknumber="";
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		Stocknumber=driver.findElement(By.id(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
	Stocknumber=driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
	Stocknumber=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");	
	}
	FileWriter fw =new FileWriter("./captureData/Stocknumber.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write(Stocknumber);
	bw.flush();
	bw.close();
	
}
public static void stocktable(WebDriver driver) throws Throwable
{
	FileReader fr =new FileReader("./captureData/Stocknumber.txt");
	BufferedReader br=new BufferedReader(fr);
	String exp_data=br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
	
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[8]/div/span/span"))
				.getText();
		Reporter.log(exp_data+"   "+act_data);
		try {
	Assert.assertEquals(exp_data, act_data,"stock number not matching");
	}catch (Exception e) 
		{
		System.out.println(e.getMessage());
		
	}
	}
public static void captureSupplierNumber(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable		
{
	String suppliarnumber="";
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		suppliarnumber=driver.findElement(By.id(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		suppliarnumber=driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		suppliarnumber=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}
	
	FileWriter fw=new FileWriter("./captureData/suppliarnumber.txt");
	BufferedWriter br= new BufferedWriter(fw);
	br.write(suppliarnumber);
	br.flush();
	br.close();
	
}
public static void suppiartable(WebDriver driver) throws Throwable
{
	FileReader fr = new FileReader("./captureData/suppliarnumber.txt");
	BufferedReader br =new BufferedReader(fr);
	String exp_data=br.readLine();
	br.close();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
	driver.findElement(By.xpath(conpro.getProperty("search-button")));
	String act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[6]/div/span/span"))
			.getText();
	try {
		Assert.assertEquals(exp_data, act_data,"values are not matching");
	}catch (Exception e) 
	{
		System.out.println(e.getMessage());
	}
}
public static void captureCustomerNumber(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable
{
	String customernumber="";
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		customernumber=driver.findElement(By.id(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		customernumber=driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		customernumber=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}
	FileWriter fr =new FileWriter("./captureData/customernumber.txt");
	BufferedWriter br =new BufferedWriter(fr);
	br.write(customernumber);
	br.flush();
	br.close();
}
public static void Customertable(WebDriver driver) throws Throwable
{
	FileReader fr=new FileReader("./captureData/customernumber.txt");
	BufferedReader br=new BufferedReader(fr);
	String exp_data=br.readLine();
	br.close();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed());
	driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	String act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[5]/div/span/span"))
			.getText();
	try {
		Assert.assertEquals(exp_data, act_data,"values are not matching");
	}catch (Exception e) {
		System.out.println(e.getMessage());
	}
	
	}
}



