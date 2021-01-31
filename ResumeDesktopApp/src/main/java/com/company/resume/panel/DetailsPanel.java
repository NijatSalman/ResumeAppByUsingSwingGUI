package com.company.resume.panel;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.company.entity.Country;
import com.company.entity.User;
import com.company.main.Context;
import com.company.resume.config.Config;

public class DetailsPanel extends JPanel {

	private JComboBox<Country> cbNationality;
	private JComboBox<Country> cbBirthplace;
	
	private JTextField textAddress;
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JTextField txtBirthdate;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Create the panel.
	 */
	public DetailsPanel() {
		
		initComponents();
		
		//fillComponents();
	}
	
	private void loadCombobox(){
		List<Country> countries=Context.countryDaoInstance().getAllCountry();
		for (Country obj : countries) {
			cbBirthplace.addItem(obj);
			cbNationality.addItem(obj);
		}
	}
	
	public void fillComponents(){
		loadCombobox();
		User loggedInUser=Config.loggedInUser;
		txtEmail.setText(loggedInUser.getEmail());
		textAddress.setText(loggedInUser.getAddress());
		txtPhoneNumber.setText(loggedInUser.getPhone());	
		txtBirthdate.setText(sdf.format(loggedInUser.getBirthDate()));
		cbBirthplace.setSelectedItem(loggedInUser.getBirthPlace());
		cbNationality.setSelectedItem(loggedInUser.getNationality());
	}
	
	public void fillUser(User u){ // this method fills user object which helps you to reach data from main class
		try {
		String email=txtEmail.getText();
		String phoneNumber=txtPhoneNumber.getText();
		String address=textAddress.getText();
		String strBirthdate=txtBirthdate.getText();
		
		u.setEmail(email);
		u.setPhone(phoneNumber);
		u.setAddress(address);
		
			Long l=Config.sdf.parse(strBirthdate).getTime();
			Date date = new Date(l);
			u.setBirthDate(date);
			
			Country countryObj=(Country)cbBirthplace.getSelectedItem();
			Country nationalityObj=(Country)cbNationality.getSelectedItem();
			u.setBirthPlace(countryObj);
			u.setNationality(nationalityObj);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void initComponents(){
		setLayout(null);
		JPanel pnlDetails = new JPanel();
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 11, 86, 14);
		add(lblAddress);
		
		textAddress = new JTextField();
		textAddress.setBounds(136, 11, 182, 20);
		add(textAddress);
		textAddress.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 37, 86, 14);
		add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(136, 37, 182, 20);
		add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone number");
		lblPhoneNumber.setBounds(10, 65, 86, 14);
		add(lblPhoneNumber);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(136, 65, 182, 20);
		add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);
		
		JLabel lblBirthdate = new JLabel("Birthdate");
		lblBirthdate.setBounds(10, 93, 86, 14);
		add(lblBirthdate);
		
		txtBirthdate = new JTextField();
		txtBirthdate.setBounds(136, 93, 182, 20);
		add(txtBirthdate);
		txtBirthdate.setColumns(10);
		
		JLabel lblBirthplace = new JLabel("Birthplace");
		lblBirthplace.setBounds(10, 123, 86, 14);
		add(lblBirthplace);
		
		JLabel lblNationality = new JLabel("Nationality");
		lblNationality.setBounds(10, 151, 86, 14);
		add(lblNationality);
		
		cbBirthplace = new JComboBox();
		cbBirthplace.setBounds(136, 121, 182, 20);
		add(cbBirthplace);
		
		 cbNationality = new JComboBox();
		cbNationality.setBounds(136, 151, 182, 20);
		add(cbNationality);
	}
}
