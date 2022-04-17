
package optima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ConsigneeList implements Serializable
{

    @SerializedName("ref_id")
    @Expose
    private String refId;
    @SerializedName("materials_list")
    @Expose
    private List<MaterialsList_> materialsList = new ArrayList<MaterialsList_>();
    @SerializedName("sold_id")
    @Expose
    private String soldId;
    @SerializedName("distance")
    @Expose
    private double distance;
    @SerializedName("index")
    @Expose
    private long index;
    @SerializedName("angle")
    @Expose
    private double angle;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("cons_id")
    @Expose
    private String consId;
    private final static long serialVersionUID = 5778173475214399122L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ConsigneeList() {
    }

    /**
     * 
     * @param materialsList
     * @param soldId
     * @param consId
     * @param distance
     * @param _long
     * @param index
     * @param angle
     * @param refId
     * @param category
     * @param lat
     */
    public ConsigneeList(String refId, List<MaterialsList_> materialsList, String soldId, double distance, long index, double angle, String category, String lat, String _long, String consId) {
        super();
        this.refId = refId;
        this.materialsList = materialsList;
        this.soldId = soldId;
        this.distance = distance;
        this.index = index;
        this.angle = angle;
        this.category = category;
        this.lat = lat;
        this._long = _long;
        this.consId = consId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public ConsigneeList withRefId(String refId) {
        this.refId = refId;
        return this;
    }

    public List<MaterialsList_> getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(List<MaterialsList_> materialsList) {
        this.materialsList = materialsList;
    }

    public ConsigneeList withMaterialsList(List<MaterialsList_> materialsList) {
        this.materialsList = materialsList;
        return this;
    }

    public String getSoldId() {
        return soldId;
    }

    public void setSoldId(String soldId) {
        this.soldId = soldId;
    }

    public ConsigneeList withSoldId(String soldId) {
        this.soldId = soldId;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ConsigneeList withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public ConsigneeList withIndex(long index) {
        this.index = index;
        return this;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public ConsigneeList withAngle(double angle) {
        this.angle = angle;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ConsigneeList withCategory(String category) {
        this.category = category;
        return this;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public ConsigneeList withLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public ConsigneeList withLong(String _long) {
        this._long = _long;
        return this;
    }

    public String getConsId() {
        return consId;
    }

    public void setConsId(String consId) {
        this.consId = consId;
    }

    public ConsigneeList withConsId(String consId) {
        this.consId = consId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("refId", refId).append("materialsList", materialsList).append("soldId", soldId).append("distance", distance).append("index", index).append("angle", angle).append("category", category).append("lat", lat).append("_long", _long).append("consId", consId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(materialsList).append(soldId).append(consId).append(distance).append(_long).append(index).append(angle).append(refId).append(category).append(lat).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ConsigneeList) == false) {
            return false;
        }
        ConsigneeList rhs = ((ConsigneeList) other);
        return new EqualsBuilder().append(materialsList, rhs.materialsList).append(soldId, rhs.soldId).append(consId, rhs.consId).append(distance, rhs.distance).append(_long, rhs._long).append(index, rhs.index).append(angle, rhs.angle).append(refId, rhs.refId).append(category, rhs.category).append(lat, rhs.lat).isEquals();
    }

}
