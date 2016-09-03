package RemoteManager.RemoteManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

public class DatabaseHelper {

		public  ArrayList<RemoteConnection> pullData() {
			
			ArrayList<RemoteConnection> dbData = new ArrayList<RemoteConnection>();
			String url = "jdbc:sqlite:resources/" + "remotedata";
			Connection conn = null;
			String sql = "select * from RemoteConnections";
			ResultSet rs = null;
			
			
			try {
				conn = DriverManager.getConnection(url);
				Statement smnt = conn.createStatement();
				rs = smnt.executeQuery(sql);
				
			   while (rs.next()) {
				        RemoteConnection connect = new RemoteConnection();
				        connect.setID(rs.getInt("ID"));
				        connect.setIP(rs.getString("URL"));
				        connect.setUsername(rs.getString("Username"));
				        connect.setPassword(rs.getString("Password"));
				        dbData.add(connect);
				        
			}} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return dbData;
	}
		
		public ResultSet getFromDB() {
			String url = "jdbc:sqlite:resources/" + "remotedata";
			Connection conn = null;
			String sql = "SELECT * FROM RemoteConnections";
			ResultSet rs = null;
			
			try {
				conn = DriverManager.getConnection(url);
				Statement smnt = conn.createStatement();
				rs = smnt.executeQuery(sql);
					
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
			return rs;
		}
		
		public boolean getPasswordSetting() {
			String url = "jdbc:sqlite:resources/" + "remotedata";
			Connection conn = null;
			String sql = "SELECT * FROM Settings";
			ResultSet rs = null;
			boolean pwEnabled = true;
			
			
			try {
				conn = DriverManager.getConnection(url);
				Statement smnt = conn.createStatement();
				rs = smnt.executeQuery(sql);
				
				 while (rs.next()) {
					 
					 pwEnabled = (rs.getBoolean("PassIsVisible"));
 
				 }
				
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
			return pwEnabled;
		}
	
		public void setPasswordSetting(boolean isSelected) {
			String url = "jdbc:sqlite:resources/" + "remotedata";
			Connection conn = null;
			String sql;
			
			
			if (isSelected == true) {
				 sql = "UPDATE Settings SET PassIsVisible = 1";
			} else{
				 sql = "UPDATE Settings SET PassIsVisible = 0";	
			}
			
			try {
				conn = DriverManager.getConnection(url);
				Statement smnt = conn.createStatement();
				smnt.executeUpdate(sql);
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
			
		}
}	
