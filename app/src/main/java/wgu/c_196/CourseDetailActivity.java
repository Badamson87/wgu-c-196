package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import Helpers.Database;
import Models.Assessment;
import Models.Course;
import Models.Mentor;

public class CourseDetailActivity extends AppCompatActivity {
    TextView courseName;
    TextView displayedCourseDates;
    TextView displayedMentorName;
    ListView allAssessmentsByCourse;
    List<Assessment> assessments;
    FloatingActionButton deleteCourse;
    FloatingActionButton editCourse;
    FloatingActionButton editMentor;
    FloatingActionButton addAssessment;
    Database db;
    int courseId;
    int termId;
    int mentorId;
    Mentor mentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        db = Database.getInstance(getApplicationContext());
        courseId = getIntent().getIntExtra("courseId", 0);
        termId = getIntent().getIntExtra("termId", 0);
        courseName = findViewById(R.id.displayedCourseName);
        displayedCourseDates = findViewById(R.id.displayedCourseDates);
        displayedMentorName = findViewById(R.id.displayedMentorName);
        allAssessmentsByCourse = findViewById(R.id.allAssessmentsByCourse);
        deleteCourse = findViewById(R.id.deleteCourse);
        editCourse = findViewById(R.id.editCourse);
        editMentor = findViewById(R.id.editMentor);
        addAssessment = findViewById(R.id.addAssessmentFAB);
        editCourse.setOnClickListener((e) -> loadEditCourse());
        deleteCourse.setOnClickListener((e) -> deleteCourse());
        editMentor.setOnClickListener((e) -> loadCreateMentor());
        addAssessment.setOnClickListener((e) -> addAssessmentScreen());
        allAssessmentsByCourse.setOnItemClickListener((parent, view, position, id) -> {
            loadAssessmentDetail(assessments.get(position).getAssessment_id());
        });
        loadCourseDetail();
    }

    private void loadAssessmentDetail(int assessmentId){
        Intent intent = new Intent(CourseDetailActivity.this, CreateAssessmentActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        intent.putExtra("assessmentId", assessmentId);
        startActivity(intent);
    }

    private void loadCourseDetail(){
       Course course = db.courseDao().getCourseById(courseId);
       String start = course.getCourse_start().toString().substring(0, 10) + ", " + course.getCourse_start().toString().substring(24, 28);
       String end = course.getCourse_end().toString().substring(0, 10) + ", " + course.getCourse_end().toString().substring(24, 28);
        courseName.setText(course.getCourse_name());
        displayedCourseDates.setText(start + " - " + end);
        this.getMentor();
        this.getAllCourseAssessments();
    }

    private void getMentor(){
        List<Mentor> mentors = db.mentorDao().getAllMentorsByCourseId(courseId);
        if (mentors.size() > 0){
            Mentor mentor = mentors.get(0);
            this.mentor = mentor;
            mentorId = mentor.getMentor_id();
            this.displayedMentorName.setText(mentor.getMentor_name());
        }
    }

    private void getAllCourseAssessments(){
        List<Assessment> assessments = db.assessmentDao().getAllAssessmentsByCourseId(this.courseId);
        this.assessments = assessments;
        ArrayAdapter<Assessment> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assessments);
        allAssessmentsByCourse.setAdapter(adapter);
    }

    private void loadEditCourse(){
        Intent intent = new Intent(CourseDetailActivity.this, CreateCourseActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    private void deleteCourse(){
        if (this.assessments.isEmpty()) {
            Course course = db.courseDao().getCourseById(courseId);
            db.courseDao().deleteCourse(course);
            sendToTermDetail();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to delete course", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void sendToTermDetail(){
        Intent intent = new Intent(CourseDetailActivity.this, TermDetailActivity.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    private void loadCreateMentor(){
        Intent intent = new Intent(CourseDetailActivity.this, CreateMentorActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        intent.putExtra("mentorId", mentorId);
        startActivity(intent);
    }

    private void addAssessmentScreen(){
        Intent intent = new Intent(CourseDetailActivity.this, CreateAssessmentActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }
}