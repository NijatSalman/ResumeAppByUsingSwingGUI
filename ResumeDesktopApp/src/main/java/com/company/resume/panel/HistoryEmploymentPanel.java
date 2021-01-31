package com.company.resume.panel;

import javax.swing.JPanel;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.company.dao.inter.EmploymentHistoryDaoInter;
import com.company.entity.EmploymentHistory;
import com.company.entity.User;
import com.company.main.Context;
import com.company.resume.config.Config;
import com.mysql.cj.x.protobuf.MysqlxResultset.Row;

import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HistoryEmploymentPanel extends JPanel {
	private EmploymentHistoryDaoInter eHistoryDao=Context.employmentHistoryDaoInstance();
	private List<EmploymentHistory> eHistoryList;
	private JTextField txtHeader;
	private JTextField textBeginDate;
	private JTextField txtPlace;
	private JTextField txtEndDate;
	private JTable tblEmpHistory;
	private JTextArea txtJobDescription;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 private Integer selectedElementId=6;

	/**
	 * Create the panel.
	 */
	public HistoryEmploymentPanel() {
		initComponents();
		
	}
	
	private void loadTable(){
		DefaultTableModel model=new DefaultTableModel();
		User user=Config.loggedInUser;
		int id=user.getId();
		eHistoryList=eHistoryDao.getAllEmploymentHistoryById(id);
		
		Vector<String> vectorHeader=new Vector<>();
		vectorHeader.add("Job Title");
		vectorHeader.add("End Date");
		vectorHeader.add("Begin Date");
		vectorHeader.add("Job Description");
		
		Vector<Vector> rows=new Vector<>();
		for (EmploymentHistory item: eHistoryList) {
			Vector row=new Vector<>();
			row.add(item.getHeader());
			row.add(item.getBeginDate());
			row.add(item.getEndDate());
			row.add(item.getJobDescription());
			rows.add(row);
		}
		
		model.setDataVector(rows, vectorHeader);
		tblEmpHistory.setModel(model);
		
	}
	
	public void fillComponents(){
		loadTable();
	
	}
	
	private void initComponents(){	
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 450, 132);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblHeader = new JLabel("Header");
		lblHeader.setBounds(10, 11, 46, 14);
		panel.add(lblHeader);
		
		txtHeader = new JTextField();
		txtHeader.setBounds(88, 8, 104, 20);
		panel.add(txtHeader);
		txtHeader.setColumns(10);
		
		JLabel lblBeginDate = new JLabel("Begin Date");
		lblBeginDate.setBounds(10, 47, 68, 14);
		panel.add(lblBeginDate);
		
		textBeginDate = new JTextField("2000-01-01");
		textBeginDate.setBounds(88, 44, 104, 20);
		panel.add(textBeginDate);
		textBeginDate.setColumns(10);
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(10, 81, 46, 14);
		panel.add(lblEndDate);
		
		txtEndDate = new JTextField("2000-01-01");
		txtEndDate.setBounds(88, 78, 104, 20);
		panel.add(txtEndDate);
		txtEndDate.setColumns(10);
		
		 txtJobDescription = new JTextArea();
		txtJobDescription.setBounds(224, 8, 216, 76);
		panel.add(txtJobDescription);
		
		JLabel lblNewLabel = new JLabel("Job Description");
		lblNewLabel.setBounds(356, 84, 84, 14);
		panel.add(lblNewLabel);
		
		tblEmpHistory = new JTable();
		tblEmpHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index=tblEmpHistory.getSelectedRow();
				EmploymentHistory eHistoryObj=eHistoryList.get(index);
				String header=eHistoryObj.getHeader();
				String jobDescription=eHistoryObj.getJobDescription();
				String beginDate=sdf.format(eHistoryObj.getBeginDate()); 
				String endDate=sdf.format(eHistoryObj.getEndDate());
				selectedElementId=eHistoryObj.getId();
				txtHeader.setText(header);
				txtJobDescription.setText(jobDescription);
				textBeginDate.setText(beginDate);
				txtEndDate.setText(endDate);
			}
		});
		tblEmpHistory.setBounds(10, 131, 430, 158);
		add(tblEmpHistory);
		
		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer id=null;
				String header=txtHeader.getText();
				Date beginDate=null;
				Date endDate=null;
				try {
					Long lEndDate=sdf.parse(txtEndDate.getText()).getTime();
					Long lBeginDate=sdf.parse(textBeginDate.getText()).getTime();
					 beginDate = new Date(lBeginDate);
					 endDate=new Date(lEndDate);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				String jobDescription=txtJobDescription.getText();
				User user=Config.loggedInUser;
					
				EmploymentHistory emp=new EmploymentHistory(id, header, beginDate, endDate, jobDescription, user);
				eHistoryDao.addEmploymentHistory(emp);
				loadTable();
			}
		});
		btnAdd.setBounds(42, 106, 46, 23);
		panel.add(btnAdd);
		
		JButton btnRemove = new JButton("-");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=tblEmpHistory.getSelectedRow();
				EmploymentHistory objEmploymentHistory=eHistoryList.get(index);
				eHistoryDao.removeEmploymentHistory(objEmploymentHistory.getId());
				loadTable();
			}
		});
		btnRemove.setBounds(98, 106, 46, 23);
		panel.add(btnRemove);
		
		JButton btnSave = new JButton("Update");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=tblEmpHistory.getSelectedRow();
				EmploymentHistory eHistoryObj=eHistoryList.get(index);
				
				String header=txtHeader.getText();
				Date beginDate=null;
				Date endDate=null;
				try {
					Long lEndDate=sdf.parse(txtEndDate.getText()).getTime();
					Long lBeginDate=sdf.parse(textBeginDate.getText()).getTime();
					 beginDate = new Date(lBeginDate);
					 endDate=new Date(lEndDate);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				String jobDescription=txtJobDescription.getText();
				User user=Config.loggedInUser;
				eHistoryObj.setId(selectedElementId);
				eHistoryObj.setHeader(header);
				eHistoryObj.setBeginDate(beginDate);
				eHistoryObj.setEndDate(endDate);
				eHistoryObj.setJobDescription(jobDescription);
				eHistoryObj.setUser(user);
				
				
				eHistoryDao.updateEmploymentHistory(eHistoryObj);
				loadTable();
			}
		});
		btnSave.setBounds(154, 106, 89, 23);
		panel.add(btnSave);
		
		

	}
}
