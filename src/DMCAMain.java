import javax.swing.*;

/**
 * This class is the main runner for all of the files and will attempt to override
 * any previous sizing issues switching b/w Windows and Mac.
 * @author steventan_12
 *
 */
public class DMCAMain
{
	public static void main(String [] args)
	{
		try
		{
			//System.out.println(dmca.TimeConverter.parseUnix(1313019739));
			//for setting the Mac's header bar name properly.
			//System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ResNet DMCA Commenter");
			//System.out.println(System.getProperties());
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			final JFrame frame = new JFrame("ResNet DMCA Commenter");
			final JPanel pane = new JPanel();
			
			//if set left for Mac, format is thrown off; top is better choice.
			final JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
			//System.out.println(UIManager.getSystemLookAndFeelClassName());
			
			pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
			pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			
			tabs.addTab("DMCA Commenter", new dmca.DMCACommenter());
			tabs.addTab("Time Converter", new dmca.TimePane());
			tabs.addTab("MAC Formatter", new macformatter.MACMain());
			if(UIManager.getSystemLookAndFeelClassName().equals("com.apple.laf.AquaLookAndFeel"))
				tabs.addTab("Help", new dmca.Help(10, 70));
			else
				tabs.addTab("Help", new dmca.Help(8, 57));
			
			
			pane.add(tabs);
			pane.add(new dmca.Checklist());
			
			frame.getContentPane().add(pane);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			//System.out.println(dmca.Checklist.notes());
		}
		catch (Exception e)
		{
			//do nothing.
		}
	}
}