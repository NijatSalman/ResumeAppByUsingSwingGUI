package com.company.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.company.dao.inter.AbstractDAO;
import com.company.dao.inter.UserDaoInter;
import com.company.entity.Country;
import com.company.entity.User;

public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

	private User getUser(ResultSet rs) throws Exception{
		int id=rs.getInt("id");
		String name=rs.getString("name");
		String surname=rs.getString("surname");
		String phone=rs.getString("phone");
		String email=rs.getString("email");
		String address=rs.getString("address");
		int nationalityId=rs.getInt("nationality_id");
		int birthPlaceId=rs.getInt("birthplace_id");
		String profileDescription=rs.getString("profile_description");
		String nationalityStr=rs.getString("nationality");
		String pirthPlaceStr=rs.getString("birthplace");
		Date birthdate=rs.getDate("birthdate");
		
		Country nationality=new Country(nationalityId,null,nationalityStr);
		Country birthPlace=new Country(birthPlaceId, pirthPlaceStr, null);
		
		return new User(id,name,surname,phone,email,profileDescription,address,birthdate,birthPlace,nationality);
	}
	
	@Override
	public List<User> getAll() {
		List<User> result=new ArrayList<>();
		
		try(Connection c=connection()) {
			
			Statement stmt= c.createStatement();
			stmt.execute("SELECT u.*,n.nationality,c.`name` as birthplace "
					+ "from user as u "
					+ "Left join country as n on n.id=u.nationality_id "
					+ "Left join country as c on c.id=u.birthplace_id");
			ResultSet rs=stmt.getResultSet();
			 
			while(rs.next()){
				User u=getUser(rs);		
				result.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;		
	}

	@Override
	public boolean updateUser(User u) {
		
		try(Connection c=connection()) {
			PreparedStatement pStmt= c.prepareStatement("Update user set name=?,surname=?,email=?,phone=?,profile_description=?,address=?,birthdate=?,birthplace_id=?,nationality_id=? where id=?");
		pStmt.setString(1, u.getName());
		pStmt.setString(2, u.getSurname());
		pStmt.setString(3, u.getEmail());
		pStmt.setString(4, u.getPhone());
		pStmt.setString(5, u.getProfileDescription());
		pStmt.setString(6, u.getAddress());
		pStmt.setDate(7, u.getBirthDate());
		pStmt.setInt(8, u.getBirthPlace().getId());
		pStmt.setInt(9, u.getNationality().getId());
		pStmt.setInt(10, u.getId());
			return	pStmt.execute();		 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public boolean removeUser(int id) {
		
		try(Connection c=connection()) {
			Statement stmt= c.createStatement();
		return	stmt.execute("delete from user where user="+id);		 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getById(int userId) {
		User result=null;
		try (Connection c=connection()){
			Statement stmt=c.createStatement();
			stmt.execute("SELECT "
					+ "u.*,"
					+ "n.nationality,"
					+ "c.`name` as birthplace "
					+ "from user as u "
					+ "Left join country as n on u.nationality_id=n.id "
					+ "Left join country as c on u.birthplace_id=c.id where u.id="+userId);
			ResultSet rs=stmt.getResultSet();
			while(rs.next()){	
				result=getUser(rs);
			}
		} catch (Exception e) {
		e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean addUser(User u) {
		try(Connection c=connection()) {
			PreparedStatement pStmt=c.prepareStatement("Insert into user (name,surname,email,phone,profile_description,address) values(?,?,?,?,?,?) ");
		pStmt.setString(1, u.getName());
		pStmt.setString(2, u.getSurname());
		pStmt.setString(3, u.getEmail());
		pStmt.setString(4, u.getPhone());
		pStmt.setString(5, u.getProfileDescription());
		pStmt.setString(6, u.getAddress());
		return pStmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}


}
