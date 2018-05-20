package com.ruchita.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ruchi.tictactoe.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btn_start;
    private Button btn_rate;
    private Button rtn_options;
    private RadioButton rbtn_android;
    private RadioButton rbtn_friend;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private SharedPreferences.Editor editor;
    private EditText player1;
    private EditText player2;
    private String p1 = "";
    private String p2 = "";
    private RadioGroup radioGroupPlayer;
    private RadioGroup radioGroupSize;


    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_home, container, false);
        btn_start = view1.findViewById(R.id.btn_start);
        rtn_options = view1.findViewById(R.id.btn_ops);
        rbtn_android = view1.findViewById(R.id.rb_android);
        rbtn_friend = view1.findViewById(R.id.rb_friend);
        radioButton3 = view1.findViewById(R.id.rbn_3);
        radioButton4 = view1.findViewById(R.id.rbn_4);
        radioButton5 = view1.findViewById(R.id.rbn_5);
        btn_rate = view1.findViewById(R.id.btn_rate);
        radioGroupPlayer = view1.findViewById(R.id.radio_group_player);
        radioGroupSize = view1.findViewById(R.id.radio_group_size);
        rbtn_android.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        rtn_options.setOnClickListener(this);
        rbtn_friend.setOnClickListener(this);
        btn_rate.setOnClickListener(this);
        return view1;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_start:
                boolean isAndroid = isAndroid();
                int gridSize = getGridSize();
                openGridFragment(gridSize, isAndroid);
                break;
            case R.id.btn_ops:
                openOptionsDialog();
                break;
            case R.id.btn_rate:
                break;
        }

    }

    private int getGridSize() {
        int id = radioGroupSize.getCheckedRadioButtonId();
        View view = radioGroupSize.findViewById(id);
        Object tag = view.getTag();
        int size = Integer.parseInt(tag.toString());
        return size;
    }

    private boolean isAndroid() {
        boolean isAndroid = false;
        int radioButtonId = radioGroupPlayer.getCheckedRadioButtonId();
        if (radioButtonId == R.id.rb_android) {
            isAndroid = true;
        }
        return isAndroid;
    }

    private void openGridFragment(int gridSize, boolean isAndroid) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int secondPlayerType;
        if (isAndroid) {
            secondPlayerType = GameGridFragment.ANDROID;
        } else {
            secondPlayerType = GameGridFragment.FRIEND;
        }
        Fragment fragment = GameGridFragment.newInstance(gridSize, secondPlayerType);
        transaction.replace(R.id.fragment_container, fragment,"GameGridFragment");
        transaction.addToBackStack("GameGridFragment");
        transaction.commit();
    }

    private void openMultiPlayerFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new MultiplayerFragmet();
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    private void openOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_options, null);
        builder.setView(view);
        final Dialog dialog = builder.create();
        player1 = view.findViewById(R.id.etxt_p1);
        player2 = view.findViewById(R.id.etxt_p2);
        final RadioGroup radioGroup_on_off = view.findViewById(R.id.radio_group_on_off);
        Button done = view.findViewById(R.id.btn_done);
        //Get setting values from pref
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARE_PRE_KEY, Context.MODE_PRIVATE);
        String myName = sharedPreferences.getString(Constants.PLAYER1, "");
        String friendName = sharedPreferences.getString(Constants.PLAYER2, "");
        boolean isSoundOn = sharedPreferences.getBoolean(Constants.IS_SOUNDON, true);
        if(!TextUtils.isEmpty(myName)){
            player1.setText(myName);
        }
        if(!TextUtils.isEmpty(p2)){
            player2.setText(friendName);
        }
        if(isSoundOn){
            radioGroup_on_off.check(R.id.rbn_on);
        }else {
            radioGroup_on_off.check(R.id.rbn_off);
        }

        if (rbtn_android.isChecked()) {
            player2.setText(R.string.android);
            player2.setEnabled(false);
            player2.setClickable(false);
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1 = player1.getText().toString();
                if (rbtn_friend.isChecked()) {
                    p2 = player2.getText().toString();
                }
                boolean IsSoundOn;
                int radioButtonOnId = radioGroup_on_off.getCheckedRadioButtonId();
                if (radioButtonOnId == R.id.rbn_on) {
                    IsSoundOn = true;
                } else {
                    IsSoundOn = false;
                }
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARE_PRE_KEY, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                if(!TextUtils.isEmpty(p1)) {
                    editor.putString(Constants.PLAYER1, p1);
                }
                if(!TextUtils.isEmpty(p2)) {
                    editor.putString(Constants.PLAYER2, p2);
                }
                editor.putBoolean(Constants.IS_SOUNDON, IsSoundOn);
                editor.apply();
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
