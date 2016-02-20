package com.example.desy.everlanceproject2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class GasListFragment extends Fragment {
    private String TAG = GasListFragment.class.getSimpleName();
    private ArrayList<GasStation> gasStationList = new ArrayList<>();
    private GasListAdapter mGasListAdapter;
    private TextView tvLat;
    private TextView tvLong;
    private Button sortPrice;
    private Button sortDist;
    private ListView mListView;
    private String stringLatitude;
    private String stringLongitude;

    private OnFragmentInteractionListener mListener;

    public GasListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(getContext());

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            stringLatitude = String.valueOf(gpsTracker.latitude);
            stringLongitude = String.valueOf(gpsTracker.longitude);

        }
        else {
            gpsTracker.showSettingsAlert();
        }


        //fetchGasStationInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gas_list, container, false);

        tvLat = (TextView) view.findViewById(R.id.myLat);
        tvLat.setText(stringLatitude);
        tvLong = (TextView) view.findViewById(R.id.myLong);
        tvLong.setText(stringLongitude);

        mListView = (ListView) view.findViewById(R.id.lvGasStations);
        mGasListAdapter = new GasListAdapter(getActivity(), 0, gasStationList);
        mListView.setAdapter(mGasListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onFragmentInteraction(gasStationList.get(position));
            }
        });

        sortPrice = (Button) view.findViewById(R.id.sortPrice);
        sortPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"sort price");
                if (!gasStationList.isEmpty()) {
                    gasStationList.clear();
                    mGasListAdapter.clear();
                }
                fetchGasStationInfo("Price");
            }
        });

        sortDist = (Button) view.findViewById(R.id.sortDist);
        sortDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"sort distance");
                if (!gasStationList.isEmpty()) {
                    gasStationList.clear();
                    mGasListAdapter.clear();
                }

                fetchGasStationInfo("Distance");

            }
        });

        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(GasStation gasStation);
    }


    //Trigger API
    private void fetchGasStationInfo(String inputString) {
        String url = "http://api.mygasfeed.com/stations/radius/" + stringLatitude + "/" + stringLongitude + "/5/reg/" + inputString + "/wcplzhec0w.json";

        //Create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        //Trigger the Get request
        client.get(url, null, new JsonHttpResponseHandler() {
            //onSuccess (worked)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray stationsJSON;
                try {
                    stationsJSON = response.getJSONArray("stations");
                    for (int i = 0; i < stationsJSON.length(); i++) {
                        JSONObject stationJSON = stationsJSON.getJSONObject(i);
                        GasStation station = new GasStation();
                        Log.d(TAG, "success");

                        String gasStationName = stationJSON.getString("station");
                        station.setGasStation(gasStationName);

                        String gasPrice = stationJSON.getString("reg_price");
                        station.setPrice(gasPrice);

                        String gasDistance = stationJSON.getString("distance");
                        station.setDistance(gasDistance);

                        String gasStationAddress = stationJSON.getString("address");
                        station.setAddress(gasStationAddress);

                        String gasStationLat = stationJSON.getString("lat");
                        station.setLat(gasStationLat);

                        String gasStationLong = stationJSON.getString("lng");
                        station.setLng(gasStationLong);

                        gasStationList.add(station);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

                mGasListAdapter.notifyDataSetChanged();
            }

            //onFailure (failed)

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO SOMETHING
                Log.d("DEBUG", "Fetch timeline error: " + throwable.toString());
            }
        });
    }


}
