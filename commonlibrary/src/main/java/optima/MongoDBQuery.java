package optima;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import common.Common;

public class MongoDBQuery {
	
	private static MongoClient mongoClient = null;
	private static MongoClientURI mongoClientURI = null;
	private static DB db = null;
	private static BasicDBObject itemModifiedPicklist = null;
	private static BasicDBObject erp_invoice_details = null;
	private static BasicDBObject pod_to_sap_payload = null;
	private static BasicDBObject epod_rejection_payload = null;
	
	/**
	 * To get the database connection to execute the query
	 * Usage: | getMongoConnection| connectionString| databaseName|
	 * @param connection
	 * @param database
	 */
	@SuppressWarnings("deprecation")
	public static void getMongoConnection(String connectionString, String databaseName) {
		mongoClientURI = new MongoClientURI(connectionString);
		mongoClient = new MongoClient(mongoClientURI);
		db = mongoClient.getDB(databaseName);
	}
	
	/**
	 * To get the itemModifiedPicklist using the Indent ID and delivery number
	 * Usage: | getModifiedPicklist| indentId| fieldKey| fieldValue|
	 * @param indentId
	 * @param deliveryNumber
	 */
	public static BasicDBObject getModifiedPicklist(String indentId, String fieldKey, String fieldValue) {

		DBObject result = null;
		DBCollection table = db.getCollection("modifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ModifiedPicklist.ItemModifiedPicklist.indent_id", indentId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		BasicDBObject modifiedPicklist = (BasicDBObject) result.get("ModifiedPicklist");
		BasicDBList allItemModifiedPickList = (BasicDBList) modifiedPicklist.get("ItemModifiedPicklist");
		
		for (Object eachItemModifiedPicklist : allItemModifiedPickList) {
			try {
				if (((BasicDBObject) eachItemModifiedPicklist).get("indent_id").equals(indentId)
						&& ((BasicDBObject) eachItemModifiedPicklist).get(fieldKey).equals(fieldValue)) {
					itemModifiedPicklist = ((BasicDBObject) eachItemModifiedPicklist);
					return itemModifiedPicklist;
				}				
			} catch (Exception e) {
				
			}
		}
		return null;
	}
	
	/**
	 * To get the SRF's itemModifiedPicklist using the Indent ID and delivery number
	 * Usage: | getSRFModifiedPicklist| indentId| deliveryNumber|
	 * @param indentId
	 * @param deliveryNumber
	 */
	public static BasicDBObject getSRFModifiedPicklist(String indentId, String deliveryNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("modifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("InputParameters.X_XXSRF_PRIMARY_PICKLIST_TAB.items.INDENT_ID", indentId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		BasicDBObject InputParameters = (BasicDBObject) result.get("InputParameters");
		BasicDBObject allPrimaryPickList = (BasicDBObject) InputParameters.get("X_XXSRF_PRIMARY_PICKLIST_TAB");
		BasicDBList allItemModifiedPickList = (BasicDBList) allPrimaryPickList.get("items");
		
		for (Object eachItemModifiedPicklist : allItemModifiedPickList) {
			try {
				if (((BasicDBObject) eachItemModifiedPicklist).get("INDENT_ID").equals(indentId)
						&& ((BasicDBObject) eachItemModifiedPicklist).get("DELIVERY_NUMBER").equals(deliveryNumber)) {
					itemModifiedPicklist = ((BasicDBObject) eachItemModifiedPicklist);
					return itemModifiedPicklist;
				}				
			} catch (Exception e) {
				
			}
		}
		return null;
	}
	
	/**
	 * To get the SRF's RestHeader using the Indent ID
	 * Usage: | getSRFRestHeader| indentId|
	 * @param indentId
	 * @param deliveryNumber
	 */
	public static BasicDBObject getSRFRestHeader(String indentId) {

		DBObject result = null;
		DBCollection table = db.getCollection("modifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("InputParameters.X_XXSRF_PRIMARY_PICKLIST_TAB.items.INDENT_ID", indentId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		itemModifiedPicklist = (BasicDBObject) result.get("RESTHeader");
		
		return itemModifiedPicklist;
	}
	
	/**
	 * To get the value from the ModifiedPicklist for the key
	 * Usage: | getValueFromModifiedPicklist| keyToRetrieveValue|
	 * @param key
	 * @return
	 */
	public static String getValueFromModifiedPicklist(String keyToRetrieveValue) {
		try {
			return itemModifiedPicklist.get(keyToRetrieveValue).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * To check if the Delete Notification sent via ModifiedPicklist using the Indent ID
	 * Usage: | isDeleteIndentModifiedPicklistSent| indentId|
	 * @param indentId
	 * @return boolean
	 */
	public static boolean isDeleteIndentModifiedPicklistSent(String indentId) {

		DBObject result = null;
		DBCollection table = db.getCollection("modifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ModifiedPicklist.ItemModifiedPicklist.indent_id", indentId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		BasicDBObject modifiedPicklist = (BasicDBObject) result.get("ModifiedPicklist");
		BasicDBList allItemModifiedPickList = (BasicDBList) modifiedPicklist.get("ItemModifiedPicklist");
		
		for (Object eachItemModifiedPicklist : allItemModifiedPickList) {
			if (((BasicDBObject) eachItemModifiedPicklist).get("indent_id").equals(indentId)
					&& ((BasicDBObject) eachItemModifiedPicklist).get("indication").equals("DELETE")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * To check if the header is present in the payload
	 * Usage: | isHeaderPresentInPayload| headerKey|
	 * @param headerKey
	 * @return boolean
	 */
	public static boolean isHeaderPresentInPayload(String headerKey) {
		return itemModifiedPicklist.containsField(headerKey);
	}
	
	/**
	 * To check if the total number of header is present in the payload
	 * Usage: | getNumberOfHeaderInPayload| 
	 * @return Integer
	 */
	public static Integer getNumberOfHeaderInPayload() {
		return itemModifiedPicklist.size();
	}
	
	/**
	 * To get the total itemModifiedPicklist sent
	 * Usage: | getTotalNoOfModifiedPicklistSent| deliveryNumber|
	 * @param deliveryNumber
	 */
	public static int getTotalNoOfItemsInModifiedPicklist(String deliveryNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("modifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ModifiedPicklist.ItemModifiedPicklist.delivery_number", deliveryNumber);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		BasicDBObject modifiedPicklist = (BasicDBObject) result.get("ModifiedPicklist");
		BasicDBList allItemModifiedPickList = (BasicDBList) modifiedPicklist.get("ItemModifiedPicklist");
		int totalNumberOfModifiedPicklistSent = 0;
		
		for (int i = 0; i < allItemModifiedPickList.size(); i++) {
			totalNumberOfModifiedPicklistSent = totalNumberOfModifiedPicklistSent + 1;
		}
		return totalNumberOfModifiedPicklistSent;
	}
	
	/**
	 * To get the total ModifiedPicklist sent
	 * Usage: | getTotalNoOfModifiedPicklistSent| deliveryNumber|
	 * @param deliveryNumber
	 */
	public static int getTotalNoOfModifiedPicklistSent(String deliveryNumber) {

		DBCollection table = db.getCollection("modifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ModifiedPicklist.ItemModifiedPicklist.delivery_number", deliveryNumber);
		
		return table.find(searchQuery).count();
	}
	
	/**
	 * To get the itemModifiedPicklist using the Indent ID and delivery number
	 * Usage: | getSrfCustomInvoicePostingPayload|invoiceNumber|
	 * @param invoiceNumber
	 * @return payload
	 */
	public static BasicDBObject getSrfCustomInvoicePostingPayload(String invoiceNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("srf_invoice_posting");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("get_freight_invoice_dtl.InputParameters.P_FREIGHT_DETAILS_TBL.items.INVOICE_NUMBER", invoiceNumber);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		erp_invoice_details = (BasicDBObject) result.get("get_freight_invoice_dtl");
		return erp_invoice_details;
	}
	
	/**
	 * To get the itemModifiedPicklist using the Indent ID and delivery number
	 * Usage: | getSrfCustomFreightProvisionPayload | indentNumber|
	 * @param invoiceNumber
	 * @return payload
	 */
	public static BasicDBObject getSrfCustomFreightProvisionPayload(String indentNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("srf_freight_provisioning");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("get_freight_invoice_dtl.InputParameters.P_FREIGHT_DETAILS_TBL.items.INDENT_NUMBER", indentNumber);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		erp_invoice_details = (BasicDBObject) result.get("get_freight_invoice_dtl");
		return erp_invoice_details;
	}
	
	/**
	 * To get the Rest header value using the key
	 * Usage: | getRestHeaderValuesFromInvoicePayload| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static String getRestHeaderValuesFromInvoicePayload(String keyToRetrieveValue) {
		try {
			BasicDBObject restHeader = (BasicDBObject) erp_invoice_details.get("RESTHeader");
            return restHeader.get(keyToRetrieveValue).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * To get the value from the Freight invoice using the key
	 * Usage: | getFreightDetailsValuesFromInvoicePayload| filterKey| filterValue| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static String getFreightDetailsValuesFromInvoicePayload(String filterKey, String filterValue, String keyToRetrieveValue) {
		try {
			BasicDBObject inputParameters = (BasicDBObject) erp_invoice_details.get("InputParameters");
			BasicDBObject freightDetails = (BasicDBObject) inputParameters.get("P_FREIGHT_DETAILS_TBL");
			BasicDBList items = (BasicDBList) freightDetails.get("items");

			for (Object item : items) {
				try {
					BasicDBObject freightDetailsitem = ((BasicDBObject) item);
					if (freightDetailsitem.get(filterKey).toString().equals(filterValue)) {
						return freightDetailsitem.get(keyToRetrieveValue).toString();
					}
				} catch (Exception e) {

				}
			}

			return items.get(keyToRetrieveValue).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * To get the value from the Freight invoice using the key
	 * Usage: | getFreightDetailsValuesFromInvoicePayload| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static String getFreightDetailsValuesFromInvoicePayload(String keyToRetrieveValue) {
		try {
			BasicDBObject inputParameters = (BasicDBObject) erp_invoice_details.get("InputParameters");
			BasicDBObject freightDetails = (BasicDBObject) inputParameters.get("P_FREIGHT_DETAILS_TBL");
			BasicDBList items = (BasicDBList) freightDetails.get("items");
			
			for (Object item : items) {
				try {
					BasicDBObject freightDetailsitem = ((BasicDBObject) item);
					return freightDetailsitem.get(keyToRetrieveValue).toString();			
				} catch (Exception e) {
					
				}
			}
				
            return items.get(keyToRetrieveValue).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * To get the strategy one invoice payload for OEL
	 * Usage: | getStrategy1InvoicePostingPayload|invoiceNumber|
	 * @param invoiceNumber
	 * @return payload
	 */
	public static BasicDBObject getStrategy1InvoicePostingPayload(String invoiceNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("strategy_1_invoice_posting");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("invoice_number", invoiceNumber);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		erp_invoice_details = (BasicDBObject) result;
		return erp_invoice_details;
	}
	
	/**
	 * To get the strategy one invoice payload for OEL
	 * Usage: | getStrategy2InvoicePostingPayload|invoiceNumber|
	 * @param invoiceNumber
	 * @return payload
	 */
	public static BasicDBObject getStrategy2InvoicePostingPayload(String invoiceNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("strategy_2_invoice_posting");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("invoice_number", invoiceNumber);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		erp_invoice_details = (BasicDBObject) result;
		return erp_invoice_details;
	}
	
	/**
	 * To get the value from the invoice payload using the key
	 * Usage: | getIndentsValuesFromInvoicePayload| IndentIdToSearch| filterKey| filterValue| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getIndentsValuesFromInvoicePayload(String IndentId, String filterKey, String filterValue, String keyToRetrieveValue) {
		try {
			BasicDBList items = (BasicDBList) erp_invoice_details.get("indents");
			
			for (Object item : items) {
				try {
					BasicDBObject indent = ((BasicDBObject) item);
					if (String.valueOf(indent.get("indent_id")).equals(IndentId) && String.valueOf(indent.get(filterKey)).equals(filterValue)) {
						return indent.get(keyToRetrieveValue).toString();
					}		
				} catch (Exception e) {
					
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * To get the value from the invoice payload using the key
	 * Usage: | getIndentsValuesFromShipmentInvoicePayload| IndentIdToSearch| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getIndentsValuesFromShipmentInvoicePayload(String filterKey, String filterValue, String keyToRetrieveValue) {
		try {
			BasicDBObject shipmentItems = (BasicDBObject) ((BasicDBList) erp_invoice_details.get("shipments")).get(0);
			BasicDBList indentItem = (BasicDBList) shipmentItems.get("indents");

			for (Object item : indentItem) {
				try {
					BasicDBObject indent = (BasicDBObject) item;
					if (String.valueOf(indent.get(filterKey)).equals(filterValue)) {
						return indent.get(keyToRetrieveValue).toString();
					}
				} catch (Exception e) {

				}
			}

			return "";
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * To get the value from the invoice using the key
	 * Usage: | getValuesFromInvoicePayload| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getValuesFromInvoicePayload(String keyToRetrieveValue) {
		return erp_invoice_details.get(keyToRetrieveValue);
	}
	
	/**
	 * To get the value from the invoice using the key
	 * Usage: | getValuesFromShipment| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getValuesFromShipment(String keyToRetrieveValue) {
		BasicDBObject shipmentItems = (BasicDBObject) ((BasicDBList) erp_invoice_details.get("shipments")).get(0);
		return shipmentItems.get(keyToRetrieveValue);
	}
	
	/**
	 * To get the payment v1 invoice payload for Marico
	 * Usage: | getPayV1InvoicePostingPayload|invoiceNumber|
	 * @param invoiceNumber
	 * @return payload
	 */
	public static BasicDBObject getPayV1InvoicePostingPayload(String invoiceNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("pay_v1_invoice_posting");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("data.invoice_number", invoiceNumber);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		erp_invoice_details = (BasicDBObject) result;
		return erp_invoice_details;
	}
	
	/**
	 * To get the value from the invoice using the key
	 * Usage: | getValuesFromV1InvoicePayload| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getValuesFromV1InvoicePayload(String keyToRetrieveValue) {
		try {
			BasicDBObject restHeader = (BasicDBObject) erp_invoice_details.get("data");
            return restHeader.get(keyToRetrieveValue).toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * To get the value from the invoice payload using the key
	 * Usage: | getIndentsValuesFromV1InvoicePayload| searchKey| searchValue| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getIndentsValuesFromV1InvoicePayload(String searchKey, String searchValue, String keyToRetrieveValue) {
		try {
			BasicDBObject data = (BasicDBObject) erp_invoice_details.get("data");
			BasicDBList items = (BasicDBList) ((BasicDBList) data.get("indents")).get(0);
			
			for (Object item : items) {
				try {
					BasicDBObject indent =  BasicDBObject.parse(item.toString());
					if (indent.get(searchKey).equals(searchValue)) {
						return indent.get(keyToRetrieveValue).toString();
					}			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
            return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * To get the value from the invoice payload using the key
	 * Usage: | getIndentsValuesFromStrategy1InvoicePayload| searchKey| searchValue| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getIndentsValuesFromStrategy1InvoicePayload(String searchKey, String searchValue, String keyToRetrieveValue) {
		try {
			//BasicDBObject data = (BasicDBObject) erp_invoice_details.get("data");
			BasicDBList items = (BasicDBList) ((BasicDBList) erp_invoice_details.get("indents"));
			
			for (Object item : items) {
				try {
					BasicDBObject indent =  BasicDBObject.parse(item.toString());
					if (String.valueOf(indent.get(searchKey)).equals(searchValue)) {
						return indent.get(keyToRetrieveValue).toString();
					}			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
            return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * To get the value from the invoice payload using the two filters
	 * Usage: | getIndentsValuesFromStrategy1InvoicePayload| searchKey1| searchValue1| searchKey2| searchValue2| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getIndentsValuesFromStrategy1InvoicePayload(String searchKey1, String searchValue1, String searchKey2, String searchValue2, String keyToRetrieveValue) {
		try {
			//BasicDBObject data = (BasicDBObject) erp_invoice_details.get("data");
			BasicDBList items = (BasicDBList) ((BasicDBList) erp_invoice_details.get("indents"));
			
			for (Object item : items) {
				try {
					BasicDBObject indent =  BasicDBObject.parse(item.toString());
					if (String.valueOf(indent.get(searchKey1)).equals(searchValue1) && String.valueOf(indent.get(searchKey2)).equals(searchValue2)) {
						return indent.get(keyToRetrieveValue).toString();
					}			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
            return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * To get the POD to SAP payload using the indent number to search
	 * Usage: | getPodToSapPayload| IndentIdToSearch|
	 * @param IndentIdToSearch
	 * @return
	 */
	public static BasicDBObject getPodToSapPayload(String IndentIdToSearch) {
		DBObject result = null;
		DBCollection table = db.getCollection("pod_sap_flow");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("indent_no", IndentIdToSearch);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		pod_to_sap_payload = (BasicDBObject) result;
		return pod_to_sap_payload;
	}
	
	/**
	 * To get the values from the PODToSAP payload
	 * Usage: | getValuesFromPodToSapPayload| keyToRetrieveValue|
	 * @param keyToRetrieveValue
	 * @return
	 */
	public static Object getValuesFromPodToSapPayload(String keyToRetrieveValue) {
		return (Object) pod_to_sap_payload.get(keyToRetrieveValue);
	}
	
	/**
	 * To get the pickup details from the PODToSAP payload
	 * Usage: | getPickupDetailsFromPodToSapPayload| pickupSearchKey|keyToRetrieveValue|
	 * @param pickupSearchKey
	 * @param keyToRetrieveValue
	 * @return
	 */
	public static Object getPickupDetailsFromPodToSapPayload(String pickupSearchKey, String keyToRetrieveValue) {
		String value = "";
		try {
			BasicDBList pickups = (BasicDBList) pod_to_sap_payload.get("pickups");
			for(Object item : pickups) {
				BasicDBObject pickup = (BasicDBObject) item;
				if(pickup.get("pickup_ref_id").equals(pickupSearchKey)) {
					value = (String) pickup.get(keyToRetrieveValue);
				}
			}
		} catch (Exception e) {
			value = "";
		}
		return value;
	}
	
	/**
	 * To get the consignee details from the PODToSAP payload
	 * Usage: | getConsigneeDetailsFromPodToSapPayload| pickupSearchKey| consigneeSearchKey| keyToRetrieveValue|
	 * @param pickupSearchKey
	 * @param consigneeSearchKey
	 * @param keyValueToRetrieve
	 * @return
	 */
	public static Object getConsigneeDetailsFromPodToSapPayload(String pickupSearchKey, String consigneeSearchKey, String keyValueToRetrieve) {
		String value = "";
		try {
			BasicDBList pickups = (BasicDBList) pod_to_sap_payload.get("pickups");
			for(Object item1 : pickups) {
				BasicDBObject pickup = (BasicDBObject) item1;
				if(pickup.get("pickup_ref_id").equals(pickupSearchKey)) {
					BasicDBList consignees = (BasicDBList) pickup.get("consignees");
					for(Object item2 : consignees) {
						BasicDBObject consignee = (BasicDBObject) item2;
						if(consignee.get("ship_to").equals(consigneeSearchKey)) {
							value = (String) consignee.get(keyValueToRetrieve);
						}
					}
				}
			}
		} catch (Exception e) {
			value = "";
		}
		return value;
	}
	
	/**
	 * To get the material details from the PODToSAP payload
	 * Usage: | getMaterialDetailsFromPodToSapPayload| pickupSearchKey| consigneeSearchKey| deliveryNumberSearchKey | keyToRetrieveValue|
	 * @param pickupSearchKey
	 * @param consigneeSearchKey
	 * @param deliveryNumberSearchKey
	 * @param keyValueToRetrieve
	 * @return
	 */
	public static Object getMaterialDetailsFromPodToSapPayload(String pickupSearchKey, String consigneeSearchKey,
			String deliveryNumberSearchKey, String keyValueToRetrieve) {
		String value = "";
		try {
			BasicDBList pickups = (BasicDBList) pod_to_sap_payload.get("pickups");
			for (Object item1 : pickups) {
				BasicDBObject pickup = (BasicDBObject) item1;
				if (pickup.get("pickup_ref_id").equals(pickupSearchKey)) {
					BasicDBList consignees = (BasicDBList) pickup.get("consignees");
					for (Object item2 : consignees) {
						BasicDBObject consignee = (BasicDBObject) item2;
						if (consignee.get("ship_to").equals(consigneeSearchKey)) {
							BasicDBList materials = (BasicDBList) consignee.get("materials");
							for (Object item3 : materials) {
								BasicDBObject material = (BasicDBObject) item3;
								if (material.get("delivery_number").equals(deliveryNumberSearchKey)) {
									value = (String) material.get(keyValueToRetrieve);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			value = "";
		}
		return value;
	}
	
	/**
	 * To get the EPOD Rejection payload using the indent number search
	 * 
	 * Usage: | getEpodRejectionPayload| IndentIdToSearch|
	 * @param IndentIdToSearch
	 * @return
	 */
	public static BasicDBObject getEpodRejectionPayload(String IndentIdToSearch) {
		DBObject result = null;
		DBCollection table = db.getCollection("epod_rejection");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("data.indents", IndentIdToSearch);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		epod_rejection_payload = (BasicDBObject) result;
		return epod_rejection_payload;
	}
	
	/**
	 * To get the itemModifiedPicklist using the Indent ID and delivery number
	 * Usage: | getTcplModifiedPicklist| indentId| fieldKey| fieldValue|
	 * @param indentId
	 * @param deliveryNumber
	 */
	public static BasicDBObject getTcplModifiedPicklist(String indentId, String fieldKey, String fieldValue) {

		DBObject result = null;
		DBCollection table = db.getCollection("tcpmodifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("asx:abap.asx:values.TAB.item.ZINDENTNO", indentId);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}

		BasicDBObject key = (BasicDBObject) result.get("asx:abap");
		BasicDBObject value = (BasicDBObject) key.get("asx:values");
		BasicDBObject tab = (BasicDBObject) value.get("TAB");
		BasicDBList listItems = null;
		try {
			listItems = (BasicDBList) tab.get("item");

			for (Object item : listItems) {
				try {
					if (((BasicDBObject) item).get("ZINDENTNO").equals(indentId)
							&& ((BasicDBObject) item).get(fieldKey).equals(fieldValue)) {
						itemModifiedPicklist = ((BasicDBObject) item);
						return itemModifiedPicklist;
					}
				} catch (Exception e) {

				}
			}
		} catch (ClassCastException e1) {
			BasicDBObject objectItems = null;
			objectItems = (BasicDBObject) tab.get("item");

			try {
				if (((BasicDBObject) objectItems).get("ZINDENTNO").equals(indentId)
						&& ((BasicDBObject) objectItems).get(fieldKey).equals(fieldValue)) {
					itemModifiedPicklist = ((BasicDBObject) objectItems);
					return itemModifiedPicklist;
				}
			} catch (Exception e) {

			}
		}

		return null;
	}
	
	/**
	 * To get the total getTotalNoOfInvoicePostingSent sent
	 * Usage: | getTotalNoOfInvoicePostingSent| invoiceNumber|
	 * @param invoiceNumber
	 */
	public static int getTotalNoOfStrategy1InvoicePostingSent(String invoiceNumber) {

		DBCollection table = db.getCollection("strategy_1_invoice_posting");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("invoice_number", invoiceNumber);
		
		return table.find(searchQuery).count();
	}
	
	/**
	 * To get the freight provision payload for common
	 * Usage: | getFreightProvisionPayload|indentNumber|
	 * @param invoiceNumber
	 * @return payload
	 */
	public static BasicDBObject getFreightProvisionPayload(String indentNumber) {

		DBObject result = null;
		DBCollection table = db.getCollection("freight_provisioning");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("indent_number", indentNumber);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		erp_invoice_details = (BasicDBObject) result;
		return erp_invoice_details;
	}
	
	/**
	 * To get the total Freight Provision sent
	 * Usage: | getTotalNoOfFreightProvisionPayloadSent| indentNumber|
	 * @param deliveryNumber
	 */
	public static int getTotalNoOfFreightProvisionPayloadSent(String indentNumber) {

		DBCollection table = db.getCollection("freight_provisioning");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("indent_number", indentNumber);
		
		return table.find(searchQuery).count();
	}
	
	/**
	 * To get the value from the Freight provision payload using the key
	 * Usage: | getValuesFromFreightProvisionPayload| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getValuesFromFreightProvisionPayload(String keyToRetrieveValue) {
		return erp_invoice_details.get(keyToRetrieveValue);
	}
	
	/**
	 * To get the material value from the freight provision payload using the key
	 * Usage: | getMaterialValuesFromFreightProvisionPayload| filterKey| filterValue|keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getMaterialValuesFromFreightProvisionPayload(String filterKey, String filterValue, String keyToRetrieveValue) {
		try {
			BasicDBList items = (BasicDBList) erp_invoice_details.get("material");
			
			for (Object item : items) {
				try {
					BasicDBObject indent = ((BasicDBObject) item);
					if (indent.get(filterKey).equals(filterValue)) {
						return indent.get(keyToRetrieveValue).toString();
					}			
				} catch (Exception e) {
					
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * To get the freight bill posting payload for common
	 * Usage: | getFreightBillPostingPayload|searchKey|searchValue|
	 * @param searchKey
	 * @param searchValue
	 * @return payload
	 */
	public static BasicDBObject getFreightBillPostingPayload(String searchKey, String searchValue) {

		DBObject result = null;
		DBCollection table = db.getCollection("freight_bill_posting");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(searchKey, searchValue);

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		erp_invoice_details = (BasicDBObject) result;
		return erp_invoice_details;
	}
	
	/**
	 * To get the total Freight Provision sent
	 * Usage: | getTotalNoOfFreightBillPostingPayloadSent| searchKey|searchValue|
	 * @param searchKey
	 * @param searchValue
	 */
	public static int getTotalNoOfFreightBillPostingPayloadSent(String searchKey, String searchValue) {

		DBCollection table = db.getCollection("freight_bill_posting");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(searchKey, searchValue);
		
		return table.find(searchQuery).count();
	}
	
	/**
	 * To get the value from the Freight provision payload using the key
	 * Usage: | getValuesFromFreightProvisionPayload| keyToRetrieveValue|
	 * @param key
	 * @return value
	 */
	public static Object getValuesFromFreightBillPostingPayload(String keyToRetrieveValue) {
		return erp_invoice_details.get(keyToRetrieveValue);
	}
	
	/**
	 * To get the material value from the freight provision payload using the key
	 * Usage: | getIndentsValuesFromFreightBillPostingPayload| filterKey| filterValue| keyToRetrieveValue|
	 * @param filterKey
	 * @param filterValue
	 * @param keyToRetrieveValue
	 * @return value
	 */
	public static Object getIndentsValuesFromFreightBillPostingPayload(String filterKey, String filterValue, String keyToRetrieveValue) {
		try {
			BasicDBList items = (BasicDBList) erp_invoice_details.get("indents");
			
			for (Object item : items) {
				try {
					BasicDBObject indent = ((BasicDBObject) item);
					if (indent.get(filterKey).equals(filterValue)) {
						return indent.get(keyToRetrieveValue).toString();
					}			
				} catch (Exception e) {
					
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * To get the material value from the freight provision payload using the key
	 * Usage: | getLegalEntitiyFromFreightBillPostingPayload| filterKey| filterValue| keyToRetrieveValue|
	 * @param filterKey
	 * @param filterValue
	 * @param keyToRetrieveValue
	 * @return value
	 */
	public static Object getLegalEntitiyFromFreightBillPostingPayload(String filterKey, String filterValue, String keyToRetrieveValue) {
		try {
			BasicDBList items = (BasicDBList) erp_invoice_details.get("indents");
			
			for (Object item : items) {
				try {
					BasicDBObject indent = ((BasicDBObject) item);
					if (indent.get(filterKey).equals(filterValue)) {
						BasicDBList entities = (BasicDBList) indent.get("legal_entity");
						for (Object entity : entities) {
							return ((BasicDBObject)entity).get(keyToRetrieveValue).toString();
						}
					}			
				} catch (Exception e) {
					
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * To get the material value from the freight provision payload using the key
	 * Usage: | getMaterialDetailsFromFreightBillPostingPayload| filterKey1| filterValue1|filterKey2|filterValue2|keyToRetrieveValue|
	 * @param filterKey1
	 * @param filterValue1
	 * @param filterKey2
	 * @param filterValue2
	 * @param keyToRetrieveValue
	 * @return value
	 */
	public static Object getMaterialDetailsFromFreightBillPostingPayload(String filterKey1, String filterValue1, String filterKey2, String filterValue2, String keyToRetrieveValue) {
		try {
			BasicDBList items = (BasicDBList) erp_invoice_details.get("indents");
			
			for (Object item : items) {
				try {
					BasicDBObject indent = ((BasicDBObject) item);
					if (indent.get(filterKey1).equals(filterValue1)) {
						BasicDBList entities = (BasicDBList) indent.get("legal_entity");
						for (Object entity : entities) {
							BasicDBList materials = (BasicDBList) ((BasicDBObject)entity).get("materials");
							if(materials.isEmpty()) {
								return materials;
							}
							for (Object material : materials) {
								BasicDBObject materialDetails = ((BasicDBObject) material);
								if(materialDetails.get(filterKey2).equals(filterValue2)) {
									return ((BasicDBObject) materialDetails).get(keyToRetrieveValue).toString();
								}
							}
						}
					}			
				} catch (Exception e) {
					
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static BasicDBObject getModifiedPicklistpayload(String indentId) {

		DBObject result = null;
		DBCollection table = db.getCollection("modifiedpicklist");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ModifiedPicklist.ItemModifiedPicklist.indent_id", indentId);
		System.out.println("regfdv+"+searchQuery);
		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			result = cursor.next();
		}
		
		BasicDBObject modifiedPicklist = (BasicDBObject) result;
		System.out.println(modifiedPicklist);
		return modifiedPicklist;
	}
	
	public static double getMaterialDetailsFromFreightBillPostingPayload (String filterKey1, String filterValue1, String filterKey2, String filterValue2, String keyToRetrieveValue, int precision) {
		String value = (String) getMaterialDetailsFromFreightBillPostingPayload(filterKey1, filterValue1, filterKey2, filterValue2, keyToRetrieveValue);
	    return Common.getPrecisionValue(value, precision);
	}
	
	public static double getFreightDetailsValuesFromInvoicePayload (String filterKey1, String filterValue1, String keyToRetrieveValue, int precision) {
		String value = (String) getFreightDetailsValuesFromInvoicePayload (filterKey1, filterValue1, keyToRetrieveValue);
	    return Common.getPrecisionValue(value, precision);
	}
	
	public static double getIndentsValuesFromStrategy1InvoicePayload(String searchKey, String searchValue, String keyToRetrieveValue,int precision) {
		String value = (String) getIndentsValuesFromStrategy1InvoicePayload (searchKey, searchValue, keyToRetrieveValue);
	    return Common.getPrecisionValue(value, precision);
	}	
	
	public static void main(String[] args) {
		getMongoConnection("mongodb+srv://staging-admin:VpbUDlaBkv4vTzJ8@tsm-staging-mlxar.mongodb.net/db_pando_staging?retryWrites=true", "db_pando_testing");
		//getFreightBillPostingPayload("invoice_number", "pg-13030");
		//System.out.println(getMaterialDetailsFromFreightBillPostingPayload("indent_id", "pg-R20220212092210-D-4", "delivery_number", "d420220212092210", "contribution", 1));
		//getStrategy1InvoicePostingPayload("i20220224162935");
		//System.out.println(getIndentsValuesFromStrategy1InvoicePayload("sku", "REVIVE", "invoice_value_freight", 1));
		getFreightBillPostingPayload("invoice_number", "pg-14224");
		System.out.println(getMaterialDetailsFromFreightBillPostingPayload("indent_id", "pg-R20220301132800-D-2", "", "", "id"));
		
	}
	
}