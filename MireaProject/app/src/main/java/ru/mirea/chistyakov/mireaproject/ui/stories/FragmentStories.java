package ru.mirea.chistyakov.mireaproject.ui.stories;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.mirea.chistyakov.mireaproject.R;

public class FragmentStories extends Fragment {
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        List<String> data = new ArrayList<>();
        data.add("Text 1");
        data.add("Text 2");
        data.add("Text 3");

        View root = inflater.inflate(R.layout.fragment_stories, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(data, getContext());
        adapter.view_root = root;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return root;
    }

    static class CustomRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{

        static View view_root;
        List<String> data = Collections.emptyList();
        Context context;

        public CustomRecyclerAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View myView = inflater.inflate(R.layout.stories_item, parent, false);
            return new MyViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            holder.viewLarge.setText(data.get(position));
            holder.viewSmall.setText("Text");
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView viewLarge;
        TextView viewSmall;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            viewLarge = CustomRecyclerAdapter.view_root.findViewById(R.id.textViewLarge);
            viewSmall = CustomRecyclerAdapter.view_root.findViewById(R.id.textViewSmall);
        }
    }
}
