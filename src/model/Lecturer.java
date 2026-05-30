package model;

/**
 * Giang vien - ke thua tu User
 * Hien tai don gian, co the mo rong sau
 */
public class Lecturer extends User {

    private String department;

    public Lecturer(String id, String fullName, String department) {
        super(id, fullName);
        this.department = department;
    }

    // Override abstract method tu User (Polymorphism)
    @Override
    public String getInfo() {
        return "[Giang vien] " + getId() + " - " + getFullName()
                + " | Khoa: " + department;
    }

    // Getter & Setter
    public String getDepartment()              { return department; }
    public void setDepartment(String department) { this.department = department; }
}