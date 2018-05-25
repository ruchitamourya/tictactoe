package com.ruchita.tictactoe.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruchita.tictactoe.R;
import com.ruchita.tictactoe.GameGridListener;

public class GameGridAdapter extends RecyclerView.Adapter<GameGridAdapter.GameGridViewHolder> {

    private int[][] mData;
    private Context mContext;
    private static GameGridListener mGameGridListener;

    public GameGridAdapter(Context context, int[][] data, GameGridListener listener) {
        mContext = context;
        mData = data;
        mGameGridListener = listener;
    }

    @Override
    public GameGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        View cardView = view.findViewById(R.id.grid_card_view);
        TextView textView = cardView.findViewById(R.id.text_view);

        final int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
       // final int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        int gridWidth = (screenWidth-50) / mData.length;
        int gridHeight = (screenWidth-50)/mData.length;
        if (mData.length == 3) {
            gridWidth = (screenWidth - 100) / mData.length;
            gridHeight = (screenWidth- 100)/mData.length;
            cardView.setLayoutParams(new CardView.LayoutParams(gridWidth, gridHeight));
        } else if (mData.length == 4) {
            cardView.setLayoutParams(new CardView.LayoutParams(gridWidth, gridHeight));
        } else {
            textView.setTextSize(28f);
            cardView.setLayoutParams(new CardView.LayoutParams(gridWidth, gridHeight));
        }
        GameGridViewHolder holder = new GameGridViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GameGridViewHolder holder, int position) {
        int len = mData.length;
        int row = position / len;
        int col = position % len;
        holder.setData(mData[row][col], row, col);
    }

    @Override
    public int getItemCount() {
        return mData.length * mData[0].length;
    }

    public void setArrayData(int[][] data) {
        this.mData = data;
    }

    public static class GameGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        int row = -1;
        int col = -1;

        GameGridViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            textView.setOnClickListener(this);
        }

        private void setData(int value, int row, int col) {
            this.row = row;
            this.col = col;
            if (value == 1) {
                textView.setText(R.string.string0);
            } else if (value == 2) {
                textView.setText(R.string.stringX);
            }
        }

        @Override
        public void onClick(View v) {
            mGameGridListener.textOnClick(v, row, col);
        }
    }
}
