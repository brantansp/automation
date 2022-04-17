package optima;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;

public class QueryOptimaOutput {

	static OutputJsonParser result = null;

	/**
	 * OutputJsonParser to get the parsed output json file
	 * Usage: |getParsedJson| filePath|
	 * @param filePath
	 * @return
	 */
	public static void getParsedJson(String filePath) {
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			result = gson.fromJson(br, OutputJsonParser.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * To get the total number of orders formed
	 * Usage: | getTotalNumberOfOrders|
	 * @return
	 */
	public static int getTotalNumberOfOrders() {
		return (int) result.getTotalOrders();
	}

	/**
	 * To get the cost of an Order containing particular consignee
	 * Usage: |getTotalCostOfAnOrder| consignee|
	 * @param consignee
	 * @return
	 */
	public static int getTotalCostOfAnOrder(String consignee) {
		List<Order> orders = result.getOrders();
		int cost = 0;
		for (Order order : orders) {
			for (PickupPoint pickup : order.getPickupPoints()) {
				for (ConsigneeList consignees : pickup.getConsigneeList()) {
					if (consignees.getRefId().equals(consignee)) {
						cost = (int) order.getTotalCost();
					}
				}
			}

		}
		return cost;
	}

	/**
	 * To get the cost of incurred for an Consignee
	 * Usage: |getAllCostsForAnConsignee| consignee|
	 * @param consignee
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> getAllCostsForAnConsignee(String consignee) {
		List<Order> orders = result.getOrders();
		ArrayList<Integer> cost = new ArrayList<Integer>();
		for (Order order : orders) {
			for (PickupPoint pickup : order.getPickupPoints()) {
				for (ConsigneeList consignees : pickup.getConsigneeList()) {
					if (consignees.getRefId().equals(consignee)) {
						cost.add((int) order.getTotalCost());
					}
				}
			}

		}
		return cost;
	}

	/**
	 * To get the cost of a All order
	 * Usage: |getTotalCostOfAllOrders| consignee|
	 * @param consignee
	 * @return
	 */
	public static int getTotalCostOfAllOrders(String consignee) {
		return (int) result.getTotalCost();
	}

	/**
	 * To check if delivery number is in rejection list or not
	 * Usage: |isDeliveryInRejections| deliveryNumber|
	 * @param deliveryNumber
	 * @return
	 */
	public static boolean isDeliveryInRejections(String deliveryNumber) {

		boolean isPresent = false;

		for (RejectionList rejections : result.getRejectionList()) {
			for (MaterialsList materials : rejections.getMaterialsList()) {
				if (deliveryNumber.equalsIgnoreCase(materials.getDeliveryNo())) {
					isPresent = true;
				}
			}
		}
		return isPresent;
	}

	/**
	 * To get the weight of the dropped material
	 * Usage: |getMaterialWeightInRejections| deliveryNumber| materialCode|
	 * @param deliveryNumber
	 * @param materialCode
	 * @return weight
	 */
	public static double getMaterialWeightInRejections(String deliveryNumber, String materialCode) {
		double weight = 0;
		for (RejectionList rejections : result.getRejectionList()) {
			for (MaterialsList materials : rejections.getMaterialsList()) {
				if (deliveryNumber.equals(materials.getDeliveryNo()) && materialCode.equals(materials.getCode())) {
					weight = materials.getWeight();
				}
			}
		}
		return weight;
	}

	/**
	 * To get the volume of the dropped material
	 * Usage: |getMaterialVolumeInRejections| deliveryNumber| materialCode|
	 * @param deliveryNumber
	 * @param materialCode
	 * @return volume
	 */
	public static double getMaterialVolumeInRejections(String deliveryNumber, String materialCode) {
		double volume = 0;
		for (RejectionList rejections : result.getRejectionList()) {
			for (MaterialsList materials : rejections.getMaterialsList()) {
				if (deliveryNumber.equals(materials.getDeliveryNo()) && materialCode.equals(materials.getCode())) {
					volume = materials.getVolume();
				}
			}
		}
		return volume;
	}

	/**
	 * To check if the consignees are all combined in same Order
	 * Usage: |areConsigneesInSameOrder| consigneesArray |
	 * @param consignees
	 * @return
	 */
	public static boolean areConsigneesInSameOrder(String[] consignees) {

		boolean areConsigneesInSameOrder = false;

		for (Order order : result.getOrders()) {
			HashSet<String> consigneesInOrder = new HashSet<String>();
			for (PickupPoint pickUpPoint : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickUpPoint.getConsigneeList()) {
					if (!consigneesInOrder.contains(consignee.getRefId())) {
						consigneesInOrder.add(consignee.getRefId());
					}
				}
			}

			for (String consignee : consignees) {
				if (!consigneesInOrder.contains(consignee)) {
					areConsigneesInSameOrder = false;
					break;
				} else {
					areConsigneesInSameOrder = true;
				}
			}

			if (areConsigneesInSameOrder == true) {
				break;
			}
		}
		return areConsigneesInSameOrder;
	}

	/**
	 * To check if the Maximum truck utilization exceeds for any of the Orders
	 * Usage: |checkIfMaxTruckUtilizationExceeds|
	 * @return
	 */
	public static boolean checkIfMaxTruckUtilizationExceeds() {

		boolean isMaxTruckUtilExceeds = false;

		if (result.getAverageUtilizationWeight() < 100 && result.getAverageUtilizationVolume() < 100) {

			for (Order order : result.getOrders()) {
				if (order.getUtilizationWeight() > 100 || order.getUtilizationVolume() > 100) {
					isMaxTruckUtilExceeds = true;
					break;
				}
			}
		} else {
			isMaxTruckUtilExceeds = true;
		}

		return isMaxTruckUtilExceeds;
	}

	/**
	 * To get the total input weight
	 * Usage: |getTotalInputWeight|
	 * @return
	 */
	public static int getTotalInputWeight() {
		return (int) result.getTotalInputWeight();
	}

	/**
	 * To get the total input volume
	 * Usage: |getTotalInputVolume|
	 * @return
	 */
	public static int getTotalInputVolume() {
		return (int) result.getTotalInputVolume();
	}

	/**
	 * To get the total output weight
	 * Usage: |getTotalOutputWeight|
	 * @return
	 */
	public static int getTotalOutputWeight() {
		return (int) result.getTotalWeight();
	}

	/**
	 * To get the total output volume
	 * Usage: |getTotalOutputVolume|
	 * @return
	 */
	public static int getTotalOutputVolume() {
		return (int) result.getTotalVolume();
	}

	/**
	 * To get the Depot Id
	 * Usage: |getDepotId|
	 * @return
	 */
	public static String getDepotId() {
		return result.getDepotId();
	}

	/**
	 * To get the total average weight utilized
	 * Usage: |totalAverageWeightUtilized|
	 * @return
	 */
	public static int totalAverageWeightUtilized() {
		return (int) result.getAverageUtilizationWeight();
	}

	/**
	 * To get the total average volume utilized
	 * Usage: | totalAverageVolumeUtilized|
	 * @return
	 */
	public static int totalAverageVolumeUtilized() {
		return (int) result.getAverageUtilizationWeight();

	}

	/**
	 * To get the total number of orders formed for the Consignee
	 * Usage: |getTotalNumberOfOrdersFormedForTheConsignee| consigneeToCheck|
	 * @param consigneeToCheck
	 * @return
	 */
	public static int getTotalNumberOfOrdersFormedForTheConsignee(String consigneeToCheck) {
		int totalNumberOfOrders = 0;

		for (Order order : result.getOrders()) {
			for (PickupPoint pickUpPoint : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickUpPoint.getConsigneeList()) {
					if (consignee.getRefId().equals(consigneeToCheck)) {
						totalNumberOfOrders = totalNumberOfOrders + 1;
					}
				}
			}
		}

		return totalNumberOfOrders;
	}

