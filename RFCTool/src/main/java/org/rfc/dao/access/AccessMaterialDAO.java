package org.rfc.dao.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.rfc.dao.MaterialDAO;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData3;

public class AccessMaterialDAO extends AccessDAO implements MaterialDAO<Material3> {
	
	public AccessMaterialDAO() {
		super();
	}
	
	public AccessMaterialDAO(String dbPath) {
		super(dbPath);
	}
	
	public List<Material3> getChangePlantDataList() {
		List<Material3> material3s=null;
		String sql="SELECT MATERIAL,PLANT,PLAN_DEL_TIME FROM tbl_material_plantdata";
		Statement stmnt=null;
		ResultSet rs=null;
		try {
			this.openConnection();
			stmnt=this.connection.createStatement();
			rs=stmnt.executeQuery(sql);
			material3s=this.createPlannedDeliveryTimeList(rs);
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
		return material3s;
	}
	
	private List<Material3> createPlannedDeliveryTimeList(ResultSet rs) throws SQLException{
		List<Material3> material3s=new ArrayList<Material3>();
		Material3 material3=null;
		PlantData3 plantData3=null;
		String currentMaterial=null;
		
		while(rs.next()) {
			System.out.println("Looping: "+rs.getString("MATERIAL")+" "+rs.getString("PLANT"));
			if(currentMaterial==null || !currentMaterial.equals(rs.getString("MATERIAL"))) {
				material3=new Material3();
				material3.setMaterialId(rs.getString("MATERIAL"));
				material3s.add(material3);
				currentMaterial=material3.getMaterialId();
			}
			plantData3=createPlantData(rs);
			material3.addPlantData(plantData3.getPlant(),plantData3);
		}
		return material3s;
	}
	
	private PlantData3 createPlantData(ResultSet rs) throws SQLException {
		PlantData3 pd=null;
		while(rs.next()) {
			pd=new PlantData3();
			//pd.setMaterialId(rs.getString("MATERIAL"));
			pd.setPlant(rs.getString("PLANT"));
			pd.setPlannedDeliveryTime(rs.getInt("PLAN_DEL_TIME"));
		}
		return pd;
	}

	@Override
	public List<Material3> getAddPlantDataList() {
		// TODO Auto-generated method stub
		return null;
	}

}
