package com.example.admin.payrollapplication;

import android.content.Intent;
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
    EditText nameText;
    EditText passwordText;
    Button submitBtn;
    Button registerButton;

    // Write a message to the database
    DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        myRef = FirebaseDatabase.getInstance().getReference("person");
        nameText = findViewById(R.id.nameText);
        passwordText = findViewById(R.id.passwordText);
        submitBtn = findViewById(R.id.submitButton);
        registerButton = findViewById(R.id.registerButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readPerson();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        RegistrationActivity  .class);
                startActivity(myIntent);
            }
        });
    }



    private void addPerson(){
        String name = nameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if(!TextUtils.isEmpty(name)){
            String id = myRef.push().getKey();
            Person person1 = new Person(id,name,password);
            myRef.child(id).setValue(person1);
            Toast.makeText(this,"Data added.",Toast.LENGTH_LONG).show();
        }
            else{
            Toast.makeText(this,"Enter name.",Toast.LENGTH_LONG).show();
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
