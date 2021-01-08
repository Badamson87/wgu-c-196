package Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Models.Course;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course_table where term_fk = :termId ORDER BY course_id")
    List<Course> getAllCoursesByTermId(int termId);

    @Query("SELECT * FROM course_table")
    List<Course> getAllCourses();

    @Query("SELECT * FROM course_table WHERE course_id = :courseId")
    Course getCourseById(int courseId);

    @Insert
    void insertCourse(Course course);

    @Insert
    void insertAllCourses(Course... course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("DELETE FROM course_table")
    public void deleteAllCourses();

}
