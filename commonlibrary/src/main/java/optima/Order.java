
package optima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Order implements Serializable
{

    @SerializedName("order_no")
    @Expose
    private long orderNo;
    @SerializedName("total_volume")
    @Expose
    private double totalVolume;
    @SerializedName("total_cost")
    @Expose
    private double totalCost;
    @SerializedName("utilization_weight")
    @Expose
    private double utilizationWeight;
    @SerializedName("vehicle_breadth")
    @Expose
    private double vehicleBreadth;
    @SerializedName("vehicle_length")
    @Expose
    private double vehicleLength;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("total_weight")
    @Expose
    private double totalWeight;
    @SerializedName("freight_rate")
    @Expose
    private double freightRate;
    @SerializedName("vname")
    @Expose
    private String vname;
    @SerializedName("vehicle_height")
    @Expose
    private double vehicleHeight;
    @SerializedName("calculated_oda_charges")
    @Expose
    private double calculatedOdaCharges;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("no_of_extra_drops")
    @Expose
    private long noOfExtraDrops;
    @SerializedName("calculated_additional_charges")
    @Expose
    private double calculatedAdditionalCharges;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("utilization_volume")
    @Expose
    private double utilizationVolume;
    @SerializedName("freight_rate_unit")
    @Expose
    private String freightRateUnit;
    @SerializedName("pickup_points")
    @Expose
    private List<PickupPoint> pickupPoints = new ArrayList<PickupPoint>();
    private final static long serialVersionUID = -8323701917607253531L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Order() {
    }

    /**
     * 
     * @param totalVolume
     * @param vehicleBreadth
     * @param orderNo
     * @param pickupPoints
     * @param freightRateUnit
     * @param destination
     * @param vendorId
     * @param source
     * @param calculatedAdditionalCharges
     * @param utilizationWeight
     * @param utilizationVolume
     * @param freightRate
     * @param vehicleLength
     * @param vname
     * @param totalWeight
     * @param vehicleHeight
     * @param noOfExtraDrops
     * @param vehicleId
     * @param calculatedOdaCharges
     * @param totalCost
     */
    public Order(long orderNo, double totalVolume, double totalCost, double utilizationWeight, double vehicleBreadth, double vehicleLength, String destination, String source, double totalWeight, double freightRate, String vname, double vehicleHeight, double calculatedOdaCharges, String vendorId, long noOfExtraDrops, double calculatedAdditionalCharges, String vehicleId, double utilizationVolume, String freightRateUnit, List<PickupPoint> pickupPoints) {
        super();
        this.orderNo = orderNo;
        this.totalVolume = totalVolume;
        this.totalCost = totalCost;
        this.utilizationWeight = utilizationWeight;
        this.vehicleBreadth = vehicleBreadth;
        this.vehicleLength = vehicleLength;
        this.destination = destination;
        this.source = source;
        this.totalWeight = totalWeight;
        this.freightRate = freightRate;
        this.vname = vname;
        this.vehicleHeight = vehicleHeight;
        this.calculatedOdaCharges = calculatedOdaCharges;
        this.vendorId = vendorId;
        this.noOfExtraDrops = noOfExtraDrops;
        this.calculatedAdditionalCharges = calculatedAdditionalCharges;
        this.vehicleId = vehicleId;
        this.utilizationVolume = utilizationVolume;
        this.freightRateUnit = freightRateUnit;
        this.pickupPoints = pickupPoints;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public Order withOrderNo(long orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Order withTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
        return this;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Order withTotalCost(double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public double getUtilizationWeight() {
        return utilizationWeight;
    }

    public void setUtilizationWeight(double utilizationWeight) {
        this.utilizationWeight = utilizationWeight;
    }

    public Order withUtilizationWeight(double utilizationWeight) {
        this.utilizationWeight = utilizationWeight;
        return this;
    }

    public double getVehicleBreadth() {
        return vehicleBreadth;
    }

    public void setVehicleBreadth(double vehicleBreadth) {
        this.vehicleBreadth = vehicleBreadth;
    }

    public Order withVehicleBreadth(double vehicleBreadth) {
        this.vehicleBreadth = vehicleBreadth;
        return this;
    }

    public double getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(double vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public Order withVehicleLength(double vehicleLength) {
        this.vehicleLength = vehicleLength;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Order withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Order withSource(String source) {
        this.source = source;
        return this;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Order withTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
        return this;
    }

    public double getFreightRate() {
        return freightRate;
    }

    public void setFreightRate(double freightRate) {
        this.freightRate = freightRate;
    }

    public Order withFreightRate(double freightRate) {
        this.freightRate = freightRate;
        return this;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public Order withVname(String vname) {
        this.vname = vname;
        return this;
    }

    public double getVehicleHeight() {
        return vehicleHeight;
    }

    public void setVehicleHeight(double vehicleHeight) {
        this.vehicleHeight = vehicleHeight;
    }

    public Order withVehicleHeight(double vehicleHeight) {
        this.vehicleHeight = vehicleHeight;
        return this;
    }

    public double getCalculatedOdaCharges() {
        return calculatedOdaCharges;
    }

    public void setCalculatedOdaCharges(double calculatedOdaCharges) {
        this.calculatedOdaCharges = calculatedOdaCharges;
    }

    public Order withCalculatedOdaCharges(double calculatedOdaCharges) {
        this.calculatedOdaCharges = calculatedOdaCharges;
        return this;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public Order withVendorId(String vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public long getNoOfExtraDrops() {
        return noOfExtraDrops;
    }

    public void setNoOfExtraDrops(long noOfExtraDrops) {
        this.noOfExtraDrops = noOfExtraDrops;
    }

    public Order withNoOfExtraDrops(long noOfExtraDrops) {
        this.noOfExtraDrops = noOfExtraDrops;
        return this;
    }

    public double getCalculatedAdditionalCharges() {
        return calculatedAdditionalCharges;
    }

    public void setCalculatedAdditionalCharges(double calculatedAdditionalCharges) {
        this.calculatedAdditionalCharges = calculatedAdditionalCharges;
    }

    public Order withCalculatedAdditionalCharges(double calculatedAdditionalCharges) {
        this.calculatedAdditionalCharges = calculatedAdditionalCharges;
        return this;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Order withVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public double getUtilizationVolume() {
        return utilizationVolume;
    }

    public void setUtilizationVolume(double utilizationVolume) {
        this.utilizationVolume = utilizationVolume;
    }

    public Order withUtilizationVolume(double utilizationVolume) {
        this.utilizationVolume = utilizationVolume;
        return this;
    }

    public String getFreightRateUnit() {
        return freightRateUnit;
    }

    public void setFreightRateUnit(String freightRateUnit) {
        this.freightRateUnit = freightRateUnit;
    }

    public Order withFreightRateUnit(String freightRateUnit) {
        this.freightRateUnit = freightRateUnit;
        return this;
    }

    public List<PickupPoint> getPickupPoints() {
        return pickupPoints;
    }

    public void setPickupPoints(List<PickupPoint> pickupPoints) {
        this.pickupPoints = pickupPoints;
    }

    public Order withPickupPoints(List<PickupPoint> pickupPoints) {
        this.pickupPoints = pickupPoints;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("orderNo", orderNo).append("totalVolume", totalVolume).append("totalCost", totalCost).append("utilizationWeight", utilizationWeight).append("vehicleBreadth", vehicleBreadth).append("vehicleLength", vehicleLength).append("destination", destination).append("source", source).append("totalWeight", totalWeight).append("freightRate", freightRate).append("vname", vname).append("vehicleHeight", vehicleHeight).append("calculatedOdaCharges", calculatedOdaCharges).append("vendorId", vendorId).append("noOfExtraDrops", noOfExtraDrops).append("calculatedAdditionalCharges", calculatedAdditionalCharges).append("vehicleId", vehicleId).append("utilizationVolume", utilizationVolume).append("freightRateUnit", freightRateUnit).append("pickupPoints", pickupPoints).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(totalVolume).append(vehicleBreadth).append(orderNo).append(pickupPoints).append(freightRateUnit).append(destination).append(vendorId).append(source).append(calculatedAdditionalCharges).append(utilizationWeight).append(utilizationVolume).append(freightRate).append(vehicleLength).append(vname).append(totalWeight).append(vehicleHeight).append(noOfExtraDrops).append(vehicleId).append(calculatedOdaCharges).append(totalCost).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Order) == false) {
            return false;
        }
        Order rhs = ((Order) other);
        return new EqualsBuilder().append(totalVolume, rhs.totalVolume).append(vehicleBreadth, rhs.vehicleBreadth).append(orderNo, rhs.orderNo).append(pickupPoints, rhs.pickupPoints).append(freightRateUnit, rhs.freightRateUnit).append(destination, rhs.destination).append(vendorId, rhs.vendorId).append(source, rhs.source).append(calculatedAdditionalCharges, rhs.calculatedAdditionalCharges).append(utilizationWeight, rhs.utilizationWeight).append(utilizationVolume, rhs.utilizationVolume).append(freightRate, rhs.freightRate).append(vehicleLength, rhs.vehicleLength).append(vname, rhs.vname).append(totalWeight, rhs.totalWeight).append(vehicleHeight, rhs.vehicleHeight).append(noOfExtraDrops, rhs.noOfExtraDrops).append(vehicleId, rhs.vehicleId).append(calculatedOdaCharges, rhs.calculatedOdaCharges).append(totalCost, rhs.totalCost).isEquals();
    }

}
