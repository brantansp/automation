
package optima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OutputJsonParser implements Serializable
{

    @SerializedName("total_volume")
    @Expose
    private double totalVolume;
    @SerializedName("total_cost")
    @Expose
    private double totalCost;
    @SerializedName("OPTIMA EXECUTION ERROR")
    @Expose
    private String oPTIMAEXECUTIONERROR;
    @SerializedName("total_input_weight")
    @Expose
    private double totalInputWeight;
    @SerializedName("rejection_list")
    @Expose
    private List<RejectionList> rejectionList = new ArrayList<RejectionList>();
    @SerializedName("total_orders")
    @Expose
    private long totalOrders;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("total_weight")
    @Expose
    private double totalWeight;
    @SerializedName("total_input_volume")
    @Expose
    private double totalInputVolume;
    @SerializedName("depot_id")
    @Expose
    private String depotId;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = new ArrayList<Order>();
    @SerializedName("average_utilization_weight")
    @Expose
    private double averageUtilizationWeight;
    @SerializedName("average_utilization_volume")
    @Expose
    private double averageUtilizationVolume;
    private final static long serialVersionUID = 1532691811758519915L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OutputJsonParser() {
    }

    /**
     * 
     * @param totalVolume
     * @param averageUtilizationWeight
     * @param rejectionList
     * @param clientId
     * @param totalInputWeight
     * @param depotId
     * @param averageUtilizationVolume
     * @param totalInputVolume
     * @param oPTIMAEXECUTIONERROR
     * @param totalWeight
     * @param orders
     * @param totalOrders
     * @param totalCost
     */
    public OutputJsonParser(double totalVolume, double totalCost, String oPTIMAEXECUTIONERROR, double totalInputWeight, List<RejectionList> rejectionList, long totalOrders, String clientId, double totalWeight, double totalInputVolume, String depotId, List<Order> orders, double averageUtilizationWeight, double averageUtilizationVolume) {
        super();
        this.totalVolume = totalVolume;
        this.totalCost = totalCost;
        this.oPTIMAEXECUTIONERROR = oPTIMAEXECUTIONERROR;
        this.totalInputWeight = totalInputWeight;
        this.rejectionList = rejectionList;
        this.totalOrders = totalOrders;
        this.clientId = clientId;
        this.totalWeight = totalWeight;
        this.totalInputVolume = totalInputVolume;
        this.depotId = depotId;
        this.orders = orders;
        this.averageUtilizationWeight = averageUtilizationWeight;
        this.averageUtilizationVolume = averageUtilizationVolume;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public OutputJsonParser withTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
        return this;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public OutputJsonParser withTotalCost(double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public String getOPTIMAEXECUTIONERROR() {
        return oPTIMAEXECUTIONERROR;
    }

    public void setOPTIMAEXECUTIONERROR(String oPTIMAEXECUTIONERROR) {
        this.oPTIMAEXECUTIONERROR = oPTIMAEXECUTIONERROR;
    }

    public OutputJsonParser withOPTIMAEXECUTIONERROR(String oPTIMAEXECUTIONERROR) {
        this.oPTIMAEXECUTIONERROR = oPTIMAEXECUTIONERROR;
        return this;
    }

    public double getTotalInputWeight() {
        return totalInputWeight;
    }

    public void setTotalInputWeight(double totalInputWeight) {
        this.totalInputWeight = totalInputWeight;
    }

    public OutputJsonParser withTotalInputWeight(double totalInputWeight) {
        this.totalInputWeight = totalInputWeight;
        return this;
    }

    public List<RejectionList> getRejectionList() {
        return rejectionList;
    }

    public void setRejectionList(List<RejectionList> rejectionList) {
        this.rejectionList = rejectionList;
    }

    public OutputJsonParser withRejectionList(List<RejectionList> rejectionList) {
        this.rejectionList = rejectionList;
        return this;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public OutputJsonParser withTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public OutputJsonParser withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public OutputJsonParser withTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
        return this;
    }

    public double getTotalInputVolume() {
        return totalInputVolume;
    }

    public void setTotalInputVolume(double totalInputVolume) {
        this.totalInputVolume = totalInputVolume;
    }

    public OutputJsonParser withTotalInputVolume(double totalInputVolume) {
        this.totalInputVolume = totalInputVolume;
        return this;
    }

    public String getDepotId() {
        return depotId;
    }

    public void setDepotId(String depotId) {
        this.depotId = depotId;
    }

    public OutputJsonParser withDepotId(String depotId) {
        this.depotId = depotId;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public OutputJsonParser withOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public double getAverageUtilizationWeight() {
        return averageUtilizationWeight;
    }

    public void setAverageUtilizationWeight(double averageUtilizationWeight) {
        this.averageUtilizationWeight = averageUtilizationWeight;
    }

    public OutputJsonParser withAverageUtilizationWeight(double averageUtilizationWeight) {
        this.averageUtilizationWeight = averageUtilizationWeight;
        return this;
    }

    public double getAverageUtilizationVolume() {
        return averageUtilizationVolume;
    }

    public void setAverageUtilizationVolume(double averageUtilizationVolume) {
        this.averageUtilizationVolume = averageUtilizationVolume;
    }

    public OutputJsonParser withAverageUtilizationVolume(double averageUtilizationVolume) {
        this.averageUtilizationVolume = averageUtilizationVolume;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("totalVolume", totalVolume).append("totalCost", totalCost).append("oPTIMAEXECUTIONERROR", oPTIMAEXECUTIONERROR).append("totalInputWeight", totalInputWeight).append("rejectionList", rejectionList).append("totalOrders", totalOrders).append("clientId", clientId).append("totalWeight", totalWeight).append("totalInputVolume", totalInputVolume).append("depotId", depotId).append("orders", orders).append("averageUtilizationWeight", averageUtilizationWeight).append("averageUtilizationVolume", averageUtilizationVolume).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(totalVolume).append(averageUtilizationWeight).append(rejectionList).append(clientId).append(totalInputWeight).append(depotId).append(averageUtilizationVolume).append(totalInputVolume).append(oPTIMAEXECUTIONERROR).append(totalWeight).append(orders).append(totalOrders).append(totalCost).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OutputJsonParser) == false) {
            return false;
        }
        OutputJsonParser rhs = ((OutputJsonParser) other);
        return new EqualsBuilder().append(totalVolume, rhs.totalVolume).append(averageUtilizationWeight, rhs.averageUtilizationWeight).append(rejectionList, rhs.rejectionList).append(clientId, rhs.clientId).append(totalInputWeight, rhs.totalInputWeight).append(depotId, rhs.depotId).append(averageUtilizationVolume, rhs.averageUtilizationVolume).append(totalInputVolume, rhs.totalInputVolume).append(oPTIMAEXECUTIONERROR, rhs.oPTIMAEXECUTIONERROR).append(totalWeight, rhs.totalWeight).append(orders, rhs.orders).append(totalOrders, rhs.totalOrders).append(totalCost, rhs.totalCost).isEquals();
    }

}
