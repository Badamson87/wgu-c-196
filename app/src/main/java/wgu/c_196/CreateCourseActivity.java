package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import Helpers.CourseStatus;
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
    int notifyId;

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
        planToTakeRadio.setChecked(true);
        notifyId = 1;
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
           courseAlert.setChecked(originalCourse.getCourse_alert());
           String status = originalCourse.getCourse_status();
           System.out.println(status);
           System.out.println(CourseStatus.InProgress.toString());
           System.out.println(CourseStatus.Complete.toString());
           System.out.println(CourseStatus.Dropped.toString());
           if (status.equals("In Progress")){
               progressRadio.setChecked(true);
           }
           if (status.equals("Complete")){
               completeRadio.setChecked(true);
           }
           if (status.equals("Dropped")){
               droppedRadio.setChecked(true);
           }
       }
    }
    
    private void saveTerm(){
        if (checkFields()){
            if (!this.setAlert()){
                return;
            }
            if (courseId > 0) {
                Date start = new Date(courseStartInput.getText().toString());
                Date end = new Date(courseEndInput.getText().toString());
                Course course = new Course();
                course.setCourse_name(courseNameInput.getText().toString());
                course.setTerm_fk(termId);
                course.setCourse_start(start);
                course.setCourse_end(end);
                course.setCourse_alert(courseAlert.isChecked());
                course.setCourse_notes(courseNoteInput.getText().toString());
                course.setCourse_status(getCourseStatus());
                course.setCourse_id(courseId);
                db.courseDao().updateCourse(course);
                this.sendToTermDetail();
            } else {
                Date start = new Date(courseStartInput.getText().toString());
                Date end = new Date(courseEndInput.getText().toString());
                Course course = new Course();
                course.setCourse_name(courseNameInput.getText().toString());
                course.setTerm_fk(termId);
                course.setCourse_start(start);
                course.setCourse_end(end);
                course.setCourse_alert(courseAlert.isChecked());
                course.setCourse_notes(courseNoteInput.getText().toString());
                course.setCourse_status(getCourseStatus());
                db.courseDao().insertCourse(course);
                this.sendToTermDetail();
            }
        }
    }



    private boolean setAlert(){
        Date alertStartDate = new Date(courseStartInput.getText().toString());
        Date alertEndDate = new Date(courseEndInput.getText().toString());
        if (!courseAlert.isChecked()){
            return true;
        }
        if (alertStartDate.before(new Date())){
            Toast toast = Toast.makeText(getApplicationContext(), "Start Date is before current date", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (alertEndDate.before(new Date())){
            Toast toast = Toast.makeText(getApplicationContext(), "End Date is before current date", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        AlertReminder.setAlert(alertStartDate, "Course Alert", courseNameInput.getText().toString() + " is starting", getApplicationContext());
        AlertReminder.setAlert(alertEndDate, "Course Alert", courseNameInput.getText().toString() + " is ending", getApplicationContext());
        return true;
    }

    private String getCourseStatus(){
        if (planToTakeRadio.isChecked()){
            return "Plan To Take";
        }
        if (progressRadio.isChecked()){
            return "In Progress";
        }
        if (completeRadio.isChecked()){
            return "Complete";
        }
        return "Dropped";
    }

    private boolean checkFields(){
        if (courseNameInput.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Name required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (courseStartInput.getText().toString().contains("C")){
            Toast toast = Toast.makeText(getApplicationContext(), "Start date required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (courseEndInput.getText().toString().contains("C")){
            Toast toast = Toast.makeText(getApplicationContext(), "End date required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
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