package net.raginggeek.helloandroidsafari;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class NameFragment extends DialogFragment {

    private Rateable rater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        rater = (Rateable) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String name = getArguments().getString("name");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.alertTitle)
                .setIcon(android.R.drawable.star_on)
                .setMessage(String.format("How do you feel about %s?", name))
                .setPositiveButton("Positive, +1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rater.modifyRating(name, 1);
                    }
                })
                .setNeutralButton("Neutral, 0", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), String.format("%s is going to be disappointed", name), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Negative, -1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rater.modifyRating(name, -1);
                    }
                });
        return builder.create();
    }

    public interface Rateable {
        void modifyRating(String name, int amount);
    }
}
