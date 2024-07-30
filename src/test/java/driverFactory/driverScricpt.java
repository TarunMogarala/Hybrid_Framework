package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibraries;
import utilities.ExcelFileUtil;

public class driverScricpt {
	
	
 WebDriver driver;
 String input="./FileInput/Controller.xlsx";
 String output="./FileOutput/Hybridresults.xlsx";
 String sheet="TestCases";
 ExtentReports report;
 ExtentTest test;
 
 public void starttest() throws Throwable {
	 
	String modulestatus="";
	String modulenew="";
	 
	 ExcelFileUtil xl= new ExcelFileUtil(input);
	
	 for(int i=1;i<=xl.Count(sheet);i++) {
		 if(xl.GetCellData(sheet, i, 2).equalsIgnoreCase("y")) {
			String Tcmodule= xl.GetCellData(sheet, i, 1);
			report=new ExtentReports("./target/reports/"+Tcmodule+FunctionLibraries.Date()+".html");
			test=report.startTest(Tcmodule);
			
			for(int j=1;j<=xl.Count(Tcmodule);j++) {
				String des=xl.GetCellData(Tcmodule, j, 0);
				String objtype=xl.GetCellData(Tcmodule, j, 1);
				String loctype=xl.GetCellData(Tcmodule, j, 2);
				String locvalue=xl.GetCellData(Tcmodule, j, 3);
				String testdata=xl.GetCellData(Tcmodule, j, 4);
				
				try {
					if(objtype.equalsIgnoreCase("startbrowser")) {
						driver=FunctionLibraries.StartBrowser();
						test.log(LogStatus.INFO,des);		
					}
					if(objtype.equalsIgnoreCase("url")) {
						FunctionLibraries.URL();
						test.log(LogStatus.INFO,des);
					}
					if(objtype.equalsIgnoreCase("waitforelement")) {
						FunctionLibraries.WaitForElement(loctype, locvalue, testdata);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("typeaction")) {
						FunctionLibraries.TypeAction(loctype, locvalue, testdata);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("clickaction")) {
						FunctionLibraries.Clickaction(loctype, locvalue);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("validatetitle")) {
						FunctionLibraries.VilidateTitle(testdata);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("closebrowser")) {
						FunctionLibraries.CloseBrowser();
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("dropdownaction")) {
						FunctionLibraries.DropdownAction(loctype, locvalue, testdata);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("capturestock")) {
						FunctionLibraries.capturestock(loctype, locvalue);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("stocktable")) {
						FunctionLibraries.Stocktable();
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("capturesuppiler")) {
						FunctionLibraries.capturesuppiler(loctype, locvalue);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("Suppilerstable")) {
						FunctionLibraries.Suppilerstable();
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("capturecustomer")) {
						FunctionLibraries.CaptureCustomer(loctype, locvalue);
						test.log(LogStatus.INFO, des);
					}
					if(objtype.equalsIgnoreCase("customertable")) {
						FunctionLibraries.Customertable();
						test.log(LogStatus.INFO, des);
					}
					
					xl.SetCell(Tcmodule, j, 5, "Pass", output);
					test.log(LogStatus.PASS, des);
					modulestatus="true";
				
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					xl.SetCell(Tcmodule, j, 5, "Fail", output);
					modulenew="false";
				}
				if(modulestatus.equalsIgnoreCase("true")) {
					xl.SetCell(sheet, i, 3, "Pass", output);
				}
				report.endTest(test);
				report.flush();
			}
			if(modulenew.equalsIgnoreCase("false")) {
				xl.SetCell(sheet, i, 3, "Fail", output);
			}
			 
		 }else {
			 xl.SetCell(sheet, i, 3, "Blocked", output);
		 }
	 }
 }
 
}
