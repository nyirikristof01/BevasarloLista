package com.example.bevasarlolista;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText productInput;
    private ListView productListView;
    private ProductDatabase productDatabase;
    private ArrayAdapter<String> productAdapter;
    private ArrayList<String> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productInput = findViewById(R.id.productInput);
        productListView = findViewById(R.id.productListView);

        productDatabase = new ProductDatabase(this);
        productDatabase.open();

        productList = new ArrayList<>();
        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        productListView.setAdapter(productAdapter);

        loadProducts();

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productInput.getText().toString().trim();
                if (!productName.isEmpty()) {
                    productDatabase.addProduct(productName);
                    productInput.setText("");
                    loadProducts();
                    Toast.makeText(MainActivity.this, "Termék hozzáadva", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Kérlek adj meg egy terméket", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadProducts() {
        productList.clear();
        productList.addAll(productDatabase.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productDatabase.close();
    }
}
