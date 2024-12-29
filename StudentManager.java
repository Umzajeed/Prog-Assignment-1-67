import java.io.*;
import java.util.*;

public class StudentManager {
    public static void main(String[] args) {
        ArrayList<Student> studentList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File("/Users/lilaumkubp/Downloads/class_roaster67.csv"), "UTF-8")) {

            int skipLines = 7;
            while (skipLines-- > 0 && fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    String courseCode = fields[0].trim();
                    String studentId = fields[1].trim();
                    String firstName = fields[2].trim();
                    String lastName = fields[3].trim();
                    studentList.add(new Student(courseCode, studentId, firstName, lastName));
                }
            }

            handleSorting(studentList);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Unable to find the file. " + e.getMessage());
        }
    }

    private static void handleSorting(ArrayList<Student> studentList) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("-n: Sort by Student ID");
        System.out.println("-f: Sort by First Name");
        System.out.println("-l: Sort by Last Name");
        System.out.println("-s: Search for a Student");

        System.out.print("Enter your choice: ");
        String choice = userInput.nextLine();

        switch (choice) {
            case "n":
                sortByCriteria(studentList, "ID");
                break;
            case "f":
                sortByCriteria(studentList, "FirstName");
                break;
            case "l":
                sortByCriteria(studentList, "LastName");
                break;
            case "s":
                System.out.print("Enter the student name to search: ");
                String searchName = userInput.nextLine().toUpperCase();
                int position = searchStudent(studentList, searchName);
                if (position >= 0) {
                    System.out.println("Student found at position: " + position);
                } else {
                    System.out.println("Student not found.");
                }
                return;
            default:
                System.out.println("Invalid choice. Exiting program.");
                return;
        }

        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    private static void sortByCriteria(ArrayList<Student> studentList, String criteria) {
        for (int i = 0; i < studentList.size() - 1; i++) {
            for (int j = 0; j < studentList.size() - i - 1; j++) {
                boolean shouldSwap = false;

                switch (criteria) {
                    case "ID":
                        if (studentList.get(j).getId().compareTo(studentList.get(j + 1).getId()) > 0) {
                            shouldSwap = true;
                        }
                        break;
                    case "FirstName":
                        if (studentList.get(j).getFirst().compareTo(studentList.get(j + 1).getFirst()) > 0) {
                            shouldSwap = true;
                        }
                        break;
                    case "LastName":
                        if (studentList.get(j).getLast().compareTo(studentList.get(j + 1).getLast()) > 0) {
                            shouldSwap = true;
                        }
                        break;
                }

                if (shouldSwap) {
                    Student temp = studentList.get(j);
                    studentList.set(j, studentList.get(j + 1));
                    studentList.set(j + 1, temp);
                }
            }
        }
    }

    private static int searchStudent(ArrayList<Student> studentList, String name) {
        for (int i = 0; i < studentList.size(); i++) {
            if (name.equalsIgnoreCase(studentList.get(i).getFirst())) {
                return i;
            }
        }
        return -1;
    }
}

class Student extends StudentManager{
    private String cd;
    private String id;
    private String first;
    private String last;

    public Student(String cd,String id,String first,String last){
        this.cd = cd;
        this.id = id;
        this.first = first;
        this.last = last;
    }
    public String getCd(){
        return cd;
    }

    public String getId(){
        return id;
    }
    public String getFirst(){
        return first.strip();
    }
    public String getLast(){
        return last;
    }

    @Override
    public String toString() {
        return id +" "+first+" "+last;
    }
}
