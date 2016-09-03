package RemoteManager.RemoteManager;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.util.Scanner;

	public class RemoteConnection {
		
		private int ID;
		private String IP;
		private String Username;
		private String Password;
		private String Status;
		
		public int getID() {return this.ID;}
		
		public void setID(int ID) {this.ID = ID;}
		
		public void setIP(String IP) {this.IP = IP;	}
		
		public void setUsername(String Username) {this.Username = Username;	}

		public void setPassword(String password) {this.Password = password;}
		
		public void setStatus(String Status) {this.Status = Status;}
		
		public String getIP() {return this.IP;}
		
		public String getUsername() {return this.Username;}
		
		public String getPassword() {return this.Password;}
		
		public String getStatus() {return this.Status;}
		
		public String addConnection(String IP, String Username, String Password) {
		String addCommand = "cmdkey /generic:"+IP+" /user:"+Username+" /pass:"+Password;
		String s = null;
		String result = null;
		
		
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
		return result;
		}
		
		public String removeConnection(String IP) {
			String removeCommand = "cmdkey /delete " + IP;
			String result = null;
			String s = null;
			
			try {
				Process p = Runtime.getRuntime().exec(removeCommand);
				BufferedReader read = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				while ((s = read.readLine()) != null) {
					result = s;
				}
				p.destroy();
				
			} catch (IOException io) {
				io.printStackTrace();
			}
			return result;
			
		}

		//Method to actually connect to the server of choice
		public String connectToConnection(String IP) {
			String connectCommand = "mstsc /v: "+IP+" /f /console";
			String result = null;
			
			try {
			Runtime.getRuntime().exec(connectCommand);
			
			ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
	        Process process = processBuilder.start();
	        String tasksList = toString(process.getInputStream());
	        result = tasksList;
	   
			} catch(IOException io) {
				io.printStackTrace();
			}
			return result;
		}
		
		//Converting to string for the task list (list of processes running)
		private String toString(InputStream inputStream) {
			Scanner scanner = new Scanner(inputStream, "UTF-8");
			scanner.useDelimiter("\\A");
	        String string = scanner.hasNext() ? scanner.next() : "";
	        scanner.close();

	        return string;
		}
		
	}

