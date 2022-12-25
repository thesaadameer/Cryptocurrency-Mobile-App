package com.android.crypto;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {


    private RecyclerView currenciesRV;
    private Button favoritesButton2;
    private DatabaseHelper databaseHelper;
    private ArrayList<CurrencyRVModel> favorites;
    private CurrencyRVAdapter currencyRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        currenciesRV = findViewById(R.id.idRVCurrencies);
        favoritesButton2 = findViewById(R.id.favoritesButton2);

        databaseHelper = new DatabaseHelper(this);
        favorites = CryptoTable.getAllFavorites(databaseHelper);

        currencyRVAdapter = new CurrencyRVAdapter(favorites, this);
        currenciesRV.setLayoutManager(new LinearLayoutManager(this));
        currenciesRV.setAdapter(currencyRVAdapter);


        favoritesButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}