package com.example.desy.everlanceproject2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GasListFragment.OnFragmentInteractionListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String CLIENT_ID = "wcplzhec0w";
    public static final String URL = "http://api.mygasfeed.com/";
    protected Context context;
    private TextView txtLat;
    private ListView mListView;
    private GasListAdapter gasListAdapter;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<GasStation> gasStationsList = new ArrayList<>();
    public static FragmentManager fragmentManager;


    private Fragment gasStationListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLat = (TextView) findViewById(R.id.myLocation);

        fragmentManager = getSupportFragmentManager();

        gasStationListFragment = new GasListFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, gasStationListFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }


    @Override
    public void onFragmentInteraction(GasStation gasStation) {
        GasStationDetailFragment gasStationDetailFragment= new GasStationDetailFragment().newInstance(gasStation);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, gasStationDetailFragment, "gasStationDetailFragment").addToBackStack("adminFragment").commit();
    }
}
