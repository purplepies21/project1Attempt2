package com.example.project1attempt2;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.collection.LLRBNode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<RowData> rowData;
    public CustomRecyclerAdapter(Context context, List rowData) {
    this.context= context;
    this.rowData = rowData;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(rowData.get(position));

        RowData rd = rowData.get(position);

      holder.pTask.setText(rd.getTask());


      //Setting up Calendar
        Calendar rightNow = Calendar.getInstance();

        Calendar calSet = (Calendar) rightNow.clone();

        calSet.set(Calendar.DATE, rd.getDay());
        calSet.set(Calendar.MONTH, rd.getMonth());
        calSet.set(Calendar.YEAR, rd.getYear());
        long timeNow_val = rightNow.getTimeInMillis();
        long time_val = calSet.getTimeInMillis();
        String formatted_Date;

        if((calSet.get(Calendar.DAY_OF_YEAR) == rightNow.get(Calendar.DAY_OF_YEAR)) && (calSet.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR))){

            formatted_Date = (new SimpleDateFormat(", MMM dd").format(time_val));
            holder.pDueDate.setText("Today"+formatted_Date);

        }else if ((timeNow_val - time_val) <= 86400000  && (timeNow_val - time_val) > 0){
            formatted_Date = (new SimpleDateFormat(", MMM dd").format(time_val));
            holder.pDueDate.setText("Yesterday"+formatted_Date);


        }else if((time_val - timeNow_val) <= 86400000  && (time_val - timeNow_val) > 0){
            formatted_Date = (new SimpleDateFormat(", MMM dd").format(time_val));
            holder.pDueDate.setText("Tomorrow"+formatted_Date);

        }else if((calSet.get(Calendar.YEAR) != rightNow.get(Calendar.YEAR))){
            formatted_Date = (new SimpleDateFormat("EE, MMM dd yyyy").format(time_val));
            holder.pDueDate.setText(formatted_Date);


        }else{
            formatted_Date = (new SimpleDateFormat("EE, MMM dd").format(time_val));
            holder.pDueDate.setText(formatted_Date);

        }if(timeNow_val > time_val){
            holder.pDueDate.setTextColor(Color.RED);

        }else{
            holder.pDueDate.setTextColor(Color.BLUE);

        }



    }

    @Override
    public int getItemCount() {
        return rowData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pTask;
        public TextView pDueDate;
        public CheckBox checkBox;

        public ViewHolder(final View itemView) {
            super(itemView);

            pDueDate = (TextView) itemView.findViewById(R.id.timeDueTextView);
            pTask = (TextView) itemView.findViewById(R.id.rowText);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkBox2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    delete(getAdapterPosition());
                }
            });


        }
    }


    public void delete(int position) { //removes the row
        RecyclerView recyclerView = new RecyclerView(this.context);
        CustomRecyclerAdapter recyclerAdapter = new CustomRecyclerAdapter(this.context, rowData);
        rowData.remove(position);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("/users").child("files").child(rowData.get(position).getFileID());

        recyclerView.setAdapter(recyclerAdapter);
        notifyItemRemoved(position);
        databaseReference.removeValue();
        recyclerAdapter.notifyDataSetChanged();


    }


}