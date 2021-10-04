package com.example.malert.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.malert.R;
import com.example.malert.data.MedicinePOJO;
import com.example.malert.ui.HomeActivity;
import com.example.malert.ui.ViewMedicineActivity;
import com.google.gson.Gson;

import java.util.List;

public class ListOfMedicineAdapter extends RecyclerView.Adapter<ListOfMedicineAdapter.ListOfMedicineAdapterViewHolder> {

    List<MedicinePOJO> medicinePOJOList = null;
    Context context;

    public ListOfMedicineAdapter(List<MedicinePOJO> medicinePOJOList, Context context)
    {
        this.medicinePOJOList = medicinePOJOList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListOfMedicineAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_medicine_adapter_item, parent, false);
        return new ListOfMedicineAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfMedicineAdapterViewHolder holder, int position) {
        holder.bind(medicinePOJOList.get(position));
    }

    @Override
    public int getItemCount() {
        if (medicinePOJOList != null)
        {
            return medicinePOJOList.size();
        }
        else {
            return 0;
        }
    }

    class ListOfMedicineAdapterViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView medicineName;
        private final TextView medicineDateAndTime;

        public ListOfMedicineAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            medicineName = itemView.findViewById(R.id.medicineName);
            medicineDateAndTime = itemView.findViewById(R.id.takeMedicineTime);
        }

        public void bind(final MedicinePOJO medicinePOJO)
        {
            medicineName.setText(medicinePOJO.getName());
            medicineDateAndTime.setText(medicinePOJO.getDateAndTime());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String medicinePOJOString = gson.toJson(medicinePOJO);
                    Intent intent = new Intent(context, ViewMedicineActivity.class);
                    intent.putExtra("medicinePOJO", medicinePOJOString);
                    context.startActivity(intent);
                }
            });
        }
    }
}
