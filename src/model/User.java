package model;

/**
 * Class cha cua Student va Lecturer
 * Dung abstract vi khong ai la "User" chung chung ca
 * chi co the la Student hoac Lecturer cu the
 */
public abstract class User {

    private String id;
    private String fullName;

    public User(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    // Getters
    public String getId()       { return id; }
    public String getFullName() { return fullName; }

    // Setters
    public void setId(String id)             { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    /**
     * Abstract method: moi subclass phai tu dinh nghia cach hien thi thong tin
     * Day la Polymorphism - Student va Lecturer se in ra khac nhau
     */
    public abstract String getInfo();

    @Override
    public String toString() {
        return getInfo();
    }
}