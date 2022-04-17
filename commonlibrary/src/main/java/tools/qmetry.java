package tools;

import Database.MongoDB;
import web.Http;
import web.Json;

public class qmetry {

	public static String qBaseUrl;
	public static String token;

	public static void main(String[] args) throws Exception {
		
		initializeDatabase("mongodb+srv://staging-admin:VpbUDlaBkv4vTzJ8@tsm-staging-mlxar.mongodb.net/db_pando_staging?retryWrites=true", "db_pando_testing");
		initializeQmetry("https://qtmcloud.qmetry.com", "307e3d9a5231bd4a6ac16e3ecae0117b50414479174a065204627b9ea2e487aaa7d95e9055bbe91a7cac1535ea748554c51a93cdf108658034dd94190bdd6870e84559ca48aa545772cfbbfba70883de");

		updateTestcase( "PAN-TR-32",  "PAN-TC-16621",  "true",  "20210622163809",  "https://indent.pandodev.in",  "indent", "FrontPage.Pando.PandoSuites.Indents.DropOffCharges.PAN-TC-6380", "description", "no");
	
		//https://qtmcloud.qmetry.com/rest/api/latest/testcycles/ZmlH89wT19NL/testcase-executions/7317
		
		
		//https://qtmcloud.qmetry.com/rest/api/ui/testcases/K3Vc8v7TgN3Y/versions/1 GET
		//{"data":{"id":"K3Vc8v7TgN3Y","key":"PAN-TC-6159","version":{"versionNo":1,"isLatestVersion":true},"summary":"Re-indent  based on point","priority":{"id":35887,"name":"Medium","iconUrl":"https://s3.amazonaws.com/qtm4j-cloud-prod-4/staticResources/priorityIcons/Medium.svg","isArchive":false},"status":{"id":61816,"name":"Done","color":"#14892c","isArchive":false},"assignee":"5c39acb971c9f02df7750311","isAutomated":false,"isParameterized":false,"projectId":10000}}
		
		//https://qtmcloud.qmetry.com/rest/api/ui/testcases/K3Vc8v7TgN3Y/versions/1 PUT
		//{"assignee":"5e9979cd63acd40c3fa4027f"}
		/*
		try (Stream<Path> walk = Files.walk(Paths.get(
				"C:\\Users\\brantan.sp\\Repository\\automation\\workflow\\FitNesseRoot\\FrontPage\\Pando\\PandoSuites"))) {
			List<Path> result = walk.filter(Files::isRegularFile)
					.filter(x -> x.getFileName().toString().startsWith("PAN-TC")).collect(Collectors.toList());
			
			result.forEach(file -> {
				try {
					updateTestCaseAsignee("PAN-TR-12",file.getFileName().toString().substring(0,
							file.getFileName().toString().lastIndexOf(".wiki")),"5c39acb971c9f02df7750311","5e9979cd63acd40c3fa4027f");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	   */
	}
	
	/** setTestExecutionDetails methods needs to be updated in this case
	public static void updateTestCaseAsignee(String testCycle, String testcase, String currentAssignee, String assignTo) throws Exception {

		Http http = new Http();
		String status = "";

		String execution_id = MongoDB.getTestExecutionDetails(testCycle, testcase);
		String cid = MongoDB.getTestCycleId(testCycle);
		String edid = null;
		String eid = null;
		String tcid = null;
		
		if (execution_id == null || Json.getArrayValueFromJSON(execution_id, "data").equals("[]")) {

			if (cid == null) {
				// Getting Cycle ID
				Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcycles/" + testCycle + "?fields=key");
				http.addHeader("apiKey", token);
				String cycle_id = http.getResponse("Application/Json", "get", "");
				cid = (String) Json.getValueFromJSON(cycle_id, "data.id");
				MongoDB.setTestCycleId(testCycle, cid);
			}

			// Getting Execution id
			Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcycles/" + cid
					+ "/testcases/search/?fields=key&maxResults=1&sort=key%3Aasc&startAt=0");
			http.addHeader("apiKey", token);
			execution_id = http.getResponse("Application/Json", "post", "{\"filter\":{\"key\":\"" + testcase + "\"}}");
			MongoDB.setTestExecutionDetails(testCycle, testcase, execution_id, "", "", "");
		}
		edid = Json.getArrayValueFromJSON(execution_id, "data");
		eid = Json.getValueFromJSONByType(edid, "[0].testCaseExecutionId", "int");
		tcid = Json.getValueFromJSONByType(edid, "[0].id", "java.lang.String");
		
		System.out.println(tcid);
		
		Http.setBaseUrl(qBaseUrl + "/rest/api/ui/testcases/"+tcid+"/versions/1");
		http.addHeader("apiKey", token);
		http.addHeader("authorization","jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ZTk5NzljZDYzYWNkNDBjM2ZhNDAyN2YiLCJpc3MiOiIxMDU1OTFjZS1iZjk4LTNiYzUtYjZiMC1iMDhlMDRlNWQzODUiLCJjb250ZXh0Ijp7ImxpY2Vuc2UiOnsiYWN0aXZlIjp0cnVlfSwiamlyYSI6eyJwcm9qZWN0Ijp7ImtleSI6IlBBTiIsImlkIjoiMTAwMDAifX19LCJleHAiOjE2MTQwNzMzMDIsImlhdCI6MTYxNDA3MjQwMn0.KYu5uyxnfIHMMWm4qXsdt9poi6-M6Nt23-LE_izbS_Y");
		String test_execution_details = http.getResponse("Application/Json", "get", "");
		if(Json.getValueFromJSONByType(test_execution_details, "data.assignee","java.lang.String").equals(currentAssignee)) {
			Http.setBaseUrl(qBaseUrl + "/rest/api/ui/testcases/"+tcid+"/versions/1");
			http.addHeader("apiKey", token);
			http.addHeader("authorization","jwt eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ZTk5NzljZDYzYWNkNDBjM2ZhNDAyN2YiLCJpc3MiOiIxMDU1OTFjZS1iZjk4LTNiYzUtYjZiMC1iMDhlMDRlNWQzODUiLCJjb250ZXh0Ijp7ImxpY2Vuc2UiOnsiYWN0aXZlIjp0cnVlfSwiamlyYSI6eyJwcm9qZWN0Ijp7ImtleSI6IlBBTiIsImlkIjoiMTAwMDAifX19LCJleHAiOjE2MTQwNzMzMDIsImlhdCI6MTYxNDA3MjQwMn0.KYu5uyxnfIHMMWm4qXsdt9poi6-M6Nt23-LE_izbS_Y");
			String test_updation_result = http.getResponse("Application/Json", "put", "{\"assignee\":\""+assignTo+"\"}");
			System.out.println("result: "+test_updation_result);
		}

	}**/
	
