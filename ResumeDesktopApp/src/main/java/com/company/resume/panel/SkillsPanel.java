package com.company.resume.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.company.dao.inter.SkillDaoInter;
import com.company.dao.inter.UserSkillDaoInter;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.main.Context;
import com.company.resume.config.Config;

import javax.swing.JSlider;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SkillsPanel extends JPanel {
	private SkillDaoInter skillDao=Context.skillDaoInstance();
	private UserSkillDaoInter userSkillDao=Context.userSkillDaoInstance();
	private JTextField txtSkill;
	private JComboBox cbSkill;
	private JTable tblSkill;
	private JPanel panel;
	private List<UserSkill> userSkillList;
	private JSlider sliderPower;
	private List<Skill> skills;
	private JButton btnSave;
	/**
	 * Create the panel.
	 */
	public SkillsPanel() {
		initComponents();
		//loadCombobox();
	}
	
	private void loadCombobox(){
		cbSkill.removeAllItems();
		skills=skillDao.getAllSkill();	
		for (Skill item : skills) {
			cbSkill.addItem(item);
		}
		loadTable();
	}
	
	public void fillComponents(){
		loadCombobox();
	}
	
	private void loadTable(){
		DefaultTableModel model=new DefaultTableModel();
		User user=Config.loggedInUser;
		int id=user.getId();
		userSkillList=userSkillDao.getAllSkillByUserId(id);
		
		Vector<String> columns=new Vector<>();
		columns.add("Skill");
		columns.add("Power");	
		
		Vector<Vector> rows=new Vector<>();
		for (UserSkill item : userSkillList) {
			Vector row=new Vector<>();
			row.add(item.getSkill());
			row.add(item.getPower());
			rows.add(row);
		}
		
	
		model.setDataVector(rows, columns);
		
		tblSkill.setModel(model);
	}
	
	private void initComponents(){	
		setLayout(null);
			
		panel = new JPanel();
		panel.setBounds(10, 11, 540, 77);
		add(panel);
		panel.setLayout(null);	
		
		JLabel lblSkill = new JLabel("Skill");
		lblSkill.setBounds(10, 11, 46, 14);
		panel.add(lblSkill);
		
		txtSkill = new JTextField();
		txtSkill.setBounds(174, 8, 94, 20);
		panel.add(txtSkill);
		txtSkill.setColumns(10);
		
		JLabel lblPower = new JLabel("power");
		lblPower.setBounds(319, 11, 46, 14);
		panel.add(lblPower);
		
		sliderPower = new JSlider();
		sliderPower.setPaintTicks(true);
		sliderPower.setValue(1);
		sliderPower.setMinimum(1);
		sliderPower.setMaximum(10);
		sliderPower.setBounds(370, 11, 138, 26);
		panel.add(sliderPower);
		
		cbSkill = new JComboBox();
		cbSkill.setBounds(42, 8, 122, 20);
		panel.add(cbSkill);
		
		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String skillName=txtSkill.getText();
				Skill skill=null;
				if(skillName!=null && !skillName.trim().isEmpty()){
					skill=new Skill(null,skillName);
					skillDao.addSkill(skill);
				}else{
					skill=(Skill) cbSkill.getSelectedItem();
				}
				int power=sliderPower.getValue();
				UserSkill us=new UserSkill(null, Config.loggedInUser, skill, power);
				userSkillDao.insertUserSkill(us);
				//loadTable();
				loadCombobox();
			}
		});
		btnAdd.setBounds(10, 51, 46, 23);
		panel.add(btnAdd);
		
		JButton btnRemove = new JButton("-");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=tblSkill.getSelectedRow();
				UserSkill us=userSkillList.get(index);
				userSkillDao.removeUserSkill(us.getId());
				loadTable();
			}
		});
		btnRemove.setBounds(66, 51, 46, 23);
		panel.add(btnRemove);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserSkill us=userSkillList.get(tblSkill.getSelectedRow());
				String skillName=txtSkill.getText();
				Skill skill=null;
				if(skillName!=null && !skillName.trim().isEmpty()){
					skill=new Skill(null,skillName);
					skillDao.addSkill(skill);
				}else{
					skill=(Skill) cbSkill.getSelectedItem();
				}
				int power=sliderPower.getValue();
				us.setPower(power);
				us.setSkill(skill);
				userSkillDao.updateUserSkill(us);
				loadCombobox();
			}
		});
		btnSave.setBounds(135, 51, 71, 23);
		panel.add(btnSave);
		
		tblSkill = new JTable();
		tblSkill.setBounds(10, 90, 540, 200);
		add(tblSkill);
		
	}
}
