import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

public class PostgreSQLJDBCConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/StudentDatabase";
        String user = "postgres";
        String password = "password";
        Scanner scanner = new Scanner(System.in);

        try { // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Successfully Connected to PostgreSQL!\n");

                printMenu();
                int menuOption = Integer.parseInt(scanner.nextLine());

                if (menuOption == 1) {
                    getAllStudents(conn);
                    System.out.println("\nThat is all of the students in this database!");
                } else if (menuOption == 2) {
                    System.out.println("Enter first name:");
                    String first_name = scanner.nextLine();
                    System.out.println();

                    System.out.println("Enter last name:");
                    String last_name = scanner.nextLine();
                    System.out.println();

                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println();

                    System.out.println("Enter enrollment date:");
                    String enrollment_date = scanner.nextLine();
                    System.out.println();

                    addStudent(conn, first_name, last_name, email, enrollment_date);
                    System.out.println("Student has been successfully added!");
                } else if (menuOption == 3) {
                    System.out.println("Enter student ID:");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println();

                    System.out.println("Enter new email:");
                    String new_email = scanner.nextLine();
                    System.out.println();

                    updateStudentEmail(conn, id, new_email);
                    System.out.println("Student's email has been successfully been updated!");
                } else if (menuOption == 4) {
                    System.out.println("Enter student ID of student to delete:");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println();

                    deleteStudent(conn, id);
                    System.out.println("Student with ID" + id + " has been successfully deleted!");
                } else {
                    System.out.println("Error: Not one of the menu options.");
                }

            } else {
                System.out.println("Failed to establish connection.");
            }
            conn.close();
            scanner.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("(1) Print all students.");
        System.out.println("(2) Add a student.");
        System.out.println("(3) Update a student's email.");
        System.out.println("(4) Delete a student.");
    }

    private static void getAllStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students;";

        try (Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
            System.out.println("Printing all students:");
            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + ", " +
                        rs.getString("first_name") + ", " +
                        rs.getString("last_name") + ", " +
                        rs.getString("email") + ", " +
                        rs.getString("enrollment_date"));
            }
            rs.close();
        }
        
    }

    private static void addStudent(Connection conn, String first_name, String last_name, String email,
            String enrollment_date) throws SQLException {
        String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?);";

        try (PreparedStatement prepared_statement = conn.prepareStatement(sql)) {
            prepared_statement.setString(1, first_name);
            prepared_statement.setString(2, last_name);
            prepared_statement.setString(3, email);
            prepared_statement.setDate(4, Date.valueOf(enrollment_date));
            prepared_statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Student with email '" + email + "' already exists.");
        }
    }

    private static void updateStudentEmail(Connection conn, int student_id, String new_email) throws SQLException {
        String sql = "UPDATE students SET email = ? WHERE student_id = ?;";

        try (PreparedStatement prepared_statement = conn.prepareStatement(sql)) {
            prepared_statement.setString(1, new_email);
            prepared_statement.setInt(2, student_id);
            int affected_rows = prepared_statement.executeUpdate();
            if (affected_rows == 0) {
                System.out.println("Error: No student found with ID " + student_id);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Email '" + new_email + "' is already in use by another student.");
        }
    }

    private static void deleteStudent(Connection conn, int student_id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?;";

        try (PreparedStatement prepared_statement = conn.prepareStatement(sql)) {
            prepared_statement.setInt(1, student_id);
            int affected_rows = prepared_statement.executeUpdate();
            if (affected_rows == 0) {
                System.out.println("Error: No student found with ID " + student_id);
            }
        }
    }
}