package main.java.travelbook.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exception.DBException;
import main.java.travelbook.controller.AllQuery;
import main.java.travelbook.model.EmailAccount;

public class EmailDao{
	public EmailAccount getData() throws DBException{
		String query=AllQuery.getInstance().getEmail();
		try (Connection conn=AllQuery.getInstance().getConnection()){
			
			try(Statement stmt=conn.createStatement()){
			ResultSet rs=stmt.executeQuery(query);
			EmailAccount account=new EmailAccount();
			rs.next();
			account.setEmail(rs.getString("EmailT"));
			account.setPswd(rs.getString("pswdEmail"));
			return account;
			}
		} catch (SQLException e) {
			throw new DBException("Error Email");
		}
	}

}
