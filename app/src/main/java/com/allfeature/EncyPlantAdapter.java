package com.allfeature;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EncyPlantAdapter extends RecyclerView.Adapter<EncyPlantAdapter.EncyHolder> implements Filterable {


    private static final String BASE_IMAGE_URL = "http://45.77.171.22:3000/img-ency/";

    private ArrayList<EncyPlant> mEncys;
    private ArrayList<EncyPlant> mFilteredList;

    public EncyPlantAdapter(ArrayList<EncyPlant> mEncys) {
        this.mEncys = mEncys;
        mFilteredList = mEncys;
    }

    @Override
    public EncyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ency_list_item, parent, false);
        return new EncyHolder(v);
    }

    @Override
    public void onBindViewHolder(final EncyHolder holder, final int position) {
        Uri imageUrl = Uri.parse(BASE_IMAGE_URL + mEncys.get(position).getImage());

        ImageView ivEncyImage = holder.ivEncyImage;
        TextView tvEncyName = holder.tvEncyName;
        TextView tvEncyType = holder.tvEncyType;

        GlideApp.with(ivEncyImage)
                .load(imageUrl)
                .circleCrop()
                .thumbnail(.25f)
                .placeholder(R.drawable.ayoplant)
                .into(ivEncyImage);
        tvEncyName.setText(mEncys.get(position).getName());
        String TypePlant = String.valueOf(mEncys.get(position).getType());
        tvEncyType.setText(TypePlant);

        holder.itemView.setOnClickListener(view -> Toast.makeText(
                holder.itemView.getContext(),
                mEncys.get(position).getName(),
                Toast.LENGTH_SHORT)
                .show());
    }


    @Override
    public int getItemCount() {
        return mEncys.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mEncys;
                } else {

                    ArrayList<EncyPlant> filteredList = new ArrayList<>();

                    for (EncyPlant androidVersion : mEncys) {

                        if (androidVersion.getName().toLowerCase().contains(charString.toLowerCase())
                                || androidVersion.getType().contains(charString.toLowerCase())) {
                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<EncyPlant>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setEncys(ArrayList<EncyPlant> mEncys) {
        this.mEncys = mEncys;
    }

    static class EncyHolder extends RecyclerView.ViewHolder {

        ImageView ivEncyImage;
        TextView tvEncyName;
        TextView tvEncyType;

        EncyHolder(View itemView) {
            super(itemView);
            this.ivEncyImage = itemView.findViewById(R.id.iv_ency_list_image);
            this.tvEncyName = itemView.findViewById(R.id.tv_ency_list_name);
            this.tvEncyType = itemView.findViewById(R.id.tv_ency_list_type);
        }
    }
}
