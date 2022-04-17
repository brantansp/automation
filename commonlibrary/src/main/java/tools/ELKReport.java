package tools;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import common.DateUtil;
import io.restassured.response.Response;

public class ELKReport {
	
	public static void logTestCaseInfo (String testCycle, String testcase, String result, String reference, String testClient, String testTag, String testCasePath, String testDescription) {
		
		Map <String, String> body = new HashMap<String, String>();
		body.put("test_cycle", testCycle);
		body.put("test_case", testcase);
		body.put("test_result", result);
		body.put("test_reference", reference);
		body.put("test_client", testClient);
		body.put("test_tag", testTag);
		body.put("test_case_path", testCasePath);
		body.put("test_case_description", testDescription);
		body.put("executionTime", DateUtil.getCurrentDateTimeStamp("iso"));
		
		Response response = given().auth().basic("elastic", "Ny)64^es").header("Content-Type","application/json")
		.body(body)
		.post("http://20.219.3.98:9200/regressionlog/log");
		
		System.out.println(response.asString());
	}
	
	public static void logTestCaseStartStatus (String testcase, String result) {
		
		Map <String, String> body = new HashMap<String, String>();
		body.put("test_result", result);
		body.put("executionTime", DateUtil.getCurrentDateTimeStamp("iso"));
		
		Response response = given().auth().basic("elastic", "Ny)64^es").header("Content-Type","application/json")
		.body(body)
		.post("http://20.219.3.98:9200/regressionstatus/status/"+testcase);
		
		System.out.println(response.asString());
	}
	
	public static void logTestCaseEndStatus (String testCycle, String testcase, String result, String reference, String testClient, String testTag, String testCasePath, String testDescription) {
		
		Map <String, String> body = new HashMap<String, String>();
		body.put("test_cycle", testCycle);
		body.put("test_result", result);
		body.put("test_reference", reference);
		body.put("test_client", testClient);
		body.put("test_tag", testTag);
		body.put("test_case_path", testCasePath);
		body.put("test_case_description", testDescription);
		body.put("executionTime", DateUtil.getCurrentDateTimeStamp("iso"));
		
		Response response = given().auth().basic("elastic", "Ny)64^es").header("Content-Type","application/json")
		.body(body)
		.post("http://20.219.3.98:9200/regressionstatus/status/"+testcase);
		
		System.out.println(response.asString());
	}
	
	public static void main(String[] args) {
		//logTestCaseInfo( "PAN-TR-32",  "PAN-TC-0000",  "pass",  "20210622163809",  "https://indent.pandodev.in",  "indent", "FrontPage.Pando.PandoSuites.Indents.DropOffCharges.PAN-TC-6380","Ensure that Secondary Indent are created from optima in QA sandbox");
		//logTestCaseStatus ("PAN-TC-0000",  "started");
		
		//System.out.println(DateUtil.getCurrentDateTimeStamp("iso"));
	}

}
