package com.fahim.madbcdrecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText enterUsername;
    Button submitButton;
    TextView resultTextView;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        enterUsername = findViewById(R.id.username);
        submitButton = findViewById(R.id.submit);
        resultTextView = findViewById(R.id.result);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = enterUsername.getText().toString();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> userList;
                        if (snapshot.exists() && snapshot.getValue() != null) {
                            userList = (List<String>) snapshot.getValue();
                        } else {
                            userList = new ArrayList<>();
                        }
                        userList.add(username);
                        myRef.setValue(userList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue() != null) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                    List<String> userList = snapshot.getValue(t);
                    if (userList != null) {
                        StringBuilder builder = new StringBuilder();
                        for (String user : userList) {
                            builder.append(user).append("\n");
                        }
                        resultTextView.setText(builder.toString());
                    }
                } else {
                    resultTextView.setText("No users yet.");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
//                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
/*
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String, String> usermap = (HashMap<String, String>) dataSnapshot.getValue(new TypeToken<HashMap<String, String>>() {
                }.getRawType());
                StringBuilder builder = new StringBuilder();
                for (String username : usermap.values()) {
                    Log.d("TAG", "Value is: " + username);
                    builder.append(username);
                    builder.append("\n");

                }
                resultTextView.setText(builder.toString());
//                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
*/


    }


}