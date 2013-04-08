package dmca;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.text.JTextComponent;

public class DMCACommenter extends JPanel
{
	private static final long serialVersionUID = 7705814330424158073L;
	final static JLabel lastCopy = new JLabel("");
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	public DMCACommenter()
	{
		final JButton alen = new JButton("Copy ALEN");
		final JButton netapps = new JButton("Copy NetApps");
		final JButton uberblock = new JButton("Copy UberBlock");
		final JButton noteCopy = new JButton("Copy Notes");
		final JButton reset = new JButton("Reset Fields");
		
		final JButton copyUser = new JButton("Copy");
		final JButton copyTicket = new JButton("Copy");
		final JButton copyTech = new JButton("Copy");
		
		final JLabel userLabel = new JLabel("Username");
		final JLabel techLabel = new JLabel("Technician");
		final JLabel ticketNum = new JLabel("Ticket No.");
		final JLabel vioNum = new JLabel("Violation");
		final JLabel copyName = new JLabel("Last Copied");
		
		final JPanel userArea = new JPanel();
		final JPanel ticketArea = new JPanel();
		final JPanel techArea = new JPanel();
		
		final JTextField userLine = new JTextField();
		final JTextField techLine = new JTextField();
		final JTextField ticketLine = new JTextField();
		
		final String [] vios = {"1st", "2nd", "3rd"};
		final JComboBox vioList = new JComboBox(vios);
		//final JTextArea noteArea = new JTextArea(7, 50);//WHEEEEEEE
		//final JScrollPane textArea = new JScrollPane(noteArea);
		
		final JPanel left = new JPanel();
		final JPanel middle = new JPanel();
		final JPanel right = new JPanel();
		final JPanel grid = new JPanel();
		
		GridLayout indiv = new GridLayout(1, 2);
		userArea.setLayout(indiv);
		techArea.setLayout(indiv);
		ticketArea.setLayout(indiv);
		
		userArea.add(userLabel);
		userArea.add(copyUser);
		
		ticketArea.add(ticketNum);
		ticketArea.add(copyTicket);
		
		techArea.add(techLabel);
		techArea.add(copyTech);
		
		GridLayout sect = new GridLayout(5, 1);
		sect.setVgap(3);
		left.setLayout(sect);
		middle.setLayout(sect);
		right.setLayout(sect);
		
		left.add(userArea);
		left.add(techArea);
		left.add(ticketArea);
		left.add(vioNum);
		left.add(copyName);
		
		middle.add(userLine);
		middle.add(techLine);
		middle.add(ticketLine);
		middle.add(vioList);
		middle.add(lastCopy);
		
		right.add(alen);
		right.add(netapps);
		right.add(uberblock);
		right.add(noteCopy);
		right.add(reset);
		
		GridLayout g = new GridLayout(1, 3);
		g.setHgap(5);
		//g.setVgap(5);
		grid.setLayout(g);
		grid.add(left);
		grid.add(middle);
		grid.add(right);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(grid);
		//add(textArea);
		
		//noteArea.setLineWrap(true);
		
		class CopyAction extends AbstractAction
		{
			private static final long serialVersionUID = 1L; //warning suppression
			public String copy = new String();
			public String source = new String();
			
			public CopyAction()
			{
			}
			public void copyActionPerf()
			{
				Clipboard c = new JFrame().getToolkit().getSystemClipboard();
				StringSelection data = new StringSelection(copy);
				c.setContents(data, data);
				lastCopy.setText(source);
				//JOptionPane.showMessageDialog(null, new String(source + " have been copied to the clipboard."));
			}
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				copyActionPerf();
			}
		}
		
		final CopyAction copyAct = new CopyAction();
		
