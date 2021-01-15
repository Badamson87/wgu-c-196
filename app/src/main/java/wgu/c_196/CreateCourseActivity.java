package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import Helpers.Database;
import Helpers.DatePickerFragment;
import Models.Course;

public class CreateCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText courseNameInput;
    TextView courseStartInput;
    TextView courseEndInput;
    CheckBox courseAlert;
    RadioGroup courseStatusRadioGroup;
    RadioButton planToTakeRadio;
    RadioButton progressRadio;
    RadioButton completeRadio;
    RadioButton droppedRadio;
    TextInputEditText courseNoteInput;
    Button saveCourseButton;
    Button cancelSaveCourseButton;
    Database db;
    Boolean updateStart;
    int termId;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        db = Database.getInstance(getApplicationContext());
        courseNameInput = findViewById(R.id.courseNameInput);
        courseStartInput = findViewById(R.id.courseStartInput);
        courseEndInput = findViewById(R.id.courseEndInput);
        courseAlert = findViewById(R.id.courseAlert);
        courseStatusRadioGroup = findViewById(R.id.courseStatusRadioGroup);
        planToTakeRadio = findViewById(R.id.planToTakeRadio);
        progressRadio = findViewById(R.id.progressRadio);
        completeRadio = findViewById(R.id.completeRadio);
        droppedRadio = findViewById(R.id.droppedRadio);
        courseNoteInput = findViewById(R.id.courseNoteInput);
        saveCourseButton = findViewById(R.id.saveCourseButton);
        cancelSaveCourseButton = findViewById(R.id.cancelSaveCourseButton);
        termId = getIntent().getIntExtra("termId", 0);

        courseStartInput.setOnClickListener((e) -> updateStart());
        courseEndInput.setOnClickListener((e) -> updateEnd());
        cancelSaveCourseButton.setOnClickListener((e) -> sendToTermDetail());
        saveCourseButton.setOnClickListener((e) -> saveTerm());
        checkForUpdate();
    }

    private void checkForUpdate(){
       courseId = getIntent().getIntExtra("courseId", 0);
       if (courseId > 0) {
           Course originalCourse = db.courseDao().getCourseById(courseId);
           courseNameInput.setText(originalCourse.getCourse_name());
           courseStartInput.setText(originalCourse.getCourse_start().toString().substring(0, 10) + ", " + originalCourse.getCourse_start().toString().substring(24, 28));
           courseEndInput.setText(originalCourse.getCourse_end().toString().substring(0, 10) + ", " + originalCourse.getCourse_end().toString().substring(24, 28));
           courseNoteInput.setText(originalCourse.getCourse_notes());
           // todo radio button and checkbox
           courseAlert.setChecked(originalCourse.getCourse_alert());
       }
    }

    private void saveTerm(){
        // todo
        this.sendToTermDetail();
    }

    private void updateStart(){
        this.updateStart = true;
        displayPicker();
    }
    private void updateEnd(){
        this.updateStart = false;
        displayPicker();
    }

    private void displayPicker() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "coursePicker");
    }

    public void sendToTermDetail(){
        Intent intent = new Intent(CreateCourseActivity.this, TermDetailActivity.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (this.updateStart){
            courseStartInput.setText((month + 1) + "/" + dayOfMonth + "/" + year);
        }
        else {
            courseEndInput.setText((month + 1) + "/" + dayOfMonth + "/" + year);
        }
    }

}