package dmca;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import java.util.regex.*;

public class TimePane extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final static JTextField time = new JTextField();
	private final static JTextField trans = new JTextField();
	private final static JButton submit = new JButton("Submit");
	private final static JLabel original = new JLabel("Original Time");
	private final static JLabel translated = new JLabel("Converted Time");
	
	private final static JPanel left = new JPanel();
	private final static JPanel right = new JPanel();
	private final static JLabel previous = new JLabel("Previous Day: ");
	private final static JLabel roughLabel = new JLabel("Rough Hour Converter: Input times in xx:xx:xx format.");
	//private final static JLabel instr = new JLabel("Input only times in xx:xx:xx format.");
	private final static JTextField hour = new JTextField();
	private final static JTextField target = new JTextField();
	private final static String[] originTimeZones = {"GMT / UTC", "EST", "EDT"};
	private final static String[] targetTimeZones = {"PST", "PDT", "GMT / UTC"}; 
	private final static JComboBox originZones = new JComboBox(originTimeZones);
	private final static JComboBox targetZones = new JComboBox(targetTimeZones);
	private final static JButton sub = new JButton("Submit");
	
	public TimePane()
	{
		//setPreferredSize(new Dimension(525, 160));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		trans.setEditable(false);
		target.setEditable(false);
		GridLayout master = new GridLayout(1, 2);
		GridLayout lr = new GridLayout(5, 1);
		master.setHgap(3);
		lr.setVgap(3);
		setLayout(master);
		left.setLayout(lr);
		
		left.add(original);
		left.add(time);
		left.add(translated);
		left.add(trans);
		left.add(submit);
		
		JPanel top = new JPanel();
		//top.setLayout(new GridLayout(2, 1));
		top.add(roughLabel);
		//top.add(instr);
		
		JPanel mid1 = new JPanel();
		mid1.setLayout(new GridLayout(1, 2));
		mid1.add(hour);
		mid1.add(originZones);
		JPanel mid2 = new JPanel();
		//mid.add(new JLabel(" to "));
		mid2.setLayout(new GridLayout(1, 2));
		mid2.add(target);
		mid2.add(targetZones);
		
		JPanel bot = new JPanel();
		bot.setLayout(new GridLayout(1, 1));
		bot.add(previous);
		bot.add(sub);
		right.setLayout(lr);
		right.add(top);
		right.add(mid1);
		right.add(mid2);
		right.add(previous);
		right.add(sub);
		
		add(left);
		add(right);
		
		ActionListener submitAction = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String t = time.getText().trim();
				if(t.length() == 0)
					return;
				if(TimeConverter.parseOther(t) == null)
					trans.setText("Unparsable - Send date to stt009@ucsd.edu");
				else
					trans.setText(TimeConverter.parseOther(t));
			}
		};
		
		ActionListener roughSubmit = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int originZone = originZones.getSelectedIndex();
				int targetZone = targetZones.getSelectedIndex();
				int mod = 0;
				if(originZone == 0)
					mod = targetZone == 0 ? -8 : targetZone == 1 ? -7 : 0;
				else if(originZone == 1)
					mod = targetZone == 0 ? -3 : targetZone == 1 ? -2 : 5;
				else // originZone == 2)
					mod = targetZone == 0 ? -4 : targetZone == 1 ? -3 : 4;
				String[] nums = hour.getText().split(Pattern.quote(":"));
				int hour = Integer.parseInt(nums[0]) + mod;
				if(hour < 0)
				{
					previous.setText("Previous Day: Yes");
					hour += 24;
				}
				else
					previous.setText("Previous Day: No");
				nums[0] = ((Integer)hour).toString();
				if(nums[0].length() == 1)
					nums[0] = "0" + nums[0];
				target.setText(nums[0] + ":" + nums[1] + ":" + nums[2]);
			}
		};
		
		sub.addActionListener(roughSubmit);
		hour.addActionListener(roughSubmit);
		submit.addActionListener(submitAction);
		time.addActionListener(submitAction);
	}
	
	public static void reset()
	{
		trans.setText("");
		time.setText("");
		hour.setText("");
		target.setText("");
		previous.setText("Previous Day: ");
	}
}