
package optima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MaterialsList implements Serializable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("quantity")
    @Expose
    private long quantity;
    @SerializedName("breadth")
    @Expose
    private double breadth;
    @SerializedName("item_desc")
    @Expose
    private String itemDesc;
    @SerializedName("length")
    @Expose
    private double length;
    @SerializedName("weight")
    @Expose
    private double weight;
    @SerializedName("invoiceValue")
    @Expose
    private String invoiceValue;
    @SerializedName("priority")
    @Expose
    private double priority;
    @SerializedName("delivery_no")
    @Expose
    private String deliveryNo;
    @SerializedName("pickup_id")
    @Expose
    private String pickupId;
    @SerializedName("vunit")
    @Expose
    private String vunit;
    @SerializedName("volume")
    @Expose
    private double volume;
    @SerializedName("sub_categories")
    @Expose
    private String subCategories;
    @SerializedName("bu")
    @Expose
    private String bu;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("wunit")
    @Expose
    private String wunit;
    @SerializedName("bun")
    @Expose
    private String bun;
    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("lr_no")
    @Expose
    private Object lrNo;
    @SerializedName("height")
    @Expose
    private double height;
    @SerializedName("associated_material")
    @Expose
    private List<Object> associatedMaterial = new ArrayList<Object>();
    private final static long serialVersionUID = 8744418605773129483L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MaterialsList() {
    }

    /**
     * 
     * @param code
     * @param quantity
     * @param breadth
     * @param length
     * @param deliveryNo
     * @param weight
     * @param invoiceValue
     * @param pickupId
     * @param itemDesc
     * @param priority
     * @param subCategories
     * @param vunit
     * @param volume
     * @param bu
     * @param lrNo
     * @param name
     * @param wunit
     * @param associatedMaterial
     * @param bun
     * @param invoiceNo
     * @param category
     * @param height
     */
    public MaterialsList(String code, long quantity, double breadth, String itemDesc, double length, double weight, String invoiceValue, double priority, String deliveryNo, String pickupId, String vunit, double volume, String subCategories, String bu, String name, String wunit, String bun, String invoiceNo, String category, Object lrNo, double height, List<Object> associatedMaterial) {
        super();
        this.code = code;
        this.quantity = quantity;
        this.breadth = breadth;
        this.itemDesc = itemDesc;
        this.length = length;
        this.weight = weight;
        this.invoiceValue = invoiceValue;
        this.priority = priority;
        this.deliveryNo = deliveryNo;
        this.pickupId = pickupId;
        this.vunit = vunit;
        this.volume = volume;
        this.subCategories = subCategories;
        this.bu = bu;
        this.name = name;
        this.wunit = wunit;
        this.bun = bun;
        this.invoiceNo = invoiceNo;
        this.category = category;
        this.lrNo = lrNo;
        this.height = height;
        this.associatedMaterial = associatedMaterial;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MaterialsList withCode(String code) {
        this.code = code;
        return this;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public MaterialsList withQuantity(long quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getBreadth() {
        return breadth;
    }

    public void setBreadth(double breadth) {
        this.breadth = breadth;
    }

    public MaterialsList withBreadth(double breadth) {
        this.breadth = breadth;
        return this;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public MaterialsList withItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public MaterialsList withLength(double length) {
        this.length = length;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public MaterialsList withWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public String getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(String invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public MaterialsList withInvoiceValue(String invoiceValue) {
        this.invoiceValue = invoiceValue;
        return this;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public MaterialsList withPriority(double priority) {
        this.priority = priority;
        return this;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public MaterialsList withDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
        return this;
    }

    public String getPickupId() {
        return pickupId;
    }

    public void setPickupId(String pickupId) {
        this.pickupId = pickupId;
    }

    public MaterialsList withPickupId(String pickupId) {
        this.pickupId = pickupId;
        return this;
    }

    public String getVunit() {
        return vunit;
    }

    public void setVunit(String vunit) {
        this.vunit = vunit;
    }

    public MaterialsList withVunit(String vunit) {
        this.vunit = vunit;
        return this;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public MaterialsList withVolume(double volume) {
        this.volume = volume;
        return this;
    }

    public String getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }

    public MaterialsList withSubCategories(String subCategories) {
        this.subCategories = subCategories;
        return this;
    }

    public String getBu() {
        return bu;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    public MaterialsList withBu(String bu) {
        this.bu = bu;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialsList withName(String name) {
        this.name = name;
        return this;
    }

    public String getWunit() {
        return wunit;
    }

    public void setWunit(String wunit) {
        this.wunit = wunit;
    }

    public MaterialsList withWunit(String wunit) {
        this.wunit = wunit;
        return this;
    }

    public String getBun() {
        return bun;
    }

    public void setBun(String bun) {
        this.bun = bun;
    }

    public MaterialsList withBun(String bun) {
        this.bun = bun;
        return this;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public MaterialsList withInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MaterialsList withCategory(String category) {
        this.category = category;
        return this;
    }

    public Object getLrNo() {
        return lrNo;
    }

    public void setLrNo(Object lrNo) {
        this.lrNo = lrNo;
    }

    public MaterialsList withLrNo(Object lrNo) {
        this.lrNo = lrNo;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public MaterialsList withHeight(double height) {
        this.height = height;
        return this;
    }

    public List<Object> getAssociatedMaterial() {
        return associatedMaterial;
    }

    public void setAssociatedMaterial(List<Object> associatedMaterial) {
        this.associatedMaterial = associatedMaterial;
    }

    public MaterialsList withAssociatedMaterial(List<Object> associatedMaterial) {
        this.associatedMaterial = associatedMaterial;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("quantity", quantity).append("breadth", breadth).append("itemDesc", itemDesc).append("length", length).append("weight", weight).append("invoiceValue", invoiceValue).append("priority", priority).append("deliveryNo", deliveryNo).append("pickupId", pickupId).append("vunit", vunit).append("volume", volume).append("subCategories", subCategories).append("bu", bu).append("name", name).append("wunit", wunit).append("bun", bun).append("invoiceNo", invoiceNo).append("category", category).append("lrNo", lrNo).append("height", height).append("associatedMaterial", associatedMaterial).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).append(quantity).append(breadth).append(length).append(deliveryNo).append(weight).append(invoiceValue).append(pickupId).append(itemDesc).append(priority).append(subCategories).append(vunit).append(volume).append(bu).append(lrNo).append(name).append(wunit).append(associatedMaterial).append(bun).append(invoiceNo).append(category).append(height).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MaterialsList) == false) {
            return false;
        }
        MaterialsList rhs = ((MaterialsList) other);
        return new EqualsBuilder().append(code, rhs.code).append(quantity, rhs.quantity).append(breadth, rhs.breadth).append(length, rhs.length).append(deliveryNo, rhs.deliveryNo).append(weight, rhs.weight).append(invoiceValue, rhs.invoiceValue).append(pickupId, rhs.pickupId).append(itemDesc, rhs.itemDesc).append(priority, rhs.priority).append(subCategories, rhs.subCategories).append(vunit, rhs.vunit).append(volume, rhs.volume).append(bu, rhs.bu).append(lrNo, rhs.lrNo).append(name, rhs.name).append(wunit, rhs.wunit).append(associatedMaterial, rhs.associatedMaterial).append(bun, rhs.bun).append(invoiceNo, rhs.invoiceNo).append(category, rhs.category).append(height, rhs.height).isEquals();
    }

}
