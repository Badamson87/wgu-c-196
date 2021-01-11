package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import Helpers.Database;
import Models.Term;

public class AllTermsActivity extends AppCompatActivity {
    ListView allTermsList;
    List<Term> allTerms;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        db = Database.getInstance(getApplicationContext());
        allTermsList = findViewById(R.id.allTermsList);
        this.getAllTerms();
        allTermsList.setOnItemClickListener((parent, view, position, id) -> {
            loadTermDetail(allTerms.get(position).getTerm_id());
        });
    }

    private void loadTermDetail(int termId){
        Intent intent = new Intent(AllTermsActivity.this, TermDetailActivity.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
    }

    private void getAllTerms(){
        List<Term> terms = db.termDao().getAllTerms();
        this.allTerms = terms;
        ArrayAdapter<Term> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, terms);
        allTermsList.setAdapter(adapter);
    }

    public void addTermScreen(View view) {
        Intent intent = new Intent(AllTermsActivity.this, AddTermActivity.class);
        startActivity(intent);
    }
}