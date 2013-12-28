package soulpatch.com.utilities.pwdgen;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Utils {
	public static int asciiStart = 32;
	public static int asciiEnd = 127;
	public static int pwdLen = 10;
	public static JLabel passwordLen = new JLabel("Password Length: ", JLabel.TRAILING);
	public static JTextField passwordText = new JTextField();
	public static JLabel password = new JLabel("                                                        ");
	
}