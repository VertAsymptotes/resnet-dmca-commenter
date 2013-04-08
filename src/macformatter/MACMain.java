package macformatter;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;

import dmca.Checklist;

public class MACMain extends JPanel
{
	private static final long serialVersionUID = -1901069829725316554L;

	public MACMain()
	{
		final JPanel inputText = new JPanel();
		final JPanel formatted = new JPanel();
		final JPanel buttons = new JPanel();
		
		final JTextField original = new JTextField();
		final JTextField split34 = new JTextField();
		final JTextField split2c6 = new JTextField();
		final JTextField split2d6 = new JTextField();
		final JButton submit = new JButton("Submit");
		final JButton clear = new JButton("Clear");
		final JButton copy34 = new JButton("Copy To Clipboard");
		final JButton copy2c6 = new JButton("Copy To Clipboard");
		final JButton copy2d6 = new JButton("Copy To Clipboard");
		final JButton copyCheck = new JButton("Copy To Checklist");
		final JCheckBox capital = new JCheckBox("Capitalize Letters");
		
		final GridLayout formLayout = new GridLayout(3, 4);
//		final GridBagLayout formLayout = new GridBagLayout();
//		formLayout.columnWeights = new double[]{0.0f, 2.0f, 1.0f};
		formLayout.setHgap(5);
		formLayout.setVgap(2);
		
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		setPreferredSize(new Dimension(525, 150));
		inputText.setPreferredSize(new Dimension(500, 50));
		formatted.setPreferredSize(new Dimension(500, 75));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		formatted.setLayout(formLayout);
		//formatted.setLayout(new GridBagLayout());
//		formatted.setLayout(new BoxLayout(formatted, BoxLayout.Y_AXIS));
		
		original.setColumns(25);
		split34.setEditable(false);
		split2c6.setEditable(false);
		split2d6.setEditable(false);
		
		inputText.add(new JLabel("Input MAC Address:"));
		inputText.add(original);
		
		formatted.add(new JLabel("xxxx.xxxx.xxxx"));
		formatted.add(split34);
		formatted.add(copy34);
		
		formatted.add(new JLabel("xx:xx:xx:xx:xx:xx"));
		formatted.add(split2c6);
		formatted.add(copy2c6);
		
		formatted.add(new JLabel("xx-xx-xx-xx-xx-xx"));
		formatted.add(split2d6);
		formatted.add(copy2d6);
		
		buttons.add(submit);
		buttons.add(clear);
		buttons.add(copyCheck);
		buttons.add(capital);
		
		add(inputText);
		add(formatted);
		add(buttons);
		
		//ActionListeners
		final ActionListener submitAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String text = original.getText();
					text = text.toLowerCase();
					String [] formats = Formatter.format(text);
					split34.setText(formats[0]);
					split2c6.setText(formats[1]);
					split2d6.setText(formats[2]);
					original.setText(text.toLowerCase());
					original.requestFocusInWindow();
				}
				catch(IllegalArgumentException err)
				{
					JOptionPane.showMessageDialog(null, "Error! The MAC address you inputted is invalid." +
							"\nCheck to see if you have all the characters and it is in hexadecimal.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		final ActionListener clearAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(JOptionPane.YES_OPTION ==
					JOptionPane.showConfirmDialog(null, "This will clear all text fields.\nWould you like to continue?"))
				{
					original.setText("");
					split34.setText("");
					split2c6.setText("");
					split2d6.setText("");
					original.requestFocusInWindow();
				}
			}
		};
		final ActionListener capitalAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				split34.setText(capital.isSelected() ? split34.getText().toUpperCase() : split34.getText().toLowerCase());
				split2c6.setText(capital.isSelected() ? split2c6.getText().toUpperCase() : split2c6.getText().toLowerCase());
				split2d6.setText(capital.isSelected() ? split2d6.getText().toUpperCase() : split2d6.getText().toLowerCase());
				original.requestFocusInWindow();
			}
		};
		final ActionListener copy34Action = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clipboard c = new JFrame().getToolkit().getSystemClipboard();
				StringSelection data = new StringSelection(split34.getText());
				c.setContents(data, data);
				//JOptionPane.showMessageDialog(null, "xxxx.xxxx.xxxx format has been copied to the clipboard.");
				original.requestFocusInWindow();
			}
		};
		final ActionListener copy2c6Action = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clipboard c = new JFrame().getToolkit().getSystemClipboard();
				StringSelection data = new StringSelection(split2c6.getText());
				c.setContents(data, data);
				//JOptionPane.showMessageDialog(null, "xx:xx:xx:xx:xx:xx format has been copied to the clipboard.");
				original.requestFocusInWindow();
			}
		};
		final ActionListener copy2d6Action = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clipboard c = new JFrame().getToolkit().getSystemClipboard();
				StringSelection data = new StringSelection(split2d6.getText());
				c.setContents(data, data);
				//JOptionPane.showMessageDialog(null, "xx-xx-xx-xx-xx-xx format has been copied to the clipboard.");
				original.requestFocusInWindow();
			}
		};
		
		final ActionListener copyCheckAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				submitAction.actionPerformed(e);
				String mac = split2c6.getText().toLowerCase();
				Checklist.addMacAddr(mac);
			}
		};
		
		class CopyAct extends AbstractAction
		{
			private static final long serialVersionUID = 1L;
			String toCopy = new String();
			public CopyAct(String arg)
			{
				toCopy = arg;
			}
			public void actionPerformed(ActionEvent e) 
			{
				if(toCopy.equals("copy34"))
					copy34Action.actionPerformed(e);
				else if(toCopy.equals("copy2c6"))
					copy2c6Action.actionPerformed(e);
				else if(toCopy.equals("copy2d6"))
					copy2d6Action.actionPerformed(e);
			}
		}
		
		final int modKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('1', modKey), "copy34");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('2', modKey), "copy2c6");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('3', modKey), "copy2d6");
		getActionMap().put("copy34", new CopyAct("copy34"));
		getActionMap().put("copy2c6", new CopyAct("copy2c6"));
		getActionMap().put("copy2d6", new CopyAct("copy2d6"));
		submit.addActionListener(submitAction);
		original.addActionListener(submitAction);
		clear.addActionListener(clearAction);
		capital.addActionListener(capitalAction);
		copy34.addActionListener(copy34Action);
		copy2c6.addActionListener(copy2c6Action);
		copy2d6.addActionListener(copy2d6Action);
		copyCheck.addActionListener(copyCheckAction);
		original.requestFocusInWindow();
	}
}