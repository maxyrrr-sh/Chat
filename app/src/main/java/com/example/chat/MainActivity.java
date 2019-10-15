package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    MyAdapter adapter;
    EditText text;
    Button send;
    RecyclerView recycler;
    LinkedList<UserMessage> messages = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        send = findViewById(R.id.buttonsend);
        recycler = findViewById(R.id.recycler);
        send.setOnClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(messages);
        recycler.setAdapter(adapter);
        getData();
    }
    public void getData(){
        final LinkedList<UserMessage> list = new LinkedList<>();
        db.collection("messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("ssss" , document.getId() + " => " + document.getData());
                                UserMessage message = new UserMessage(document.get("message").toString(), document.get("name").toString());

                                document.get("message");
                                list.add(message);
                            }
                            adapter.setMessages(list);
                            adapter.notifyDataSetChanged();
                            recycler.scrollToPosition(adapter.getItemCount()-1);
                        } else {
                            //Log.d( "ssss", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        UserMessage message = new UserMessage(text.getText().toString(), " test user");
        Map<String, String> msg = new HashMap<>();
        msg.put("message", text.getText().toString());
        SimpleDateFormat format = new SimpleDateFormat("dd-M hh:mm:ss");
        long date = format.getCalendar().getTimeInMillis();
        msg.put("name", "test user");
        msg.put("time", String.valueOf(date));
        db.collection("messages").document(getTime()).set(msg);

        text.setText(" ");
        getData();
    }
    String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd1M1yyyy1hh:mm:ss");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }
}
