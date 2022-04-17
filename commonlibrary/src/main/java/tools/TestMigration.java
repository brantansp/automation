package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import Database.MongoDB;
import web.Http;
import web.Json;

/**
 * API interface to let fitnesse to interact with testlink
 * 
 * @author Administrator
 *
 */

public class TestMigration {

	static String token = "1a7cb954f5239ab0ccebf0ebf950f5d4e759de3c246932f9d3f658e9737730a4d26668d7ec755c0d8adfc0f7824c03bbd351041444c89e80f78e4644fd418d837a83270ed568ce2b936271ef24eaa6d4";
	public final static String mongoDatabase = "db_pando_testing";
	public final static String mongodbURI = "mongodb+srv://staging-admin:VpbUDlaBkv4vTzJ8@tsm-staging-mlxar.mongodb.net/"
			+ mongoDatabase + "?retryWrites=true";

	public static void main(String[] args) throws Exception {
		//

		/*
		 * Http http = new Http(); Http.setBaseUrl(
		 * "https://qtmcloud.qmetry.com/rest/api/latest/testcycles/z8gH9GYSdWjv/testcases/testCycleTestCaseMapId/executions/?maxResults=1000&startAt=0"
		 * ); http.addHeader("apiKey", token); http.addHeader("Content-Type",
		 * "application/json"); String execution_id =
		 * http.getResponse("Application/Json", "get", "");
		 * System.out.println(execution_id);
		 */
		//findingNotExecutedTestCase();
		findingTestCaseWithExpectedVariableUsage();
		//findingTestCaseWithExpectedVariableUsage();
		//findingNotExecutedTestCase();
	}
	
