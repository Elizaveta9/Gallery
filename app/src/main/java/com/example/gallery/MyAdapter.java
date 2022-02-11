package com.example.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Image> galleryList;
    private Context context;
    ArrayList<String> paths;
    public ImageView img;

    public MyAdapter(Context context, ArrayList<Image> galleryList, ArrayList<String> paths) {
        this.paths = paths;
        this.context = context;
        this.galleryList = galleryList;
    }

    // Создание нового объекта View
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cell, parent, false);
        return new ViewHolder(view);
    }

    // Меняет контент объкта View
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageFromPath(galleryList.get(position).getPath(), viewHolder.img);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = v.findViewById(R.id.img);
                Intent intent = new Intent(v.getContext(), Cell.class);
                intent.putExtra("Index", position);
                intent.putExtra("Image", galleryList.get(position).getPath());
                intent.putExtra("Paths", paths);
                v.getContext().startActivity(intent);
                Toast.makeText(context, galleryList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
        }
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    private void setImageFromPath(String path, ImageView image) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = ImageHelper.decodeSampleBitmapFromPath(imgFile.getAbsolutePath(), 200, 200);
            image.setImageBitmap(myBitmap);
        }
    }
}