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
		final JComboBox<String> PingDropDown = new JComboBox<String>();

		
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
		
		final JRadioButton OptionsMenuHideShowPasswordRadioButton = new JRadioButton("Show Passwords    ");
		if (pwSetting) {
			OptionsMenuHideShowPasswordRadioButton.setSelected(true);
		} else {
			OptionsMenuHideShowPasswordRadioButton.setSelected(false);
		}
		panel.setLayout(null);
		
		JScrollPane DatabaseScrollPane = new JScrollPane(ConnectionsTable);
		DatabaseScrollPane.setBounds(1, 1, 448, 75);
		panel.add(DatabaseScrollPane);
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
		final JFrame addUI = AddServerUI.buildAddUI(ConnectionsTable,Populate);
		EditServerUI editserv = new EditServerUI();
		final JFrame editUI = editserv.buildEditUI();
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
		EditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (ConnectionsTable.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "You have not selected a database record");
					return;
				} 
				
				
				int selectedRow = ConnectionsTable.getSelectedRow();
				String serverURL = ConnectionsTable.getValueAt(selectedRow, 1).toString();
				RemoteConnection Editing = EditServerUI.editHandler(serverURL);
				
				String selectedURL = Editing.getIP();
				String selectedUser = Editing.getUsername();
				String selectedPass = Editing.getPassword();
				
				EditServerUI editserv = new EditServerUI();
				editserv.URLValue = selectedURL;
				editserv.UserValue = selectedUser;
				editserv.PassValue = selectedPass;
				
				JFrame editUI = editserv.buildEditUI();
			
				editUI.setVisible(true);
			}
		});
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
		
		JButton ConnectButton = new JButton("Connect RDP ");
		ConnectButton.setIcon(new ImageIcon("resources/Connect.png"));
		ConnectButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		ConnectButton.setBorder(null);
		ConnectButton.setBackground(Color.LIGHT_GRAY);
		ConnectButton.setBounds(101, 31, 105, 34);
		contentPanel.add(ConnectButton);
		
		JButton QueryButton = new JButton("Query Site");
		QueryButton.setIcon(new ImageIcon("resources/VPN.png"));
		
		QueryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		QueryButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		QueryButton.setBorder(null);
		QueryButton.setBackground(Color.LIGHT_GRAY);
		QueryButton.setBounds(0, 31, 98, 34);
		contentPanel.add(QueryButton);
		
		
		DefaultListCellRenderer myRender = new DefaultListCellRenderer();
		myRender.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		myRender.setVerticalAlignment(DefaultListCellRenderer.CENTER);
		
		PingDropDown.setRenderer(myRender);
		PingDropDown.setFont(new Font("SansSerif", Font.BOLD, 16));
		PingDropDown.setBounds(246, 0, 206, 30);
		contentPanel.add(PingDropDown);

		ArrayList<RemoteConnection> dbData = DH.pullData();
		for (int i = 0; i < dbData.size(); i++) {
			PingDropDown.addItem(dbData.get(i).getIP());
		}
		
		PingDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				
			
			}
	    	});
	
		JButton PingButton = new JButton("Ping");
		PingButton.setIcon(new ImageIcon("resources/Ping.png"));
		final NetworkHelper myNH = new NetworkHelper();
		PingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (PingDropDown.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "No Server Selected!");
					
				} else if (myNH.isServerOnline(PingDropDown.getSelectedItem().toString())) {
					txtPing.setText("Online");
					txtPing.setBackground(Color.GREEN);
				
				} else {
					txtPing.setText("Offline");
					txtPing.setBackground(Color.RED);
					}
				}
			}
		);
		PingButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		PingButton.setBorder(null);
		PingButton.setBackground(Color.LIGHT_GRAY);
		PingButton.setBounds(246, 31, 79, 35);
		contentPanel.add(PingButton);
		
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
		
		final JComboBox<String> ConnectionDropDown = new JComboBox<String>();
		ConnectionDropDown.setFont(new Font("SansSerif", Font.BOLD, 16));
		ConnectionDropDown.setBounds(0, 0, 206, 30);
		ConnectionDropDown.setRenderer(myRender);
		contentPanel.add(ConnectionDropDown);
		
		for (int i = 0; i < dbData.size(); i++) {
			ConnectionDropDown.addItem(dbData.get(i).getIP());
		}
		ConnectionDropDown.addActionListener(new ActionListener() {
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
		
		JMenuItem FileMenuAddButton = new JMenuItem("Add Server");
		FileMenuAddButton.setIcon(new ImageIcon("resources/Plus.png"));
		FileMenuAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addUI.setVisible(true);
			}
		});
		FileMenuAddButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(FileMenuAddButton);
		
		JSeparator separator = new JSeparator();
		FileMenu.add(separator);
		
		JMenuItem FileMenuEditButton = new JMenuItem("Edit Servers");
		FileMenuEditButton.setIcon(new ImageIcon("resources/Edit.png"));
		FileMenuEditButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(FileMenuEditButton);
		
		JSeparator separator_1 = new JSeparator();
		FileMenu.add(separator_1);
		
		JMenuItem FileMenuDeleteButton = new JMenuItem("Delete Server");
		FileMenuDeleteButton.setIcon(new ImageIcon("resources/Delete.png"));
		FileMenuDeleteButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(FileMenuDeleteButton);
		FileMenuDeleteButton.addActionListener(new ActionListener() {
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
				PingDropDown.removeAllItems();
				ConnectionDropDown.removeAllItems();
				
				for (int i = 0; i < dbData.size(); i++) {
					PingDropDown.addItem(dbData.get(i).getIP());
				}
				for (int i = 0; i < dbData.size(); i++) {
					ConnectionDropDown.addItem(dbData.get(i).getIP());
				}}
		}
    	});
		
		JSeparator separator_2 = new JSeparator();
		FileMenu.add(separator_2);
		
		
		JMenuItem FileMenuExitButton = new JMenuItem("Exit System");
		FileMenuExitButton.setIcon(new ImageIcon("resources/Exit.png"));
		FileMenuExitButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		FileMenu.add(FileMenuExitButton);
		FileMenuExitButton.addActionListener(new ActionListener() {;
		public void actionPerformed(ActionEvent a) {
			System.exit(0);
		}
    	});
		
		JMenu OptionsMenu = new JMenu("Options");
		OptionsMenu.setIcon(new ImageIcon("resources/Settings.png"));
		
		OptionsMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
		menuBar.add(OptionsMenu);
		
	
		OptionsMenuHideShowPasswordRadioButton.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	
		    	boolean isSelected = OptionsMenuHideShowPasswordRadioButton.isSelected();  
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
					return;
				} 
				
				int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?");
				if (answer == JOptionPane.YES_OPTION) {
				
				
					DeletionHandler DHandle = new DeletionHandler();
					int selectedRow = ConnectionsTable.getSelectedRow();
					String value = ConnectionsTable.getValueAt(selectedRow, 1).toString();
					DHandle.deleteServer(value);
					DatabaseHelper DH = new DatabaseHelper();
					ResultSet updated = DH.getFromDB();
					ConnectionsTable.setModel(DbUtils.resultSetToTableModel(updated));
					ArrayList<RemoteConnection> dbData = DH.pullData();
					PingDropDown.removeAllItems();
					ConnectionDropDown.removeAllItems();
					
					for (int i = 0; i < dbData.size(); i++) {
						PingDropDown.addItem(dbData.get(i).getIP());
					}
					for (int i = 0; i < dbData.size(); i++) {
						ConnectionDropDown.addItem(dbData.get(i).getIP());
					}
				}
			
				
				 else {
					JOptionPane.getRootFrame().dispose();
					
				}
				}
		});
		
		DeleteButton.setIcon(new ImageIcon("resources/Delete.png"));
		DeleteButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		DeleteButton.setBorder(null);
		DeleteButton.setBackground(Color.LIGHT_GRAY);
		DeleteButton.setBounds(401, 2, 70, 35);
		BottomMenu.add(DeleteButton);
		
		OptionsMenuHideShowPasswordRadioButton.setFont(new Font("SansSerif", Font.BOLD, 11));
		OptionsMenuHideShowPasswordRadioButton.setHorizontalAlignment(SwingConstants.RIGHT);
		OptionsMenu.add(OptionsMenuHideShowPasswordRadioButton);
		OptionsMenuHideShowPasswordRadioButton.setSize(100,200);
		
		Component glue_1 = Box.createGlue();
		OptionsMenu.add(glue_1);
		
			
			JMenu AttributionsMenu = new JMenu("Attributions");
			AttributionsMenu.setIcon(new ImageIcon("resources/Attribution.png"));
			AttributionsMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
			menuBar.add(AttributionsMenu);
			
			JLabel AttributionsLabel = new JLabel("   All Icons provided by https://icons8.com    ");
			AttributionsMenu.add(AttributionsLabel);
		
		Component glue = Box.createGlue();
		menuBar.add(glue);
		
		JMenu AboutMenu = new JMenu("About  ");
		AboutMenu.setIcon(new ImageIcon("resources/About.png"));
		AboutMenu.setFont(new Font("SansSerif", Font.BOLD, 12));
		menuBar.add(AboutMenu);
		
		JLabel AboutLabel = new JLabel("   Windows RDP Manager - https://github.com/symonk/     ");
		AboutMenu.add(AboutLabel);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setSize(478,262);
	
}
}
