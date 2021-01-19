package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Helpers.Database;
import Models.Course;

public class ShareNoteActivity extends AppCompatActivity {
    TextView shareNoteInput;
    EditText shareTextInput;
    Button shareTextButton;
    Button cancelShareTextButton;
    int courseId;
    int termId;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_note);
        db = Database.getInstance(getApplicationContext());
        courseId = getIntent().getIntExtra("courseId", 0);
        termId = getIntent().getIntExtra("termId", 0);
        shareNoteInput = findViewById(R.id.shareNoteInput);
        shareTextInput = findViewById(R.id.shareTextInput);
        shareTextButton = findViewById(R.id.shareTextButton);
        cancelShareTextButton = findViewById(R.id.cancelShareTextButton);
        shareTextButton.setOnClickListener((e) -> sendText());
        cancelShareTextButton.setOnClickListener((e) -> sendToCourseDetail());
        loadNote();
    }

    public void loadNote(){
        Course course = db.courseDao().getCourseById(courseId);
        shareNoteInput.setText(course.getCourse_notes());
    }

    public void sendText() {

    }

    public void sendToCourseDetail() {
        Intent intent = new Intent(ShareNoteActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }
}