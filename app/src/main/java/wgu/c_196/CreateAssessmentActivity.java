package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import Helpers.Database;
import Helpers.DatePickerFragment;
import Models.Assessment;

public class CreateAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText assessmentName;
    TextView assessmentStartInput;
    TextView assessmentEndInput;
    CheckBox assessmentAlert;
    Button saveAssessmentButton;
    Button cancelAssessmentButton;
    FloatingActionButton deleteAssessment;
    Database db;
    int termId;
    int courseId;
    int assessmentId;
    Boolean updateStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);
        db = Database.getInstance(getApplicationContext());
        assessmentName = findViewById(R.id.assessmentNameInput);
        assessmentStartInput = findViewById(R.id.assessmentStartInput);
        assessmentEndInput = findViewById(R.id.assessmentEndInput);
        assessmentAlert = findViewById(R.id.assessmentAlert);
        cancelAssessmentButton = findViewById(R.id.cancelSaveAssessmentButton);
        saveAssessmentButton = findViewById(R.id.saveAssessmentButton);
        deleteAssessment = findViewById(R.id.deleteAssessment);
        saveAssessmentButton.setOnClickListener((e) -> saveAssessment());
        cancelAssessmentButton.setOnClickListener((e) -> sendToCourseDetail());
        deleteAssessment.setOnClickListener((e) -> deleteAssessment());
        courseId = getIntent().getIntExtra("courseId", 0);
        termId = getIntent().getIntExtra("termId", 0);
        assessmentId = getIntent().getIntExtra("assessmentId", 0);
        assessmentStartInput.setOnClickListener((e) -> updateStart());
        assessmentEndInput.setOnClickListener((e) -> updateEnd());
        this.loadAssessmentDetails();
    }

    public void updateStart(){
        this.updateStart = true;
        displayPicker();
    }

    public void updateEnd(){
        this.updateStart = false;
        displayPicker();
    }

    private void displayPicker() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "coursePicker");
    }

    public void loadAssessmentDetails(){
        if (assessmentId > 0) {
            Assessment assessment = db.assessmentDao().getAssessmentById(assessmentId);
            assessmentName.setText(assessment.getAssessment_name());
            assessmentStartInput.setText(assessment.getAssessment_start().toString().substring(0, 10) + ", " + assessment.getAssessment_start().toString().substring(24, 28));
            assessmentEndInput.setText(assessment.getAssessment_end().toString().substring(0, 10) + ", " + assessment.getAssessment_end().toString().substring(24, 28));
            assessmentAlert.setChecked(assessment.getAssessment_alarm());
        }
    }

    public void deleteAssessment() {
        if (assessmentId > 0) {
            Assessment assessment = db.assessmentDao().getAssessmentById(assessmentId);
            db.assessmentDao().deleteAssessment(assessment);
            this.sendToCourseDetail();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Assessment is not saved", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void saveAssessment() {
        if (checkFields()) {
            if (!this.setAlert()){
                return;
            }
            Date start = new Date(assessmentStartInput.getText().toString());
            Date end = new Date(assessmentEndInput.getText().toString());
            Assessment assessment = new Assessment();
            assessment.setAssessment_name(assessmentName.getText().toString());
            assessment.setAssessment_start(start);
            assessment.setAssessment_end(end);
            assessment.setAssessment_alarm(assessmentAlert.isChecked());
            assessment.setCourse_fk(courseId);
            if (assessmentId > 0) {
                assessment.setAssessment_id(assessmentId);
                db.assessmentDao().updateAssessment(assessment);
            } else {
                db.assessmentDao().insertAssessment(assessment);
            }
            sendToCourseDetail();
        }
    }

    public boolean setAlert(){
        if (!assessmentAlert.isChecked()){
            return true;
        }
        Date startDate = new Date(assessmentStartInput.getText().toString());
        Date startEnd = new Date(assessmentEndInput.getText().toString());
        if (startDate.before(new Date())){
            Toast toast = Toast.makeText(getApplicationContext(), "Start Date is before current date", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (startEnd.before(new Date())){
            Toast toast = Toast.makeText(getApplicationContext(), "End Date is before current date", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        AlertReminder.setAlert(startDate, "Assessment Alert", assessmentName.getText().toString() + " is starting", getApplicationContext());
        AlertReminder.setAlert(startEnd, "Assessment Alert", assessmentName.getText().toString() + " is ending", getApplicationContext());
        return true;
    }

    public boolean checkFields(){
        if (assessmentName.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Name required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (assessmentStartInput.getText().toString().contains("C")){
            Toast toast = Toast.makeText(getApplicationContext(), "Start date required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (assessmentEndInput.getText().toString().contains("C")){
            Toast toast = Toast.makeText(getApplicationContext(), "End date required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    public void sendToCourseDetail(){
        Intent intent = new Intent(CreateAssessmentActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (this.updateStart){
            assessmentStartInput.setText((month + 1) + "/" + dayOfMonth + "/" + year);
        }
        else {
            assessmentEndInput.setText((month + 1) + "/" + dayOfMonth + "/" + year);
        }
    }
}