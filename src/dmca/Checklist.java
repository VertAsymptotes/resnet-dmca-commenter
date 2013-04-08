package dmca;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.util.*;

public class Checklist extends JPanel
{
	private final static long serialVersionUID = 1L;
	private final static JPanel left = new JPanel();
	private final static JPanel right = new JPanel();
	private final static JPanel labels = new JPanel();
		
	private final static JPanel macList = new JPanel();
	private final static JScrollPane macScroll = new JScrollPane(macList);
	private final static JTextField insertMac = new JTextField();
	private final static JButton submit = new JButton("Add MAC");
	private final static JButton remove = new JButton("Remove MAC");
	private final static JPanel macLine = new JPanel();
	
	private final static JCheckBox verify = new JCheckBox("Verified Violation?");
	private final static JCheckBox fillInfo = new JCheckBox("Filled in Sponge Info");
	private final static JCheckBox blAlenUser = new JCheckBox("Blocked Username on Alen?");
	private final static JCheckBox blNetUser = new JCheckBox("Blocked Username on Netapps?");
	private final static JCheckBox blUberUser = new JCheckBox("Blocked Username on Uberblock?");
	private final static JCheckBox kickWCS = new JCheckBox("Removed Username from WCS?");
	private final static JCheckBox blOldUberUser = new JCheckBox("Old Username Uberblock");
	
	private final static ArrayList <String> macAddrs = new ArrayList<String> ();
	private final static JTextArea noteArea = new JTextArea(5, 50);//WHEEEEEEE
	private final static JScrollPane textArea = new JScrollPane(noteArea);
	private final static JPanel top = new JPanel();
	
