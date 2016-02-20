package com.example.desy.everlanceproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by desy on 2/19/16.
 */
public class GasListAdapter extends ArrayAdapter<GasStation> {

    public GasListAdapter(Context context, int resource, List<GasStation> objects) {
        super(context, resource, objects);
    }

    public static class ViewHolder {
        TextView tvGasStation;
        TextView tvPrice;
        TextView tvDistance;
        TextView tvAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GasStation gasStation = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item_gas_data,parent,false);
            viewHolder.tvGasStation = (TextView) convertView.findViewById(R.id.list_item_station_name);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.list_item_price);
            viewHolder.tvDistance = (TextView) convertView.findViewById(R.id.list_item_distance);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.list_item_address);
            convertView.setTag(viewHolder);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvGasStation.setText(gasStation.getGasStation());
        viewHolder.tvPrice.setText(gasStation.getPrice());
        viewHolder.tvDistance.setText(gasStation.getDistance());
        viewHolder.tvAddress.setText(gasStation.getAddress());

        return convertView;
    }
}
