package Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import common.DateUtil;
import tools.Extend;
import web.Json;

/**
 * Exposes methods for MongoDB operation.
 * 
 * @author Administrator
 */
public class MongoDB {

	private static MongoClient mongoClient = null;
	private static MongoClientURI mongoClientURI = null;
	private static DB db = null;
	private static MongoDatabase mongodb = null;

	// Establishing connections
	public static void initialize(String connection, String database) {

		mongoClientURI = new MongoClientURI(connection);
		mongoClient = new MongoClient(mongoClientURI);
		db = mongoClient.getDB(database);
		mongodb = mongoClient.getDatabase(database);

	}

	/**
	 * Wait for the specified seconds
	 * 
	 * @param interget value
	 * @throws InterruptedException
	 */
	public static void wait(int i) {
		System.out.println("Waiting for time: " + i + " seconds ");
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	// Getting all DB names
	@SuppressWarnings("deprecation")
	public static String transporterOTP(String email) {

		DBObject result = null;
		DBCollection table = db.getCollection("transporter_user");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("email_id", email);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		cursor.close();

		Extend.info("Password for Email " + email + " is => " + result.get("otp").toString());
		return result.get("otp").toString();
	}

	public static String roleId(String roletype, String domain) {

		DBObject result = null;
		domain = domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
		DBCollection table = db.getCollection("clients");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("domain_url", domain);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		cursor.close();

		String cid = result.get("_id").toString();

		DBObject result1 = null;
		DBCollection table1 = db.getCollection("roles");
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("type", roletype);
		searchQuery1.put("client_id", new ObjectId(cid));

		DBCursor cursor1 = table1.find(searchQuery1);

		while (cursor1.hasNext()) {
			result1 = cursor1.next();
		}
		cursor1.close();

		return result1.get("_id").toString();

	}
	
	/**
	 * To update the values in the collections
	 * Usage: | updateValuesIntoCollection | collectionName | searchKey| searchValue| updateFieldName | updateValue |
	 * @param domain
	 * @param updateFieldName
	 * @param updateValue
	 */
	public static void updateValuesIntoCollection(String collectionName, String searchKey, String searchValue, String updateFieldName,
			Object updateValue) {

		try {
			DBObject collectionObject = null;
			DBCollection collection = db.getCollection(collectionName);
			BasicDBObject searchQuery1 = new BasicDBObject();
			searchQuery1.put(searchKey, searchValue);

			DBCursor collectionCursor = collection.find(searchQuery1);

			while (collectionCursor.hasNext()) {
				collectionObject = collectionCursor.next();
			}
			collectionCursor.close();

			MongoCollection<Document> collections = mongodb.getCollection(collectionName);

			if (updateValue.equals("true")) {
				collections.updateOne(Filters.eq(new ObjectId(collectionObject.get("_id").toString())),
						Updates.set(updateFieldName, true));
			} else if (updateValue.equals("false")) {
				collections.updateOne(Filters.eq(new ObjectId(collectionObject.get("_id").toString())),
						Updates.set(updateFieldName, false));
			} else {
				collections.updateOne(Filters.eq(new ObjectId(collectionObject.get("_id").toString())),
						Updates.set(updateFieldName, updateValue));
			}
		} catch (Exception e) {
			
			Extend.info("Exception when updateValuesIntoCollection : "+e.getMessage());

		}

	}
	
	/**
	 * To update the client configurations
	 * Usage: | updateClientConfiguration | domain | updateFieldName | updateValue |
	 * @param domain
	 * @param updateFieldName
	 * @param updateValue
	 */
	public static void updateClientConfiguration(String domain, String updateFieldName,
			Object updateValue) {
		DBObject clientObject = null;
		domain = domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
		DBCollection table = db.getCollection("clients");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("domain_url", domain);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			clientObject = clientCursor.next();
		}
		clientCursor.close();

		String cid = clientObject.get("_id").toString();

		DBObject clientConfigurationObject = null;
		DBCollection clientConfigurationCollection = db.getCollection("client_configurations");
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("client_id", new ObjectId(cid));

		DBCursor clientConfigCursor = clientConfigurationCollection.find(searchQuery1);

		while (clientConfigCursor.hasNext()) {
			clientConfigurationObject = clientConfigCursor.next();
		}
		clientConfigCursor.close();

		MongoCollection<Document> collection = mongodb.getCollection("client_configurations");

		if (updateValue.equals("true")) {
			collection.updateOne(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())), Updates
					.set( updateFieldName, true));
		} else if (updateValue.equals("false")) {
			collection.updateOne(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())), Updates
					.set( updateFieldName, false));
		} else if (updateValue.equals("unset"))  {
			collection.updateOne(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())), Updates
					.unset(updateFieldName));
		} else {
			collection.updateOne(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())), Updates
					.set( updateFieldName, updateValue));
		}
	}
	
	/**
	 * To get the value of a field from the db collection
	 * Usage: |getValuesFromCollection | collectionName | filterKey| filterValue| getValueByFieldName|
	 * @param collectionName
	 * @param filterKey filterValue
	 * @param getValueByFieldName
	 * @return value
	 */
	public static Object getValuesFromCollection(String collectionName, String filterKey, String filterValue, String getValueByFieldName) {
		DBObject document = null;
		DBCollection table = db.getCollection(collectionName);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(filterKey , filterValue);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			document = clientCursor.next();
		}
		clientCursor.close();
		
		@SuppressWarnings("deprecation")
		String json = com.mongodb.util.JSON.serialize(document);

		return Json.getValueFromJSON(json, getValueByFieldName);
	}

	/**
	 * To get the value of a field from the client configurations
	 * Usage: |getValueFromClientConfiguration | domain | getValueOfFieldName|
	 * @param domain
	 * @param getValueOfFieldName
	 * @return value
	 */
	public static Object getValuesFromClientConfiguration(String domain, String getValueOfFieldName) {
		DBObject clientObject = null;
		domain = domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
		DBCollection table = db.getCollection("clients");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("domain_url", domain);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			clientObject = clientCursor.next();
		}
		clientCursor.close();

		String cid = clientObject.get("_id").toString();

		DBObject clientConfigurationObject = null;
		DBCollection clientConfigurationCollection = db.getCollection("client_configurations");
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("client_id", new ObjectId(cid));

		DBCursor clientConfigCursor = clientConfigurationCollection.find(searchQuery1);

		while (clientConfigCursor.hasNext()) {
			clientConfigurationObject = clientConfigCursor.next();
		}
		clientConfigCursor.close();

		MongoCollection<Document> collection = mongodb.getCollection("client_configurations");

		FindIterable<Document> config = collection
				.find(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())));

		Document document = (Document) config.first();

		@SuppressWarnings("deprecation")
		String json = com.mongodb.util.JSON.serialize(document);

		return Json.getValueFromJSON(json, getValueOfFieldName);
	}
	
	/**
	 * To update the freight bill client configuration 
	 * Usage: | updateFreightBillClientConfiguration | domain | categoryName | updateFieldName | updateValue |
	 * 
	 * @param domain
	 * @param categoryName
	 * @param updateFieldName
	 * @param updateValue
	 */
	public static void updateFreightBillClientConfiguration(String domain, String categoryName, String updateFieldName,
			Object updateValue) {
		DBObject clientObject = null;
		domain = domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
		DBCollection table = db.getCollection("clients");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("domain_url", domain);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			clientObject = clientCursor.next();
		}
		clientCursor.close();

		String cid = clientObject.get("_id").toString();

		DBObject clientConfigurationObject = null;
		DBCollection clientConfigurationCollection = db.getCollection("client_configurations");
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("client_id", new ObjectId(cid));

		DBCursor clientConfigCursor = clientConfigurationCollection.find(searchQuery1);

		while (clientConfigCursor.hasNext()) {
			clientConfigurationObject = clientConfigCursor.next();
		}
		clientConfigCursor.close();

		MongoCollection<Document> collection = mongodb.getCollection("client_configurations");

		FindIterable<Document> config = collection
				.find(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())));

		@SuppressWarnings("unchecked")
		List<Document> categories = (List<Document>) ((Document) config.first().get("freight_bill")).get("categories");

		int categoryPosition = 0;

		for (Document category : categories) {
			if (category.get("name").toString().equalsIgnoreCase(categoryName)) {
				break;
			}
			categoryPosition++;
		}

		collection.updateOne(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())), Updates
				.set("freight_bill.categories." + categoryPosition + ".date_range." + updateFieldName, updateValue));
	}
	
	/**
	 * To add the new values to the client configuration
	 * Usage: |addToFreightBillClientConfiguration | domain | categoryName | fieldName | addValue |
	 * 
	 * @param domain
	 * @param categoryName
	 * @param fieldName
	 * @param addValue
	 */
	public static void addToFreightBillClientConfiguration(String domain, String categoryName, String fieldName,
			String addValue) {
		DBObject clientObject = null;
		domain = domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
		DBCollection table = db.getCollection("clients");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("domain_url", domain);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			clientObject = clientCursor.next();
		}
		clientCursor.close();

		String cid = clientObject.get("_id").toString();

		DBObject clientConfigurationObject = null;
		DBCollection clientConfigurationCollection = db.getCollection("client_configurations");
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("client_id", new ObjectId(cid));

		DBCursor clientConfigCursor = clientConfigurationCollection.find(searchQuery1);

		while (clientConfigCursor.hasNext()) {
			clientConfigurationObject = clientConfigCursor.next();
		}
		clientConfigCursor.close();

		MongoCollection<Document> collection = mongodb.getCollection("client_configurations");

		FindIterable<Document> config = collection
				.find(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())));

		@SuppressWarnings("unchecked")
		List<Document> categories = (List<Document>) ((Document) config.first().get("freight_bill")).get("categories");

		int categoryPosition = 0;

		for (Document category : categories) {
			 if (category.get("name").toString().equalsIgnoreCase(categoryName)) {
				break;
			}
			categoryPosition++;
		}

		collection.findOneAndUpdate(Filters.eq(new ObjectId(clientConfigurationObject.get("_id").toString())),
				Updates.push("freight_bill.categories." + categoryPosition + "." + fieldName, new ObjectId(addValue)));
	}
	
	public static String getTestCycleId(String testCycle) {
		DBObject clientObject = null;
		DBCollection table = db.getCollection("test_case_cycle");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			clientObject = clientCursor.next();
		}
		clientCursor.close();
		
		String testCycleId = null;
		
		try {
			testCycleId = clientObject.get("test_cycle_id").toString();
		} catch (Exception e) {
			//returning null
		}

		return testCycleId;
	}
	
	public static void setTestCycleId(String testCycle, String testCycleId) {
		DBCollection table = db.getCollection("test_case_cycle");
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);
		
		DBObject modifiedColumn =new BasicDBObject();
		modifiedColumn.put("test_cycle_id", testCycleId);
		
		DBObject modifiedObject =new BasicDBObject();
		modifiedObject.put("$set", modifiedColumn);

		table.update(searchQuery, modifiedObject, true, false);
	}
	
	public static String getTestExecutionDetails(String testCycle, String testCase) {
		DBObject clientObject = null;
		DBCollection table = db.getCollection("test_case_details");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);
		searchQuery.put("test_case", testCase);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			clientObject = clientCursor.next();
		}
		clientCursor.close();
		
		String testCaseExecutionDetails = null;
		
		try {
			testCaseExecutionDetails = clientObject.get("test_execution").toString();
		} catch (Exception e) {
			//returning null
		}

		return testCaseExecutionDetails;
	}
	
	public static void setTestExecutionDetails(String testCycle, String testCase, String testExecutionDetails, String testClient,
			String testCasePath, String description) {
		DBCollection table = db.getCollection("test_case_details");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);
		searchQuery.put("test_case", testCase);
		
		DBObject modifiedColumn =new BasicDBObject();
		modifiedColumn.put("test_execution", testExecutionDetails);
		modifiedColumn.put("rerun", 0);
		modifiedColumn.put("test_client", testClient);
		modifiedColumn.put("test_case_path", testCasePath);
		modifiedColumn.put("test_case_description", description);
				
		DBObject modifiedObject =new BasicDBObject();
		modifiedObject.put("$set", modifiedColumn);

		table.update(searchQuery, modifiedObject, true, false);
	}
	
	public static HashMap<String, HashMap<String, String>> getAllTestCasesForTestCycle(String testCycle,
			String[] clientFilters) {

		DBCollection table = db.getCollection("test_case_details");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);

		if (clientFilters != null && clientFilters.length > 0) {
			if (!clientFilters[0].equalsIgnoreCase("all")) {
				BasicDBList docIds = new BasicDBList();
				for (String clientFilter : clientFilters) {
					docIds.add(clientFilter);
				}
				DBObject inClause = new BasicDBObject();
				inClause.put("$in", docIds);
				searchQuery.put("test_client", inClause);
			}
		} /*
			 * else { if (clientFilters.length != 0 &&
			 * !clientFilters[0].equalsIgnoreCase("all")) { searchQuery.put("test_client",
			 * clientFilters[0]); } }
			 */

		DBCursor clientCursor = table.find(searchQuery);

		HashMap<String, HashMap<String, String>> testCases = new HashMap<String, HashMap<String, String>>();

		while (clientCursor.hasNext()) {
			DBObject clientObject = clientCursor.next();
			HashMap<String, String> testCaseDetails = new HashMap<String, String>();
			testCaseDetails.put("test_cycle",(String) clientObject.get("test_cycle"));
			testCaseDetails.put("test_case_path",(String) clientObject.get("test_case_path"));
			testCaseDetails.put("test_case_description",(String) clientObject.get("test_case_description"));
			testCaseDetails.put("test_client",(String) clientObject.get("test_client"));
			testCaseDetails.put("test_case_tag",(String) clientObject.get("test_case_tag"));
			testCaseDetails.put("testCaseResult",(String) clientObject.get("testCaseResult"));

			// Optional.ofNullable((String) clientObject.get("testCaseResult")).filter(s ->
			// !s.isEmpty()).orElse("");

			BasicDBList referenceids = (BasicDBList) clientObject.get("referenceId");
			testCaseDetails.put("referenceId",referenceids.toString());

			testCases.put((String) clientObject.get("test_case"), testCaseDetails);
		}
		clientCursor.close();

		return testCases;
	}

	public static int getTestCaseRerunCount(String testCycle, String testCase) {
		DBObject clientObject = null;
		DBCollection table = db.getCollection("test_case_details");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);
		searchQuery.put("test_case", testCase);

		DBCursor clientCursor = table.find(searchQuery);

		while (clientCursor.hasNext()) {
			clientObject = clientCursor.next();
		}
		clientCursor.close();
		
		int testCaseRerunCount = 0;
		
		try {
			testCaseRerunCount = ((Number) clientObject.get("rerun")).intValue();
		} catch (Exception e) {
			//returning null
		}
		return testCaseRerunCount;
	}

	public static void setTestCaseRerunCount(String testCycle, String testCase, String testTag, String testCaseResult, String referenceId) {
		DBCollection table = db.getCollection("test_case_details");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);
		searchQuery.put("test_case", testCase);
		
		List<String> referenceIdArray = new ArrayList<String>();
		referenceIdArray.add(referenceId);
		
		DBObject modifiedColumn =new BasicDBObject();
		modifiedColumn.put("testCaseResult", testCaseResult);
		
		DBObject modifiedObject =new BasicDBObject();
		modifiedObject.put("$set", modifiedColumn);
		modifiedObject.put("$inc", new BasicDBObject().append("rerun", 1));
		modifiedObject.put("$push", new BasicDBObject().append("referenceId", referenceIdArray));
		if (!testTag.equalsIgnoreCase("tagx") || !testTag.isEmpty() || !(testTag == null)) {
			modifiedColumn.put("test_case_tag", testTag);
		}

		table.update(searchQuery, modifiedObject, true, false);
	}

	public static void setTestCaseRerunCountToZero(String testCycle, String testCase, String testTag, String testCaseResult, String referenceId) {
		DBCollection table = db.getCollection("test_case_details");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);
		searchQuery.put("test_case", testCase);
		
		List<String> referenceIdArray = new ArrayList<String>();
		referenceIdArray.add(referenceId);
		
		DBObject modifiedColumn =new BasicDBObject();
		modifiedColumn.put("rerun", 0);
		modifiedColumn.put("testCaseResult", testCaseResult);
		if (!testTag.equalsIgnoreCase("tagx") || !testTag.isEmpty() || !(testTag == null)) {
			modifiedColumn.put("test_case_tag", testTag);
		}
		
		DBObject modifiedObject =new BasicDBObject();
		modifiedObject.put("$set", modifiedColumn);
		modifiedObject.put("$push", new BasicDBObject().append("referenceId", referenceIdArray));

		table.update(searchQuery, modifiedObject, true, false);
	}
	
	
	/** Commenting this function for later use
	public static void setTestCaseDetails(String testCycle, String testCase, String testClient,
			String testCasePath) {
		DBCollection table = db.getCollection("test_case_details");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("test_cycle", testCycle);
		searchQuery.put("test_case", testCase);
		
		DBObject modifiedColumn =new BasicDBObject();
		modifiedColumn.put("test_client", testClient);
		modifiedColumn.put("test_case_path", testCasePath);
		
		DBObject modifiedObject =new BasicDBObject();
		modifiedObject.put("$set", modifiedColumn);

		table.update(searchQuery, modifiedObject, true, false);
	}  */
	
	/**
	 * Used to delete the old wallet entry from collection
	 * @param domain
	 * @param nMinusDay
	 */
	public static void deleteOldWalletRecords(String domain, Integer nMinusDay) {
		DBObject result = null;
		domain = domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
		DBCollection table = db.getCollection("clients");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("domain_url", domain);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		cursor.close();

		String cid = result.get("_id").toString();

		DBCollection table1 = db.getCollection("wallet");
		
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("client_id", new ObjectId(cid));
		
		try {
			searchQuery1.put("created_at", BasicDBObjectBuilder.start("$lt", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(new DateUtil().dateCalculation("yyyy-MM-dd'T'HH:mm:ss", nMinusDay))).get());
		} catch (ParseException e) {
			Extend.error("Applying date filter in wallet collection query is failed : "+e.getMessage());
		}
		
		WriteResult c1 = table1.remove(searchQuery1);
		Extend.info("Removed entries from wallet collection : "+c1.toString());
	}
	
	/**
	 * Used to delete the old optima entries in the optima_materials collection
	 * @param domain
	 * @param depotname
	 */
	public static void removingOldOptimaMaterials(String domain, String depotName) {
		DBObject clientResult = null;
		DBObject depotResult = null;
		domain = domain.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
		DBCollection clientTable = db.getCollection("clients");
		DBCollection depotTable = db.getCollection("depot_factory");
		
		BasicDBObject clientSearchQuery = new BasicDBObject();
		clientSearchQuery.put("domain_url", domain);
		BasicDBObject depotSearchQuery = new BasicDBObject();
		depotSearchQuery.put("name", depotName);

		DBCursor clientCursor = clientTable.find(clientSearchQuery);

		while (clientCursor.hasNext()) {
			clientResult = clientCursor.next();
		}
		clientCursor.close();
		
		String cid = clientResult.get("_id").toString();
		
		DBCursor depotCursor = depotTable.find(depotSearchQuery);

		while (depotCursor.hasNext()) {
			depotResult = depotCursor.next();
		}
		depotCursor.close();

		String did = depotResult.get("_id").toString();

		DBCollection table1 = db.getCollection("optima_material");
		
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("client_id", new ObjectId(cid));
		searchQuery1.put("depot_id", new ObjectId(did));
		
		WriteResult c1 = table1.remove(searchQuery1);
		Extend.info("Removed entries from optima collection : "+c1.toString());
	}
	
	/**
	 * To close the mongo db connection when the 
	 */
	public static void closeDBConnection () {
		mongoClient.close();
	}
	
	/**
	 * To store order id for PG auto approval check
	 * Usage: | updateOrderIdPG | cid | updateFieldName | updateValue |
	 * @param cid
	 * @param updateFieldName
	 * @param updateValue
	 */
	public static void updateOrderIdPG(String cid, String updateFieldName, Object order_id) {

		DBObject orderidObject = null;
		DBCollection orderidCollection = db.getCollection("order_id_pg");
		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("client_id", new ObjectId(cid));

		DBCursor orderidCursor = orderidCollection.find(searchQuery1);

		while (orderidCursor.hasNext()) {
			orderidObject = orderidCursor.next();
		}
		orderidCursor.close();

		MongoCollection<Document> collection = mongodb.getCollection("order_id_pg");
		collection.updateOne(Filters.eq(new ObjectId(orderidObject.get("_id").toString())),
				Updates.set(updateFieldName, order_id));
	}

	public static void main(String[] args) {
		initialize("mongodb+srv://staging-admin:VpbUDlaBkv4vTzJ8@tsm-staging-mlxar.mongodb.net/db_pando_staging?retryWrites=true",
					"db_pando_testing");
		//updateClientConfiguration("srf-auto.pandodev.in", "payment_settings.max_indents_for_invoice.secondary","unset");
		getAllTestCasesForTestCycle("PAN-TR-33",new String [] {}).forEach((k,v) -> {
			System.out.println(k+":"+v);
		});
	}
}
