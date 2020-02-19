package org.rfc.dao.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.rfc.dao.MaterialDAO;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;

public class AccessMaterialDAO extends AccessDAO implements MaterialDAO<Material> {
	
	public AccessMaterialDAO() {
		super();
	}
	
	public AccessMaterialDAO(String dbPath) {
		super(dbPath);
	}
	
	public List<Material> getChangePlantDataList() {
		List<Material> materials=null;
		String sql="SELECT MATERIAL,PLANT,PLAN_DEL_TIME FROM tbl_material_plantdata";
		Statement stmnt=null;
		ResultSet rs=null;
		try {
			this.openConnection();
			stmnt=this.connection.createStatement();
			rs=stmnt.executeQuery(sql);
			materials=this.createPlannedDeliveryTimeList(rs);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				stmnt.close();
				this.closeConnection();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return materials;
	}
	
	private List<Material> createPlannedDeliveryTimeList(ResultSet rs) throws SQLException{
		List<Material> materials=new ArrayList<Material>();
		Material material=null;
		PlantData plantData=null;
		String currentMaterial=null;
		
		while(rs.next()) {
			System.out.println("Looping: "+rs.getString("MATERIAL")+" "+rs.getString("PLANT"));
			if(currentMaterial==null || !currentMaterial.equals(rs.getString("MATERIAL"))) {
				material=new Material();
				material.setMaterialId(rs.getString("MATERIAL"));
				materials.add(material);
				currentMaterial=material.getMaterialId();
			}
			plantData=createPlantData(rs);
			material.addPlantData(plantData.getPlant(),plantData);
		}
		return materials;
	}
	
	private PlantData createPlantData(ResultSet rs) throws SQLException {
		PlantData pd=null;
		while(rs.next()) {
			pd=new PlantData();
			//pd.setMaterialId(rs.getString("MATERIAL"));
			pd.setPlant(rs.getString("PLANT"));
			pd.setPlannedDeliveryTime(rs.getInt("PLAN_DEL_TIME"));
		}
		return pd;
	}

	@Override
	public List<Material> getAddPlantDataList() {
		// TODO Auto-generated method stub
		return null;
	}

}
