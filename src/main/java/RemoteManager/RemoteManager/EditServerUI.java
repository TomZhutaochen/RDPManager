package RemoteManager.RemoteManager;


import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Panel;

public class EditServerUI {
	public String URLValue;
	public String UserValue;
	public String PassValue;
	private JTextField URLText;
	private JTextField UserText;
	private JTextField PassText;
	

	/**
	 * @wbp.parser.entryPoint
	 */
	public  JFrame buildEditUI() {
		JFrame editServer = new JFrame();
		editServer.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sy\\Desktop\\RemoteManager\\RemoteManager\\resources\\Plus.png"));
		editServer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editServer.setFont(new Font("SansSerif", Font.BOLD, 12));
		editServer.setTitle("Edit Server");
		
		
		editServer.setLocationRelativeTo(null);
		editServer.setSize(250,215);
		editServer.setResizable(true);
		editServer.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));

		panel.setBounds(0, 0, 249, 176);
		editServer.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton UpdateButton = new JButton("Update");
		UpdateButton.setBounds(85, 5, 67, 31);
		
		
		JButton ClearButton = new JButton("Clear");
		ClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				URLText.setText("");
				UserText.setText("");
				PassText.setText("");
			}
		});
		ClearButton.setBounds(162, 5, 67, 31);
		
		
		JLabel label = new JLabel("Server URL:");
		label.setFont(new Font("SansSerif", Font.BOLD, 11));
		label.setBounds(10, 17, 77, 14);
		panel.add(label);
		
		URLText = new JTextField();
		URLText.setHorizontalAlignment(SwingConstants.LEFT);
		URLText.setFont(new Font("SansSerif", Font.BOLD, 12));
		URLText.setColumns(10);
		URLText.setBounds(97, 11, 122, 27);
		panel.add(URLText);
		
		UserText = new JTextField();
		UserText.setHorizontalAlignment(SwingConstants.LEFT);
		UserText.setFont(new Font("SansSerif", Font.BOLD, 12));
		UserText.setColumns(10);
		UserText.setBounds(97, 48, 122, 27);
		panel.add(UserText);
		
		JLabel label_1 = new JLabel("Username:");
		label_1.setFont(new Font("SansSerif", Font.BOLD, 11));
		label_1.setBounds(10, 50, 77, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Password:");
		label_2.setFont(new Font("SansSerif", Font.BOLD, 11));
		label_2.setBounds(10, 83, 77, 14);
		panel.add(label_2);
		
		PassText = new JTextField();
		PassText.setHorizontalAlignment(SwingConstants.LEFT);
		PassText.setFont(new Font("SansSerif", Font.BOLD, 12));
		PassText.setColumns(10);
		PassText.setBounds(97, 83, 122, 27);
		panel.add(PassText);
		
		Panel buttonPanel = new Panel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBounds(0, 129, 239, 47);
		panel.add(buttonPanel);
		buttonPanel.setLayout(null);
		buttonPanel.add(UpdateButton);
		buttonPanel.add(ClearButton);
		
		return editServer;
		
	}
	
	public static RemoteConnection editHandler(String serverURL) {
		
		String url = "jdbc:sqlite:resources/" + "remotedata";
		Connection conn = null;
		String sql = "SELECT * FROM RemoteConnections WHERE URL = " +serverURL+ " LIMIT 1";
		ResultSet rs = null;
		RemoteConnection connect = new RemoteConnection();
		
		try {
			conn = DriverManager.getConnection(url);
			Statement smnt = conn.createStatement();
			rs = smnt.executeQuery(sql);
			
		   while (rs.next()) {
			        connect.setID(rs.getInt("ID"));
			        connect.setIP(rs.getString("URL"));
			        connect.setUsername(rs.getString("Username"));
			        connect.setPassword(rs.getString("Password"));
			        
			
		}} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		return connect;
		
	
		
		
		
	}
}

