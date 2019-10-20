package com.translatordata.vpman.hackcbspractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.translatordata.vpman.hackcbspractice.ModelClass.UpvoteData;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Myholder>
{
    List<UpvoteData> upvoteData;
    Context context;

    public Adapter(List<UpvoteData> upvoteData, Context context) {
        this.upvoteData = upvoteData;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyle_data_one, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Myholder holder, int position) {

        holder.Addreess.setText(upvoteData.get(position).getEventAddress());
        holder.name.setText(upvoteData.get(position).getEventName());
        holder.Time.setText(upvoteData.get(position).getTime());
        holder.date.setText(upvoteData.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return upvoteData.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView date,Time,name,Addreess;
        ImageView upVote;
        TextView increement;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.eventDate1);
            Time=itemView.findViewById(R.id.eventTime);
            name=itemView.findViewById(R.id.eventName1);
            upVote=itemView.findViewById(R.id.upVote);
            increement=itemView.findViewById(R.id.increement);

            Addreess=itemView.findViewById(R.id.eventAddress1);
        }
    }
}
