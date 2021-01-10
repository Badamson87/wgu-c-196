package Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "course_table",
        foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "term_id",
                childColumns = "term_fk",
                onDelete = CASCADE
        ))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int course_id;
    @ColumnInfo(name = "term_fk")
    private int term_fk;
    @ColumnInfo(name = "course_name")
    private String course_name;
    @ColumnInfo(name = "course_start")
    private Date course_start;
    @ColumnInfo(name = "course_end")
    private Date course_end;
    @ColumnInfo(name = "course_status")
    private String course_status;
    @ColumnInfo(name = "course_notes")
    private String course_notes;
    @ColumnInfo(name = "course_alert")
    private boolean course_alert;

    public Course(){}

    public Course(int course_id, int term_fk, String course_name, Date course_start, Date course_end, String course_status, String course_notes, boolean course_alert ){
        this.course_id = course_id;
        this.term_fk = term_fk;
        this.course_name = course_name;
        this.course_start = course_start;
        this.course_end = course_end;
        this.course_status = course_status;
        this.course_notes = course_notes;
        this.course_alert = course_alert;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getTerm_fk() {
        return term_fk;
    }

    public void setTerm_fk(int term_fk) {
        this.term_fk = term_fk;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Date getCourse_start() {
        return course_start;
    }

    public void setCourse_start(Date course_start) {
        this.course_start = course_start;
    }

    public Date getCourse_end() {
        return course_end;
    }

    public void setCourse_end(Date course_end) {
        this.course_end = course_end;
    }

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public String getCourse_notes() {
        return course_notes;
    }

    public void setCourse_notes(String course_notes) {
        this.course_notes = course_notes;
    }

    public boolean getCourse_alert() {
        return course_alert;
    }

    public void setCourse_alert(boolean course_alert) {
        this.course_alert = course_alert;
    }

    @Override
    public String toString(){ return this.getCourse_name();}
}
