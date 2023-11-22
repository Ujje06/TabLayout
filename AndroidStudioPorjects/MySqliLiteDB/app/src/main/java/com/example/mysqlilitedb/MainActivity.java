package com.example.mysqlilitedb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, occupationEditText, serialNumberEditText;
    private Button addButton, viewButton, updateButton, deleteButton;

    private DBHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView displayTextView = findViewById(R.id.displayTextView);
        nameEditText = findViewById(R.id.nameEditText);
        occupationEditText = findViewById(R.id.occupationEditText);
        serialNumberEditText = findViewById(R.id.serialNumberEditText);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        dbHelper = new DBHandler(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String occupation = occupationEditText.getText().toString();
                int serialNumber = Integer.parseInt(serialNumberEditText.getText().toString());

                dbHelper.insertData(name, occupation, serialNumber);
                Toast.makeText(MainActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = dbHelper.fetchData();
//                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                displayTextView.setText(data);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int serialNumber = Integer.parseInt(serialNumberEditText.getText().toString());
                String newName = nameEditText.getText().toString();
                String newOccupation = occupationEditText.getText().toString();

                dbHelper.updateData(serialNumber, newName, newOccupation);
                Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int serialNumber = Integer.parseInt(serialNumberEditText.getText().toString());
                dbHelper.deleteData(serialNumber);
                Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
