package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.Interface.NotesClickListener;
import com.example.noteapp.Model.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    Context context;
    List<Notes> notesList;
    NotesClickListener listener;

    public NoteListAdapter(Context context, List<Notes> notesList, NotesClickListener listener) {
        this.context = context;
        this.notesList = notesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.titleTxt.setText(notesList.get(position).getTitle());
        holder.notesTxt.setText(notesList.get(position).getNotes());
        holder.dateTxt.setText(notesList.get(position).getDate());
        holder.dateTxt.setSelected(true);

        if (notesList.get(position).isPinned()) {
            holder.imageView.setImageResource(R.drawable.map_pin_9239326);
        } else {
            holder.imageView.setImageResource(0);
        }

        int color_code = getRandomColor();
        holder.cardView.setCardBackgroundColor(holder.imageView.getResources().getColor(color_code));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(notesList.get(holder.getAdapterPosition()));
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLong(notesList.get(holder.getAdapterPosition()), holder.cardView);
                return true;
            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.Soft_peach);
        colorCode.add(R.color.Light_yellow);
        colorCode.add(R.color.Pastel_heavenly);
        colorCode.add(R.color.Light_purple);
        colorCode.add(R.color.Light_green);
        colorCode.add(R.color.Light_pink);
        colorCode.add(R.color.Lime);
        colorCode.add(R.color.Aquamarine);
        colorCode.add(R.color.Lilac);
        colorCode.add(R.color.dark_blue);
        colorCode.add(R.color.ujujujy);
        colorCode.add(R.color.grayish_blue);
        colorCode.add(R.color.yellow_mallow);
        colorCode.add(R.color.amethyst);
        colorCode.add(R.color.rich_purple_blue);
        colorCode.add(R.color.grayish_purple_pink);
        colorCode.add(R.color.almond);
        colorCode.add(R.color.desert_sand);
        colorCode.add(R.color.pastel_greenish_blue);
        colorCode.add(R.color.dark_green_tea);
        colorCode.add(R.color.gray_lavender);
        colorCode.add(R.color.color_gray_aquamarine);
        colorCode.add(R.color.light_yellow);
        colorCode.add(R.color.dark_gray);
        colorCode.add(R.color.dusty_blue);
        colorCode.add(R.color.pink_brown);
        colorCode.add(R.color.light_golden);
        colorCode.add(R.color.platinum_gray);
        colorCode.add(R.color.apricot);
        colorCode.add(R.color.Niagara_color);
        colorCode.add(R.color.pale_green_gray);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return random_color;
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder {

    CardView cardView;
    TextView notesTxt, titleTxt, dateTxt;
    ImageView imageView;
    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.note_container);
        titleTxt = itemView.findViewById(R.id.txtTitle);
        notesTxt = itemView.findViewById(R.id.notesTxt);
        dateTxt = itemView.findViewById(R.id.dateTxt);
    }
}