	public Checklist()
	{
		left.setLayout(new GridLayout(8, 1));
		left.add(verify);
		left.add(fillInfo);
		left.add(kickWCS);
		left.add(blAlenUser);
		left.add(blNetUser);
		left.add(blUberUser);
		left.add(blOldUberUser);
		left.add(new JLabel("Additional Notes"));
		
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(labels);
		right.add(macScroll);
		right.add(macLine);
		
		blOldUberUser.setEnabled(blUberUser.isSelected());
		
		macLine.setLayout(new BoxLayout(macLine, BoxLayout.X_AXIS));
		
		macLine.add(insertMac);
		macLine.add(submit);
		//macLine.add(remove);
		
		labels.add(new JLabel("MAC Address List"));
		
		macList.setLayout(new BoxLayout(macList, BoxLayout.PAGE_AXIS));
		macScroll.setPreferredSize(new Dimension(575, 130));
		macScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.add(left);
		top.add(right);
		
		noteArea.setLineWrap(true);
		noteArea.setWrapStyleWord(true);
		textArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		insertMac.setMinimumSize(new Dimension(450, 26));
		insertMac.setPreferredSize(new Dimension(450, 26));
		insertMac.setMaximumSize(new Dimension(450, 26));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(top);
		add(textArea);
		
		ActionListener oldAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				blOldUberUser.setEnabled(blUberUser.isSelected());
				if(!blOldUberUser.isEnabled())
					blOldUberUser.setSelected(false);
			}
		};
		ActionListener submitAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String mac = insertMac.getText();
					mac = macformatter.Formatter.format(mac.toLowerCase())[1];
					if(macAddrs.contains(mac))
					{
						JOptionPane.showMessageDialog(null, "Error! MAC Address is already in list!");
						return;
					}
					final JPanel toAdd = new JPanel();
					toAdd.setMinimumSize(new Dimension(550, 26));
					toAdd.setPreferredSize(new Dimension(550, 26));
					toAdd.setMaximumSize(new Dimension(550, 26));
					toAdd.setLayout(new BoxLayout(toAdd, BoxLayout.X_AXIS));
					final JTextField m = new JTextField(mac);
					final JButton copy = new JButton("Copy MAC");
					final JButton remove = new JButton("Remove MAC");
					m.setColumns(10);
					m.setEditable(false);
					toAdd.add(m);
					toAdd.add(new JCheckBox("Netapps")); //netapps
					toAdd.add(new JCheckBox("Uberblock")); //uber
					toAdd.add(copy);
					toAdd.add(remove);
					macList.add(toAdd);
					//macList.add(Box.createVerticalGlue());
					macList.updateUI();
					insertMac.setText(new String());
					
					ActionListener copyAction = new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							Clipboard c = new JFrame().getToolkit().getSystemClipboard();
							StringSelection data = new StringSelection(m.getText());
							c.setContents(data, data);
							DMCACommenter.updateCopy(m.getText());
						}
					};
					
					ActionListener removeAction = new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							macList.remove(macAddrs.indexOf(m.getText()));
							macList.updateUI();
							macAddrs.remove(m.getText());
						}
					};
					
					copy.addActionListener(copyAction);
					remove.addActionListener(removeAction);
					macAddrs.add(mac);
				}
				catch(IllegalArgumentException err)
				{
					JOptionPane.showMessageDialog(null, "Error! The MAC address you inputted is invalid.\n" +
							"Please confirm that you have valid characters.");
				}
			}
		};
		ActionListener removeAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String mac = insertMac.getText();
					mac = macformatter.Formatter.format(mac.toLowerCase())[1];
					if(!macAddrs.contains(mac))
					{
						JOptionPane.showMessageDialog(null, "Error! MAC Address does not exist in list!");
						return;
					}
					macList.remove(macAddrs.indexOf(mac));
					macList.updateUI();
					insertMac.setText(new String());
					macAddrs.remove(mac);
				}
				catch(IllegalArgumentException err)
				{
					JOptionPane.showMessageDialog(null, "Error! The MAC address you inputted is invalid.\n" +
							"Please confirm that you have valid characters.");
				}
			}
		};
		
		final UndoManager undo = new UndoManager();
		class UndoAct extends AbstractAction
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					undo.undo();
				}
				catch(Exception err)
				{
					
				}
			}
		}
		
		noteArea.getDocument().addUndoableEditListener(new UndoableEditListener()
		{
			public void undoableEditHappened(UndoableEditEvent un) 
			{
				undo.addEdit(un.getEdit());
			}
		});
		
		blUberUser.addActionListener(oldAction);
		insertMac.addActionListener(submitAction);
		submit.addActionListener(submitAction);
		remove.addActionListener(removeAction);
		
		final int modKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('Z', modKey), "undo");
		getActionMap().put("undo", new UndoAct());
	}
	public static void addMacAddr(final String mac)
	{
		if(macAddrs.contains(mac))
		{
			JOptionPane.showMessageDialog(null, "Error! MAC Address is already in list!");
			return;
		}
		final JPanel toAdd = new JPanel();
		toAdd.setMinimumSize(new Dimension(550, 26));
		toAdd.setPreferredSize(new Dimension(550, 26));
		toAdd.setMaximumSize(new Dimension(550, 26));
		toAdd.setLayout(new BoxLayout(toAdd, BoxLayout.X_AXIS));
		final JTextField m = new JTextField(mac);
		final JButton copy = new JButton("Copy MAC");
		final JButton remove = new JButton("Remove MAC");
		m.setColumns(10);
		m.setEditable(false);
		toAdd.add(m);
		toAdd.add(new JCheckBox("Netapps")); //netapps
		toAdd.add(new JCheckBox("Uberblock")); //uber
		toAdd.add(copy);
		toAdd.add(remove);
		macList.add(toAdd);
		//macList.add(Box.createVerticalGlue());
		macList.updateUI();
		insertMac.setText(new String());
		
		ActionListener copyAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clipboard c = new JFrame().getToolkit().getSystemClipboard();
				StringSelection data = new StringSelection(m.getText());
				c.setContents(data, data);
				DMCACommenter.updateCopy(m.getText());
			}
		};
		
		ActionListener removeAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				macList.remove(macAddrs.indexOf(mac));
				macList.updateUI();
				macAddrs.remove(mac);
			}
		};
		
		copy.addActionListener(copyAction);
		remove.addActionListener(removeAction);
		macAddrs.add(mac);
	}
	public static String notes()
	{
		String notes = new String();
		/*
		 * violation verified
		 * 
		 * blocked USERNAME on alen
		 * blocked USERNAME on netapps
		 * blocked USERNAME on uberblock
		 * 
		 * blocked macaddr1 on netapps
		 * blocked macaddr1 on ubersearch
		 * ...
		 */
		if(!verify.isSelected())
			JOptionPane.showMessageDialog(null, "Warning! Make sure to verify the violation,\nto ensure accuracy in processing.", "Warning!", JOptionPane.WARNING_MESSAGE);
		else
			notes += "violation verified\n\n";
		if(!fillInfo.isSelected())
			JOptionPane.showMessageDialog(null, "Did you input the Sponge info, like the name and affiliation?\nEnsure that this info is filled in and correct.", "Warning!", JOptionPane.WARNING_MESSAGE);
		if(!kickWCS.isSelected())
			notes += "USERNAME not associated on WCS\n";
			//JOptionPane.showMessageDialog(null, "Did you kick the user off of WCS? This will remove them from the network!\nAsk a lead if you don't know how.", "Warning!", JOptionPane.WARNING_MESSAGE);
		else
			notes += "kicked USERNAME off WCS\n";
		if(blAlenUser.isSelected())
			notes += "blocked USERNAME on alen\n";
		if(blNetUser.isSelected())
			notes += "blocked USERNAME on netapps\n";
		if(blUberUser.isSelected())
			notes += blOldUberUser.isSelected() ? "blocked old(USERNAME) on uberblock\n" : "blocked USERNAME on uberblock\n";
		notes += "\n";
		String netBlock = new String();
		String uberblocks = new String();
		for(int i = 0; i < macAddrs.size(); i++)
		{
			JPanel p = (JPanel) macList.getComponent(i);
			if(!((JCheckBox) p.getComponent(1)).isSelected() && !((JCheckBox) p.getComponent(2)).isSelected())
			{
				JOptionPane.showMessageDialog(null, "Warning! " +  macAddrs.get(i) +  " has not been blocked!\n" +
						"Please ensure that all MACs are blocked on Netapps and / or Ubersearch!", "Warning!", JOptionPane.WARNING_MESSAGE);
				return null;
			}
			else
			{
				if(((JCheckBox) p.getComponent(1)).isSelected())
					netBlock += new String("blocked " + macAddrs.get(i) + " on netapps\n");
				if(((JCheckBox) p.getComponent(2)).isSelected())
					uberblocks += new String("blocked " + macAddrs.get(i) + " on uberblock\n");
			}
		}
		if(!netBlock.equals(""))
			notes += netBlock;
		if(!uberblocks.equals(""))
			notes += "\n" + uberblocks;
		//notes += netBlock + "\n" + uberblocks;
		if(!noteArea.getText().trim().equals(""))
			notes += "\nAdditional Notes:\n" + noteArea.getText();
		return notes.trim();
	}
	public static void reset()
	{
		verify.setSelected(false);
		fillInfo.setSelected(false);
		blAlenUser.setSelected(false);
		blNetUser.setSelected(false);
		blUberUser.setSelected(false);
		blOldUberUser.setSelected(false);
		kickWCS.setSelected(false);
		blOldUberUser.setEnabled(false);
		noteArea.setText(new String());
		macList.removeAll();
		macAddrs.clear();
		macList.updateUI();
	}
}