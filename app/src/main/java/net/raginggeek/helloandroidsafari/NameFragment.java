package net.raginggeek.helloandroidsafari;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class NameFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String name = getArguments().getString("name");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("This is an alert")
                .setIcon(android.R.drawable.star_on)
                .setMessage(String.format("You clicked on %s", name))
                .setNeutralButton("Yeah, I know", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), String.format("Hello, %s from toast", name), Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }
}
