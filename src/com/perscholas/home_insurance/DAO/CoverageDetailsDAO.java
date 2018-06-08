package com.perscholas.home_insurance.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.perscholas.home_insurance.models.CoverageDetails;

public class CoverageDetailsDAO {
public void StoreCoverageDetails(CoverageDetails CD) throws SQLException {
	Connection conn = null;
	PreparedStatement stmt= null;
	ResultSet result=null;
	
	String SAVE = "INSERT INTO quote "
	+ "	(quote_id,premium,coverage,detached_structure,pp,adle,me,ded)" 
			+"VALUES(?,?,?,?,?,?,?,?);";
	
	MySqlConnection mysql = new MySqlConnection();
try {
	conn = mysql.getConnection();
	stmt=conn.prepareStatement(SAVE);
	stmt.setString(1, CD.getQuote_id());
	stmt.setInt(2, CD.getMonthlyPremuim());
	stmt.setInt(3, CD.getDwellingCoverage());
	stmt.setInt(4, CD.getDetachedStructorsl());
	stmt.setInt(5, CD.getPersonalProperty());
	stmt.setInt(6, CD.getAdditional());
	stmt.setInt(7, CD.getMedical());
	stmt.setInt(8,CD.getDeductible());
	
	
	stmt.executeUpdate();
}
catch (ClassNotFoundException | IOException | SQLException e) {
	e.printStackTrace();
			}finally {
				if (result != null) {
					result.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
		}


}

//public static void main(String[] args) throws SQLException {
//	CoverageDetails cd= new CoverageDetails();
//	CoverageDetailsDAO cdDAO = new CoverageDetailsDAO();
//	cd.setAdditional("5000");
//	cd.setDeductible("1000");
//	cd.setDetachedStructorsl("1000");
//	cd.setDwellingCoverage("100000");
//	cd.setMedical("5000");
//	cd.setMonthlyPremuim("10.42");
//	cd.setPersonalProperty("1000");
//	
//	cdDAO.StoreCoverageDetails(cd);
//}
