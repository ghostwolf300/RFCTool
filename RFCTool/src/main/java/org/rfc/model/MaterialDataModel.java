package org.rfc.model;

import java.util.List;

import org.rfc.dto.Material;

public class MaterialDataModel extends AbstractModel {
	
	public static final String P_MATERIALS="p_materials";
	
	private List<Material> materials=null;
	
	public MaterialDataModel() {
		super();
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
		this.firePropertyChange(P_MATERIALS, null, materials);
	}
	
}
