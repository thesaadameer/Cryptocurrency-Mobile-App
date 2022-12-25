package com.android.crypto;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder> {

    private ArrayList<CurrencyRVModel> currencyRVModelArrayList;
    private ArrayList<CurrencyRVModel> favorites;
    private DatabaseHelper databaseHelper;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");



    public CurrencyRVAdapter(ArrayList<CurrencyRVModel> currencyRVModelArrayList, Context context) {
        this.currencyRVModelArrayList = currencyRVModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_rv_item, parent, false);
        return new CurrencyRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.ViewHolder holder, int position) {
        CurrencyRVModel currencyRVModel = currencyRVModelArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVModel.getName());
        holder.rateTV.setText("$ " + df2.format(currencyRVModel.getPrice()));
        holder.symbolTV.setText(currencyRVModel.getSymbol());
        holder.rvCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isExist(currencyRVModel)) {
                    customDialog(currencyRVModel);
                } else {
                    CryptoTable.insert(databaseHelper, currencyRVModel);
                    Toast.makeText(context, "Added to database", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    private boolean isExist(CurrencyRVModel currencyRVModel) {
        favorites = CryptoTable.getAllFavorites(databaseHelper);
        for (CurrencyRVModel coin : favorites) {
            if (coin.getName().equals(currencyRVModel.getName())) {
                return true;
            }
        }
        return false;
    }

    private void customDialog(CurrencyRVModel currencyRVModel) {
        Dialog dialog;
        Button btnCancel;
        Button btnOK;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnOK = dialog.findViewById(R.id.btnOk);
        dialog.show();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CryptoTable.delete(databaseHelper, currencyRVModel);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return currencyRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView symbolTV, currencyNameTV, rateTV;
        private CardView rvCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvCardView = itemView.findViewById(R.id.rvCardView);
            currencyNameTV = itemView.findViewById(R.id.idTVName);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            databaseHelper = new DatabaseHelper(context);

        }
    }
}