		final ActionListener copyAlenAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(techLine.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Technician's username is missing! Please input your work username.", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				copyAct.source = "ALEN";
				copyAct.copy = dmca.Comments.alenComment(techLine.getText(), (String)vioList.getSelectedItem());
				copyAct.copyActionPerf();
			}
		};
		final ActionListener copyNetappsAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(userLine.getText().equalsIgnoreCase("") || ticketLine.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Username or ticket number is missing!\nPlease input the infringer's username and Sponge ticket number.", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				copyAct.source = "NetApps";
				copyAct.copy = dmca.Comments.netappsComment(userLine.getText(), ticketLine.getText());
				copyAct.copyActionPerf();
			}
		};
		final ActionListener copyUberAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(techLine.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Technician's username is missing! Please input your work username.", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				copyAct.source = "UberBlock";
				copyAct.copy = dmca.Comments.uberComment(techLine.getText(), (String)vioList.getSelectedItem());
				copyAct.copyActionPerf();
			}
		};
		
		final ActionListener copyUserAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(userLine.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Username is missing!\nPlease input the infringer's username!", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				copyAct.source = "Username";
				copyAct.copy = userLine.getText();
				copyAct.copyActionPerf();
			}
		};
		
		final ActionListener copyTicketAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(ticketLine.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Ticket number is missing!\nPlease input the Sponge ticket number!", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				copyAct.source = "Ticket Number";
				copyAct.copy = ticketLine.getText();
				copyAct.copyActionPerf();
			}
		};
		
		final ActionListener copyTechAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(techLine.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Technician is missing!\nPlease input your work username!", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				copyAct.source = "Technician";
				copyAct.copy = techLine.getText();
				copyAct.copyActionPerf();
			}
		};
		
		final ActionListener resetAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean yes = JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "This will clear all fields, except for technician. Would you like to continue?");
				if(yes)
				{
					userLine.setText(new String());
					ticketLine.setText(new String());
					lastCopy.setText(new String());
					//noteArea.setText(new String());
					Checklist.reset();
					TimePane.reset();
				}
			}
		};
		
		final ActionListener copyNotesAction = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				copyAct.source = "Notes";
				String notes = Checklist.notes();
				if(notes == null)
					return;
				if(userLine.getText().equalsIgnoreCase(""))
				{
					JOptionPane.showMessageDialog(null, "Username is missing!\n" +
							"Please input the infringer's username.", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				copyAct.copy = notes.replace("USERNAME", userLine.getText());
				copyAct.copyActionPerf();
			}
		};
		
		class CopyKeyAction extends AbstractAction
		{
			private static final long serialVersionUID = 1L;
			String toCopy = new String();
			public CopyKeyAction(String arg)
			{
				toCopy = arg;
			}
			public void actionPerformed(ActionEvent e) 
			{
				if(toCopy.equals("alen"))
					copyAlenAction.actionPerformed(e);
				else if(toCopy.equals("netapps"))
					copyNetappsAction.actionPerformed(e);
				else if(toCopy.equals("ubersearch"))
					copyUberAction.actionPerformed(e);
				else if(toCopy.equals("notes"))
					copyNotesAction.actionPerformed(e);
				else if(toCopy.equals("username"))
					copyUserAction.actionPerformed(e);
				else if(toCopy.equals("ticket"))
					copyTicketAction.actionPerformed(e);
				else if(toCopy.equals("technician"))
					copyTechAction.actionPerformed(e);
			}
		}
		
		class ResetAction extends AbstractAction
		{
			private static final long serialVersionUID = 2043643249634711877L;

			public void actionPerformed(ActionEvent e)
			{
				resetAction.actionPerformed(e);
			}
		}
		/*
		class PasteAction extends AbstractAction
		{
			private static final long serialVersionUID = 1L;
			private JTextComponent jt;
			public PasteAction()
			{
				jt = (JTextComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
			}
			public void actionPerformed(ActionEvent e)
			{
				Clipboard c = new JFrame().getToolkit().getSystemClipboard();
				Transferable clipData = c.getContents(c);
				if (clipData != null)
				{
					try 
					{
						if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor))
						{
							String s = (String)(clipData.getTransferData(DataFlavor.stringFlavor));
							jt.replaceSelection(s);
			            }
					}
					catch (Exception err) 
					{
						System.err.println("Problems getting data:" + err);
					}
				}
			}
		}//*/
		
		alen.addActionListener(copyAlenAction);
		netapps.addActionListener(copyNetappsAction);
		uberblock.addActionListener(copyUberAction);
		reset.addActionListener(resetAction);
		noteCopy.addActionListener(copyNotesAction);
		copyUser.addActionListener(copyUserAction);
		copyTech.addActionListener(copyTechAction);
		copyTicket.addActionListener(copyTicketAction);
		
		/*
		 * 1 = alen
		 * 2 = netapps
		 * 3 = ubersearch
		 * N = notes
		 * R = reset
		 * 
		 * U = username
		 * T = ticket number
		 * E = tech (BECAUSE I CAN!) (and the T was already taken)
		 */
		final int modKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('1', modKey), "alen");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('2', modKey), "netapps");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('3', modKey), "ubersearch");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('N', modKey), "notes");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('R', modKey), "reset");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('U', modKey), "username");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('T', modKey), "ticket");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('E', modKey), "technician");
		//getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('V', modKey), "paste");
		
		getActionMap().put("alen", new CopyKeyAction("alen"));
		getActionMap().put("netapps", new CopyKeyAction("netapps"));
		getActionMap().put("ubersearch", new CopyKeyAction("ubersearch"));
		getActionMap().put("notes", new CopyKeyAction("notes"));
		getActionMap().put("username", new CopyKeyAction("username"));
		getActionMap().put("ticket", new CopyKeyAction("ticket"));
		getActionMap().put("technician", new CopyKeyAction("technician"));
		getActionMap().put("reset", new ResetAction());
		//getActionMap().put("paste", new PasteAction());
	}
	
	public static void updateCopy(String s)
	{
		lastCopy.setText(s);
		lastCopy.updateUI();
	}
}