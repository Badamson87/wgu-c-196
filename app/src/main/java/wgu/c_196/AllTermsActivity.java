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
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        db = Database.getInstance(getApplicationContext());
        allTermsList = findViewById(R.id.allTermsList);
        this.getAllTerms();
        System.out.println("starting term print from all terms");
        db.termDao().getAllTerms().forEach((term -> {
          System.out.println(term.getTerm_name());
      }));
    }

    private void getAllTerms(){
        List<Term> terms = db.termDao().getAllTerms();
        String[] items = new String[terms.size()];
        for (int i = 0; i < terms.size(); i++){
            items[i] = terms.get(i).getTerm_name();
        }
//        terms.forEach(size(term -> {
//            termStringArray.push()
//        }));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        allTermsList.setAdapter(adapter);
    }

    public void addTermScreen(View view) {
        Intent intent = new Intent(AllTermsActivity.this, AddTermActivity.class);
        startActivity(intent);
    }
}