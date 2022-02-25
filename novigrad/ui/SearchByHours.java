package novigrad.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import novigrad.R;

public class SearchByHours extends AppCompatDialogFragment {
    EditText searchOpen;
    SearchHoursListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builderSearch = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaterSearch = getActivity().getLayoutInflater();
        View view = inflaterSearch.inflate(R.layout.activity_search_by_hours, null);

        builderSearch.setView(view).setTitle("Search by Hours")
                .setNegativeButton("Exit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        ;
                    }
                })
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String desiredHours = searchOpen.getText().toString();
                        listener.applyTextsHours(desiredHours);
                    }
                });
        searchOpen = view.findViewById(R.id.search_open);
        return builderSearch.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (SearchHoursListener) context;
    }

    public interface SearchHoursListener{
        void applyTextsHours(String desiredHours);
    }
}