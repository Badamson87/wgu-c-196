package Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessment_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_fk",
                onDelete = CASCADE
        ))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;
    @ColumnInfo(name = "course_fk")
    private int course_fk;
    @ColumnInfo(name = "assessment_name")
    private String assessment_name;
    @ColumnInfo(name = "assessment_start")
    private Date assessment_start;
    @ColumnInfo(name = "assessment_end")
    private Date assessment_end;

    public Assessment(){}

    public Assessment(int assessment_id, int course_fk, String assessment_name, Date assessment_start, Date assessment_end){
        this.assessment_id = assessment_id;
        this.course_fk = course_fk;
        this.assessment_name = assessment_name;
        this.assessment_start = assessment_start;
        this.assessment_end = assessment_end;
    }

    public int getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public int getCourse_fk() {
        return course_fk;
    }

    public void setCourse_fk(int course_fk) {
        this.course_fk = course_fk;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }

    public Date getAssessment_start() {
        return assessment_start;
    }

    public void setAssessment_start(Date assessment_start) {
        this.assessment_start = assessment_start;
    }

    public Date getAssessment_end() {
        return assessment_end;
    }

    public void setAssessment_end(Date assessment_end) {
        this.assessment_end = assessment_end;
    }
}
