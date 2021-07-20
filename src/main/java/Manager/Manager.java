package Manager;

import Entity.Report;
import Entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager {
    public ArrayList<Student> list = new ArrayList<Student>();

    public Manager() {
        list.add(new Student("SE150905", "Bao Minh", "Spring", "Java"));
        list.add(new Student("SE150246", "Trung Nghia", "Summer", "Python"));
        list.add(new Student("SE170905", "Hoang Minh", "Spring", "Python"));
        list.add(new Student("SE170906", "Bla Bla", "Spring", "Java"));
        list.add(new Student("SE161975", "Bao Minh", "Spring", "C++"));
        list.add(new Student("SE160955", "Bao Minh", "Spring", "Java"));
    }

    // Show menu
    public static Scanner sc = new Scanner(System.in);

    public static void menu() {
        System.out.println(" 1.	Create");
        System.out.println(" 2.	Find and Sort");
        System.out.println(" 3.	Update/Delete");
        System.out.println(" 4.	Report");
        System.out.println(" 5.	Exit");
        System.out.print(" Enter your choice: ");
    }

    // Allow user create new student
    public boolean createStudent(ArrayList<Student> list, Student student) {
        boolean result = true;
        String id = student.getId();
        if (student.getId().isEmpty())
            result = false;
        for (Student astudent : list) {
            if (astudent.getId().equals(id)) {
                result = false;
            }
        }
        list.add(student);
        return result;
    }

    // Allow user find a student
    public boolean findStudentByID(String id, List<Student> list) {
        boolean result = false;
        if (list.isEmpty()) {
            result = false;
        } else {
            for (Student student : list) {
                if (student.getId().equals(id)) {
                    result = true;
                }
            }
        }

        return result;
    }

    public Student findStudentByID1(String id, List<Student> list) {
        Student student = null;
        if (list.isEmpty()) {
            return null;
        } else {
            for (Student currStudent : list) {
                if (currStudent.getId().equals(id)) {
                    student = new Student(id, currStudent.getStudentName(), currStudent.getSemester(),
                            currStudent.getCourseName());
                }
            }
        }
        return student;
    }

    // Update a student
    public boolean updateStudentByID(String id, List<Student> list) {
        boolean result = false;
        if (list.isEmpty())
            result = false;
        else {
            Student student = findStudentByID1(id, list);
            if (student != null) {
                System.out.println("Enter new Student name: ");
                String newName = sc.nextLine();
                System.out.println("Enter new semester: ");
                String newSemester = sc.nextLine();
                System.out.println("Enter new course name: ");
                String newCourseName = sc.nextLine();
                boolean isChanged = checkChangeInformation(student, newName, newSemester, newCourseName);
                if (isChanged) {
                    student.setStudentName(newName);
                    student.setSemester(newSemester);
                    student.setCourseName(newCourseName);
                    result = true;
                } else result = false;
            } else {
                result = false;
            }
        }
        return result;
    }

    private boolean checkChangeInformation(Student student, String name, String semester, String course) {
        if (name.equalsIgnoreCase(student.getStudentName()) && semester.equalsIgnoreCase(student.getSemester())
                && course.equalsIgnoreCase(student.getCourseName())) {
            return false;
        }
        return true;
    }

    public boolean deleteStudentByID(String id, List<Student> list) {
        boolean result = false;
        if (list.isEmpty())
            result = false;
        else {
            Student student = findStudentByID1(id, list);
            if (student != null) {
                result = true;
                list.remove(student);
            } else
                result = false;
        }
        return result;
    }

    // List student found by name
    public static ArrayList<Student> listStudentFindByName(ArrayList<Student> ls) {
        ArrayList<Student> listStudentFindByName = new ArrayList<Student>();
        System.out.print("Enter name to search: ");
        String name = Validation.checkInputString();
        for (Student student : ls) {
            // check student have name contains input
            if (student.getStudentName().contains(name)) {
                listStudentFindByName.add(student);
            }
        }
        return listStudentFindByName;
    }

    // Allow user update and delete
    public static void updateOrDelete(int count, ArrayList<Student> ls) {
        // if list empty
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.print("Enter id: ");
        String id = Validation.checkInputString();
        ArrayList<Student> listStudentFindByName = getListStudentById(ls, id);
        // check list empty
        if (listStudentFindByName.isEmpty()) {
            System.err.println("Not found student.");
            return;
        } else {
            Student student = getStudentByListFound(listStudentFindByName);
            System.out.print("Do you want to update (U) or delete (D) student: ");
            // check user want to update or delete
            if (Validation.checkInputUD()) {
                System.out.print("Enter id: ");
                String idStudent = Validation.checkInputString();
                System.out.print("Enter name student: ");
                String name = Validation.checkInputString();
                System.out.print("Enter semester: ");
                String semester = Validation.checkInputString();
                System.out.print("Enter name course: ");
                String course = Validation.checkInputCourse();
                // check user change or not
                if (!Validation.checkChangeInfomation(student, id, name, semester, course)) {
                    System.err.println("Nothing change.");
                }
                // check student exist or not
                if (Validation.checkStudentExist(ls, id, name, semester, course)) {
                    student.setId(idStudent);
                    student.setStudentName(name);
                    student.setSemester(semester);
                    student.setCourseName(course);
                    System.err.println("Update success.");
                }
                return;
            } else {
                ls.remove(student);
                count--;
                System.err.println("Delete success.");
                return;
            }
        }
    }

    // Get student user want to update/delete in list found
    public static Student getStudentByListFound(ArrayList<Student> listStudentFindByName) {
        System.out.println("List student found: ");
        int count = 1;
        System.out.printf("%-10s%-15s%-15s%-15s\n", "Number", "Student name", "semester", "Course Name");
        // display list student found
        for (Student student : listStudentFindByName) {
            System.out.printf("%-10d%-15s%-15s%-15s\n", count, student.getStudentName(), student.getSemester(),
                    student.getCourseName());
            count++;
        }
        System.out.print("Enter student: ");
        int choice = Validation.checkInputIntLimit(1, listStudentFindByName.size());
        return listStudentFindByName.get(choice - 1);
    }

    // Get list student find by id
    public static ArrayList<Student> getListStudentById(ArrayList<Student> ls, String id) {
        ArrayList<Student> getListStudentById = new ArrayList<Student>();
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())) {
                getListStudentById.add(student);
            }
        }
        return getListStudentById;
    }

    // Print report
    public static void report(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        ArrayList<Report> lr = new ArrayList<Report>();
        int size = ls.size();
        for (int i = 0; i < size; i++) {
            int total = 0; // totalCourse
            for (Student student : ls) {
                String id = student.getId();
                String courseName = student.getCourseName();
                String studentName = student.getStudentName();
                for (Student studentCountTotal : ls) {
                    if (id.equalsIgnoreCase(studentCountTotal.getId())
                            && courseName.equalsIgnoreCase(studentCountTotal.getCourseName())) {
                        total++;
                    }
                }
                if (Validation.checkReportExist(lr, studentName, courseName, total)) {
                    lr.add(new Report(student.getStudentName(), studentName, total));
                }
            }
        }
        // print report
        for (int i = 0; i < lr.size(); i++) {
            System.out.printf("%-15s|%-10s|%-5d\n", lr.get(i).getStudentName(), lr.get(i).getCourseName(),
                    lr.get(i).getTotalCourse());
        }
    }
}