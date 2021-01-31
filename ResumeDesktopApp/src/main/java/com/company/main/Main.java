package com.company.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.company.dao.inter.UserDaoInter;
import com.company.entity.Country;
import com.company.entity.User;
import com.company.resume.config.Config;
import com.company.resume.panel.DetailsPanel;
import com.company.resume.panel.HistoryEmploymentPanel;
import com.company.resume.panel.ProfilePanel;
import com.company.resume.panel.SkillsPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField textSurname;
	private JSeparator separator;
	private JTabbedPane tpUserInfo;
	private JTextArea txtAreaProfile;
	private JComboBox cbNationality;
	private JComboBox<Country> cbBirthplace;
	private DetailsPanel pnlDetails=new com.company.resume.panel.DetailsPanel();
	private ProfilePanel pnlProfile = new com.company.resume.panel.ProfilePanel();
	private SkillsPanel pnlSkills =new com.company.resume.panel.SkillsPanel();
	private HistoryEmploymentPanel pnlHistoryEmployment =new com.company.resume.panel.HistoryEmploymentPanel();
	/**
	 * @author Nijat Salmanov
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private UserDaoInter userDao=Context.userDaoInstance();
	
	private JTextField textAddress;
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JTextField txtBirthdate;
	
	
	public Main() {
		Config.loggedInUser=userDao.getById(7);
		initComponents();
		
		fillUserComponents();
		pnlDetails.fillComponents();
		pnlProfile.fillComponents();
		pnlSkills.fillComponents();
		pnlHistoryEmployment.fillComponents();
	}
	
	
	private void fillUserComponents(){
		txtName.setText(Config.loggedInUser.getName());
		textSurname.setText(Config.loggedInUser.getSurname());
	}
	
	private void initComponents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 587, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlUserInfo = new JPanel();
		pnlUserInfo.setBounds(0, 11, 279, 86);
		contentPane.add(pnlUserInfo);
		pnlUserInfo.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 11, 46, 21);
		pnlUserInfo.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 43, 57, 14);
		pnlUserInfo.add(lblSurname);
		
		txtName = new JTextField();
		txtName.setBounds(77, 11, 86, 20);
		pnlUserInfo.add(txtName);
		txtName.setColumns(10);
		
		textSurname = new JTextField();
		textSurname.setBounds(77, 40, 86, 20);
		pnlUserInfo.add(textSurname);
		textSurname.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User userObj=Config.loggedInUser;
				String name=txtName.getText();
				String surname=textSurname.getText();
				
				userObj.setName(name);
				userObj.setSurname(surname);
				pnlDetails.fillUser(userObj);
				pnlProfile.fillUser(userObj);
				
				userDao.updateUser(userObj);
			}
		});
		btnSave.setBounds(180, 55, 89, 23);
		pnlUserInfo.add(btnSave);
		
		separator = new JSeparator();
		separator.setBounds(10, 102, 551, 8);
		contentPane.add(separator);
		
		tpUserInfo = new JTabbedPane(JTabbedPane.TOP);
		tpUserInfo.setBounds(10, 108, 541, 272);
		contentPane.add(tpUserInfo);
			
		tpUserInfo.addTab("Profile", null, pnlProfile, null);			
		tpUserInfo.addTab("Details", null, pnlDetails, null);		
		tpUserInfo.addTab("Skills", null, pnlSkills, null);	
		tpUserInfo.addTab("History Employment", null, pnlHistoryEmployment, null);
		
	}
}
