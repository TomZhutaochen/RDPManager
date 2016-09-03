package RemoteManager.RemoteManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NetworkHelper {
	
	public boolean isServerOnline(String serverURL) {
		Boolean serverIsOnline = false;
		try {
			serverIsOnline = InetAddress.getByName(serverURL).isReachable(4);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return serverIsOnline;
	}

	public static void main(String[] args) {
		NetworkHelper NH = new NetworkHelper();
		System.out.println(NH.isServerOnline("10.0.10.10"));
	}
	
	public  void addNewServer(String URL, String User, String Pass) {
		String result = "Failed to add server";
		
			String addCommand = "cmdkey /generic:"+URL+" /user:"+User+" /pass:"+Pass;
			String s = null;
			
			String url = "jdbc:sqlite:resources/" + "remotedata";
			Connection conn = null;
			String sql = "INSERT INTO RemoteConnections (URL, Username, Password) VALUES ('"+URL+"', '"+User+"', '"+Pass+"')";
	
			
			
			try {
				conn = DriverManager.getConnection(url);
				Statement smnt = conn.createStatement();
				smnt.executeUpdate(sql);
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
			
			try {
				Process p = Runtime.getRuntime().exec(addCommand);
				BufferedReader addOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			while ((s = addOutput.readLine()) != null) {
				result = s;
			}
			p.destroy();
			
			} catch(IOException io) {
				io.printStackTrace();		
			
			
			
			}
		}	
	}
	

