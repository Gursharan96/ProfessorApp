/*
Gursharan Deol
Assignment-1
 */
package data;

import java.util.Scanner;

/**
 *
 * @author Deol
 */
public class MainMenu {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        //create a DataAccess object
        DbOperations conn = new DbOperations();

        Scanner input = new Scanner(System.in);

        boolean connected;
        int selction = -1;
        int id;
        String name, highestEducation;

        while (selction != 5) {
            //connect to the MySQL 
            try {
                connected = conn.connect();
                if (connected) {
                    //display the option
                    System.out.println("\n 1: Create Professor table\n 2: Insert a professor "
                            + "\n 3: Show all proffesor\n 4: Search for a professor\n 5: Exit from the application");
                    System.out.println("\n Make a selection");
                    selction = input.nextInt();
                    switch (selction) {

                        case 1:
                            conn.createProfessorTable();
                            break;
                        case 2:
                            System.out.println("ID:");
                            id = input.nextInt();
                            System.out.println("Name:");
                            name = input.next();
                            System.out.println("Highest Education:");
                            highestEducation = input.next();
                            //call the method insertcontact to insert data into the database
                            conn.insertProf(id, name, highestEducation);
                            break;
                        case 3:
                            conn.showAllProf();
                            break;
                        case 4:
                            System.out.println("Enter name");
                            name = input.next();
                            conn.search(name);
                            break;
                        case 5:
                            System.out.println("Exiting from the application");
                        default:
                            System.out.println("Invalid option");

                    }

                } else {
                    System.out.println("Not connected ");
                    System.exit(1);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
