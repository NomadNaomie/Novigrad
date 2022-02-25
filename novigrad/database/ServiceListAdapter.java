 package novigrad.database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

import novigrad.core.BranchAccount;
import novigrad.services.Service;
import novigrad.ui.ServiceAddOrRemove;


 public class ServiceListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Service> services;
    private DatabaseWriter dbWriter = new DatabaseWriter();

    public ServiceListAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

//    public void longClick(){
//        Toast.makeText(null, "Testing longpress button", Toast.LENGTH_LONG);
//    }

    @SuppressLint("SetTextI18n")
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
        twoLineListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener deletionChoice = new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int choice){
                        switch (choice){
                            case DialogInterface.BUTTON_POSITIVE:
                                dbWriter.deleteService(services.get(position).getName());
                                notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //Not doing anything
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Would you like to delete the selected service?").setPositiveButton("Yes", deletionChoice)
                        .setNegativeButton("No", deletionChoice).show();
            }
        });

        twoLineListItem.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                Intent editIntent = new Intent(context, ServiceAddOrRemove.class);
                editIntent.putExtra("Edit","Edit");
                editIntent.putExtra("name", services.get(position).getName());
                editIntent.putExtra("forms", services.get(position).getUserForm());
                editIntent.putExtra("docs", services.get(position).getUserDoc());
                context.startActivity(editIntent);
                return true;
            }
        });

        text1.setText(services.get(position).getName());
        //***getName is not working***
        text2.setText(services.get(position).getCombo());

        return twoLineListItem;
    }
}