	@SuppressWarnings("unused")
	public static String updateTestcase(String testCycle, String testcase, String result, String reference, String testClient, String testTag, String testCasePath, String testDescription, String logToELK)
			throws Exception {
		//
		Http http = new Http();
		String status = "";

		String execution_id = MongoDB.getTestExecutionDetails(testCycle, testcase);
		String cid = MongoDB.getTestCycleId(testCycle);
		String edid = null;
		String eid = null;
		String tcid = null;
		
		if (execution_id == null || Json.getArrayValueFromJSON(execution_id, "data").equals("[]") || execution_id.contains("error")) {

			if (cid == null) {
				// Getting Cycle ID
				Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcycles/" + testCycle + "?fields=key");
				http.addHeader("apiKey", token);
				String cycle_id = http.getResponse("Application/Json", "get", "");
				cid = (String) Json.getValueFromJSON(cycle_id, "data.id");
				MongoDB.setTestCycleId(testCycle, cid);
			}

			// Getting Execution id
			Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcycles/" + cid
					+ "/testcases/search/?fields=key&maxResults=1&sort=key%3Aasc&startAt=0");
			http.addHeader("apiKey", token);
			execution_id = http.getResponse("Application/Json", "post", "{\"filter\":{\"key\":\"" + testcase + "\"}}");
			MongoDB.setTestExecutionDetails(testCycle, testcase, execution_id, testClient, testCasePath, testDescription);
		}
		edid = Json.getArrayValueFromJSON(execution_id, "data");
		eid = Json.getValueFromJSONByType(edid, "[0].testCaseExecutionId", "int");
		tcid = Json.getValueFromJSONByType(edid, "[0].id", "java.lang.String");

		if (false) {
			// Update Comments
			Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcases/" + tcid + "/versions/1/comments");
			http.addHeader("apiKey", token);
			http.getResponse("Application/Json", "post", "{\"comments\":[\"" + reference + "\"]}");
		}
		
		String tresult = "";

		if (result.equalsIgnoreCase("true")) {
			tresult = "pass";
			status = "87353"; // Auto-pass
			// status = "26338"; //Pass
			Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcycles/" + cid + "/testcase-executions/" + eid + "");
			http.addHeader("apiKey", token);
			MongoDB.setTestCaseRerunCountToZero(testCycle, testcase, testTag, "pass", reference + " - passed");
			http.getResponse("Application/Json", "put", "{\"executionResultId\":" + status + "}");
			//Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcases/" + tcid + "/versions/1/comments");
			//http.addHeader("apiKey", token);
			//http.getResponse("Application/Json", "post", "{\"comments\":[\"" + comment + "\"]}");
		} else {
			tresult = "fail";
			status = "87354"; // Auto-fail
			// status = "26335"; //fail

			if (MongoDB.getTestCaseRerunCount(testCycle, testcase) >= 1) {
				Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcycles/" + cid + "/testcase-executions/" + eid + "");
				http.addHeader("apiKey", token);
				MongoDB.setTestCaseRerunCountToZero(testCycle, testcase, testTag, "fail", reference+ " - failed");
				http.getResponse("Application/Json", "put", "{\"executionResultId\":" + status + "}");
				//Http.setBaseUrl(qBaseUrl + "/rest/api/latest/testcases/" + tcid + "/versions/1/comments");
				//http.addHeader("apiKey", token);
				//http.getResponse("Application/Json", "post", "{\"comments\":[\"" + comment + "\"]}");
			} else {
				MongoDB.setTestCaseRerunCount(testCycle, testcase, testTag, "fail" ,reference+ " - failed");
			}
		}
		
		if (logToELK.equalsIgnoreCase("yes")) {
			ELKReport.logTestCaseEndStatus(testCycle, testcase, tresult, reference, testClient, testTag, testCasePath,
					testDescription);
			ELKReport.logTestCaseInfo(testCycle, testcase, tresult, reference, testClient, testTag, testCasePath,
					testDescription);
		}
		return tcid;
	}
	
	public static void initializeDatabase(String mongodbURI, String mongoDatabase) {
		MongoDB.initialize(mongodbURI, mongoDatabase);
	}
	
	public static void initializeQmetry(String qBaseUrl, String token) {
		qmetry.token = token;
		qmetry.qBaseUrl = qBaseUrl;
	}
}
