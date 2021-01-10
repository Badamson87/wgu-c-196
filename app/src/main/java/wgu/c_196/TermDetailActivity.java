package wgu.c_196;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TermDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        Long id = getIntent().getLongExtra("termId", -1);
        System.out.println("term id from the termDetail " + id);
    }
}