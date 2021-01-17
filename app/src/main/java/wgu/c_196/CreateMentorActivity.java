package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Helpers.Database;
import Models.Mentor;

public class CreateMentorActivity extends AppCompatActivity {
    EditText mentorName;
    EditText mentorPhone;
    EditText mentorEmail;
    Button saveMentorButton;
    Button cancelSaveMentorButton;
    int courseId;
    int mentorId;
    int termId;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mentor);
        db = Database.getInstance(getApplicationContext());
        mentorName = findViewById(R.id.mentorNameInput);
        mentorPhone = findViewById(R.id.mentorPhoneInput);
        mentorEmail = findViewById(R.id.mentorEmailInput);
        saveMentorButton = findViewById(R.id.saveMentorButton);
        cancelSaveMentorButton = findViewById(R.id.cancelSaveMentorButton);
        courseId = getIntent().getIntExtra("courseId", 0);
        termId = getIntent().getIntExtra("termId", 0);
        mentorId = getIntent().getIntExtra("mentorId", 0);

        saveMentorButton.setOnClickListener((e) -> saveMentor());
        cancelSaveMentorButton.setOnClickListener((e) -> sendToCourseDetail());
        loadMentor();
    }

    public void loadMentor(){
        if (mentorId > 0) {
            Mentor mentor = db.mentorDao().getMentorById(mentorId);
            mentorName.setText(mentor.getMentor_name());
            mentorEmail.setText(mentor.getMentor_email());
            mentorPhone.setText(mentor.getMentor_phone());
        }
    }

    public void saveMentor(){
        if (checkFields()){
           if (mentorId > 0){
               Mentor mentor = new Mentor();
               mentor.setMentor_id(mentorId);
               mentor.setMentor_name(mentorName.getText().toString());
               mentor.setMentor_email(mentorEmail.getText().toString());
               mentor.setMentor_phone(mentorPhone.getText().toString());
               mentor.setCourse_fk(courseId);
               db.mentorDao().updateMentor(mentor);
           }
           else {
               Mentor mentor = new Mentor();
               mentor.setMentor_name(mentorName.getText().toString());
               mentor.setMentor_email(mentorEmail.getText().toString());
               mentor.setMentor_phone(mentorPhone.getText().toString());
               mentor.setCourse_fk(courseId);
               db.mentorDao().insertMentor(mentor);
           }
           this.sendToCourseDetail();
        }
    }

    public boolean checkFields() {
        if (mentorName.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Name required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (mentorPhone.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Phone required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (mentorPhone.getText().toString().length() != 10){
            Toast toast = Toast.makeText(getApplicationContext(), "Ten digits required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (mentorEmail.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    public void sendToCourseDetail() {
        Intent intent = new Intent(CreateMentorActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }
}