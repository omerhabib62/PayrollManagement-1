package com.example.admin.payrollapplication;

import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button empButton;
    Button deptButton;
    Button registerButton;
    Button emailBtn;
    Button schedBtn;
    // Write a message to the database
    DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        myRef = FirebaseDatabase.getInstance().getReference("person");

        empButton = findViewById(R.id.empButton);

        empButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        DetailsForEmployeeActivity.class);
                startActivity(myIntent);
            }
        });

        deptButton = findViewById(R.id.deptButton);

        deptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        DetailsForManager  .class);
                startActivity(myIntent);
            }
        });

        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        RegistrationActivity  .class);
                startActivity(myIntent);
            }
        });
        emailBtn = (Button) findViewById(R.id.sendEmail);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });

        schedBtn = (Button) findViewById(R.id.scheduleButton);

        schedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ScheduleActivity.class);
                startActivity(intent);
            }
        });
    }



   /* private void addPerson(){
       // String name = nameText.getText().toString().trim();
        //String password = passwordText.getText().toString().trim();
        if(!TextUtils.isEmpty(name)){
            String id = myRef.push().getKey();
            Person person1 = new Person(id,name,password);
            myRef.child(id).setValue(person1);
            Toast.makeText(this,"Data added.",Toast.LENGTH_LONG).show();
        }
            else{
            Toast.makeText(this,"Enter name.",Toast.LENGTH_LONG).show();
            }


    }*/
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void readPerson(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               // String value = dataSnapshot.getValue(String.class);
                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    String key = ds.getKey();
                    Person p =   ds.getValue(Person.class);

                    Log.d(TAG, "Test Name is: " + p.getName());
                    Log.d(TAG, "Test ID is: " + p.getId());
                    //Toast.makeText(this,p.toString(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



}
