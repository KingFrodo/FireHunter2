package com.example.firehunter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Einsatz> {
    private List<Einsatz> einsätze = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;

    public MyAdapter(Context context, int resource, List<Einsatz> objects){
        super(context, resource, objects);

        this.einsätze = objects;
        this.layoutId = resource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Einsatz einsatz = einsätze.get(position);
        View listItem = (convertView == null) ? inflater.inflate(this.layoutId, null) : convertView;

        ((TextView) listItem.findViewById(R.id.art)).setText(einsatz.getArt());
        ((TextView) listItem.findViewById(R.id.time)).setText(einsatz.getTime());
        ((TextView) listItem.findViewById(R.id.alarmstufe)).setText(einsatz.getAlarmStufe() + "");
        ((TextView) listItem.findViewById(R.id.anzahl)).setText(einsatz.getAnzahl() + "");
        ((TextView) listItem.findViewById(R.id.ort)).setText(einsatz.getOrt());
        ((TextView) listItem.findViewById(R.id.straße)).setText(einsatz.getAdresse());

        return listItem;
    }
}
