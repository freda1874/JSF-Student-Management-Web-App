 JSF Student Management Web App

## Overview

This project is a web application for managing student data using Java Server Faces (JSF), JDBC, and MySQL. The application allows users to perform CRUD (Create, Read, Update, Delete) operations on student records through a user-friendly web interface.

## Features
![image](https://github.com/freda1874/JSF-Student-Management-Web-App/assets/85437054/44d57054-edf3-4e70-9919-d37ce70ed00f)

1. **Create/Add a New Student**: Users can add new student records to the database.
2. ![image](https://github.com/freda1874/JSF-Student-Management-Web-App/assets/85437054/be1db257-b031-4118-8faa-41852af79994)

3. **Read/View a List of Students**: Users can view a list of all students stored in the database.
4. **Update/Edit an Existing Student**: Users can update existing student records. After updating, the user is redirected back to the main page. Users can also cancel the update operation and return to the main page.
5. ![image](https://github.com/freda1874/JSF-Student-Management-Web-App/assets/85437054/870e1bc1-13d5-49ae-ba92-9f29a53386bb)

6. **Delete/Remove an Existing Student**: Users can delete student records. A confirmation pop-up will appear to confirm the deletion.

## Technologies Used

- **Java EE**: Used for developing the enterprise application.
- **JSF (Java Server Faces)**: Used for building the user interface.
- **JDBC (Java Database Connectivity)**: Used for database interactions.
- **MySQL**: The database used to store student records.
- **Eclipse EE**: The IDE used for development.

## Setup Instructions

### Prerequisites

- Java Development Kit (JDK) 8 or later
- Eclipse IDE for Enterprise Java Developers
- MySQL Server
- MySQL JDBC Driver

### Database Setup

1. **Create the Database**:
   ```sql
   CREATE DATABASE DataBank;
   ```
2. **Create the `students` Table**:
   ```sql
   USE DataBank;

   CREATE TABLE students (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       age INT NOT NULL,
       email VARCHAR(100) NOT NULL
   );
   ```

### Project Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/student-management.git
   ```
2. **Import the Project into Eclipse**:
   - Open Eclipse.
   - Go to `File` > `Import`.
   - Select `Maven` > `Existing Maven Projects` and click `Next`.
   - Browse to the location of the cloned repository and click `Finish`.

3. **Configure the Database Connection**:
   - In the `src/main/resources` directory, create a `db.properties` file with the following content:
     ```properties
     jdbc.url=jdbc:mysql://localhost:3306/DataBank
     jdbc.username=your-username
     jdbc.password=your-password
     jdbc.driverClassName=com.mysql.cj.jdbc.Driver
     ```
   - Replace `your-username` and `your-password` with your MySQL credentials.

4. **Add the MySQL JDBC Driver to the Project**:
   - Download the MySQL JDBC Driver from the [official website](https://dev.mysql.com/downloads/connector/j/).
   - Add the driver to your project's build path.

5. **Run the Application**:
   - Right-click on the project in Eclipse.
   - Select `Run As` > `Run on Server`.
   - Choose the server you have configured (e.g., Apache Tomcat) and click `Finish`.

## Usage

1. **Create/Add a New Student**:
   - Navigate to the "Add Student" page.
   - Fill in the student details and click "Submit".

2. **Read/View a List of Students**:
   - Navigate to the "Students List" page to view all student records.

3. **Update/Edit an Existing Student**:
   - On the "Students List" page, click the "Edit" button next to the student you want to update.
   - Update the necessary information and click "Save". Alternatively, click "Back" to cancel the operation.

4. **Delete/Remove an Existing Student**:
   - On the "Students List" page, click the "Delete" button next to the student you want to remove.
   - Confirm the deletion in the pop-up. Click "OK" to delete or "Cancel" to abort the operation.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

For any questions or suggestions, please contact [your-email@example.com].

---

Feel free to customize this template according to your project's specific details and requirements.
