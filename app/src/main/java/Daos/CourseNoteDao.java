package Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Models.CourseNote;

@Dao
public interface CourseNoteDao {
    @Query("SELECT * FROM courseNote_table")
    List<CourseNote> getAllCourseNotes();

    @Query("SELECT * FROM courseNote_table where course_fk = :course_id ORDER BY courseNote_id")
    List<CourseNote> getAllCourseNotesByCourseId(int course_id);

    @Query("SELECT * FROM courseNote_table WHERE courseNote_id = :courseNote_id")
    CourseNote getCourseNoteById(int courseNote_id);

    @Insert
    void insertCourseNote(CourseNote courseNote);

    @Insert
    void insertAllCourseNotes(CourseNote... courseNotes);

    @Update
    void updateCourseNote(CourseNote courseNote);

    @Delete
    void deleteCourseNote(CourseNote courseNote);

    @Query("DELETE FROM courseNote_table")
    public void deleteAllCourseNotes();

}
