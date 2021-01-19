package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Helpers.Database;
import Models.Course;

public class ShareNoteActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int PERMISSIONS_REQUEST_SMS = 0;
    private static final int REQUEST_READ_PHONE_STATE = 0;
    TextView shareNoteInput;
    EditText shareTextInput;
    Button shareTextButton;
    Button cancelShareTextButton;
    int courseId;
    int termId;
    Database db;
    Course course;

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
        shareTextButton.setOnClickListener((e) -> checkSendText());
        cancelShareTextButton.setOnClickListener((e) -> sendToCourseDetail());
        loadNote();
    }

    public void loadNote(){
        Course course = db.courseDao().getCourseById(courseId);
        this.course = course;
        shareNoteInput.setText(course.getCourse_notes());
    }

    public void checkSendText() {
        if (checkFields()){
            permissionCheck();
        }
    }

    private void permissionCheck() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SMS);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendSMSMessage();
            }
        }
    }

    private void sendSMSMessage() {
        String number =  shareTextInput.getText().toString();
        String message = "Course Name: " + course.getCourse_name() + " Note: " + course.getCourse_notes();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_LONG).show();
        this.sendToCourseDetail();
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResult) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_SMS: {
                String number =  shareTextInput.getText().toString();
                String message = "Course Name: " + course.getCourse_name() + " Note: " + course.getCourse_notes();
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS message sent", Toast.LENGTH_LONG).show();
                    this.sendToCourseDetail();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS message failed, please try again", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }


    public boolean checkFields(){
        if (shareTextInput.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Phone required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (shareTextInput.getText().toString().length() != 10) {
            Toast toast = Toast.makeText(getApplicationContext(), "Ten digits required", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    public void sendToCourseDetail() {
        Intent intent = new Intent(ShareNoteActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseId", courseId);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }
}