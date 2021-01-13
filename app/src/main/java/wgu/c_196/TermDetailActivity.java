package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import Helpers.Database;
import Models.Course;
import Models.Term;

public class TermDetailActivity extends AppCompatActivity {
    Database db;
    TextView displayedTermName;
    TextView displayedTermDates;
    ListView allCoursesByTermList;
    List<Course> allCoursesByTerm;
    FloatingActionButton deleteTerm;
    int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        termID = getIntent().getIntExtra("termId", 0);
        displayedTermName = findViewById(R.id.displayedTermName);
        displayedTermDates = findViewById(R.id.displayedTermDates);
        allCoursesByTermList = findViewById(R.id.allCoursesByTermList);
        deleteTerm = findViewById(R.id.deleteTerm);

        deleteTerm.setOnClickListener((e) -> attemptDeleteTerm());
        db = Database.getInstance(getApplicationContext());
        this.loadDisplay();
    }

    private void attemptDeleteTerm(){
        if (this.allCoursesByTerm.isEmpty()){
            Term term = db.termDao().getTermById(termID);
            db.termDao().deleteTerm(term);
            sendToAllTerms();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to delete term with courses", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void sendToAllTerms(){
        Intent intent = new Intent(TermDetailActivity.this, AllTermsActivity.class);
        startActivity(intent);
    }

    private void loadDisplay(){
        Term term = db.termDao().getTermById(termID);
        String start = term.getTerm_start().toString().substring(0, 10) + ", " + term.getTerm_start().toString().substring(24, 28);
        String end = term.getTerm_end().toString().substring(0, 10) + ", " + term.getTerm_end().toString().substring(24, 28);
        displayedTermName.setText(term.getTerm_name());
        displayedTermDates.setText(start + " - " + end);
        this.getAllCoursesForTerm();
    }

    private void getAllCoursesForTerm(){
        List<Course> courses = db.courseDao().getAllCoursesByTermId(this.termID);
        this.allCoursesByTerm = courses;
        ArrayAdapter<Course> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        allCoursesByTermList.setAdapter(adapter);
    }

    public void addCourseScreen(View view) {
        Intent intent = new Intent(TermDetailActivity.this, CreateCourseActivity.class);
        startActivity(intent);
    }
}