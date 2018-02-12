package com.allfeature;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlantFragment extends Fragment {

//    RecyclerView recyclerView;
//    PlantAdapter adapter;
//
//    List<Plant> plantList;
//    PopupWindow ePopUpWindow;
    public PlantFragment(){

    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_plant, container, false);
//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.tambah_button);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (getActivity(),pop.class);
//                startActivity(intent);
//            }
//        });
//
//        plantList = new ArrayList<>();
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        plantList.add(
//                new Plant(
//                        1,
//                        "Anggrek Hitam",
//                        "Anggrek",
//                        R.drawable.anggrek2));
//        plantList.add(
//                new Plant(
//                        2,
//                        "Anggrek Bulan",
//                        "Anggrek",
//                        R.drawable.anggrek2));
//        plantList.add(
//                new Plant(
//                        3,
//                        "Mawar Hitam",
//                        "Mawar",
//                        R.drawable.mawar));
//        plantList.add(
//                new Plant(
//                        4,
//                        "Anggrek Tebu",
//                        "Anggrek",
//                        R.drawable.anggrek2));
//        plantList.add(
//                new Plant(
//                        5,
//                        "Anggrek Violet",
//                        "Anggrek",
//                        R.drawable.anggrek2));
//        plantList.add(
//                new Plant(
//                        6,
//                        "Anggrek Cattleya",
//                        "Anggrek",
//                        R.drawable.anggrek2));
//
//        adapter = new PlantAdapter(getActivity(),plantList);
//        recyclerView.setAdapter(adapter);
//
//
//        return view;
//    }

    /**
     *
     */
    public static final String KEY_PLANTS = "key_plant";
    private static final String TAG = "PlantListFragment";
    /**
     *
     */
    private static final String BASE_URL = "http://45.77.171.22:3030";

    /**
     *
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     *
     */
    private ArrayList<Plant> mPlants;

    /**
     *
     */
    private PlantAdapter mPlantAdapter;

    /**
     *
     */
    private TextView mTvSwipeDownInfo;

    /**
     *
     */
    private Context mContext;

    /**
     *
     */
    private Disposable mNetworkConnectivityObserver;

    /**
     *
     */
    private PlantService mPlantService;

    /**
     *
     */
    private Call<String> mCall;

    /**
     *
     */

    private Callback<String> mCallback = new Callback<String>() {
        @Override
        public void onResponse(@NonNull Call<String> call, @NonNull final Response<String> response) {
            String jsonResponse = response.body();
            mPlants.clear();

            try {
                JSONArray jsonArray = new JSONArray(jsonResponse);

                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String plantName = jsonObject.getString("name");
                            String plantType = jsonObject.getString("type");
                            float plantTemp = jsonObject.getLong("temp");
                            float plantHumadity = jsonObject.getLong("ph");
                            String plantImage = jsonObject.getString("gbr_plant");


                            mPlants.add(
                                    new Plant(
                                            plantName,
                                            plantType,
                                            plantTemp,
                                            plantHumadity,
                                            plantImage));

                    }
                }

                mPlantAdapter.notifyDataSetChanged();
                mTvSwipeDownInfo.setText(R.string.swipe_down_info);
                mSwipeRefreshLayout.setRefreshing(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(@NonNull Call<String> mCall, @NonNull Throwable t) {
            t.printStackTrace();
        }
    };


    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_plant, container, false);
        if (savedInstanceState != null) {
            mPlants = savedInstanceState.getParcelableArrayList(KEY_PLANTS);
        } else {
            mPlants = new ArrayList<>();
        }

        FloatingActionButton fab = (FloatingActionButton) viewRoot.findViewById(R.id.tambah_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(),pop.class);
                startActivity(intent);
            }
        });

        mPlantService = (PlantService) NetworkUtils.fetchUrl(BASE_URL, PlantService.class);

        mTvSwipeDownInfo = viewRoot.findViewById(R.id.tv_swipe_down_info);

        mSwipeRefreshLayout = viewRoot.findViewById(R.id.srl_list);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        mNetworkConnectivityObserver = ReactiveNetwork.observeNetworkConnectivity(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if (connectivity.getState() == NetworkInfo.State.CONNECTED) {
                        if (savedInstanceState == null) {
                            mSwipeRefreshLayout.setEnabled(true);
                            mSwipeRefreshLayout.setRefreshing(true);
                            mTvSwipeDownInfo.setText(R.string.synchronizing);

                            mCall = mPlantService.getPlant();
                            mCall.enqueue(mCallback);
                        }
                    } else {
                        mTvSwipeDownInfo.setText(R.string.network_is_not_connected);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mTvSwipeDownInfo.setText(R.string.synchronizing);

            mCall = mPlantService.getPlant();
            mCall.enqueue(mCallback);
        });

        RecyclerView plantRecyclerView = viewRoot.findViewById(R.id.rv_list);
        plantRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        plantRecyclerView.setLayoutManager(layoutManager);

        mPlantAdapter = new PlantAdapter(mPlants);
        plantRecyclerView.setAdapter(mPlantAdapter);

        ItemClickSupportUtils.addTo(plantRecyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Intent plantDetailIntent = new Intent(mContext, StatisticsActivity.class);
                    Plant plant = mPlants.get(position);

                    plantDetailIntent.putExtra("name", plant.getName());
                    plantDetailIntent.putExtra("type", plant.getType());
                    plantDetailIntent.putExtra("temp", plant.getTemp());
                    plantDetailIntent.putExtra("humadity", plant.getHumadity());
                    plantDetailIntent.putExtra("image", plant.getImage());


                    startActivity(plantDetailIntent);
                });

        return viewRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
        mTvSwipeDownInfo.setText(R.string.synchronizing);

        mCall = mPlantService.getPlant();
        mCall.enqueue(mCallback);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_PLANTS, mPlants);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNetworkConnectivityObserver.dispose();
    }
}
