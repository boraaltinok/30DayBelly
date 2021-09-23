package com.example.a30daybellytransformation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


//TODO Font: Euclid
//TODO Loading Page
//TODO Motivation Array
//TODO Streak
// TODO Videolar çekilecek ve DB'ye bağlanıp egzersizin başında yüklenecek
public class MainActivity extends AppCompatActivity {
    //decleration
    EditText et_name;
    EditText et_fitnessLevel;
    Button btn_calculateProgram, btn_nextPage;
    TextView et_programView;
    ArrayList<Day> monthExercises;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializaiton
        et_name = (EditText)findViewById(R.id.et_name);
        et_fitnessLevel = (EditText)findViewById(R.id.et_fitnessLevel);
        btn_calculateProgram = (Button)findViewById(R.id.btn_calculateProgram);
        et_programView = (TextView) findViewById(R.id.et_programView);
        btn_nextPage = (Button)findViewById(R.id.btn_nextPage);

        loadData();
        showProgram();

        btn_calculateProgram.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                user = new User(et_name.getText().toString(), Integer.parseInt(et_fitnessLevel.getText().toString()));
                monthExercises = user.month;
                saveData();
                showProgram();
            }
        });

        btn_nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), program_main.class);
                startActivity(intent);
            }
        });
    }
    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("User", json);
        editor.apply();
    }

    private void clearData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);

        if(user == null)
        {
            user = new User();
            Toast.makeText(this, "HERE", Toast.LENGTH_SHORT).show();
        }
    }
    private void showProgram()
    {
        if(user.getName().equals("default_name") || user == null)
        {
            Toast.makeText(this, "NO PROGRAM TO SHOW", Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i = 0; i < 30; i++)
        {
            et_programView.append("DAY " + (i+1) + "\n");
            for(int j = 0; j < user.month.get(i).exerciseProgramList.size(); j++ )
            {
                et_programView.append(user.month.get(i).exerciseProgramList.get(j).getName() + "\n");
            }
            et_programView.append("\n");
        }

    }
}