	/**
	 * To check whether PTL truck is assigned for the Consignee based on truck's
	 * capacity(i.e. x==100%)
	 * Usage: |isPtlTruckAssignedForTheConsignee| consigneeToCheck|
	 * @param consigneeToCheck
	 * @return
	 */
	public static boolean isPtlTruckAssignedForTheConsignee(String consigneeToCheck) {

		for (Order order : result.getOrders()) {
			for (PickupPoint pickUpPoint : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickUpPoint.getConsigneeList()) {
					if (consignee.getRefId().equals(consigneeToCheck)) {
						if (order.getUtilizationWeight() == 100 && order.getUtilizationVolume() == 100) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * To check whether FTL truck is assigned for the Consignee based on trucks
	 * capacity(i.e. x<100%)
	 * Usage: |isFtlTruckAssignedForTheConsignee| consigneeToCheck|
	 * @param consigneeToCheck
	 * @return
	 */
	public static boolean isFtlTruckAssignedForTheConsignee(String consigneeToCheck) {

		for (Order order : result.getOrders()) {
			for (PickupPoint pickUpPoint : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickUpPoint.getConsigneeList()) {
					if (consignee.getRefId().equals(consigneeToCheck)) {
						if (order.getUtilizationWeight() < 100 && order.getUtilizationVolume() < 100) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * To check if the given truck is assigned to the Consignee based on the truck
	 * Id
	 * Usage: |isThisTruckAssignedForTheConsignee|consigneeToCheck|truckId|
	 * @param truckId
	 * @param consigneeToCheck
	 * @return
	 */
	public static boolean isThisTruckAssignedForTheConsignee(String consigneeToCheck, String truckId) {

		for (Order order : result.getOrders()) {
			for (PickupPoint pickUpPoint : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickUpPoint.getConsigneeList()) {
					if (consignee.getRefId().equals(consigneeToCheck)) {
						if (order.getVehicleId().equals(truckId)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * To get the total material quantity of material for the consignee id
	 * Usage: |getTotalMaterialQuantityForTheConsignee|consigneeToCheck|
	 * 
	 * @param consigneeToCheck
	 * @return
	 */
	public static int getTotalMaterialQuantityForTheConsignee(String consigneeToCheck) {

		int countOfMaterials = 0;

		for (Order order : result.getOrders()) {
			for (PickupPoint pickUpPoint : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickUpPoint.getConsigneeList()) {
					if (consignee.getRefId().equals(consigneeToCheck)) {
						for (MaterialsList_ material : consignee.getMaterialsList()) {
							countOfMaterials = countOfMaterials + (int) material.getQuantity();
						}
					}
				}
			}
		}
		return countOfMaterials;
	}
	
	/**
	 * To get the total rejected material weight for the delivery number
	 * Usage: |getTotalRejectedMaterialWeightForDelivery|deliveryNumberToCheck|
	 * 
	 * @param deliveryNumberToCheck
	 * @return
	 */
	public static int getTotalRejectedMaterialWeightForDelivery(String deliveryNumberToCheck) {

		int totalRejectedMaterialWeight = 0;

		for (RejectionList rejections : result.getRejectionList()) {
			for (MaterialsList materials : rejections.getMaterialsList()) {
				if (materials.getDeliveryNo().equals(deliveryNumberToCheck)) {
					totalRejectedMaterialWeight = (int) (totalRejectedMaterialWeight + materials.getWeight());
				}
			}
		}

		return totalRejectedMaterialWeight;
	}
	
	/**
	 * To get the total rejected material volume for the delivery number
	 * Usage: |getTotalRejectedMaterialVolumeForDelivery|deliveryNumberToCheck|
	 * 
	 * @param deliveryNumberToCheck
	 * @return
	 */
	public static int getTotalRejectedMaterialVolumeForDelivery(String deliveryNumberToCheck) {

		int totalRejectedMaterialVolume = 0;

		for (RejectionList rejections : result.getRejectionList()) {
			for (MaterialsList materials : rejections.getMaterialsList()) {
				if (materials.getDeliveryNo().equals(deliveryNumberToCheck)) {
					totalRejectedMaterialVolume = (int) (totalRejectedMaterialVolume + materials.getVolume());
				}
			}
		}

		return totalRejectedMaterialVolume;
	}
	
	/**
	 * To get the total rejected material quantity for the delivery number
	 * Usage: |getTotalRejectedMaterialQuantityForDelivery|deliveryNumberToCheck|
	 * 
	 * @param deliveryNumberToCheck
	 * @return
	 */
	public static int getTotalRejectedMaterialQuantityForDelivery(String deliveryNumberToCheck) {

		int totalRejectedMaterialQuantity = 0;

		for (RejectionList rejections : result.getRejectionList()) {
			for (MaterialsList materials : rejections.getMaterialsList()) {
				if (materials.getDeliveryNo().equals(deliveryNumberToCheck)) {
					totalRejectedMaterialQuantity = (int) (totalRejectedMaterialQuantity + materials.getQuantity());
				}
			}
		}

		return totalRejectedMaterialQuantity;
	}
	
	/**
	 * To get the total material weight for the delivery number
	 * Usage: |getTotalMaterialWeightForDelivery|deliveryNumberToCheck|
	 * @param deliveryNumberToCheck
	 * @return totalMaterialWeight
	 */
	public static double getTotalMaterialWeightForDelivery(String deliveryNumberToCheck) {
		
		double totalMaterialWeight = 0;
		
		for (Order order : result.getOrders()) {
			for (PickupPoint pickup : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickup.getConsigneeList()) {
					for (MaterialsList_ material : consignee.getMaterialsList()) {
						if (material.getDeliveryNo().equals(deliveryNumberToCheck)) {
							totalMaterialWeight = totalMaterialWeight+material.getWeight();
						}
					}
				}
			}
			
		}
		return totalMaterialWeight;
	}
	
	/**
	 * To get the total material volume for the delivery number
	 * Usage: |getTotalMaterialVolumeForDelivery|deliveryNumberToCheck|
	 * @param deliveryNumberToCheck
	 * @return tatalMaterialVolume
	 */
	public static double getTotalMaterialVolumeForDelivery(String deliveryNumberToCheck) {
		
		double totalMaterialVolume = 0;
		
		for (Order order : result.getOrders()) {
			for (PickupPoint pickup : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickup.getConsigneeList()) {
					for (MaterialsList_ material : consignee.getMaterialsList()) {
						if (material.getDeliveryNo().equals(deliveryNumberToCheck)) {
							totalMaterialVolume = totalMaterialVolume+material.getVolume();
						}
					}
				}
			}
			
		}
		return totalMaterialVolume;
	}
	
	/**
	 * To get the total material quantity for the delivery number
	 * Usage: |getTotalMaterialQuantityForDelivery|deliveryNumberToCheck|
	 * @param deliveryNumberToCheck
	 * @return totalMaterialQuantity
	 */
	public static double getTotalMaterialQuantityForDelivery(String deliveryNumberToCheck) {
		
		double totalMaterialQuantity= 0;
		
		for (Order order : result.getOrders()) {
			for (PickupPoint pickup : order.getPickupPoints()) {
				for (ConsigneeList consignee : pickup.getConsigneeList()) {
					for (MaterialsList_ material : consignee.getMaterialsList()) {
						if (material.getDeliveryNo().equals(deliveryNumberToCheck)) {
							totalMaterialQuantity = totalMaterialQuantity+material.getQuantity();
						}
					}
				}
			}
			
		}
		return totalMaterialQuantity;
	}
}


