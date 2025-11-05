package model;

public class Email {
    private int idEmail;
    private String Email;

    public Email(){}

    public Email(int idEmail, String email) {
        this.idEmail = idEmail;
        this.Email = email;
    }

    public int getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(int idEmail) {
        this.idEmail = idEmail;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
