package com.example.app2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder> {

    private List<Developer> developers;

    public DeveloperAdapter(List<Developer> developers) {
        this.developers = developers;
    }

    @NonNull
    @Override
    public DeveloperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_developer, parent, false);
        return new DeveloperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeveloperViewHolder holder, int position) {
        Developer developer = developers.get(position);
        holder.textViewName.setText(developer.getName());
        holder.textViewPublisher.setText(developer.getPublisher());
        holder.textViewFounded.setText(developer.getFounded());
        holder.textViewHeadquarters.setText(developer.getHeadquarters());
        holder.imageViewDeveloper.setImageResource(developer.getImageResId());
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    static class DeveloperViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewDeveloper;
        TextView textViewName;
        TextView textViewPublisher;
        TextView textViewFounded;
        TextView textViewHeadquarters;

        DeveloperViewHolder(View view) {
            super(view);
            imageViewDeveloper = view.findViewById(R.id.imageViewDeveloper);
            textViewName = view.findViewById(R.id.textViewName);
            textViewPublisher = view.findViewById(R.id.textViewPublisher);
            textViewFounded = view.findViewById(R.id.textViewFounded);
            textViewHeadquarters = view.findViewById(R.id.textViewHeadquarters);
        }
    }
}