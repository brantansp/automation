
package optima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PickupPoint implements Serializable
{

    @SerializedName("consignee_list")
    @Expose
    private List<ConsigneeList> consigneeList = new ArrayList<ConsigneeList>();
    @SerializedName("pickup_id")
    @Expose
    private String pickupId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    private final static long serialVersionUID = 6852527923224042208L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PickupPoint() {
    }

    /**
     * 
     * @param consigneeList
     * @param _long
     * @param pickupId
     * @param lat
     */
    public PickupPoint(List<ConsigneeList> consigneeList, String pickupId, String lat, String _long) {
        super();
        this.consigneeList = consigneeList;
        this.pickupId = pickupId;
        this.lat = lat;
        this._long = _long;
    }

    public List<ConsigneeList> getConsigneeList() {
        return consigneeList;
    }

    public void setConsigneeList(List<ConsigneeList> consigneeList) {
        this.consigneeList = consigneeList;
    }

    public PickupPoint withConsigneeList(List<ConsigneeList> consigneeList) {
        this.consigneeList = consigneeList;
        return this;
    }

    public String getPickupId() {
        return pickupId;
    }

    public void setPickupId(String pickupId) {
        this.pickupId = pickupId;
    }

    public PickupPoint withPickupId(String pickupId) {
        this.pickupId = pickupId;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public PickupPoint withLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public PickupPoint withLong(String _long) {
        this._long = _long;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("consigneeList", consigneeList).append("pickupId", pickupId).append("lat", lat).append("_long", _long).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pickupId).append(consigneeList).append(lat).append(_long).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PickupPoint) == false) {
            return false;
        }
        PickupPoint rhs = ((PickupPoint) other);
        return new EqualsBuilder().append(pickupId, rhs.pickupId).append(consigneeList, rhs.consigneeList).append(lat, rhs.lat).append(_long, rhs._long).isEquals();
    }

}
