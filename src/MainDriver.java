import javax.swing.UIManager;


public class MainDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Game of Life");
		try {
			   //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
			   UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel" );

			}
		catch (Exception e) { }
		
		
		MainController application = new MainController();
	}

}
