package com.company.resume.panel;

import java.sql.Date;
import java.text.ParseException;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.company.entity.User;
import com.company.resume.config.Config;


public class ProfilePanel extends JPanel {
	private JTextArea txtAreaProfile;

	/**
	 * Create the panel.
	 */
	public ProfilePanel() {
		initComponents();
	}
	
	public void fillComponents(){
		txtAreaProfile.setText(Config.loggedInUser.getProfileDescription());
	}
	
	public void fillUser(User u){
			String profileDescription=txtAreaProfile.getText();
			u.setProfileDescription(profileDescription);
	}
	
	private void initComponents(){
	setLayout(null);
		
	txtAreaProfile = new JTextArea();
	txtAreaProfile.setTabSize(11);
	txtAreaProfile.setBounds(3, 8, 541, 270);
	add(txtAreaProfile);
	txtAreaProfile.setRows(7);
	}

}
