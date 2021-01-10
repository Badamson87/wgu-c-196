package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import Helpers.Database;
import Models.Term;

public class AddTermActivity extends AppCompatActivity {
    EditText termNameInput;
    EditText termStartInput;
    EditText termEndInput;
    Button  saveTermButton;
    Button  cancelSaveTerm;
    Database db;
    SimpleDateFormat formatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        db = Database.getInstance(getApplicationContext());
        saveTermButton = findViewById(R.id.saveTermButton);
        cancelSaveTerm = findViewById(R.id.cancelSaveTerm);
        termNameInput = findViewById(R.id.termNameInput);
        termStartInput = findViewById(R.id.termStartInput);
        termEndInput = findViewById(R.id.termendInput);
        formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        saveTermButton.setOnClickListener((e) -> {saveTerm();});
        cancelSaveTerm.setOnClickListener((e) -> cancelSaveTerm());
    }

    private void saveTerm() {
        if (termNameInput.getText().toString().equals("") || termStartInput.getText().toString().equals("") || termEndInput.getText().toString().equals("")){
            return;
        }
        Date start = new Date(termStartInput.getText().toString());
        Date end = new Date(termEndInput.getText().toString());
        Term term = new Term();
        term.setTerm_name(termNameInput.getText().toString());
        term.setTerm_start(start);
        term.setTerm_end(end);
        db.termDao().insertTerm(term);
    }

    public void cancelSaveTerm(){
        Intent intent = new Intent(AddTermActivity.this, AllTermsActivity.class);
        startActivity(intent);
    }
}