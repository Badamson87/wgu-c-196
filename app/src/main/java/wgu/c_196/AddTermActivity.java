package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

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
    Database db;
    SimpleDateFormat formatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        db = Database.getInstance(getApplicationContext());
        saveTermButton = findViewById(R.id.saveTermButton);
        termNameInput = findViewById(R.id.termNameInput);
        termStartInput = findViewById(R.id.termStartInput);
        termEndInput = findViewById(R.id.termendInput);
        formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        saveTermButton.setOnClickListener((e) -> {saveTerm();});
    }

    private void saveTerm(){
        System.out.println("save term clicked");
        System.out.println(termNameInput.getText().toString());
        System.out.println(termStartInput.getText().toString());
        System.out.println(termEndInput.getText().toString());
     if (termNameInput != null && termStartInput != null && termEndInput !=null)
     {
         System.out.println("got past save term null checks");
         Date start = new Date(termStartInput.getText().toString());
         Date end = new Date(termEndInput.getText().toString());
         Term term = new Term();
         term.setTerm_name(termNameInput.getText().toString());
         term.setTerm_start(start);
         term.setTerm_end(end);
         db.termDao().insertTerm(term);
     }
        System.out.println("starting term print from add term");
        db.termDao().getAllTerms().forEach((term -> {
            System.out.println(term.getTerm_name());
        }));
    }
}