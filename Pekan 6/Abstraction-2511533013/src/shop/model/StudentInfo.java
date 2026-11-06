package shop.model; // Sesuaikan package jika berada di paket lain (misal: package student;)

public class StudentInfo {
    private String studentId;
    private String name;
    private String major;
    private double gpa;

    // Konstruktor dengan parameter
    public StudentInfo(String studentId, String name, String major, double gpa) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.gpa = gpa;
    }

    // Getter dan Setter
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be blank");
        }
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }

    // Method untuk menampilkan rincian informasi mahasiswa
    public String getDisplayInfo() {
        return String.format("[%s] %s - %s (IPK: %.2f)", studentId, name, major, gpa);
    }

    @Override
    public String toString() {
        return getDisplayInfo();
    }
}