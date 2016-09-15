package RemoteManager.RemoteManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class AddServerUI {
	private static JPanel panel;
	private static JTextField addURL;
	private static JTextField addUser;
	private static JTextField addPass;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static JFrame buildAddUI(final JTable ConnectionsTable, final ResultSet Populate) {
	final JFrame addServer = new JFrame();
	addServer.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sy\\Desktop\\RemoteManager\\RemoteManager\\resources\\Plus.png"));
	addServer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	addServer.setFont(new Font("SansSerif", Font.BOLD, 12));
	addServer.setTitle("AddServer");
	
	
	addServer.setLocationRelativeTo(null);
	addServer.setSize(268,203);
	addServer.setResizable(true);
	addServer.getContentPane().setLayout(null);
	
	panel = new JPanel();
	panel.setBackground(new Color(30, 144, 255));
	panel.setBorder(new LineBorder(new Color(0, 0, 0)));

	panel.setBounds(0, 0, 252, 165);
	addServer.getContentPane().add(panel);
	panel.setLayout(null);
	
	JPanel panel_1 = new JPanel();
	panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
	panel_1.setBackground(new Color(255, 255, 255));
	panel_1.setBounds(0, 119, 252, 46);
	panel.add(panel_1);
	panel_1.setLayout(null);
	
	JButton btnAdd = new JButton("Add");
	btnAdd.setIcon(new ImageIcon("resources/Plus.png"));
	btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			NetworkHelper NH = new NetworkHelper();
			if (addURL.getText().isEmpty() || addUser.getText().isEmpty() || addPass.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sorry, all fields do not contain suitable data");
			} else
				NH.addNewServer(addURL.getText(), addUser.getText(), addPass.getText());
				DatabaseHelper DH = new DatabaseHelper();
				ResultSet updated = DH.getFromDB();
				ConnectionsTable.setModel(DbUtils.resultSetToTableModel(updated));
				
				addServer.dispose();
		
				
			}
			
	});
	btnAdd.setFont(new Font("SansSerif", Font.BOLD, 11));
	btnAdd.setBounds(65, 5, 86, 35);
	panel_1.add(btnAdd);
	
	JButton btnClear = new JButton("Clear");
	btnClear.setIcon(new ImageIcon("resources/Empty.png"));
	btnClear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			addURL.setText("");
			addUser.setText("");
			addPass.setText("");
		}
	});
	btnClear.setFont(new Font("SansSerif", Font.BOLD, 11));
	btnClear.setBounds(163, 5, 79, 35);
	
	panel_1.add(btnClear);
	Border tbBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	addURL = new JTextField();
	addURL.setBorder(tbBorder);
	addURL.setFont(new Font("SansSerif", Font.BOLD, 12));
	addURL.setHorizontalAlignment(SwingConstants.LEFT);
	addURL.setBounds(97, 13, 122, 27);
	panel.add(addURL);
	addURL.setColumns(10);
	
	JLabel lblAddURL = new JLabel("Server URL:");
	lblAddURL.setFont(new Font("SansSerif", Font.BOLD, 11));
	lblAddURL.setBounds(10, 19, 77, 14);
	panel.add(lblAddURL);
	
	JLabel lblAddUsername = new JLabel("Username:");
	lblAddUsername.setFont(new Font("SansSerif", Font.BOLD, 11));
	lblAddUsername.setBounds(10, 52, 77, 14);
	panel.add(lblAddUsername);
	
	JLabel lblAddPassword = new JLabel("Password:");
	lblAddPassword.setFont(new Font("SansSerif", Font.BOLD, 11));
	lblAddPassword.setBounds(10, 85, 77, 14);
	panel.add(lblAddPassword);
	
	addUser = new JTextField();
	addUser.setBorder(tbBorder);
	addUser.setFont(new Font("SansSerif", Font.BOLD, 12));
	addUser.setHorizontalAlignment(SwingConstants.LEFT);
	addUser.setColumns(10);
	addUser.setBounds(97, 50, 122, 27);
	panel.add(addUser);
	
	addPass = new JTextField();
	addPass.setBorder(tbBorder);
	addPass.setFont(new Font("SansSerif", Font.BOLD, 12));
	addPass.setHorizontalAlignment(SwingConstants.LEFT);
	addPass.setColumns(10);
	addPass.setBounds(97, 85, 122, 27);
	panel.add(addPass);
	addServer.setVisible(true);


	return addServer;


	
	}

	public void addServer() {
		
		
	}
}
