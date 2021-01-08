package Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Models.Term;

@Dao
public interface TermDao {
    @Query("SELECT * FROM term_table ORDER BY term_id")
    List<Term> getTermList();

    @Query("SELECT * FROM term_table WHERE term_id = :termId ORDER BY term_id")
    Term getTermById(int termId);

    @Query("SELECT * FROM term_table")
    List<Term> getAllTerms();

    @Insert
    void insertTerm(Term term);

    @Insert
    void insertAllTerms(Term... term);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("DELETE FROM term_table")
    public void deleteAllTerms();

}
