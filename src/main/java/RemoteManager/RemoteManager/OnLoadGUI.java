package RemoteManager.RemoteManager;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.SystemColor;
import javax.swing.SpringLayout;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JMenu;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JCheckBoxMenuItem;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.DropMode;


public class OnLoadGUI {
	private static JTextField txtPing;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void createMainGUI() {
		
		JFrame mainFrame = new JFrame();
		final DatabaseHelper DH = new DatabaseHelper();
		final PasswordToggler togglePW = new PasswordToggler();
		boolean pwSetting = DH.getPasswordSetting();
		final JComboBox<String> PingCombo = new JComboBox<String>();

		
		mainFrame.setBackground(new Color(30, 144, 255));
		mainFrame.setFont(new Font("SansSerif", Font.BOLD, 12));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			mainFrame.setIconImage(ImageIO.read(new File("resources/icon.png")));
		} catch (IOException IOe) {
			
			IOe.printStackTrace();
		}
		
		
		mainFrame.setResizable(false);
		mainFrame.getContentPane().setBackground(SystemColor.textHighlight);
		SpringLayout springLayout = new SpringLayout();
		mainFrame.getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 87, SpringLayout.NORTH, mainFrame.getContentPane());
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, mainFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, mainFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 460, SpringLayout.WEST, mainFrame.getContentPane());
		mainFrame.getContentPane().add(panel);
		
		
		final JTable ConnectionsTable = new JTable();	
	
		final ResultSet Populate = DH.getFromDB();
		ConnectionsTable.setModel(DbUtils.resultSetToTableModel(Populate));
		ConnectionsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		ConnectionsTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		ConnectionsTable.setGridColor(Color.BLACK);
		ConnectionsTable.setShowHorizontalLines(true);
		ConnectionsTable.setShowVerticalLines(true);
		ConnectionsTable.setForeground(Color.BLACK);
		ConnectionsTable.setCellSelectionEnabled(false);
		ConnectionsTable.setColumnSelectionAllowed(false);
		ConnectionsTable.setBounds(0, 0, 452, 100);
		
		
		if(pwSetting) {
            ConnectionsTable.getColumnModel().getColumn(3).setCellRenderer(togglePW);
		}
		
		final JRadioButton HidePW = new JRadioButton("Show Passwords    ");
		if (pwSetting) {
			HidePW.setSelected(true);
		} else {
			HidePW.setSelected(false);
		}
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(ConnectionsTable);
		scrollPane.setBounds(1, 1, 448, 75);
		panel.add(scrollPane);
		ConnectionsTable.setBackground(Color.WHITE);
		ConnectionsTable.setFont(new Font("SansSerif", Font.PLAIN, 11));
		
		JPanel BottomMenu = new JPanel();
		BottomMenu.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		BottomMenu.setBackground(new Color(255, 255, 255));
		springLayout.putConstraint(SpringLayout.NORTH, BottomMenu, -40, SpringLayout.SOUTH, mainFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, BottomMenu, 0, SpringLayout.WEST, mainFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, BottomMenu, 0, SpringLayout.SOUTH, mainFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, BottomMenu, 472, SpringLayout.WEST, mainFrame.getContentPane());
		mainFrame.getContentPane().add(BottomMenu);
		BottomMenu.setLayout(null);
		final JFrame addUI = AddServerUI.buildAddEditUI(ConnectionsTable,Populate);
		addUI.setVisible(false);
		
		
		JButton AddButton = new JButton("Add");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addUI.setVisible(true);
			}
		});
		AddButton.setBorder(null);
		AddButton.setIcon(new ImageIcon("resources/Plus.png"));
		AddButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		AddButton.setBackground(Color.LIGHT_GRAY);
		AddButton.setBounds(255, 2, 70, 35);
		springLayout.putConstraint(SpringLayout.SOUTH, AddButton, -5, SpringLayout.SOUTH, BottomMenu);
		springLayout.putConstraint(SpringLayout.EAST, AddButton, -165, SpringLayout.EAST, BottomMenu);
		BottomMenu.add(AddButton);
		BottomMenu.setBorder(null);
		
		JButton EditButton = new JButton("Edit");
		EditButton.setIcon(new ImageIcon("resources/Edit.png"));
		EditButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		EditButton.setBorder(null);
		EditButton.setBackground(Color.LIGHT_GRAY);
		EditButton.setBounds(329, 2, 70, 35);
		BottomMenu.add(EditButton);
		
		
		
		JPanel contentPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, contentPanel, 6, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, contentPanel, -2, SpringLayout.NORTH, BottomMenu);
		contentPanel.setBackground(UIManager.getColor("PasswordField.selectionBackground"));
		contentPanel.setBorder(null);
		springLayout.putConstraint(SpringLayout.WEST, contentPanel, 10, SpringLayout.WEST, mainFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, contentPanel, -10, SpringLayout.EAST, mainFrame.getContentPane());
		mainFrame.getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JButton ConnectButtonRDP = new JButton("Connect RDP ");
		ConnectButtonRDP.setIcon(new ImageIcon("resources/Connect.png"));
		ConnectButtonRDP.setFont(new Font("SansSerif", Font.BOLD, 11));
		ConnectButtonRDP.setBorder(null);
		ConnectButtonRDP.setBackground(Color.LIGHT_GRAY);
		ConnectButtonRDP.setBounds(101, 31, 105, 34);
		contentPanel.add(ConnectButtonRDP);
		
		JButton checkVPNConnection = new JButton("Query Site");
		checkVPNConnection.setIcon(new ImageIcon("resources/VPN.png"));
		
		checkVPNConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		checkVPNConnection.setFont(new Font("SansSerif", Font.BOLD, 11));
		checkVPNConnection.setBorder(null);
		checkVPNConnection.setBackground(Color.LIGHT_GRAY);
		checkVPNConnection.setBounds(0, 31, 98, 34);
		contentPanel.add(checkVPNConnection);
		
		
		DefaultListCellRenderer myRender = new DefaultListCellRenderer();
		myRender.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		myRender.setVerticalAlignment(DefaultListCellRenderer.CENTER);
		
		PingCombo.setRenderer(myRender);
		PingCombo.setFont(new Font("SansSerif", Font.BOLD, 16));
		PingCombo.setBounds(246, 0, 206, 30);
		contentPanel.add(PingCombo);

		ArrayList<RemoteConnection> dbData = DH.pullData();
		for (int i = 0; i < dbData.size(); i++) {
			PingCombo.addItem(dbData.get(i).getIP());
		}
		
		PingCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
			
			}
	    	});
	
		JButton PingServer = new JButton("Ping");
		PingServer.setIcon(new ImageIcon("resources/Ping.png"));
		final NetworkHelper myNH = new NetworkHelper();
		PingServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (PingCombo.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "No Server Selected!");
					
				} else if (myNH.isServerOnline(PingCombo.getSelectedItem().toString())) {
					txtPing.setText("Online");
					txtPing.setBackground(Color.GREEN);
				
				} else {
					txtPing.setText("Offline");
					txtPing.setBackground(Color.RED);
					}
				}
			}
		);
		PingServer.setFont(new Font("SansSerif", Font.BOLD, 11));
		PingServer.setBorder(null);
		PingServer.setBackground(Color.LIGHT_GRAY);
		PingServer.setBounds(246, 31, 79, 35);
		contentPanel.add(PingServer);
		
		ConnectionsTable.setRowSelectionAllowed(true);
		ConnectionsTable.setDefaultEditor(Object.class, null);
		Border tbBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		txtPing = new JTextField();
		txtPing.setEditable(false);
		txtPing.setBorder(tbBorder);
		txtPing.setFont(new Font("SansSerif", Font.BOLD, 16));
		txtPing.setHorizontalAlignment(SwingConstants.CENTER);
		txtPing.setBounds(326, 31, 126, 34);
		contentPanel.add(txtPing);
		txtPing.setColumns(10);
		
		final JComboBox<String> ConnectionCombo = new JComboBox<String>();
		ConnectionCombo.setFont(new Font("SansSerif", Font.BOLD, 16));
		ConnectionCombo.setBounds(0, 0, 206, 30);
		ConnectionCombo.setRenderer(myRender);
		contentPanel.add(ConnectionCombo);
		
		for (int i = 0; i < dbData.size(); i++) {
			ConnectionCombo.addItem(dbData.get(i).getIP());
		}
		ConnectionCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
			}
	    	});
				
		mainFrame.setTitle("Remote Server Manager");
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("SansSerif", Font.BOLD, 12));
		menuBar.setBackground(new Color(255, 255, 255));
		mainFrame.setJMenuBar(menuBar);
		
		JMenu FileMenu = new JMenu("File  ");
		FileMenu.setIcon(new ImageIcon("resources/File.png"));
		
		FileMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
		menuBar.add(FileMenu);
		
		JMenuItem mntmAdd = new JMenuItem("Add Server");
		mntmAdd.setIcon(new ImageIcon("resources/Plus.png"));
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addUI.setVisible(true);
			}
		});
		mntmAdd.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(mntmAdd);
		
		JSeparator separator = new JSeparator();
		FileMenu.add(separator);
		
		JMenuItem mntmEdit = new JMenuItem("Edit Servers");
		mntmEdit.setIcon(new ImageIcon("resources/Edit.png"));
		mntmEdit.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(mntmEdit);
		
		JSeparator separator_1 = new JSeparator();
		FileMenu.add(separator_1);
		
		JMenuItem mntmDelete = new JMenuItem("Delete Server");
		mntmDelete.setIcon(new ImageIcon("resources/Delete.png"));
		mntmDelete.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(mntmDelete);
		mntmDelete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent a) {

			if (ConnectionsTable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "You have not selected a database record");
			} else {
				DeletionHandler DHandle = new DeletionHandler();
				int selectedRow = ConnectionsTable.getSelectedRow();
				String value = ConnectionsTable.getValueAt(selectedRow, 1).toString();
				DHandle.deleteServer(value);
				DatabaseHelper DH = new DatabaseHelper();
				ResultSet updated = DH.getFromDB();
				ConnectionsTable.setModel(DbUtils.resultSetToTableModel(updated));
				ArrayList<RemoteConnection> dbData = DH.pullData();
				PingCombo.removeAllItems();
				ConnectionCombo.removeAllItems();
				
				for (int i = 0; i < dbData.size(); i++) {
					PingCombo.addItem(dbData.get(i).getIP());
				}
				for (int i = 0; i < dbData.size(); i++) {
					ConnectionCombo.addItem(dbData.get(i).getIP());
				}}
		}
    	});
		
		JSeparator separator_2 = new JSeparator();
		FileMenu.add(separator_2);
		
		
		JMenuItem mntmExit = new JMenuItem("Exit System");
		mntmExit.setIcon(new ImageIcon("resources/Exit.png"));
		mntmExit.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {;
		public void actionPerformed(ActionEvent a) {
			System.exit(0);
		}
    	});
		
		JMenu OptionsMenu = new JMenu("Options");
		OptionsMenu.setIcon(new ImageIcon("resources/Settings.png"));
		
		OptionsMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
		menuBar.add(OptionsMenu);
		
	
		HidePW.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	
		    	boolean isSelected = HidePW.isSelected();  
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		        	   	
		        	DH.setPasswordSetting(isSelected);
		            ConnectionsTable.getColumnModel().getColumn(3).setCellRenderer(togglePW);	 
		            ConnectionsTable.repaint();
		            
		        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
		        	DH.setPasswordSetting(isSelected);
		        	ConnectionsTable.getColumnModel().getColumn(3).setCellRenderer(ConnectionsTable.getDefaultRenderer(String.class));
		        	ConnectionsTable.repaint();
		        };
		    }
		});
	
		JButton DeleteButton = new JButton("Delete");
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if (ConnectionsTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "You have not selected a database record");
				} else {
					DeletionHandler DHandle = new DeletionHandler();
					int selectedRow = ConnectionsTable.getSelectedRow();
					String value = ConnectionsTable.getValueAt(selectedRow, 1).toString();
					DHandle.deleteServer(value);
					DatabaseHelper DH = new DatabaseHelper();
					ResultSet updated = DH.getFromDB();
					ConnectionsTable.setModel(DbUtils.resultSetToTableModel(updated));
					ArrayList<RemoteConnection> dbData = DH.pullData();
					PingCombo.removeAllItems();
					ConnectionCombo.removeAllItems();
					
					for (int i = 0; i < dbData.size(); i++) {
						PingCombo.addItem(dbData.get(i).getIP());
					}
					for (int i = 0; i < dbData.size(); i++) {
						ConnectionCombo.addItem(dbData.get(i).getIP());
					}
				}
			}
		});
		DeleteButton.setIcon(new ImageIcon("resources/Delete.png"));
		DeleteButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		DeleteButton.setBorder(null);
		DeleteButton.setBackground(Color.LIGHT_GRAY);
		DeleteButton.setBounds(401, 2, 70, 35);
		BottomMenu.add(DeleteButton);
		
		HidePW.setFont(new Font("SansSerif", Font.BOLD, 11));
		HidePW.setHorizontalAlignment(SwingConstants.RIGHT);
		OptionsMenu.add(HidePW);
		HidePW.setSize(100,200);
		
		Component glue_1 = Box.createGlue();
		OptionsMenu.add(glue_1);
		
			
			JMenu mnNewMenu = new JMenu("Attributions");
			mnNewMenu.setIcon(new ImageIcon("resources/Attribution.png"));
			mnNewMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
			menuBar.add(mnNewMenu);
			
			JLabel lblNewLabel = new JLabel("   All Icons provided by https://icons8.com    ");
			mnNewMenu.add(lblNewLabel);
		
		Component glue = Box.createGlue();
		menuBar.add(glue);
		
		JMenu mnAbout = new JMenu("About  ");
		mnAbout.setIcon(new ImageIcon("resources/About.png"));
		mnAbout.setFont(new Font("SansSerif", Font.BOLD, 12));
		menuBar.add(mnAbout);
		
		JLabel lblAbout = new JLabel("   Windows RDP Manager - https://github.com/symonk/     ");
		mnAbout.add(lblAbout);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(478,262);
	}
}
