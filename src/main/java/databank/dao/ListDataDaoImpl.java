/********************************************************************************************************2*4*w*
 * File:  ListDataDaoImpl.java Course materials CST8277
 *@author Lei Luo
 * @author Teddy Yap
 */
package databank.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import databank.model.StudentPojo;

@SuppressWarnings("unused")
/**
 * Description: API for reading list data from the database
 */
@Named
@ApplicationScoped
public class ListDataDaoImpl implements ListDataDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";
	private static final String READ_ALL_PROGRAMS = "SELECT * FROM program";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllProgramsPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			// Initialize PreparedStatements
			readAllProgramsPstmt = conn.prepareStatement(READ_ALL_PROGRAMS);
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			// Close PreparedStatements
			readAllProgramsPstmt.close();
			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	/*
	 * logMsg("reading all students"); List<StudentPojo> students = new
	 * ArrayList<>(); try (ResultSet rs = readAllPstmt.executeQuery();) { while
	 * (rs.next()) { StudentPojo newStudent = new StudentPojo();
	 * newStudent.setId(rs.getInt("id"));
	 * newStudent.setLastName(rs.getString("last_name"));
	 * newStudent.setFirstName(rs.getString("first_name"));
	 * newStudent.setEmail(rs.getString("email"));
	 * newStudent.setPhoneNumber(rs.getString("phone"));
	 * newStudent.setProgram(rs.getString("program"));
	 * newStudent.setCreated(rs.getTimestamp("created").toLocalDateTime());
	 * students.add(newStudent); }
	 * 
	 * } catch (SQLException e) {
	 * logMsg("something went wrong accessing database:  " +
	 * e.getLocalizedMessage()); }
	 * 
	 * return students;
	 * 
	 * }
	 */

	@Override
	public List<String> readAllPrograms() {
		logMsg("reading all programs");
		List<String> programs = new ArrayList<>();
		// Complete the retrieval of all programs
		// Be sure to use try-and-catch statement
		try (ResultSet rs = readAllProgramsPstmt.executeQuery();) {
			while (rs.next()) {
				programs.add(rs.getString("name"));
			}

		} catch (SQLException e) {
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}

		return programs;

	}

}
