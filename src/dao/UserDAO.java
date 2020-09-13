package dao;

//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import utility.ConnectionManager;

public class UserDAO implements UserDaoInterface {

	public int signUp(User user) throws Exception {
		String sql = "INSERT INTO USERS(email, password)VALUES(?,?)";

		int result = 0;
		try
		{
			ConnectionManager con = new ConnectionManager();
			
			// Step 2:Create a statement using connection object
			PreparedStatement st = con.getConnection().prepareStatement(sql);
			st.setString(1,user.getEmail());
			st.setString(2,user.getPassword());
			System.out.println(st);
			// Step 3: Execute the query or update query
			result = st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return result;
	}
	
	public boolean loginUser(User user) throws Exception {
		boolean status = false;
		try{
			ConnectionManager con = new ConnectionManager();
		
				// Step 2:Create a statement using connection object
			String sql = "select * from users where email = ? and password = ? ";
			PreparedStatement st = con.getConnection().prepareStatement(sql);
			 
			st.setString(1, user.getEmail());
			st.setString(2, user.getPassword());

			System.out.println(st);
			ResultSet rs = st.executeQuery();
			status = rs.next();
			
		} catch (SQLException e) {
			// process sql exception
			System.out.println(e);
		}
		return status;
	}

}
