/********************************************************************************************************2*4*w*
 * File:  StudentDaoImpl.java Course materials CST8277
 *@author Lei Luo
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

import java.sql.Timestamp;

import databank.model.StudentPojo;

@Named
@ApplicationScoped
/**
 * Description: Implements the C-R-U-D API for the database
 */
public class StudentDaoImpl implements StudentDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;
	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";
	private static final String READ_ALL = "SELECT * FROM student";
	private static final String READ_STUDENT_BY_ID = "SELECT * FROM student where id = ?";
	private static final String INSERT_STUDENT = "INSERT INTO student(last_name, first_name, email, phone, program, created) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STUDENT_ALL_FIELDS = "UPDATE student set last_name = ?, first_name = ?, email = ?, phone = ?, program = ? where id = ?";
	private static final String DELETE_STUDENT_BY_ID = "DELETE FROM student where id = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllPstmt;
	protected PreparedStatement readByIdPstmt;
	protected PreparedStatement createPstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement deleteByIdPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			readAllPstmt = conn.prepareStatement(READ_ALL);
			createPstmt = conn.prepareStatement(INSERT_STUDENT, RETURN_GENERATED_KEYS);
			// Initialize other PreparedStatements
			readByIdPstmt = conn.prepareStatement(READ_STUDENT_BY_ID);
			updatePstmt = conn.prepareStatement(UPDATE_STUDENT_ALL_FIELDS);
			deleteByIdPstmt = conn.prepareStatement(DELETE_STUDENT_BY_ID);
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			readAllPstmt.close();
			createPstmt.close();
			// Close other PreparedStatements
			readByIdPstmt.close();
			updatePstmt.close();
			deleteByIdPstmt.close();
			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<StudentPojo> readAllStudents() {
		logMsg("reading all students");
		List<StudentPojo> students = new ArrayList<>();
		try (ResultSet rs = readAllPstmt.executeQuery();) {
			while (rs.next()) {
				StudentPojo newStudent = new StudentPojo();
				newStudent.setId(rs.getInt("id"));
				newStudent.setLastName(rs.getString("last_name"));
				newStudent.setFirstName(rs.getString("first_name"));
				newStudent.setEmail(rs.getString("email"));
				newStudent.setPhoneNumber(rs.getString("phone"));
				newStudent.setProgram(rs.getString("program"));
				newStudent.setCreated(rs.getTimestamp("created").toLocalDateTime());
				students.add(newStudent);
			}

		} catch (SQLException e) {
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}

		return students;

	}

	@Override
	public StudentPojo createStudent(StudentPojo student) {
		logMsg("creating a student");
		// Complete the insertion of a new student
		// Be sure to use try-and-catch statement
		try {
			createPstmt.setString(1, student.getLastName());
			createPstmt.setString(2, student.getFirstName());
			createPstmt.setString(3, student.getEmail());
			createPstmt.setString(4, student.getPhoneNumber());
			createPstmt.setString(5, student.getProgram());
			// Timestamp is used for JDBC, LocalDateTime is used for general date-time
			createPstmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
			createPstmt.execute();
		} catch (Exception e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		return student;
	}

	@Override
	public StudentPojo readStudentById(int studentId) {
		StudentPojo newStudent = new StudentPojo();
		logMsg("read a specific student");
		// Complete the retrieval of a specific student by its id
		// Be sure to use try-and-catch statement
		try {
			readByIdPstmt.setInt(1, studentId);
			ResultSet rs = readByIdPstmt.executeQuery();
			while (rs.next()) {
				newStudent.setId(rs.getInt("id"));
				newStudent.setLastName(rs.getString("last_name"));
				newStudent.setFirstName(rs.getString("first_name"));
				newStudent.setEmail(rs.getString("email"));
				newStudent.setPhoneNumber(rs.getString("phone"));
				newStudent.setProgram(rs.getString("program"));
				newStudent.setCreated(rs.getTimestamp("created").toLocalDateTime());
			}
		} catch (Exception e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
		return newStudent;

	}

	@Override
	public void updateStudent(StudentPojo student) {
		logMsg("updating a specific student");
		try {
			updatePstmt.setString(1, student.getLastName());
			updatePstmt.setString(2, student.getFirstName());
			updatePstmt.setString(3, student.getEmail());
			updatePstmt.setString(4, student.getPhoneNumber());
			updatePstmt.setString(5, student.getProgram());
			updatePstmt.setInt(6, student.getId());
			updatePstmt.execute();
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
	}

	@Override
	public void deleteStudentById(int studentId) {
		logMsg("deleting a specific student");
		try {
			deleteByIdPstmt.setInt(1, studentId);
			deleteByIdPstmt.execute();
		} catch (SQLException e) {
			logMsg("something went wrong accessing database: " + e.getLocalizedMessage());
		}
	}

}