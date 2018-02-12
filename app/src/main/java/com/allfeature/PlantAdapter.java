package com.allfeature;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by USER on 12/3/2017.
 */

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantHolder>{
//    private Context mCtx;
//    private List<Plant> plantList;
//
//
//    public PlantAdapter(Context mCtx, List<Plant> plantList) {
//        this.mCtx = mCtx;
//        this.plantList = plantList;
//    }
//
//    @Override
//    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
//        View view = inflater.inflate(R.layout.list_layout, null);
//        return new PlantViewHolder(view);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onBindViewHolder(PlantViewHolder holder, int position) {
//        Plant plant = plantList.get(position);
//
//        holder.name.setText(plant.getName());
//        holder.type.setText(plant.getType());
//
//        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(plant.getImage()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return plantList.size();
//    }
//
//
//    class PlantViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imageView;
//        TextView name,type;
//
//        public PlantViewHolder(View itemView) {
//            super(itemView);
//
//            imageView = itemView.findViewById(R.id.imageView);
//            name = itemView.findViewById(R.id.name);
//            type = itemView.findViewById(R.id.type);
//            CardView cardView = itemView.findViewById(R.id.cardView);
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Plant plant = plantList.get(getPosition());
//                    Log.d("####!!", "onClick: " + plant.getName() + " " + plant.getId());
//                    Intent intent = new Intent(v.getContext(), StatisticsActivity.class);
//                    v.getContext().startActivity(intent);
//                }
//            });
//        }
//    }
    private static final String BASE_IMAGE_URL = "http://45.77.171.22:3000/img-plant/";

    private ArrayList<Plant> mPlants;

    PlantAdapter(ArrayList<Plant> mPlants) {
        this.mPlants = mPlants;
    }

    @Override
    public PlantAdapter.PlantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_list_item, parent, false);
        return new PlantAdapter.PlantHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlantAdapter.PlantHolder holder, final int position) {
        Uri imageUrl = Uri.parse(BASE_IMAGE_URL + mPlants.get(position).getImage());

        ImageView ivEncyImage = holder.ivEncyImage;
        TextView tvEncyName = holder.tvEncyName;
        TextView tvEncyType = holder.tvEncyType;

        GlideApp.with(ivEncyImage)
                .load(imageUrl)
                .circleCrop()
                .thumbnail(.25f)
                .placeholder(R.drawable.ayoplant)
                .into(ivEncyImage);
        tvEncyName.setText(mPlants.get(position).getName());
        String TypePlant = String.valueOf(mPlants.get(position).getType());
        tvEncyType.setText(TypePlant);

        holder.itemView.setOnClickListener(view -> Toast.makeText(
                holder.itemView.getContext(),
                mPlants.get(position).getName(),
                Toast.LENGTH_SHORT)
                .show());
    }

    @Override
    public int getItemCount() {
        return mPlants.size();
    }

    public void setPlants(ArrayList<Plant> mPlants) {
        this.mPlants = mPlants;
    }

    static class PlantHolder extends RecyclerView.ViewHolder {

        ImageView ivEncyImage;
        TextView tvEncyName;
        TextView tvEncyType;

        PlantHolder(View itemView) {
            super(itemView);
            this.ivEncyImage = itemView.findViewById(R.id.iv_plant_list_image);
            this.tvEncyName = itemView.findViewById(R.id.tv_plant_list_name);
            this.tvEncyType = itemView.findViewById(R.id.tv_plant_list_type);
        }
    }

}
