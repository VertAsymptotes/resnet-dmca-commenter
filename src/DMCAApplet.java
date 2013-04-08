import javax.swing.*;

/**
 * This class is the main runner for all of the files and will attempt to override
 * any previous sizing issues switching b/w Windows and Mac.
 * @author steventan_12
 *
 */
public class DMCAApplet extends JApplet
{
	private static final long serialVersionUID = 1L;
	
	public class mainPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public mainPanel()
		{
			//for setting the Mac's header bar name properly.
			//System.setProperty("apple.laf.useScreenMenuBar", "true");
			//System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ResNet DMCA Commenter");
			//System.out.println(System.getProperties());
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
				//if set left for Mac, format is thrown off; top is better choice.
				final JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
				//System.out.println(UIManager.getSystemLookAndFeelClassName());
				
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
				tabs.addTab("DMCA Commenter", new dmca.DMCACommenter());
				tabs.addTab("Time Converter", new dmca.TimePane());
				tabs.addTab("MAC Formatter", new macformatter.MACMain());
				tabs.addTab("Help", new dmca.Help(10, 60));
				
				add(tabs);
				add(new dmca.Checklist());
			}
			catch(Exception e)
			{
				//do nothing.
			}
		}
	}
	public void init()
	{
		getContentPane().add(new mainPanel());
	}
	
	public static void main (String [] args)
	{
		DMCAApplet app = new DMCAApplet();
		app.init();
	}
}