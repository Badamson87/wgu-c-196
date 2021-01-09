package Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courseNote_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_fk",
                onDelete = CASCADE
        ))
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private int courseNote_id;
    @ColumnInfo(name = "course_fk")
    private int course_fk;
    @ColumnInfo(name = "note")
    private String note;

    public CourseNote(){}

    public CourseNote(int courseNote_id, int course_fk, String note){
        this.courseNote_id = courseNote_id;
        this.course_fk = course_fk;
        this.note = note;
    }

    public int getCourseNote_id() {
        return courseNote_id;
    }

    public void setCourseNote_id(int courseNote_id) {
        this.courseNote_id = courseNote_id;
    }

    public int getCourse_fk() {
        return course_fk;
    }

    public void setCourse_fk(int course_fk) {
        this.course_fk = course_fk;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
