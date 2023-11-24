# COMP3005 Assignment4 Part 1

## Video demo
https://youtu.be/l-VSK1JmUSc

## Instructions
### Step 1
Create a database called `StudentDatabase` on PostgreSQL.

### Step 2
Download and run `CreateAndInitialize.sql` on the database to create the students table, as well as have some initilized students in the table.

### Step 3
Create a java project and add `PostgreSQLJDBCConnection.java` into the /src/ file.

### Step 4
Download `postgresql-42.7.0.jar` and add this .jar file to your classpath (or referenced libraries on vscode) in order to have the ability to connect to PostgreSQL.

### Step 5
Next, run the program. You should get a menu with four options, each option running one of the functions.

Selecting one of these options, will give you the ability to add, delete, print or edit the table in this database, which can be looked at by calling a `SELECT * FROM students` query on PostgreSQL, everytime a function is run.

*One parameter was added to all of the functions*
## Application Functions
getAllStudents(Connection conn): Retrieves and displays all records from the students table.

addStudent(Connection conn, String first_name, String last_name, String email, String enrollment_date): Inserts a new student record into the students table.

updateStudentEmail(Connection conn, int student_id, String new_email): Updates the email address for a student with the specified student_id.

deleteStudent(Connection conn, int student_id): Deletes the record of the student with the specified student_id.
