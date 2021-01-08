package Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Models.Assessment;

@Dao
public interface AssessmentDao {
    @Query("SELECT * FROM assessment_table WHERE course_fk = :course_id ORDER BY assessment_id")
    List<Assessment> getAllAssessmentsByCourseId(int course_id);

    @Query("SELECT * FROM assessment_table")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENT_TABLE WHERE assessment_id = :assessmentId")
    Assessment getAssessmentById(int assessmentId);

    @Insert
    void insertAssessment(Assessment assessment);

    @Insert
    void insertAllAssessments(Assessment... assessments);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("DELETE FROM assessment_table ")
    public void deleteAllAssessment();

}
