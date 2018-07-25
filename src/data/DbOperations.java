/*
Gursharan Deol
Assignment1
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Deol
 */
public class DbOperations {

    private Connection connec;
    private Statement stmnt;
    private ResultSet rsltst;

    // define a no-argumet constructor 
    public DbOperations() {
    }

    //define a method connect
    public boolean connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //define and set data for the required connection

        String driverName = "com.mysql.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/professors";
        String userName = "root";
        String password = "sesame";
        // create an instance from the jdbc driver 
        Class.forName(driverName).newInstance();
        try {
            // open the connection
            connec = DriverManager.getConnection(dbUrl, userName, password);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }//end to connect method

    //add createPhoneTable method
    public void createProfessorTable() {
        String createTbl = "CREATE TABLE professorTable(id INT NOT NULL, name VARCHAR(30),HighestEducation VARCHAR(50));";
        //create a statement object to include the sql statement
        try {
            stmnt = connec.createStatement();
            //execute the sql statement
            stmnt.executeUpdate(createTbl);
        } catch (SQLException e) {
            if (e.getSQLState().equals("42S01") && e.getErrorCode() == 1050) {
                System.out.println("Warning! This table already exists");
            } else {
                System.out.println("Error:" + e.getMessage());
            }
        } finally {
            close();
        }
    }//end of Createprofessortable method

    //insertProf method to insert into table
    public void insertProf(int id, String name, String highestEducation) {
        String insertRow = "INSERT INTO professorTable VALUES ('" + id + "','" + name + "','" + highestEducation + "');";
        try {
            //write all commands that access the sql server
            stmnt = connec.createStatement();
            stmnt.executeUpdate(insertRow);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }  //end of insert method

//method to show all professors
    public void showAllProf() {
        //prepare the sql statement
        String queryStm = "SELECT * FROM professorTable;";
        try {
            stmnt = connec.createStatement();
            //execute the statement
            rsltst = stmnt.executeQuery(queryStm);
            //print the data from ResultSet(res)
            while (rsltst.next()) {
                //print a single record for each round
                System.out.println("ID::" + rsltst.getString("id"));
                System.out.println("Name:" + rsltst.getString("name"));
                System.out.println("Highest Education:" + rsltst.getString("highestEducation"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//end of show contacts

//search method to search for a particular prof
    public void search(String prof) {
        // prepare the sql statement ( we will use prepared statement for security)
        String queryStm = "select * from professorTable where name=?";
        // create prepared statment object 
        try {
            PreparedStatement pstm = connec.prepareStatement(queryStm);
            pstm.setString(1, prof);
            rsltst = pstm.executeQuery();
            // print the results 
            if (rsltst.next()) {
                rsltst.previous();
                while (rsltst.next()) {
                    // print a single record each round 
                    System.out.println("ID:" + rsltst.getString("id"));
                    System.out.println("Name:" + rsltst.getString("name"));
                    System.out.println("Highest Education:" + rsltst.getString("highestEducation"));
                }
            } else {
                System.out.println("Not found ");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 //end of search method

    //method to close the application
    private void close() {
        try {
            if (rsltst != null) {
                rsltst.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            if (connec != null) {
                connec.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//end of close method

}
//end of the DbOperations
