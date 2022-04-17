package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import org.everit.json.schema.loader.SchemaLoader;
import org.apache.commons.io.FileUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Json {

	/**
	 * Extracts string value from JSON
	 * Usage: | getValueFromJSON| json| field|
	 * @param json
	 * @param field
	 * Input example: student.address.city
	 * @return
	 */
	public static Object getValueFromJSON(String json, String field) {
		String[] objectHierarchy = field.split("\\.");
		JSONObject jsonObject;
		Object value = "";
		try {
			// json = json.substring(1, json.length()-1);
			System.out.println(json);
			jsonObject = new JSONObject(json);
			for (int i = 0; i < objectHierarchy.length; i++) {
				String objectStr = objectHierarchy[i];
				if (i == objectHierarchy.length - 1) {
					value = jsonObject.get(objectStr);
				} else {
					jsonObject = jsonObject.getJSONObject(objectStr);
				}
			}
		} catch (Exception exception) {
			System.err.println(exception.toString());
		}

		return value;
	}
	
	/**
	 * Extracts Array value as a string from JSON
	 * Usage: | getArrayValueFromJSON| json| field|
	 * @param String json
	 * @param String field
	 * @return String
	 */
	public static String getArrayValueFromJSON(String json, String field) {
		String[] objectHierarchy = field.split("\\.");
		JSONObject jsonObject;
		String value = "";
		try {
			System.out.println(json);
			jsonObject = new JSONObject(json);
			for (int i = 0; i < objectHierarchy.length; i++) {
				String objectStr = objectHierarchy[i];
				if (i == objectHierarchy.length - 1) {
					value = jsonObject.getJSONArray(objectStr).toString();
					System.out.println(value);
				} else {
					jsonObject = jsonObject.getJSONObject(objectStr);
				}
			}
		} catch (Exception exception) {
			System.err.println(exception.toString());
		}

		return value;
	}
	
	/**
	 * Fetching Json from URL extarct Data
	 * Usage: | getValueFromJSONURL| url| field|
	 * @param url
	 * @param field
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	
	public static String getValueFromJSONURL(String url, String field) throws MalformedURLException, IOException {
		
	    InputStream is = new URL(url).openStream();
	    
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    
	    String jsonText = sb.toString();
	    JSONObject json = new JSONObject(jsonText);
	    String data  = json.get(field).toString();
		return data;

	}
	
    

	/**
	 * Extracts JSON Object from JSON containing more than one JSON objects
	 * Usage: | getJSONObjectFromJsonArray| json| index|
	 * @param json
	 * @param object
	 *            index Example : o,1,2,...n etc.
	 * @return JSON object at provided index
	 */
	public static JSONObject getJSONObjectFromJsonArray(String json, int index) {
		JSONArray jSONArray;
		JSONObject jSONObject = null;
		try {
			jSONArray = new JSONArray(json);
			jSONObject = (JSONObject) jSONArray.get(index);
		} catch (JSONException exception) {
			// exception.printStackTrace();
			System.err.println(exception.getMessage());
		}
		return jSONObject;
	}

	/**
	 * Extracts specified type of value from JSON
	 * Usage: | getValueFromJSON| json| field| Class type|
	 * @param json
	 * @param field
	 * @param type
	 *            example int.class, String.class etc
	 * @return string value of specified type of field
	 */
	public static String getValueFromJSON(String json, String field,
			Class<?> type) {
		String[] objectHierarchy = field.split("\\.");
		String strValue = "";
		JSONObject jsonObject;

		try {
			// Log.Message(json, LogLevel.INFO);
			jsonObject = new JSONObject(json);

			for (int i = 0; i < objectHierarchy.length; i++) {
				String objectStr = objectHierarchy[i];

				if (i == objectHierarchy.length - 1) {
					String className = type.getName();

					switch (className) {
					case "int":
						int intValue = jsonObject.getInt(field);
						strValue = Integer.toString(intValue);
						break;
					case "boolean":
						boolean boolValue = jsonObject.getBoolean(field);
						strValue = Boolean.toString(boolValue);
						break;
					case "double":
						double floatValue = jsonObject.getDouble(field);
						strValue = Double.toString(floatValue);
						break;
					case "java.lang.String":
						strValue = jsonObject.getString(field);
						break;
					}
				} else {
					jsonObject = jsonObject.getJSONObject(objectStr);
				}
			}
		} catch (Exception exception) {
			System.err.println(exception.toString());
		}
		System.out.println(strValue);
		return strValue;
	}

	/**
	 * Extracts specified type of value from JSON based on the data type
	 * specified
	 * Usage: | getValueFromJSONByType| json| field| className|
	 * @param json
	 *            -Actual json
	 * @param field
	 *            -Name of the field to be extracted example:[0].datamodelID.
	 *            Here square bracket indicates Array. Above example will search
	 *            for object on 0th index in array. Then it will return value of
	 *            field 'datamodelID' of the object.
	 * @param type
	 *            example int,boolean,double,java.lang.String etc
	 * @return string value of specified type of field
	 */
	public static String getValueFromJSONByType(String json, String field,
			String className) {
		json = json.trim();
		String[] objectHierarchy = field.split("\\.");
		String strValue = "";
		JSONObject jsonObject;
		JSONArray jSONArray;
		int i = 0;

		try {
			// Log.Message(json, LogLevel.INFO);
			if (json.startsWith("[")) {
				jSONArray = new JSONArray(json);
				String objectStr = objectHierarchy[i++];
				final String[] splitWithOpenBracket = objectStr.split("\\[");
				final String[] splitWithCloseBracket = splitWithOpenBracket[1]
						.split("\\]");
				int arrayIndex = Integer.parseInt(splitWithCloseBracket[0]);
				jsonObject = jSONArray.getJSONObject(arrayIndex);
			} else {
				jsonObject = new JSONObject(json);
			}

			for (; i < objectHierarchy.length; i++) {
				String objectStr = objectHierarchy[i];
				if (objectStr.contains("[")) {
					final String[] splitWithOpenBracket = objectStr
							.split("\\[");
					final String[] splitWithCloseBracket = splitWithOpenBracket[1]
							.split("\\]");
					String arrayName = splitWithOpenBracket[0];
					int arrayIndex = Integer.parseInt(splitWithCloseBracket[0]);
					JSONArray jsonArray = jsonObject.getJSONArray(arrayName);
					Object valueFromArray = jsonArray.get(arrayIndex);
					if (valueFromArray instanceof JSONObject) {
						jsonObject = (JSONObject) valueFromArray;
					} else if (valueFromArray instanceof String) {
						return (String) valueFromArray;
					}
					continue;
				}
				if (i == objectHierarchy.length - 1) {

					switch (className) {
					case "int":
						int intValue = jsonObject.getInt(objectStr);
						strValue = Integer.toString(intValue);
						break;
					case "boolean":
						boolean boolValue = jsonObject.getBoolean(objectStr);
						strValue = Boolean.toString(boolValue);
						break;
					case "double":
						double floatValue = jsonObject.getDouble(objectStr);
						strValue = Double.toString(floatValue);
						break;
					case "java.lang.String":
						strValue = jsonObject.getString(objectStr);
						break;
					}
				} else {
					jsonObject = jsonObject.getJSONObject(objectStr);
				}
			}
		} catch (Exception exception) {
			System.err.println(exception.toString());
			System.out.println(exception);
		}
		System.out.println(strValue);
		return strValue;
	}

	/**
	 * This method accepts a JSON array as a String and returns an ArrayList of
	 * map, which is populated with values in input JSON
	 * Usage: | getMapArrayListFromJsonArray| json|
	 * @param json
	 *            : JSON array
	 * @return
	 */
	public static ArrayList<HashMap<String, String>> getMapArrayListFromJsonArray(
			String json) {
		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		JSONArray jSONArray;
		JSONObject jSONObject = null;
		try {
			jSONArray = new JSONArray(json);
			int length = jSONArray.length();
			for (int index = 0; index < length; index++) {
				jSONObject = (JSONObject) jSONArray.get(index);
				String[] jsonFieldNames = JSONObject.getNames(jSONObject);
				HashMap<String, String> row = new HashMap<String, String>();
				for (String jsonFieldName : jsonFieldNames) {
					Object jsonFieldValue = jSONObject.get(jsonFieldName);
					row.put(jsonFieldName, jsonFieldValue.toString());
				}
				resultList.add(row);
			}
		} catch (JSONException exception) {
			exception.printStackTrace();
			System.err.println(exception.getMessage());
		}
		return resultList;
	}

	/**
	 * 
	 * Usage: | getKeyIndexFromJSONByKeyAndValueType| json| key| keyValue|
	 * 
	 * @param json
	 *            - Pass the json in string format in which user wants to find
	 *            the expected value
	 * @param key
	 *            - key which user wants to find in the json
	 * @param keyValue
	 *            - key value which when gets matched, the corresponding index
	 *            value from json will be read.
	 * @param field
	 *            - expected field for which value has to be retrieved.
	 * @return jsonArrayIndex - Index in json where the expected key value is
	 *         matched.
	 * 
	 *         This index can be further used to send input parameters to
	 *         getValueFromJSONByType from fitnesse wiki
	 * 
	 */
	public static int getKeyIndexFromJSONByKeyAndValueType(String json,
			String key, String keyValue) {
		int jsonArrayIndex = 1000;

		ArrayList<HashMap<String, String>> resultJson = new ArrayList<HashMap<String, String>>();

		if (!json.isEmpty()) {
			System.out.println("Converting JSON Response to Array List Using Common Function");
			resultJson = getMapArrayListFromJsonArray(json);
			System.out.println("Total size of Response JSON-" + resultJson.size());
		} else {
			System.out.println("Total size of Response JSON - 0");
			return jsonArrayIndex;
		}
		boolean isKeyExists = false;

		int i = 0;
		for (HashMap<String, String> row : resultJson) {
			// Get the key from json
			Object expectedKey = row.get(key);
			if (keyValue.equals(expectedKey.toString())) {
				System.out.println("Key is matched -" + key + ","
						+ expectedKey.toString());
				isKeyExists = true;
				String test = row.toString();
				System.out.println("Row is -" + test);
				jsonArrayIndex = i;
				break;
			}
			i++;
		}
		if (!isKeyExists) {
			String strValue = "Key not found in json- " + key + "," + keyValue;
			System.out.println(strValue);
		}

		return jsonArrayIndex;
	}

	/**
	 * This method gets File path of a json and returns it in string format
	 * Usage: | getJsonFromFile| jsonFilePath|
	 * @param jsonFilePath
	 *            - path of file containing input json
	 * @return json in string format
	 * @throws Exception
	 */
	public String getJsonFromFile(String jsonFilePath) throws Exception {
		String jsonfromfile = "";

		try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			jsonfromfile = stringBuilder.toString();
		}
		System.out.println("Reading json from file: " + jsonFilePath);

		return jsonfromfile;
	}
	
	/**
	 * Triming Json value.
	 * Usage: | trimJson| json |
	 * @param jsonString
	 * @return
	 */
	public String trimJson(String jsonString) {
		String outPutjson = jsonString.replaceAll("[\n\r\t]", "");
		System.out.println("Trimmed Json [" + outPutjson + "]");
		return outPutjson;

	}

	/**
	 * This method is to validate the json payload using the schema file
	 * Usage: |validateJsonDocument |schemaFilePath |payload |
	 * @param schemaFilePath
	 * @param payload
	 * @return
	 */
	public static String validateJsonDocument(String schemaFilePath, String payload) throws JSONException, IOException {
		ArrayList<String> validationResponse = new ArrayList<String>();

		try {
			File file = new File(schemaFilePath);

			JSONObject jsonSchema = new JSONObject(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
			JSONObject jsonData = new JSONObject(payload);

			Schema schema = SchemaLoader.load(jsonSchema);
			schema.validate(jsonData);

			validationResponse.add("true");
		} catch (ValidationException e) {
			for (String ex : e.getAllMessages()) {
				validationResponse.add(ex);
			}
		}

		return String.join(",\n", validationResponse);
	}
	
	/**
	 * Returns the values as a array from json array
	 * Usage: | getValuesAsArrayFromJSONArray| json| field|
	 * @param json
	 * @param field
	 * Input example: student.address.city
	 * @return
	 */
	public static ArrayList<Object> getValuesAsArrayFromJSONArray(String json, String field) {

		JSONArray jsonObject;
		ArrayList<Object> value = new ArrayList<Object>();

		try {

			jsonObject = new JSONArray(json);

			for (Object obj : jsonObject) {
				value.add("\""+getValueFromJSON(obj.toString(), field)+"\"");
			}

		} catch (Exception exception) {
			System.err.println(exception.toString());
		}

		return value;
	}
	
	public static void main(String[] args) {
		String json = "{\"data\":{\"id\":\"614d9a12af00120049a48c51\",\"client_id\":\"5e6879714210bf3da4df3bcf\",\"status\":24,\"order_number\":\"MAR-R20219241452S-M-2\",\"movement\":1,\"order_type\":0,\"delivery_pending_reason\":0,\"delivery_numbers\":[],\"has_quote\":false,\"auto_deliver\":false,\"reported_at\":\"2021-09-24T09:28:31.220Z\",\"assigned_at\":\"2021-09-24T09:28:21.851Z\",\"created_at\":\"2021-09-24T09:27:46.469Z\",\"updated_at\":\"2021-09-24T09:28:31.712Z\",\"truck_out_disable\":false,\"cancelled_at\":null,\"base_frieght\":{\"freight_charge\":10000,\"freight_unit\":1},\"vehicle\":{\"number\":\"CCC 5555\",\"chasis_number\":null,\"engine_number\":null,\"insurance_validity\":null,\"fitness_validity\":null},\"vehicle_type\":{\"id\":\"614d98eef8b0ae00499287e9\",\"name\":\"v20219241452S MRT\",\"type\":\"FTL\",\"cft\":965,\"kg\":1000},\"transporter\":{\"id\":\"614d98eaf8b0ae00499287e5\",\"name\":\"t20219241452S\",\"created_at\":\"2021-09-24T09:22:50.639Z\",\"ref_id\":\"tr20219241452S\"},\"changedTransporter\":[],\"driver\":{\"id\":\"614d9a36f8b0ae0049928830\",\"mobile\":\"6665554443\"},\"sources\":[{\"type\":1,\"unique_id\":\"614d9a12af00120049a48c53\",\"location_id\":\"614d98eff5acde0049e74e52\",\"gate_id\":\"614d98f0f5acde0049e74e53\",\"loading_started_at\":\"\",\"loading_ended_at\":\"\",\"loader_available\":false,\"add_to_invoice\":false,\"arrived_at\":null,\"dispatched_at\":null,\"loading_date\":\"2021-09-15T09:27:32.000Z\",\"details\":{\"name\":\"d20219241452S\",\"id\":\"614d98eff5acde0049e74e52\",\"customer_type\":\"\",\"country\":\"India\",\"reference_id\":\"R20219241452S\",\"city\":\"Chennai\",\"category\":\"\",\"combine\":\"\",\"country_code\":\"\",\"postal_code\":\"\",\"customer\":\"\",\"address\":\"\"},\"load_point_arrived_at\":null},{\"type\":1,\"unique_id\":\"614d9a12af00120049a48c52\",\"location_id\":\"614d98eff5acde0049e74e52\",\"gate_id\":\"614d98f0f5acde0049e74e54\",\"loading_started_at\":\"\",\"loading_ended_at\":\"\",\"loader_available\":false,\"add_to_invoice\":false,\"arrived_at\":null,\"dispatched_at\":null,\"loading_date\":\"2021-09-15T09:27:36.000Z\",\"details\":{\"name\":\"d20219241452S\",\"id\":\"614d98eff5acde0049e74e52\",\"customer_type\":\"\",\"country\":\"India\",\"reference_id\":\"R20219241452S\",\"city\":\"Chennai\",\"category\":\"\",\"combine\":\"\",\"country_code\":\"\",\"postal_code\":\"\",\"customer\":\"\",\"address\":\"\"},\"load_point_arrived_at\":null}],\"docks\":[],\"destinations\":[{\"type\":2,\"lr_attachment_ids\":[],\"location_id\":\"614d98eaf8b0ae00499287e4\",\"source_location_id\":\"614d98eff5acde0049e74e52\",\"source_gate_id\":\"614d98f0f5acde0049e74e53\",\"gate_id\":\"\",\"sold_to_id\":\"614d98eaf8b0ae00499287e4\",\"unloader_available\":false,\"add_to_invoice\":false,\"expected_delivery\":\"2021-09-16T09:27:32.000Z\",\"has_lr_attachment\":false,\"details\":{\"name\":\"c120219241452S\",\"id\":\"614d98eaf8b0ae00499287e4\",\"customer_type\":\"\",\"country\":\"India\",\"reference_id\":\"cr120219241452S\",\"city\":\"Pune\",\"category\":\"\",\"combine\":true,\"country_code\":\"\",\"postal_code\":\"\",\"customer\":\"\",\"address\":\"\"},\"sold_details\":{\"name\":\"c120219241452S\",\"id\":\"614d98eaf8b0ae00499287e4\",\"reference_id\":\"cr120219241452S\"},\"reported_at\":\"\",\"release_at\":\"\",\"geofence_entry_time\":\"\",\"otp_verified\":\"\"},{\"type\":2,\"lr_attachment_ids\":[],\"location_id\":\"614d98eaf8b0ae00499287e4\",\"source_location_id\":\"614d98eff5acde0049e74e52\",\"source_gate_id\":\"614d98f0f5acde0049e74e54\",\"gate_id\":\"\",\"sold_to_id\":\"614d98eaf8b0ae00499287e4\",\"unloader_available\":false,\"add_to_invoice\":false,\"expected_delivery\":\"2021-09-16T09:27:36.000Z\",\"has_lr_attachment\":false,\"details\":{\"name\":\"c120219241452S\",\"id\":\"614d98eaf8b0ae00499287e4\",\"customer_type\":\"\",\"country\":\"India\",\"reference_id\":\"cr120219241452S\",\"city\":\"Pune\",\"category\":\"\",\"combine\":true,\"country_code\":\"\",\"postal_code\":\"\",\"customer\":\"\",\"address\":\"\"},\"sold_details\":{\"name\":\"c120219241452S\",\"id\":\"614d98eaf8b0ae00499287e4\",\"reference_id\":\"cr120219241452S\"},\"reported_at\":\"\",\"release_at\":\"\",\"geofence_entry_time\":\"\",\"otp_verified\":\"\"}],\"custom_fields\":{},\"flow\":{\"inspection\":{},\"driver_accepted\":false,\"documentation_status\":{\"lorry_receipt\":false,\"eway_bill\":true,\"materials\":true},\"sim_tracking\":{\"is_enabled\":false,\"old_trip_ids\":[],\"trackable\":false,\"invalidTrip\":false},\"change_vehicle_type_id\":{},\"secondary_vehicle_type\":{},\"rfq_comment\":null,\"gps_tracking\":{\"is_enabled\":false,\"old_trip_ids\":[]},\"app_tracking\":{},\"change_hilly_region\":false,\"epod_deletion_data\":[],\"exim_document_fields\":{},\"courier_consignee_delivery\":[],\"vehicle_type_changed\":{}},\"lorry_receipt\":[],\"approval_status\":1,\"locationMissedCall\":[],\"comments\":{\"cancel\":{}},\"sla_breach\":false,\"acceptance_sla_breach\":false,\"sla_job_data\":{\"sla_breach_job_id\":\"5689\",\"sla_mail_notifier_job_id\":\"5690\",\"sla_breach_at\":\"2021-09-25T15:00:00.000Z\",\"cancel_job_id\":\"5691\",\"auto_cancel_at\":\"2021-09-30T16:30:46.000Z\"},\"transit_days\":0,\"previous_transporters\":[],\"timelines\":{\"_id\":\"614d9a12af00120049a48c57\",\"client_id\":\"5e6879714210bf3da4df3bcf\",\"order_id\":\"614d9a12af00120049a48c51\",\"vehicle_types\":[],\"vehicles\":[],\"transporters\":[],\"gates\":[],\"consignees\":[],\"status\":[],\"rejections\":[],\"created_at\":\"2021-09-24T09:27:46.563Z\",\"updated_at\":\"2021-09-24T09:27:46.563Z\"},\"is_open_rfq\":false,\"retry_modified_picklist\":{\"shouldRetry\":false,\"associated_indent_ids\":[]},\"is_round_trip_eligible\":false,\"meta\":{\"courier\":{}},\"master_indent\":null,\"master_indent_movement_type\":null,\"master_indent_freight_forwarder\":null,\"eway_bill\":{\"attachments\":[]},\"legType\":null,\"container_picked\":null,\"container\":null,\"container_id\":null,\"child_indent_ids\":[],\"no_of_vehicles\":1,\"purchase_order_details\":{},\"package_details\":{},\"weigh_bridge\":{},\"allow_retry_freight_provision\":false,\"payment_terms\":\"\",\"freight_terms\":\"\",\"related_party\":\"\",\"caution_code\":\"\",\"is_domestic\":false,\"label_details\":{\"errors\":[],\"stored_labels\":[]},\"deliveries\":[],\"vehicle_loading_date\":\"2021-09-15T09:27:32.000Z\",\"transporter_name\":\"t20219241452S\",\"vehicle_type_name\":\"v20219241452S MRT\",\"route_location\":\"Chennai - Pune\"}}";
		String sources = String.valueOf(getValueFromJSON(json, "data.sources"));
		System.out.println(sources);
		int position = getKeyIndexFromJSONByKeyAndValueType(sources, "gate_id", "614d98f0f5acde0049e74e54");
		System.out.println(position);
		String object = String.valueOf(getJSONObjectFromJsonArray(String.valueOf(getValueFromJSON(json, "data.sources")),position));
		System.out.println(object);
		System.out.println(getValueFromJSON(object,"unique_id"));
	}
}
