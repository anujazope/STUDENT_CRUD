

import java.sql.*;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

public class CRUD {

    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

        try {
            int choice = 0;
            student s = new student();
            do {
                System.out.println(
                        "Select an operation \n 1- insert \n 2-  Update \n 3- Delete a Record \n 4- Search for a Student \n 5-all Student \n 6 Exit");
                Scanner choicein = new Scanner(System.in);
                choice = choicein.nextInt();
                switch (choice) {
                    case 1:
                        s.getStudentDetails();
                        s.insertStudent();
                        break;
                    case 2:
                        s.updateStudentPassword();
                        break;
                    case 3:
                        s.deleteStudentRecord();
                        break;
                    case 4:
                        s.searchStudent();
                        break;
                    case 5:
                        s.allstudents();
                        break;
                    case 6:
                        break;    
                    default:
                        System.out.println("Select the correct choice");
                }
            } while (choice != 5);
            System.out.println("Thanks for Using our Software");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class student {
    private Integer Roll;
    private String name;
    private int dOBDate;
    private int dOJDate;

    public void getStudentDetails() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your ROLL NO");
        Roll = input.nextInt();
        System.out.println("Enter your NAME");
        name = input.nextLine();
        System.out.println("Enter your date of birth");
        dOBDate = input.nextInt();
        System.out.println("Enter your date of joining");
        dOJDate = input.nextInt();
    }

    public void insertStudent()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        // here we are going to work with a database
        // we need to open a database connection
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql:@localhost:3306", "students", "root");
        Connection con = dbmsconnect.getConnection();
        String sql = "insert into student values (?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, Roll);
        stmt.setString(2, name);
        stmt.setInt(3, dOBDate);
        stmt.setInt(4, dOJDate);
        int i = stmt.executeUpdate();
        System.out.println("Record  inserted successfully");
        dbmsconnect.closeConnection(con, stmt);
    }

    public void updateStudentPassword()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql:@localhost:3306", "students", "root");
        Connection con = dbmsconnect.getConnection();
        System.out.println("Enter Your ROLL NO");
        Scanner scanner = new Scanner(System.in);
        String inputname = String.nextLine();
        System.out.println("Enter Your Name");
        String name = String.nextLine();
        String sql = "update student set name = ? where name = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        String inputnameString;
        stmt.setString(1, inputname);
        stmt.setInt(2, dOBDate);
        int i = stmt.executeUpdate();
        if (i > 0) {
            System.out.println("Record updated sucessfully");
        } else {
            System.out.println("No Such record in the Database");
        }
        dbmsconnect.closeConnection(con, stmt);
    }

    public void deleteStudentRecord()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql:@localhost:3306", "students", "root");
        Connection con = dbmsconnect.getConnection();
        System.out.println("Enter the Name of the Student");
        Scanner input = new Scanner(System.in);
        String inputname = input.nextLine();
        String sql = "delete from student where name = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, inputname);
        int i = stmt.executeUpdate();
        if (i > 0) {
            System.out.println("Record Deleted Successfully");
        } else {
            System.out.println("No Such Record in the Database");
        }
        dbmsconnect.closeConnection(con, stmt);
    }

    public void searchStudent()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql:@localhost:3306", "students", "root");
        Connection con = dbmsconnect.getConnection();
        System.out.println("Enter Your ROLLNO");
        Scanner input = new Scanner(System.in);
        String sql = "select * from student where ROLLNO=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, input Roll;
        ResultSet rs = stmt.executeQuery();
        if (rs.next() == false) {
            System.out.println("No such record found in the database");
        } else {
            System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getInt(4));

        }
        dbmsconnect.closeConnection(con, stmt);
    }

    public void allstudents()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql:@localhost:3306", "students", "root");
        Connection con = dbmsconnect.getConnection();
        System.out.println("Enter Your Name");
        Scanner scanner = new Scanner(System.in);
        String Name = String.nextLine();
        String sql = "select * from students";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next() == false) {
            System.out.println("No such record found in the database");
        } else {
            System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getInt(4));

        }
        dbmsconnect.closeConnection(con, stmt);
    }
}

class dbmsconnection {
    String url;
    String username;
    String password;

    public dbmsconnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(url, username, password);
        System.out.println("Connection Established Successfully");
        return con;
    }

    public void closeConnection(Connection con, Statement stmt) throws SQLException {
        stmt.close();
        con.close();
        System.out.println("The connection is closed");
    }
}
