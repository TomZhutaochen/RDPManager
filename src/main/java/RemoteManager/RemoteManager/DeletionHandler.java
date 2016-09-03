package RemoteManager.RemoteManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeletionHandler {

	
	
	public void deleteServer(String serverURL) {
		String url = "jdbc:sqlite:resources/" + "remotedata";
		Connection conn = null;
		String sql = "DELETE FROM REMOTECONNECTIONS WHERE URL = '"+ serverURL +"'";
		
		try {
			conn = DriverManager.getConnection(url);
			Statement smnt = conn.createStatement();
			smnt.executeUpdate(sql);
			
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		
	}
	}
	

