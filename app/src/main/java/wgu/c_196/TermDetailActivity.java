package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import Helpers.Database;
import Models.Term;

public class TermDetailActivity extends AppCompatActivity {
    Database db;
    TextView displayedTermName;
    TextView displayedTermDates;
    int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        displayedTermName = findViewById(R.id.displayedTermName);
        displayedTermDates = findViewById(R.id.displayedTermDates);
        db = Database.getInstance(getApplicationContext());
        termID = getIntent().getIntExtra("termId", 0);
        this.loadDisplay();
    }

    private void loadDisplay(){
        Term term = db.termDao().getTermById(termID);
        String start = term.getTerm_start().toString().substring(0, 10) + ", " + term.getTerm_start().toString().substring(24, 28);
        String end = term.getTerm_end().toString().substring(0, 10) + ", " + term.getTerm_end().toString().substring(24, 28);
        displayedTermName.setText(term.getTerm_name());
        displayedTermDates.setText(start + " - " + end);
    }
}