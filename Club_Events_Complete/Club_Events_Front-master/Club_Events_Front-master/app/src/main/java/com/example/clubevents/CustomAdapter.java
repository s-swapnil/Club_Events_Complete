package com.example.clubevents;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private ArrayList<DisplayContent.Entry> list;
    private ViewListener viewListener;

    public CustomAdapter(ArrayList<DisplayContent.Entry> list, ViewListener viewListener) {
        this.list = list;
        this.viewListener = viewListener;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int viewnum;
        Log.d("Adapt", "onCreateViewHolder: " + viewType + "  " + list.get(viewType).VID_IMG_URL);
        if (list.get(viewType).VID_IMG_URL == null && list.get(viewType).IMG_URLS.isEmpty()) {
            viewnum = R.layout.holder_text;
        } else if (list.get(viewType).VID_IMG_URL == null) {
            viewnum = R.layout.holder_pics;
        } else {
            viewnum = R.layout.holder_video;
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewnum, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.text.setText(list.get(position).text);
        holder.clubName.setText(list.get(position).clubName);
        holder.board.setText(list.get(position).board);
        holder.time.setText(list.get(position).timestamp.toString());

        if (list.get(position).VID_IMG_URL != null) {
            Picasso.get().load(list.get(position).VID_IMG_URL).error(R.drawable.baseline_hd_black_48dp).placeholder(R.drawable.baseline_hd_black_48dp)
                    .into(holder.video_img);
        }
        else  if (list.get(position).IMG_URLS.size() >= 1){
            if (list.get(position).IMG_URLS.size() >= 1) {
                Picasso.get().load(list.get(position).IMG_URLS.get(0)).error(R.drawable.baseline_hd_black_48dp).placeholder(R.drawable.baseline_hd_black_48dp)
                        .into(holder.img1);
            } else {
                holder.img1.setVisibility(View.INVISIBLE);
            }
            if (list.get(position).IMG_URLS.size() >= 2) {
                Picasso.get().load(list.get(position).IMG_URLS.get(1)).error(R.drawable.baseline_hd_black_48dp).placeholder(R.drawable.baseline_hd_black_48dp)
                        .into(holder.img2);
            } else {
                holder.img2.setVisibility(View.INVISIBLE);
                holder.txt2.setText("");
            }
            if (list.get(position).IMG_URLS.size() >= 3) {
                Picasso.get().load(list.get(position).IMG_URLS.get(2)).error(R.drawable.baseline_hd_black_48dp).placeholder(R.drawable.baseline_hd_black_48dp)
                        .into(holder.img3);
            } else {
                holder.img3.setVisibility(View.INVISIBLE);
                holder.txt3.setText("");
            }
            if (list.get(position).IMG_URLS.size() >= 4) {
                Picasso.get().load(list.get(position).IMG_URLS.get(3)).error(R.drawable.baseline_hd_black_48dp).placeholder(R.drawable.baseline_hd_black_48dp)
                        .into(holder.img4);
                holder.extraImg.setText(list.get(position).MoreImgs);
            } else {
                holder.img4.setVisibility(View.INVISIBLE);
                holder.txt4.setText("");
                holder.extraImg.setText("");
            }
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void UpdateData(ArrayList<DisplayContent.Entry> entries) {
        list = entries;
        notifyDataSetChanged();
        Log.d("LIST", "UpdateData: " + list.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text, clubName, board, time, viewPost;
        ImageView video_img, img1, img2, img3, img4;
        TextView extraImg, txt2, txt3, txt4;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = itemView.findViewById(R.id.text_text);
            this.board = itemView.findViewById(R.id.board_text);
            this.clubName = itemView.findViewById(R.id.clubName_text);
            this.time = itemView.findViewById(R.id.time_text);
            this.viewPost = itemView.findViewById(R.id.viewPost_text);
            this.video_img = itemView.findViewById(R.id.imageView);
            this.img1 = itemView.findViewById(R.id.imageView);
            this.img2 = itemView.findViewById(R.id.imageView2);
            this.img3 = itemView.findViewById(R.id.imageView3);
            this.img4 = itemView.findViewById(R.id.imageView4);
            this.extraImg = itemView.findViewById(R.id.extraspics);
            this.txt2 = itemView.findViewById(R.id.txt2);
            this.txt3 = itemView.findViewById(R.id.txt3);
            this.txt4 = itemView.findViewById(R.id.txt4);

            Log.d("PICS", "MyViewHolder: " + itemView.findViewById(R.id.imageView));
            viewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomAdapter.this.viewListener.OnClick(getAdapterPosition());
                    Log.d("PIC", "MyViewHolderclick: " + getAdapterPosition());
                }
            });

        }
    }
}
