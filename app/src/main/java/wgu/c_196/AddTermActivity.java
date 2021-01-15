package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import Helpers.Database;
import Helpers.DatePickerFragment;
import Models.Term;

public class AddTermActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText termNameInput;
    TextView termStartInput;
    TextView termEndInput;
    Button  saveTermButton;
    Button  cancelSaveTerm;
    Database db;
    Boolean updateStart;
    int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        db = Database.getInstance(getApplicationContext());
        saveTermButton = findViewById(R.id.saveTermButton);
        cancelSaveTerm = findViewById(R.id.cancelSaveTerm);
        termNameInput = findViewById(R.id.termNameInput);
        termStartInput = findViewById(R.id.termStartInput);
        termEndInput = findViewById(R.id.termEndInput);
        termStartInput.setOnClickListener((e) -> updateStart());
        termEndInput.setOnClickListener((e) -> updateEnd());
        saveTermButton.setOnClickListener((e) -> {saveTerm();});
        cancelSaveTerm.setOnClickListener((e) -> sendToAllTerms());
        checkForUpdate();
    }

    private void checkForUpdate(){
        termID = getIntent().getIntExtra("termId", 0);
        if (termID > 0){
            Term originalTerm = db.termDao().getTermById(termID);
            termNameInput.setText(originalTerm.getTerm_name());
            termStartInput.setText(originalTerm.getTerm_start().toString().substring(0, 10) + ", " + originalTerm.getTerm_start().toString().substring(24, 28));
            termEndInput.setText(originalTerm.getTerm_end().toString().substring(0, 10) + ", " + originalTerm.getTerm_end().toString().substring(24, 28));
        }
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
        newFragment.show(getSupportFragmentManager(), "termPicker");
    }


    private void saveTerm() {
        if (termNameInput.getText().toString().equals("") || termStartInput.getText().toString().contains("Click") || termEndInput.getText().toString().contains("C")){
            Toast toast = Toast.makeText(getApplicationContext(), "Please complete form.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Date start = new Date(termStartInput.getText().toString());
        Date end = new Date(termEndInput.getText().toString());
        Term term = new Term();
        term.setTerm_name(termNameInput.getText().toString());
        term.setTerm_start(start);
        term.setTerm_end(end);
        if (termID > 0) {
            term.setTerm_id(termID);
            db.termDao().updateTerm(term);
            sendToTermDetail();
        }
        else {
            db.termDao().insertTerm(term);
            sendToAllTerms();
        }
    }

    public void sendToAllTerms(){
        Intent intent = new Intent(AddTermActivity.this, AllTermsActivity.class);
        startActivity(intent);
    }

    public void sendToTermDetail(){
        Intent intent = new Intent(AddTermActivity.this, TermDetailActivity.class);
        intent.putExtra("termId", termID);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (this.updateStart){
            termStartInput.setText((month + 1) + "/" + dayOfMonth + "/" + year);
        }
        else {
            termEndInput.setText((month + 1) + "/" + dayOfMonth + "/" + year);
        }
    }
}