package soulpatch.com.utilities.pwdgen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class PasswordGenerator implements ActionListener {

	static String password = "";
	JTextArea log;
	JButton search;
	JPanel panel = new JPanel();

	public static void main(String args[]) {
		PasswordGenerator pg = new PasswordGenerator();
		pg.createAndShowGUI();
	}

	public void generate() {
		password = "";
		int i = 0;
		while (i < Utils.pwdLen) {
			int rand = random(Utils.asciiStart, Utils.asciiEnd);
			// Allow characters only if they're among the following
			// [a-z][A-Z][0-9][#$%&@]. Otherwise generate a new number.
			if ((rand >= 65 && rand <= 90) || (rand >= 97 && rand <= 122)
					|| (rand >= 35 && rand <= 38) || (rand >= 48 && rand <= 57)
					|| (rand == 64)) {
				char ch = (char) (rand);
				password = password + ch;
				i++;
			}
		}
		System.out.println(password);
	}

	/**
	 * Validate password to see if it has at least
	 */
	public boolean validate(String pwd) {
		boolean upperCase = false;
		boolean specialChar = false;
		boolean number = false;
		if (pwd == null) // Check for null
			return false;
		if (pwd.length() < 6) // Check for length of password. Minimum 6
			// characters. Maximum anything.
			return false;
		for (int i = 0; i < pwd.length(); i++) {
			int ascii = (int) pwd.charAt(i);
			if (ascii >= 65 && ascii <= 90) { // Check if at least one character
				// is uppercase.
				upperCase = true;
			}
			if ((ascii >= 35 && ascii <= 38) || (ascii == 64)) { // Check if at
				// least one
				// special
				// character
				specialChar = true;
			}
			if (ascii >= 48 && ascii <= 57) { // Check if at least one number
				number = true;
			}
		}
		if (upperCase) {
			if (specialChar) {
				if (number) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// Generate a random number between min and max.
	public int random(int min, int max) {
		int rand = (int) (min + (Math.random() * (max - min + 1)));
		return rand;
	}

	private void createAndShowGUI() {

		// Create and populate the panel.
		panel = new JPanel(new SpringLayout());
		panel.setSize(800, 800);
		panel.add(Utils.passwordLen);
		Utils.passwordLen.setLabelFor(Utils.passwordText);
		panel.add(Utils.passwordText);

		// Create and set up the window.
		JFrame frame = new JFrame("Password Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		search = new JButton("Search");
		search.addActionListener(this);

		panel.add(search);
		panel.add(Utils.password);

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(panel, 2, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		// Set up the content pane.
		panel.setOpaque(true); // content panes must be opaque
		frame.setContentPane(panel);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		try{
			password = "";
			Utils.pwdLen = Integer.parseInt(Utils.passwordText.getText());
			if (e.getSource() == this.search) {
				PasswordGenerator pwdGen = new PasswordGenerator();
				if(Utils.pwdLen < 6 ){
					JOptionPane.showMessageDialog(panel, "Password length not enough.");
				}else{
					while (!pwdGen.validate(password)) {
						pwdGen.generate();
					}
					Utils.password.setText(password);
				}
			}
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(panel, "Password length not entered.");
		}
	}
}
