package com.allfeature;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

public class EncyclopediaFragment extends Fragment {
    public EncyclopediaFragment(){

    }

    /**
     *
     */
    public static final String KEY_ENCYS = "key_encys";
    private static final String TAG = "EncyListFragment";
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
    private ArrayList<EncyPlant> mEncys;

    /**
     *
     */
    private EncyPlantAdapter mEncyAdapter;

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
    private EncyService mEncyService;

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
            mEncys.clear();

            try {
                JSONArray jsonArray = new JSONArray(jsonResponse);

                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String encyType = jsonObject.getString("category");
                        String encyImage = jsonObject.getString("gbr_ency");
                        String encyName = jsonObject.getString("name_pedia");
                        String encyDescription = jsonObject.getString("article");
                        float encyTemp = jsonObject.getLong("temp_pedia");
                        float encyHumadity = jsonObject.getLong("ph_pedia");

                        mEncys.add(
                                new EncyPlant(
                                        encyType,
                                        encyImage,
                                        encyName,
                                        encyDescription,
                                        encyTemp,
                                        encyHumadity));
                    }
                }

                mEncyAdapter.notifyDataSetChanged();
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
        View viewRoot = inflater.inflate(R.layout.fragment_encyclopedia, container, false);
        if (savedInstanceState != null) {
            mEncys = savedInstanceState.getParcelableArrayList(KEY_ENCYS);
        } else {
            mEncys = new ArrayList<>();
        }

        mEncyService = (EncyService) NetworkUtils.fetchUrl(BASE_URL, EncyService.class);

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

                            mCall = mEncyService.getEncyclopedia();
                            mCall.enqueue(mCallback);
                        }
                    } else {
                        mTvSwipeDownInfo.setText(R.string.network_is_not_connected);
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mTvSwipeDownInfo.setText(R.string.synchronizing);

            mCall = mEncyService.getEncyclopedia();
            mCall.enqueue(mCallback);
        });

        RecyclerView encyRecyclerView = viewRoot.findViewById(R.id.rv_list);
        encyRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        encyRecyclerView.setLayoutManager(layoutManager);

        mEncyAdapter = new EncyPlantAdapter(mEncys);
        encyRecyclerView.setAdapter(mEncyAdapter);

        ItemClickSupportUtils.addTo(encyRecyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Intent encyDetailIntent = new Intent(mContext, EncyActivity.class);
                    EncyPlant encyplant = mEncys.get(position);

                    encyDetailIntent.putExtra("type", encyplant.getType());
                    encyDetailIntent.putExtra("image", encyplant.getImage());
                    encyDetailIntent.putExtra("name", encyplant.getName());
                    encyDetailIntent.putExtra("desc", encyplant.getDesc());
                    encyDetailIntent.putExtra("temp", encyplant.getTemp());
                    encyDetailIntent.putExtra("humidity", encyplant.getHumidity());



                    startActivity(encyDetailIntent);
                });

        return viewRoot;
    }


    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(true);
        mTvSwipeDownInfo.setText(R.string.synchronizing);

        mCall = mEncyService.getEncyclopedia();
        mCall.enqueue(mCallback);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_ENCYS, mEncys);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNetworkConnectivityObserver.dispose();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
