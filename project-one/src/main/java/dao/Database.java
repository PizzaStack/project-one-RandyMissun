package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import models.Reimbursement;
import models.ReimbursementStatus;
import models.User;
import models.UserType;

public class Database {
	private Connection connection;

	private void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			Properties properties = new Properties();
			properties.load(new FileInputStream(
					"E:\\Revature\\Project 1\\project-one-RandyMissun\\project-one\\connection.properties"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
					properties.getProperty("pass"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void disconnect() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertUser(User _u) {
		connect();
		try {
			Statement s = connection.createStatement();
			ResultSet count = s.executeQuery("SELECT COUNT(id)+1 AS count FROM users");
			count.next();
			_u.id = count.getInt("count");

			PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?)");
			statement.setInt(1, _u.id);
			statement.setString(2, _u.type.toString());
			statement.setString(3, _u.username);
			statement.setString(4, _u.password);
			statement.execute();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User retrieveUser(String _username) {
		connect();
		try {
			Statement statement = connection.createStatement();
			ResultSet users = statement.executeQuery("SELECT * FROM users WHERE username='" + _username + "'");
			disconnect();
			if (!users.isBeforeFirst()) {
				return null;
			}
			users.next();
			User u = new User();
			u.id = users.getInt("id");
			u.type = UserType.valueOf(users.getString("type"));
			u.username = _username;
			u.password = users.getString("password");
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateUser(User _u) {
		connect();
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE users SET type=?, username=?, password=? where id=?");
			statement.setString(1, _u.type.toString());
			statement.setString(2, _u.username);
			statement.setString(3, _u.password);
			statement.setInt(4, _u.id);
			statement.execute();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> retrieveAllUsers() {
		connect();
		try {
			Statement statement = connection.createStatement();
			ResultSet users = statement.executeQuery("SELECT * FROM users");
			disconnect();
			if (!users.isBeforeFirst()) {
				return null;
			}
			List<User> result = new ArrayList<User>();
			while (users.next()) {
				User u = new User();
				u.id = users.getInt("id");
				u.type = UserType.valueOf(users.getString("type"));
				u.username = users.getString("username");
				u.password = users.getString("password");
				result.add(u);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insertReimbursement(Reimbursement _r) {
		connect();
		try {
			Statement s = connection.createStatement();
			ResultSet count = s.executeQuery("SELECT COUNT(id)+1 AS count FROM reimbursements");
			count.next();
			_r.id = count.getInt("count");

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO reimbursements VALUES (?, ?, ?, ?, ?, ?, ?)");
			statement.setInt(1, _r.id);
			statement.setString(2, _r.employee);
			statement.setString(3, _r.description);
			statement.setBytes(4, _r.getImgData());
			statement.setString(5, _r.getImgUrl());
			statement.setString(6, _r.status.toString());
			statement.setString(7, _r.manager);
			statement.execute();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Reimbursement retrieveReimbursementById(int _id) {
		connect();
		try {
			Statement statement = connection.createStatement();
			ResultSet reimbursements = statement.executeQuery("SELECT * FROM reimbursements WHERE id=" + _id);
			disconnect();
			if (!reimbursements.isBeforeFirst()) {
				return null;
			}
			reimbursements.next();
			Reimbursement r = new Reimbursement(reimbursements.getString("employee"), reimbursements.getString("description"));
			r.id = _id;
			r.setImageFromRawData(reimbursements.getBytes("img_data"), reimbursements.getString("img_url"));
			r.status = ReimbursementStatus.valueOf(reimbursements.getString("status"));
			r.manager = reimbursements.getString("manager");
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reimbursement> retrieveReimbursementsByEmployee(String _employeeUsername) {
		connect();
		try {
			Statement statement = connection.createStatement();
			ResultSet reimbursements = statement
					.executeQuery("SELECT * FROM reimbursements WHERE employee = '" + _employeeUsername + "'");
			disconnect();
			List<Reimbursement> result = new ArrayList<Reimbursement>();
			if (!reimbursements.isBeforeFirst()) {
				return result;
			}
			while (reimbursements.next()) {
				Reimbursement r = new Reimbursement(_employeeUsername, reimbursements.getString("description"));
				r.id = reimbursements.getInt("id");
				r.setImageFromRawData(reimbursements.getBytes("img_data"), reimbursements.getString("img_url"));
				r.status = ReimbursementStatus.valueOf(reimbursements.getString("status"));
				r.manager = reimbursements.getString("manager");
				result.add(r);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateReimbursement(Reimbursement _r) {
		connect();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE reimbursements SET employee=?, description=?, img_data=?, img_url=?, status=?, manager=? where id=?");
			statement.setString(1, _r.employee);
			statement.setString(2, _r.description);
			statement.setBytes(3, _r.getImgData());
			statement.setString(4, _r.getImgUrl());
			statement.setString(5, _r.status.toString());
			statement.setString(6, _r.manager);
			statement.setInt(7, _r.id);
			statement.execute();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Reimbursement> retrieveAllReimbursements() {
		connect();
		try {
			Statement statement = connection.createStatement();
			ResultSet reimbursements = statement.executeQuery("SELECT * FROM reimbursements");
			disconnect();
			if (!reimbursements.isBeforeFirst()) {
				return null;
			}
			List<Reimbursement> result = new ArrayList<Reimbursement>();
			while (reimbursements.next()) {
				Reimbursement r = new Reimbursement(reimbursements.getString("employee"),
						reimbursements.getString("description"));
				r.id = reimbursements.getInt("id");
				r.setImageFromRawData(reimbursements.getBytes("img_data"), reimbursements.getString("img_url"));
				r.status = ReimbursementStatus.valueOf(reimbursements.getString("status"));
				r.manager = reimbursements.getString("manager");
				result.add(r);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
