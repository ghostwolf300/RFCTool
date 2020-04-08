package org.rfc.dto;

public class SalesData extends AbstractDataObject {
	
	private Material material;
	private String materialId;
	private String salesOrg;
	private String salesChannel;
	private String salesDiv;
	private String materialStatisticGroup;
	private String itemCategoryGroup;
	private String deliveringPlant;
	private String accountAssignment;
	
	public SalesData() {
		super();
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getSalesDiv() {
		return salesDiv;
	}

	public void setSalesDiv(String salesDiv) {
		this.salesDiv = salesDiv;
	}

	public String getMaterialStatisticGroup() {
		return materialStatisticGroup;
	}

	public void setMaterialStatisticGroup(String materialStatisticGroup) {
		this.materialStatisticGroup = materialStatisticGroup;
	}

	public String getItemCategoryGroup() {
		return itemCategoryGroup;
	}

	public void setItemCategoryGroup(String itemCategoryGroup) {
		this.itemCategoryGroup = itemCategoryGroup;
	}

	public String getDeliveringPlant() {
		return deliveringPlant;
	}

	public void setDeliveringPlant(String deliveringPlant) {
		this.deliveringPlant = deliveringPlant;
	}

	public String getAccountAssignment() {
		return accountAssignment;
	}

	public void setAccountAssignment(String accountAssignment) {
		this.accountAssignment = accountAssignment;
	}

}