	public static String getTestCaseDescription(String filepath) throws IOException {
		String description ="";
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("Help:")) {
					description = line.substring(6, line.length());
					break;
				}
			}
		}
		return description;
	}
	
	public static void findingTestCases () {
		try (Stream<Path> walk = Files.walk(Paths.get(
				"C:\\Users\\brantan.sp\\Repository\\automation\\workflow\\FitNesseRoot\\FrontPage\\Pando\\PandoSuites\\"))){
			List<Path> result = walk.filter(Files::isRegularFile)
					.filter(x -> x.getFileName().toString().endsWith(".wiki")).collect(Collectors.toList());
			result.forEach(file -> {
				Scanner scanner = null;
				try {
					scanner = new Scanner(file);
					while(scanner.hasNextLine()) {
						String line = scanner.nextLine();
						
						if (line.contains("isElementPresent") && line.contains("false")) {
							System.out.println(file);
						}
						
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			
		}
	}
	
	public static void findingTestCasesWithRoleAPI () {
		try (Stream<Path> walk = Files.walk(Paths.get(
				"C:\\Users\\brantan.sp\\Repository\\automation\\workflow\\FitNesseRoot\\FrontPage\\Pando\\PandoSuites\\Optima\\DeliveryDoNotCombineOrSplit\\DeliveryDoNotCombineAtClientLevel"))) {
			List<Path> result = walk.filter(Files::isRegularFile)
					.filter(x -> x.getFileName().toString().endsWith(".wiki")).collect(Collectors.toList());
			
			result.forEach(file -> {
				Scanner scanner = null;
				try {
					scanner = new Scanner(file);
					String variable = null;
					while(scanner.hasNextLine()) {
						String line = scanner.nextLine();
						
						line = line.replaceAll("\\s+", "");
						
						if(line.startsWith("!|")) {
							System.out.println();
							String [] arr = line.split("\\|");
							variable = arr[2];
							System.out.println(variable+" "+ variable.toLowerCase() +" = " + " new "+variable+"();");

						}
						
						if(line.startsWith("|")) {
							String [] arr = line.split("\\|");
							String inVariable = null;
							String method = null;
							List <String> args = new ArrayList<String>(); 
							for (String s : arr) {
								if(s.startsWith("$")) {
									inVariable = s.substring(1, s.length()-1);
								} else if (s.endsWith(";")) {
									method = s.substring(0, s.length()-1);
								} else {
									if(StringUtils.isAlpha(s) && StringUtils.isNotEmpty(s)) {
										args.add("\""+s+"\"");
									}
								}
							}
							String arg = args.toString().replace("[", "").replace("]", "");
							System.out.println("String "+ inVariable+" = "+variable.toLowerCase()+"." + method+"("+arg+");");
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
				try (Stream<Path> walk = Files.walk(Paths.get(
				"C:\\Users\\brantan.sp\\Repository\\automation\\workflow\\FitNesseRoot\\FrontPage\\Pando\\"))){
			AtomicInteger filescount= new AtomicInteger(0);
			AtomicInteger linescount= new AtomicInteger(0);
			List<Path> result = walk.filter(Files::isRegularFile)
					.filter(x -> x.getFileName().toString().endsWith(".wiki")).collect(Collectors.toList());
			
			result.forEach(file -> {
				filescount.getAndIncrement();
				Scanner scanner = null;
				try {
					scanner = new Scanner(file);
					while(scanner.hasNextLine()) {
						String line = scanner.nextLine();
						if(line == "") {
							System.out.println(line);
							linescount.getAndIncrement();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			System.out.println("Total No.of files : "+filescount+" Total No.of lines : "+linescount);
		} catch (Exception e) {
			
		}
	}

	public static void findingTestCaseWithExpectedVariableUsage() {
        
		try (Stream<Path> walk = Files.walk(Paths.get(
				"C:\\Users\\brantan.sp\\Repository\\automation\\workflow\\FitNesseRoot\\FrontPage"))) {
			List<Path> result = walk.filter(Files::isRegularFile)
					.filter(x -> x.getFileName().toString().endsWith(".wiki")).collect(Collectors.toList());
			// MongoDB.initialize(mongodbURI, mongoDatabase);
			Set <String> s = new HashSet<String>();
			result.forEach(file -> {
				Scanner scanner = null;
				try {
					scanner = new Scanner(file);
					
					

					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						
						//Printing all xpaths
						if(line.contains("|//")) {
							String xpath = line.substring(line.indexOf("//"));
							xpath = xpath.substring(0,xpath.indexOf("|"));
							System.out.println(xpath);
							s.add(xpath);
						}

						// Identifying the detention cases
						/**
						if (line.contains("mdm/detentions")) {
							System.out.println(file.toString().replace("\\", "."));
						}
                        */
						
						/**
						// Identifying the release_date without the rdatev variable
						if (line.contains("\"release_date\":")) {
							if (!line.contains("\"release_date\":\"$rdate\"")) {
								String search = line.substring(line.lastIndexOf("\"release_date\":"));
								System.out.println(search.substring(16, search.indexOf("\"}")));
								System.out.println(file.toString().replace("\\", "."));
								Scanner s = new Scanner(file);
								boolean flag = true;
								while (s.hasNextLine()) {
									String l = s.nextLine();
									if (l.replaceAll("\\s+", "").contains("$rdate=|utcConversion;")) {
										flag = false;
										break;
									}

								}
								if (flag) {
									System.out.println(file.toString().replace("\\", "."));
								}
							}

						}
						*/
						
						/**
						// Identifying the cases without rdate utc conversion
						if (line.contains("\"release_date\"")) {
							Scanner s = new Scanner(file);
							boolean flag = true;
							while (s.hasNextLine()) {
								String l = s.nextLine();
								if (l.replaceAll("\\s+", "").contains("$rdate=|utcConversion;")) {
									flag = false;
									break;
								}

							}

							if (flag) {
								System.out.println(file.toString().replace("\\", "."));
							}
							s.close();
						}
						*/
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					scanner.close();
				}

			});
			System.out.println("\n\n\n======================================================================\n\n\n");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	public static void findingNotExecutedTestCase() {
		try (Stream<Path> walk = Files.walk(Paths.get(
				"C:\\Users\\brantan.sp\\Repository\\automation\\workflow\\FitNesseRoot\\FrontPage\\Pando\\PandoSuites"))) {
			List<Path> result = walk.filter(Files::isRegularFile)
					.filter(x -> x.getFileName().toString().startsWith("PAN-TC")).collect(Collectors.toList());
			MongoDB.initialize(mongodbURI, mongoDatabase);
			result.forEach(file -> {
				if (MongoDB.getTestExecutionDetails("PAN-TR-30", file.getFileName().toString().substring(0,
						file.getFileName().toString().lastIndexOf(".wiki"))) == null) {
					System.out.println("."+file.toString().substring(50));
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void renameFitnesseTestCaseName() throws Exception {

		String filePath = "/Users/dinesh/Documents/csv/testlink";
		Class.forName("org.relique.jdbc.csv.CsvDriver");
		String csvurl = "jdbc:relique:csv:" + filePath + "?" + "separator=," + "&" + "fileExtension=.csv";
		Properties props = new Properties();
		props.put("suppressHeaders", "false");
		Connection conn = DriverManager.getConnection(csvurl, props);

		String filePath1 = "/Users/dinesh/Documents/csv/qmetry";
		Class.forName("org.relique.jdbc.csv.CsvDriver");
		String csvurl1 = "jdbc:relique:csv:" + filePath1 + "?" + "separator=," + "&" + "fileExtension=.csv";
		Properties props1 = new Properties();
		props1.put("suppressHeaders", "false");
		Connection conn1 = DriverManager.getConnection(csvurl1, props1);

		String tmquery = "select * from test1";
		Statement tmstmt = conn.createStatement();
		ResultSet tmresults = tmstmt.executeQuery(tmquery);

		Http http = new Http();

		int i = 1;

		while (tmresults.next()) {

			String[] split = tmresults.getString(1).split(":");
			String tl_id = split[0];

			String tmquery1 = "select * from qm where TestLinkID='" + tl_id + "'";
			Statement tmstmt1 = conn1.createStatement();
			ResultSet tmresults1 = tmstmt1.executeQuery(tmquery1);

			while (tmresults1.next()) {

				String qm = tmresults1.getString(1);

				// Geting Testcase id
				Http.setBaseUrl(
						"https://qtmcloud.qmetry.com/rest/api/latest/testcases/search/?fields=fixVersions&maxResults=100&sort=key&startAt=0");
				http.addHeader("apiKey", token);
				http.addHeader("Content-Type", "application/json");
				String execution_id = http.getResponse("Application/Json", "post",
						"{\"filter\":{\"projectId\":\"10000\",\"key\":\"" + qm + "\"}}");
				String tc_id = Json.getArrayValueFromJSON(execution_id, "data");
				String eid = Json.getValueFromJSONByType(tc_id, "[0].id", "java.lang.String");

				// Linking testcases
				Http.setBaseUrl("https://qtmcloud.qmetry.com/rest/api/latest/testcycles/z8gH9GYSdWjv/testcases");
				http.addHeader("apiKey", token);
				http.addHeader("Content-Type", "application/json");
				http.getResponse("Application/Json", "post",
						"{\"testCases\":[{\"id\":\"" + eid + "\",\"versionNo\":1}]}");

				System.out.println(i);
				i++;

				Http.wait(10);

				File root = new File("/Users/dinesh/Documents/GitHub/automation/workflow/FitNesseRoot/FrontPage/pando");
				String fileName = tl_id + ".wiki";

				boolean recursive = true;
				Collection<File> files = FileUtils.listFiles(root, null, recursive);

				String frname = null;
				String tlink = null;

				for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {

					File file = (File) iterator.next();

					if (file.getName().equals(fileName)) {

						frname = file.getAbsolutePath();
						System.out.println(frname);

						tlink = frname.replace(fileName, qm + ".wiki");
						System.out.println(tlink);

						File oldfile = new File(frname);
						File newfile = new File(tlink);

						if (oldfile.renameTo(newfile)) {
							System.out.println("Rename succesful");
						} else {
							System.out.println("Rename failed");
						}
					}
				}
			}
		}
	}
}