package com.example.ruchi.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btn_start;
    private Button btn_rate;
    private Button rtn_options;
    private RadioButton rbtn_android;
    private RadioButton rbtn_friend;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private SharedPreferences sharedPreferences;
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
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARE_PRE_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
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
                if (p1.equals("")) {
                    // openOptionsDialog();
                }
                boolean isAndroid = isAndroid();
                int gridSize = getGridSize();
                openGridFragment(gridSize, isAndroid);
                break;
            case R.id.btn_ops:
                openOptionsDialog();
                break;
            case R.id.btn_rate:
                openMultiplayerFragment();
                break;
            case R.id.rb_android:
                break;
            case R.id.rb_friend:
                openOptionsDialog();
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
        if(isAndroid){
            secondPlayerType = GameGridFragment.ANDROID;
        }else {
            secondPlayerType = GameGridFragment.FRIEND;
        }
        Fragment fragment = GameGridFragment.newInstance(gridSize, secondPlayerType);
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    private void openMultiplayerFragment(){
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

        final RadioButton on = view.findViewById(R.id.rbn_on);
        RadioButton off = view.findViewById(R.id.rbn_off);
        Button done = view.findViewById(R.id.btn_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1 = player1.getText().toString();
                if (rbtn_android.isChecked()) {
                    player2.setEnabled(false);
                }else {
                    p2 = player2.getText().toString();
                }
                boolean IsSoundOn;
                if (on.isChecked()) {
                    IsSoundOn = true;
                } else {
                    IsSoundOn = false;
                }

                if (rbtn_android.isChecked()) {
                    player2.setEnabled(false);
                    if (p1.equals("")) {
                        p1 = "You";
                    }
                    p2 = "Android";
                } else {
                    if (p2.equals("")) {
                        p2 = "Friend";
                        if (p1.equals("")) {
                            p1 = "You";
                        }
                    }
                }
                sharedPreferences = getActivity().getSharedPreferences(Constants.SHARE_PRE_KEY, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString(Constants.PLAYER1, p1);
                editor.putString(Constants.PLAYER2, p2);
                editor.putBoolean(Constants.IS_SOUNON, IsSoundOn);
                editor.apply();
                openGridFragment(getGridSize(), isAndroid());
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
