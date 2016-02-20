package com.example.desy.everlanceproject2;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.Locale;


public class GasStationDetailFragment extends Fragment {
    private GasStation gasStation;
    private TextView gasStationName;
    private TextView price;
    private TextView distance;
    private TextView address;
    private Button btMap;

    MapView mMapView;
    private GoogleMap googleMap;

    public GasStationDetailFragment() {
        // Required empty public constructor
    }

    public static GasStationDetailFragment newInstance(GasStation input) {
        GasStationDetailFragment defaultRecommendationFragment = new GasStationDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("gasStation", input);
        defaultRecommendationFragment.setArguments(args);
        return defaultRecommendationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gasStation = getArguments().getParcelable("gasStation");
        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gas_station_detail, container, false);
        gasStationName = (TextView) view.findViewById(R.id.tvGasStationName);
        price = (TextView) view.findViewById(R.id.tvGasPrice);
        distance = (TextView) view.findViewById(R.id.tvGasDistance);
        address = (TextView) view.findViewById(R.id.tvGasAddress);

        gasStationName.setText(gasStation.getGasStation());
        price.setText(gasStation.getPrice());
        distance.setText(gasStation.getDistance());
        address.setText(gasStation.getAddress());

        btMap = (Button) view.findViewById(R.id.btMap);
        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMapActivity(getContext());
            }
        });

        return view;
    }

    private void launchMapActivity(Context context) {
//            Uri.parse("geo:37.827500,-122.481670")
        String lat = gasStation.getLat();
        String lon = gasStation.getLng();
        String uri = String.format(Locale.ENGLISH, "geo:<" + lat + ">,<" + lon + ">?q=<" + lat + ">,<" + lon + ">");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(context.getApplicationContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }
}
