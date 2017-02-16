package nl.hu.zrb.diarieswithfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class ShowContent extends AppCompatActivity {
    String key;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        mDatabase.child("entries").child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TextView tv1 = (TextView)findViewById(R.id.titleView);
                        TextView tv2 = (TextView) findViewById(R.id.contentView);
                        TextView tv3 = (TextView) findViewById(R.id.dateView);
                        //TODO
                        DiaryEntry diaryEntry = dataSnapshot.getValue(DiaryEntry.class);
                        tv1.setText(diaryEntry.getTitle());
                        tv2.setText(diaryEntry.getContent());
                        Date date = new Date(diaryEntry.getDate());
                        String dateData = DateFormat.format("MMM dd, yyyy h:mmaa", date).toString();
                        tv3.setText(dateData);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.deleteitem) {
            mDatabase.child("entries").child(key).removeValue();

            startActivity(new Intent(this, ShowDiaries.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
