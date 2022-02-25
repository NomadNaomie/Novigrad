package novigrad.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import novigrad.R;

public class BranchHoursDialog extends AppCompatDialogFragment {
    EditText editOpen, editClose;
    DialogListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_branch_hours_dialog, null);

        builder.setView(view).setTitle("Edit Times")

                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing, this is onclick for Exit which will close the activity
            }
        })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //will replace the current TextView with what our input was
                String openTime = editOpen.getText().toString();
                String closeTime = editClose.getText().toString();
                //Passing on the dialog inputs to BranchHours
                listener.applyTexts(openTime, closeTime);
            }
        });
        editOpen = view.findViewById(R.id.edit_open);
        editClose = view.findViewById(R.id.edit_close);
        return builder.create();
    }

    //Will attempt to attach whatever our input is
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + ". Have not implemented DialogListener");
        }
    }

    public interface DialogListener{
        void applyTexts(String openTime, String closeTime);
    }
}
