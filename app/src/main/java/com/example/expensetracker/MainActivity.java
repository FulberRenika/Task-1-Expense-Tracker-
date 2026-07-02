package com.example.expensetracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper =
                new DatabaseHelper(this);

        EditText editAmount = findViewById(R.id.editAmount);
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnClear = findViewById(R.id.btnClear);

        TextView txtExpenses = findViewById(R.id.txtExpenses);
        TextView txtTotal = findViewById(R.id.txtTotal);

        String[] categories = {"Food", "Travel", "Shopping"};

        String savedExpenses =
                dbHelper.getAllExpenses();

        txtExpenses.setText(
                "Expenses:\n" +
                        savedExpenses
        );

        int savedTotal =
                dbHelper.getTotalExpense();

        txtTotal.setText(
                "Total: ₹" +
                        savedTotal
        );

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerCategory.setAdapter(adapter);

        final int[] total = {0};

        btnAdd.setOnClickListener(v -> {

            String amountText =
                    editAmount.getText().toString();

            if(amountText.isEmpty()) {

                txtExpenses.setText(
                        "Please enter amount"
                );

                return;
            }

            int amount =
                    Integer.parseInt(amountText);

            String category =
                    spinnerCategory.getSelectedItem().toString();

            dbHelper.insertExpense(
                    category,
                    amount
            );

            total[0] += amount;

            String current =
                    txtExpenses.getText().toString();

            current += "\n" +
                    category +
                    " - ₹" +
                    amount;

            txtExpenses.setText(current);

            txtTotal.setText(
                    "Total: ₹" + total[0]
            );

            editAmount.setText("");

        });

        btnClear.setOnClickListener(v -> {

            dbHelper.clearAllExpenses();

            txtExpenses.setText(
                    "Expenses:"
            );

            txtTotal.setText(
                    "Total: ₹0"
            );

            total[0] = 0;

        });
    }
}