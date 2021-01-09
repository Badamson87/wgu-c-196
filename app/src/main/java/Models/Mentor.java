package Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "mentor_table",
    foreignKeys = @ForeignKey(
            entity = Course.class,
            parentColumns = "course_id",
            childColumns = "course_fk",
            onDelete = CASCADE
))
public class Mentor {
    @PrimaryKey(autoGenerate = true)
    private int mentor_id;
    @ColumnInfo(name = "course_fk")
    private int course_fk;
    @ColumnInfo(name = "mentor_name")
    private String mentor_name;
    @ColumnInfo(name = "mentor_phone")
    private String mentor_phone;
    @ColumnInfo(name = "mentor_email")
    private String mentor_email;

    public Mentor(){}

    public Mentor (int mentor_id, int course_fk, String mentor_name, String mentor_phone, String mentor_email){
        this.mentor_id = mentor_id;
        this.course_fk = course_fk;
        this.mentor_name = mentor_name;
        this.mentor_phone = mentor_phone;
        this.mentor_email = mentor_email;
    }

    public int getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(int mentor_id) {
        this.mentor_id = mentor_id;
    }

    public int getCourse_fk() {
        return course_fk;
    }

    public void setCourse_fk(int course_fk) {
        this.course_fk = course_fk;
    }

    public String getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(String mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getMentor_phone() {
        return mentor_phone;
    }

    public void setMentor_phone(String mentor_phone) {
        this.mentor_phone = mentor_phone;
    }

    public String getMentor_email() {
        return mentor_email;
    }

    public void setMentor_email(String mentor_email) {
        this.mentor_email = mentor_email;
    }
}
