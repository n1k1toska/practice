package com.example.app4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student s = students.get(position);
        holder.tvFio.setText(s.fio);
        holder.tvDetails.setText("Возраст: " + s.age + " | Курс: " + s.course + " | GPA: " + s.gpa);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFio, tvDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFio = itemView.findViewById(R.id.tvFio);
            tvDetails = itemView.findViewById(R.id.tvDetails);
        }
    }
}