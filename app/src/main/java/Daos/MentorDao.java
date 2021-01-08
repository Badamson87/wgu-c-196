package Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Models.Mentor;

@Dao
public interface MentorDao {
    @Query("SELECT * FROM mentor_table")
    List<Mentor> getAllMentors();

    @Query("SELECT * FROM mentor_table WHERE course_fk = :course_id")
    List<Mentor> getAllMentorsByCourseId(int course_id);


    @Query("SELECT * FROM mentor_table WHERE mentor_id = :mentor_id")
    Mentor getMentorById(int mentor_id);

    @Insert
    void insertMentor(Mentor mentor);

    @Insert
    void insertAllMentors(Mentor mentor);

    @Update
    void updateMentor(Mentor mentor);

    @Delete
    void deleteMentor(Mentor mentor);

    @Query("DELETE FROM mentor_table")
    public void deleteAllMentors();
}
