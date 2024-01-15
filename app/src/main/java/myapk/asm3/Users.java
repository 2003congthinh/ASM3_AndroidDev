package myapk.asm3;
import android.graphics.Bitmap;

public class Users {
    private String name;
    private String age;
    private String description;
    private String interest;
    private String program;
    private String email;
    private String gender;
    private String partner;
    private String phone;
    private Bitmap image;

    public Users(String name, String age, String description, String interest, String program, String email, String gender, String partner, String phone, Bitmap image) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.interest = interest;
        this.program = program;
        this.email = email;
        this.gender = gender;
        this.partner = partner;
        this.phone = phone;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
