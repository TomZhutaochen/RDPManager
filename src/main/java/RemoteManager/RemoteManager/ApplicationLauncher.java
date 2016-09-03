package RemoteManager.RemoteManager;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class ApplicationLauncher {

	public static void main(String[] args) {
		
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	        if ("Nimbus".equals(info.getName())) {
	            try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException ex) {
					
					ex.printStackTrace();
					
				} catch (InstantiationException ex) {
					
					ex.printStackTrace();
					
				} catch (IllegalAccessException ex) {
					
					ex.printStackTrace();
					
				} catch (UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}
	            break;
	        }
	    }
	
		OnLoadGUI.createMainGUI();
		
		
	}
}
