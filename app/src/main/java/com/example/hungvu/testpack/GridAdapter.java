package com.example.hungvu.testpack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tuandv on 15-Aug-15.
 */
public class GridAdapter extends RecyclerView.Adapter<Holder> {
    private final List<GridObject> list;

    public GridAdapter(List<GridObject> list) {
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.txt.setText(list.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


class Holder extends RecyclerView.ViewHolder{
    TextView txt;
    public Holder(View itemView) {
        super(itemView);
        txt = (TextView) itemView.findViewById(R.id.textView);
    }
}