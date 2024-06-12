/********************************************************************************************************2*4*w*
 * File:  StudentController.java Course materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.SessionMap;
import javax.inject.Inject;
import javax.inject.Named;

import databank.dao.ListDataDao;
import databank.dao.StudentDao;
import databank.model.StudentPojo;

/**
 * Description: Responsible for collection of Student Pojo's in XHTML (list)
 * <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO
 */

// Don't forget this object is a managed bean with a session scope
@Named
@SessionScoped
public class StudentController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	@SessionMap
	private Map<String, Object> sessionMap;

	@Inject
	private StudentDao studentDao;

	@Inject
	private ListDataDao listDataDao;

	private List<StudentPojo> students;

	// Necessary methods to make controller work

	public void loadStudents() {
		setStudents(studentDao.readAllStudents());
	}

	public List<StudentPojo> getStudents() {
		return students;
	}

	public void setStudents(List<StudentPojo> students) {
		this.students = students;
	}

	public List<String> getPrograms() {
		return this.listDataDao.readAllPrograms();
	}

	public String navigateToAddForm() {
		// Pay attention to the name here, it will be used as the object name in
		// add-student.xhtml
		// ex. <h:inputText value="#{newStudent.firstName}" id="firstName" />
		sessionMap.put("newStudent", new StudentPojo());
		return "add-student.xhtml?faces-redirect=true";
	}

	public String submitStudent(StudentPojo student) {
		// Update the student object with current date. You can use
		// LocalDateTime::now().
		// Use DAO to insert the student to the database
		// Do not forget to navigate the user back to list-students.xhtml

		studentDao.createStudent(student);

		return "list-students.xhtml?faces-redirect=true";
	}

	public String navigateToUpdateForm(int studentId) {

		StudentPojo existingstudent = studentDao.readStudentById(studentId);
		sessionMap.put("existingstudent", existingstudent);

		return "edit-student.xhtml?faces-redirect=true";
	}

	public String submitUpdatedStudent(StudentPojo student) {

		studentDao.updateStudent(student);
		return "list-students.xhtml?faces-redirect=true";
	}

	public String deleteStudent(int studentId) {
		studentDao.deleteStudentById(studentId);

		return "list-students.xhtml?faces-redirect=true";
	}

}
