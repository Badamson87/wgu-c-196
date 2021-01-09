package Helpers;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import Daos.AssessmentDao;
import Daos.CourseDao;
import Daos.CourseNoteDao;
import Daos.MentorDao;
import Daos.TermDao;
import Models.Assessment;
import Models.Course;
import Models.CourseNote;
import Models.Mentor;
import Models.Term;

@androidx.room.Database(entities = {Term.class, Course.class, CourseNote.class, Mentor.class, Assessment.class}, exportSchema = false, version = 1)
@TypeConverters({Converter.class})
public abstract class Database extends RoomDatabase {
    private static final String DB_NAME = "wgu_db";
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract AssessmentDao assessmentDao();
    public abstract CourseDao courseDao();
    public abstract CourseNoteDao courseNoteDao();
    public abstract MentorDao mentorDao();
    public abstract TermDao termDao();
}
