package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


public class FunctionLibraries {
	
	public static WebDriver driver;
	public static Properties pro;
	
	public static WebDriver StartBrowser() throws Throwable, IOException {
		pro=new Properties();
		pro.load(new FileInputStream("./PropertyFile/stock.properties"));
		if(pro.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}else if(pro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver=new FirefoxDriver();
		}	
		return driver;
	}
	
	public static void URL() {
		driver.get(pro.getProperty("url"));
	}
	
	public static void WaitForElement(String LocatorType,String Locatorvalue,String Testdata) {
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Testdata)));
		if(LocatorType.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locatorvalue)));
		}
		
		if(LocatorType.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locatorvalue)));
		}
		
		if(LocatorType.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locatorvalue)));
		}
	}
	
	public static void TypeAction(String LocatorType,String Locatorvalue,String Testdata) {
		if(LocatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locatorvalue)).clear();
			driver.findElement(By.name(Locatorvalue)).sendKeys(Testdata);
		}
		if(LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locatorvalue)).clear();
			driver.findElement(By.xpath(Locatorvalue)).sendKeys(Testdata);
		}
		if(LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locatorvalue)).clear();
			driver.findElement(By.id(Locatorvalue)).sendKeys(Testdata);
		}
	}
	
	public static void Clickaction(String LocatorType,String Locatorvalue) {
		
		if(LocatorType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locatorvalue)).click();
		}
		if(LocatorType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locatorvalue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locatorvalue)).sendKeys(Keys.ENTER);
		}
	}
	
	public static void VilidateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			org.testng.Assert.assertEquals(Actual_Title, Expected_Title,"Title is Not Matching");
		}catch (AssertionError a) {
			System.out.println(a.getMessage());
		}
	}
	
	public static void CloseBrowser()
	{
		driver.quit();
	}
	
	public static String Date() {
	
		java.util.Date date= new java.util.Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD HH_mm");
		return df.format(date);
		
	}

	public static void DropdownAction(String LocatorType,String Locatorvalue,String Testdata) {
		
		if(LocatorType.equalsIgnoreCase("name")) {
		int value=Integer.parseInt(Testdata);
		Select sel=new Select(driver.findElement(By.name(Locatorvalue)));
		sel.selectByIndex(value);	
	    }
		if(LocatorType.equalsIgnoreCase("xpath")) {
			int value=Integer.parseInt(Testdata);
			Select sel=new Select(driver.findElement(By.xpath(Locatorvalue)));
			sel.selectByIndex(value);	
		    }
		if(LocatorType.equalsIgnoreCase("id")) {
			int value=Integer.parseInt(Testdata);
			Select sel=new Select(driver.findElement(By.id(Locatorvalue)));
			sel.selectByIndex(value);	
		    }
	}
	
	public static void capturestock(String LocatorType,String Locatorvalue) throws Throwable {
		String stockNum="";
		if(LocatorType.equalsIgnoreCase("name"))
		{
			stockNum = driver.findElement(By.name(Locatorvalue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			stockNum = driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			stockNum = driver.findElement(By.id(Locatorvalue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./Capture/stocknum.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush();
		bw.close();
	}
	
	public static void Stocktable() throws Throwable {
		FileReader fr=new FileReader("./Capture/stocknum.txt");
		BufferedReader br=new BufferedReader(fr);
		String exp=br.readLine();
		if(!driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).isDisplayed())
			driver.findElement(By.xpath(pro.getProperty("searchpannel"))).click();
		driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).clear();
		driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).sendKeys(exp);
		driver.findElement(By.xpath(pro.getProperty("searchbutton"))).click();
		Thread.sleep(2000);
		String act=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(act+"===="+exp,true);
		try {
			Assert.assertEquals(act, exp ,"Not matching ");
		} catch (Exception e) {
		System.out.println(e.getMessage());
		}
		
	}
	
	public static void capturesuppiler(String LocatorType,String Locatorvalue) throws Throwable {
		String suppilernum="";
		if(LocatorType.equalsIgnoreCase("name")) {
		suppilernum =	driver.findElement(By.name(Locatorvalue)).getAttribute("value");
			
		}
		if(LocatorType.equalsIgnoreCase("id")) {
			suppilernum =	driver.findElement(By.id(Locatorvalue)).getAttribute("value");
				
			}
		if(LocatorType.equalsIgnoreCase("xpath")) {
			suppilernum =	driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
				
			}
		FileWriter fw=new FileWriter("./Capture/suppilernum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(suppilernum);
		bw.flush();
		bw.close();
	}
	

	public static void Suppilerstable() throws Throwable {
		FileReader fr=new FileReader("./Capture/suppilernum.txt");
		BufferedReader br=new BufferedReader(fr);
		String act=br.readLine();
		if(!driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).isDisplayed())
				driver.findElement(By.xpath(pro.getProperty("searchpannel"))).click();
		driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).clear();
		driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).sendKeys(act);
		driver.findElement(By.xpath(pro.getProperty("searchbutton"))).click();
		Thread.sleep(2000);
	String exp=	driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(exp+"======"+act,true);
	try {
		Assert.assertEquals(act, exp,"Not matching");
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	}
	
	public static void CaptureCustomer(String LocatorType,String Locatorvalue) throws Throwable {
		String Customernum="";
		if(LocatorType.equalsIgnoreCase("name")) {
		Customernum =	driver.findElement(By.name(Locatorvalue)).getAttribute("value");
			
		}
		if(LocatorType.equalsIgnoreCase("id")) {
			Customernum =	driver.findElement(By.id(Locatorvalue)).getAttribute("value");
				
			}
		if(LocatorType.equalsIgnoreCase("xpath")) {
			Customernum =	driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
				
			}
		FileWriter fw=new FileWriter("./Capture/customernum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Customernum);
		bw.flush();
		bw.close();
	}
	
	public static void Customertable() throws Throwable {
		FileReader fr=new FileReader("./Capture/customernum.txt");
		BufferedReader br=new BufferedReader(fr);
		String act=br.readLine();
		if(!driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).isDisplayed())
				driver.findElement(By.xpath(pro.getProperty("searchpannel"))).click();
		driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).clear();
		driver.findElement(By.xpath(pro.getProperty("searchtextbox"))).sendKeys(act);
		driver.findElement(By.xpath(pro.getProperty("searchbutton"))).click();
		Thread.sleep(2000);
	String exp=	driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
	Reporter.log(exp+"======"+act,true);
	try {
		Assert.assertEquals(act, exp,"Not matching");
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	}
	
}
