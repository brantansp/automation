package tools;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import Database.MongoDB;

public class GenerateReport {

	public static void initialize(String URL, String collection) {
		MongoDB.initialize(URL, collection);
	}

	public static void generateReports(String testCycle, String clientFilter) throws MalformedURLException {
		//
		String [] clientFilters = null ;
		
		if (clientFilter == null || clientFilter.isEmpty()) {
			clientFilters = new String [] {"all"};
		} else {
			clientFilters =clientFilter.split(",");
		}

		MongoDB.getAllTestCasesForTestCycle(testCycle, clientFilters).forEach((key, value) -> {
			
			try {
				
				HashMap <String, String> result = value;
				
				String tags = result.get("test_client").replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
				//String tag = tags.split("\\.")[0]; if domain name to be filter tag
				String tag = result.get("test_case_tag") == null ? tags.split("\\.")[0] : result.get("test_case_tag");
				
                Extend.testcase(key, tag, result.get("test_case_description"));

				Extend.info("Test Cycle ID : " + result.get("test_cycle"));
				Extend.info("Test Case ID : " + key);
				Extend.info("Test Case Description : " + result.get("test_case_description"));
				Extend.info("Test Case Client : " + result.get("test_client"));
				Extend.info("Test Case Tag : " + result.get("test_case_tag"));
				Extend.info("Test Case Path : " + result.get("test_case_path"));
				//Extend.table("Test Result Reference : " + result.get("referenceId"));

				if (result.get("testCaseResult") == null) {
					Extend.skip("Test Case Skipped");
				} else if (result.get("testCaseResult").equalsIgnoreCase("pass")) {
					Extend.success("Test Case Passed");
				} else {
					Extend.error("Test Case Failed");
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} finally {
				Extend.end();
			}
		});
	}
	
	public static void main(String[] args) throws MalformedURLException {
		initialize("mongodb+srv://staging-admin:VpbUDlaBkv4vTzJ8@tsm-staging-mlxar.mongodb.net/db_pando_staging?retryWrites=true",
				"db_pando_testing");
		generateReports("PAN-TR-36", "All");
		
		//String domain = "https://marico-auto.pandodev.in";
		//System.out.println(domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""));
	}
}