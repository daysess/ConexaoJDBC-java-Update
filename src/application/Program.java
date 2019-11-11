package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		
		Connection conn = null;
		Statement st = null;
		try {
			
			conn = DB.getConnection();
			
			conn.setAutoCommit(false);
			
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 1500 WHERE DepartmentId = 1", Statement.RETURN_GENERATED_KEYS);
			
			int x = 2;
			if(x < 2) {
				throw new SQLException("Fake error");
			}
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3000 WHERE DepartmentId = 2", Statement.RETURN_GENERATED_KEYS);
			
			conn.commit();
			
			System.out.println("Seller atualizado: "+ rows1);
			System.out.println("Seller atualizado: "+ rows2);
			
		}catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Cause by: "+e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Cause by: "+e1.getMessage());
			}			
		}finally {
			DB.closeStatement(st);
			DB.closeConnection();
			
		}
				
		
	}

}
