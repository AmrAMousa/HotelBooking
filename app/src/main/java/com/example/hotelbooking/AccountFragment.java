package com.example.hotelbooking;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hotelbooking.adapter.HotelItemAdapter;
import com.example.hotelbooking.models.HotelModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    View view;
    EditText editText;
    public HotelItemAdapter hotelItemAdapter;
    ProgressBar progressBar;
    SharedPreferences sharedPreferencesAccount;
    SharedPreferences.Editor editorAccount;
    TextView emptyAccount;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        progressBar = view.findViewById(R.id.progress_bar_account);
        editText =view.findViewById(R.id.search_faild_account);
        emptyAccount=view.findViewById(R.id.empty_account);
        sharedPreferencesAccount = this.getActivity().getSharedPreferences("Booked", Context.MODE_PRIVATE);
        editorAccount = sharedPreferencesAccount.edit();

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        setupData();
        executeFetchedData();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hotelItemAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupData() {
        RecyclerView recyclerView = view.findViewById(R.id.hotels_recyclerView_account);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        hotelItemAdapter = new HotelItemAdapter(getContext());
        recyclerView.setAdapter(hotelItemAdapter);
        hotelItemAdapter.notifyDataSetChanged();
    }

    private void executeFetchedData() {
        new AccountFragment.FetchData().execute();
    }

    class FetchData extends AsyncTask<Void, Void, List<HotelModel>> {
        URL url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<HotelModel> doInBackground(Void... voids) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                url = new URL("https://api.myjson.com/bins/exyew");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return parseJson(stringBuilder.toString());
        }

        private List<HotelModel> parseJson(String jsonString) {
            List<HotelModel> hotelModels = new ArrayList<>();
            try {
                JSONObject mainObject = new JSONObject(jsonString);
                JSONArray hotelsData = mainObject.getJSONArray("hotels");
                for (int i = 0; i < hotelsData.length(); i++) {
                    String hotelDescription;
                    String hotelId;
                    String hotelImage;
                    String hotelLocation;
                    String hotelName;
                    String hotelPrice;
                    String hotelRate;
                    String hotelType;
                    String hotelLocationOnMap;
                    JSONObject hotelData = hotelsData.getJSONObject(i);
                    if (hotelData.has("hotelDescription")) {
                        hotelDescription = hotelData.getString("hotelDescription");
                    } else {
                        hotelDescription = "hotelDescription not found";
                    }

                    if (hotelData.has("hotelId")) {
                        hotelId = hotelData.getString("hotelId");
                    } else {
                        hotelId = "hotelId not found";
                    }

                    if (hotelData.has("hotelImage")) {
                        hotelImage = hotelData.getString("hotelImage");
                    } else {
                        hotelImage = "hotelImage not found";
                    }
                    if (hotelData.has("hotelLocation")) {
                        hotelLocation = hotelData.getString("hotelLocation");
                    } else {
                        hotelLocation = "hotelLocation not found";
                    }

                    if (hotelData.has("hotelName")) {
                        hotelName = hotelData.getString("hotelName");
                    } else {
                        hotelName = "hotelName not found";
                    }

                    if (hotelData.has("hotelPrice")) {
                        hotelPrice = hotelData.getString("hotelPrice");
                    } else {
                        hotelPrice = "hotelPrice not found";
                    }

                    if (hotelData.has("hotelRate")) {
                        hotelRate = hotelData.getString("hotelRate");
                    } else {
                        hotelRate = "hotelRate not found";
                    }

                    if (hotelData.has("hotelType")) {
                        hotelType = hotelData.getString("hotelType");
                    } else {
                        hotelType = "hotelType not found";
                    }
                    if (hotelData.has("hotelLocationOnMap")) {
                        hotelLocationOnMap = hotelData.getString("hotelLocationOnMap");
                    } else {
                        hotelLocationOnMap = "hotelLocationOnMap not found";
                    }
                    if (sharedPreferencesAccount.contains(hotelId)){
                        hotelModels.add(new HotelModel(hotelDescription, hotelId, hotelImage, hotelLocation, hotelName, hotelPrice, hotelRate, hotelType, hotelLocationOnMap));
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return hotelModels;
        }

        @Override
        protected void onPostExecute(List<HotelModel> hotelModels) {
            super.onPostExecute(hotelModels);
            if (hotelModels != null && hotelModels.size() != 0) {

                hotelItemAdapter.setData(hotelModels);
                hotelItemAdapter.notifyDataSetChanged();
                editText.setEnabled(true);
            }else {
                emptyAccount.setVisibility(View.VISIBLE);

            }
            progressBar.setVisibility(View.GONE);


        }
    }



}
