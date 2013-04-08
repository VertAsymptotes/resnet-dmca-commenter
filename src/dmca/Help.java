package dmca;

import java.awt.Font;
import java.util.Calendar;

import javax.swing.*;

public class Help extends JPanel
{
	private static final long serialVersionUID = -1006443173160339246L;

	public Help(int row, int col)
	{
		final JTextArea helpArea = new JTextArea(row, col);
		final JScrollPane help = new JScrollPane(helpArea);
		helpArea.setFont(new Font("Dialog",Font.PLAIN,12));
		helpArea.setLineWrap(true);
		helpArea.setWrapStyleWord(true);
		helpArea.setEditable(false);
		Calendar c = Calendar.getInstance();
		String date = (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
		
		helpArea.setText(
				"This program is the UCSD ResNet DMCA Commenter and MAC Address Formatter, " +
				"designed to assist the technician in processing DMCA blocks.\n\n" +
						
				"DMCA Commenter Instructions:\n" + 
				"The DMCA Commenter requires the technician's username, the infringing username " +
				"the Sponge record number, and violation type (1st / 2nd / 3rd).\n\n" +
				
				"Useful shortcuts: \n" +
				"Ctrl / Cmd + 1 - Copies ALEN Comments\n" +
				"Ctrl / Cmd + 2 - Copies NetApps Comments\n" +
				"Ctrl / Cmd + 3 - Copies Uberblock Comments\n" +
				"Ctrl / Cmd + N - Copies Notes\n" +
				"Ctrl / Cmd + U - Copies Username\n" +
				"Ctrl / Cmd + T - Copies Ticket Number\n" +
				"Ctrl / Cmd + E - Copies Technician Username\n" +
				"Ctrl / Cmd + R - Resets All Fields except for Technician\n\n" +
				
				"Formats for each comment:\n\n" +
				"Netapps:\n" +
				"     DMCA Copyright Violation - Please contact the ACMS Help Desk at 858-534-2267\n" +
				"     Username: <infringing user>\n" +
				"     Ticket #: <Sponge ticket number>\n\n" +
				"     Example:\n" +
				"     DMCA Copyright Violation - Please contact the ACMS Help Desk at 858-534-2267\n" + 
				"     Username: gbluefin\n" + 
				"     Ticket #: 1000\n\n" +
				"ALEN:\n" + 
				"     DMCA Block - ACMS; <1st / 2nd / 3rd> Vio - <technician's username> - <date>\n" +
				"     Example: DMCA Block - ACMS; 1st Vio - stt009 - " + date + "\n\n" +
				"Uberblock / Ubersearch:\n" +
				"     DMCA <1st / 2nd / 3rd> Vio - <technician's username> <date>\n" +
				"     Example: DMCA 1st Vio - stt009 - " + date + "\n\n" + 
				
				"Time Converter Instructions:\n" +
				"There are two different ways to translate times: the first section allows for " +
				"straight copying and pasting from the Sponge record into the program; it will " +
				"attempt to parse the date and return a date translated into PST or PDT, depending " +
				"on when the date falls into the standard US calendar.\n" +
				"The second section allows for user inputted times in xx:xx:xx format and conversion " +
				"between the most common time zones used in DMCA processing. This can be used in error " +
				"checking the first section or if the first section cannot parse the given date. Any " +
				"incorrectly parsed dates or unparsable dates should be reported to stevent@ucsd.edu and " +
				"subsequent updates will have improved tolerance for different date formats.\n\n" + 
				
				"MAC Address Formatter Instructions:\n" +
				"To use the MAC address formatter, input the MAC address into the text field." +
				"The program will then verify that the MAC address has the correct number " +
				"of characters and is in hexadecimal format. To copy the formats, you can " +
				"click the corresponding copy button or the following buttons:\n" +
				"Ctrl / Cmd + 1 - Copies the xxxx.xxxx.xxxx format\n" +
				"Ctrl / Cmd + 2 - Copies the xx:xx:xx:xx:xx:xx format\n" +
				"Ctrl / Cmd + 3 - Copies the xx-xx-xx-xx-xx-xx format\n\n" +
				
				"If you have suggestions for improvement or you find any bugs, please email " +
				"stevent@ucsd.edu with a report on the bug or improvement.\n\n" +
				
				"ver. 2.0.2 (c) Steven Tan, 2011-2012, University of California, San Diego.\n" +
				"Academic Computing and Media Services, Residential Networking\n" +
				"This program is free for use, with due credit to the author.\n\n" +
				
				"Change Log from 2.0.1 to 2.0.2:\n" +
				"Fixed version number in documentation\n" +
				"Changed WCS to NCS\n" +
				"Removed MAC Formatter Copy notification\n" +
				"");
		add(help);
	}
}