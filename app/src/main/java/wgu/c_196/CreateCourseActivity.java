package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import Helpers.Database;

public class CreateCourseActivity extends AppCompatActivity {
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

    }


}