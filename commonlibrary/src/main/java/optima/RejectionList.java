
package optima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RejectionList implements Serializable
{

    @SerializedName("materials_list")
    @Expose
    private List<MaterialsList> materialsList = new ArrayList<MaterialsList>();
    @SerializedName("sold_id")
    @Expose
    private String soldId;
    @SerializedName("pickup_id")
    @Expose
    private String pickupId;
    @SerializedName("cons_id")
    @Expose
    private String consId;
    private final static long serialVersionUID = -2183865444925000180L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RejectionList() {
    }

    /**
     * 
     * @param materialsList
     * @param soldId
     * @param consId
     * @param pickupId
     */
    public RejectionList(List<MaterialsList> materialsList, String soldId, String pickupId, String consId) {
        super();
        this.materialsList = materialsList;
        this.soldId = soldId;
        this.pickupId = pickupId;
        this.consId = consId;
    }

    public List<MaterialsList> getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(List<MaterialsList> materialsList) {
        this.materialsList = materialsList;
    }

    public RejectionList withMaterialsList(List<MaterialsList> materialsList) {
        this.materialsList = materialsList;
        return this;
    }

    public String getSoldId() {
        return soldId;
    }

    public void setSoldId(String soldId) {
        this.soldId = soldId;
    }

    public RejectionList withSoldId(String soldId) {
        this.soldId = soldId;
        return this;
    }

    public String getPickupId() {
        return pickupId;
    }

    public void setPickupId(String pickupId) {
        this.pickupId = pickupId;
    }

    public RejectionList withPickupId(String pickupId) {
        this.pickupId = pickupId;
        return this;
    }

    public String getConsId() {
        return consId;
    }

    public void setConsId(String consId) {
        this.consId = consId;
    }

    public RejectionList withConsId(String consId) {
        this.consId = consId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("materialsList", materialsList).append("soldId", soldId).append("pickupId", pickupId).append("consId", consId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(materialsList).append(soldId).append(pickupId).append(consId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RejectionList) == false) {
            return false;
        }
        RejectionList rhs = ((RejectionList) other);
        return new EqualsBuilder().append(materialsList, rhs.materialsList).append(soldId, rhs.soldId).append(pickupId, rhs.pickupId).append(consId, rhs.consId).isEquals();
    }

}
