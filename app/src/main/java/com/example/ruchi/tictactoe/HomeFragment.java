package com.example.ruchi.tictactoe;

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
import android.widget.Toast;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btn_start;
    private Button rtn_options;
    private RadioButton rbtn_android;
    private RadioButton rbtn_friend;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean isAndroidSelected;



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
         editor.putBoolean(Constants.IS_ANDROID_SELECTED,true);
         editor.apply();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btn_start = view.findViewById(R.id.btn_start);
        rtn_options = view.findViewById(R.id.btn_ops);
        rbtn_android = view.findViewById(R.id.rb_android);
        rbtn_friend = view.findViewById(R.id.rb_friend);
        boolean b = sharedPreferences.getBoolean(Constants.IS_ANDROID_SELECTED,true);
       // rbtn_friend.setSelected(true);
        rbtn_android.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        rtn_options.setOnClickListener(this);
        rbtn_friend.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_start:
                openTicTacToeFragment();
                break;
            case R.id.btn_ops:
                openOptionsDialog();
                break;
            case R.id.btn_rate:
                break;
            case R.id.rb_android:
                break;
            case R.id.rb_friend:
                openOptionsDialog();
                break;
                default:
                    this.onClick(rbtn_android);
        }

    }

    private void openTicTacToeFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TicTacToeFragment fragment = TicTacToeFragment.newInstance();
        transaction.add(R.id.fragment_container, fragment, "ticTagToeFragment");
        transaction.addToBackStack("ticTacToeFragment");
        transaction.commit();
    }

    private void openOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_options, null);
        final EditText player1 = view.findViewById(R.id.etxt_p1);
        final EditText player2 = view.findViewById(R.id.etxt_p2);
        RadioButton on = view.findViewById(R.id.rbn_on);
        RadioButton off = view.findViewById(R.id.rbn_off);
        Button done = view.findViewById(R.id.btn_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p1 = player1.getText().toString();
                String p2 = player2.getText().toString();
                if (p1.equals("")){
                    Toast.makeText(getContext(), "Please fill the names ", Toast.LENGTH_SHORT).show();
                }else {
                    editor.putString(Constants.PLAYER1, p1);
                    editor.putString(Constants.PLAYER2, p2);
                }
                Toast.makeText(getContext(), "done clicked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
