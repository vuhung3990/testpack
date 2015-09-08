package com.example.hungvu.testpack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerItemFragment extends Fragment {
    private RecyclerView recycle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_item_layout, container, false);

        recycle = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        recycle.setAdapter(new MyAdapter());
        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<Holder>{

        @Override
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int i) {
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
