package com.vitaliyhtc.stone1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TimerDialogFragment extends DialogFragment {

    private Context mContext;
    private View view;
    private String mTimeString;
    private String mImageUrl;

    public void setInitialParameters(Context context, String timeString, String imageUrl){
        mContext = context;
        mTimeString = timeString;
        mImageUrl = imageUrl;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_timer_dialog, null);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        builder.setCancelable(true);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        displayData();
    }

    private void displayData(){
        Picasso.with(mContext).load(mImageUrl).into((ImageView) view.findViewById(R.id.image_view));
        ((TextView) view.findViewById(R.id.text_timer_string)).setText(mTimeString);
    }

}
