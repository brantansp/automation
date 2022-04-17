package tools;

import java.net.MalformedURLException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Extend {
	

	public static ExtentReports  reports = new ExtentReports(System.getProperty("user.dir") + "/er/report.html", false);
	public static ExtentTest  test = null;
	
	
	/**
	 * Function where we start to add testcase in extend report
	 * @param testcase
	 * @throws MalformedURLException
	 */
	
	public static void testcase(String testcase,String tag, String testCasePath) throws MalformedURLException {
		
		
		/*
		 * try { String tcName = Testlink.testCaseName(testcase); test =
		 * reports.startTest(testcase + " => " + tcName); String tcSummary =
		 * Testlink.testCaseSummary(testcase); test.setDescription(tcSummary); } catch
		 * (Exception e) { // TODO Auto-generated catch block test =
		 * reports.startTest(testcase); test.setDescription("Not Available"); }
		 */
		
		test = reports.startTest(testcase, testCasePath);
		test.assignCategory(tag);
		
	}
	
	/**
	 * Add log info for each testcase
	 * @param message
	 */
	
	public static void info(String message) {
		test.log(LogStatus.INFO, "<b style='margin:0;color:black;'>"+message+"</b>");
		
	}
	
	/**
	 * Add log info for each testcase
	 * @param message
	 */
	
	public static void info(String message, String path) {
		test.log(LogStatus.INFO, "<b style='margin:0;color:black;'>"+message+"</b>");
		test.log(LogStatus.INFO, test.addScreenCapture(path));
	}
	
	/**
	 * Add log info along with steps in a table
	 * @param fc
	 * @param message
	 */
	
	public static void table(String message) {
		String [] rows = message.replaceAll("Test Result Reference : ", "").split(",");

		String stepDetails = "Test Result Reference";
		
		for (String row : rows) {
			row = row.replaceAll("\\[", "").replaceAll("\\]", "").replace("Debug referenace => ", "");
			String [] items = row.split("-");
			stepDetails =  stepDetails + "<TR style='border:2px solid black'><TD style='width:50%'><B>"+ items[0] +"</B></TD><TD><SPAN style='font-weight:bold'>" + items[1] + "</SPAN></TD></TR>";
		}
		
		test.log(LogStatus.INFO, "Test Result Reference", stepDetails);
	}
	
	/**
	 * Add log info along with steps 
	 * @param fc
	 * @param message
	 */
	
	public static void step(String fc,String message) {
		String StepName  = "<TABLE style='border=2px solid black; table-layout: fixed'>";
		StepName = StepName + "<TR style='border:2px solid black'><TD style='border:2px solid black; width: 200px'><B>Step Name</B></TD><TD><SPAN style='font-weight:bold'>" + fc + "</SPAN></TD></TR>";          
		String stepDetails =  "<TR style='border:2px solid black'><TD style='border:2px solid black'><B>Step Details</B></TD><TD><SPAN style='font-weight:bold'>" + message + "</SPAN></TD></TR>";

		test.log(LogStatus.INFO, StepName, stepDetails);
	}
	
	/**
	 * Add Log Pass  for testcase
	 * @param message
	 */
	public static void success(String message) {
		test.log(LogStatus.PASS, "<b style='margin:0;color:blue;'>"+message+"</b>");
	}
	
	/**
	 * Function to add error in extend report log
	 * @param message
	 * @param path
	 */
	
	public static void error(String message,String path) {
		String errorMessage = "<TABLE style='border=2px solid black; table-layout: fixed'>";
		errorMessage = errorMessage + "<TR style='border:2px solid black'><TD style='border:2px solid black; width: 50px'><B>Message</B></TD><TD><SPAN style='font-weight:bold'>" + message + "</SPAN></TD></TR>";          
		errorMessage = errorMessage + "<TR style='border:2px solid black'><TD style='border:2px solid black'><B>Test Status</B></TD><TD style='background-color:#ff0000'><SPAN style='font-weight:bold'>FAILED</SPAN></TD></TR></TABLE>";
	
		//test.log(LogStatus.INFO, errorMessage);
		//test.log(LogStatus.INFO, test.addScreenCapture(path));
		test.log(LogStatus.FAIL, errorMessage);
		test.log(LogStatus.FAIL, test.addScreenCapture(path));
	}
	
	/**
	 * Function to add error in testcase with screenshoot
	 * @param message
	 */
	
	public static void error(String message) {
		//test.log(LogStatus.INFO, "<b style='margin:0;color:red;'>"+message+"</b>");
		test.log(LogStatus.FAIL, "<b style='margin:0;color:red;'>"+message+"</b>");
	}
	
	/**
	 * Function to eliminate particular case
	 * @param message
	 */
	
	public static void skip(String message) {
		test.log(LogStatus.SKIP, "<b style='margin:0;color:yellow;'>"+message+"</b>");
	}
	
	/**
	 * Function to highlight the verification message
	 * @param message
	 */
	public static void verificationMessage(String message) {
		test.log(LogStatus.INFO, "<b><p align='center' style='border:2px solid black;background-color:#D3D3D3;font-size=80px'>" + message + "</p></b>");		
	}
	
	/**
	 * Function to add screenshot of current screen to report
	 * @param path
	 */
	public static void takeScreenshot(String message, String path) {
		test.log(LogStatus.INFO, "<b style='margin:0;color:black;'>"+message+"</b>");
		test.log(LogStatus.INFO, test.addScreenCapture(path));
	}
	
	/**
	 * Function to finish the extend report for tht particular case
	 */
	
	public static void end() {
		reports.endTest(test);
		reports.flush();
	}
	
}