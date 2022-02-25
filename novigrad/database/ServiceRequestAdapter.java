package novigrad.database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

import novigrad.core.CustomerAccount;
import novigrad.services.Service;
import novigrad.services.ServiceRequest;
import novigrad.ui.ServiceAddOrRemove;

public class ServiceRequestAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ServiceRequest> requests;
    private DatabaseWriter dbWriter = new DatabaseWriter();


    public ServiceRequestAdapter(Context context, ArrayList<ServiceRequest> requests) {
        this.context = context;
        this.requests = requests;
    }
    @Override
    public int getCount() {
        return requests.size();
    }


    @Override
    public Object getItem(int position) {
        return requests.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();


        text1.setText("Request ID:" + requests.get(position).getId());
        if (requests.get(position).getType().equals("License")){text2.setText(requests.get(position).getType()+" " +requests.get(position).getLicenseType()+ "Request");}
        else{text2.setText(requests.get(position).getType());}

        return twoLineListItem;
    }
}
