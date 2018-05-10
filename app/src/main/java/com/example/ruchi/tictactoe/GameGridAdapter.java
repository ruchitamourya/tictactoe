package com.example.ruchi.tictactoe;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        CardView cardView = view.findViewById(R.id.grid_card_view);
        TextView textView = cardView.findViewById(R.id.text_view);
        final int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        int cardViewWidth = (screenWidth - 48) / mData.length;
        if (mData.length == 3) {
            cardViewWidth = (screenWidth - 200) / mData.length;
            cardView.setLayoutParams(new CardView.LayoutParams(cardViewWidth, cardViewWidth));
        } else if (mData.length == 4) {
            cardView.setLayoutParams(new CardView.LayoutParams(cardViewWidth, cardViewWidth));
        } else {
            textView.setTextSize(30f);
            cardView.setLayoutParams(new CardView.LayoutParams(cardViewWidth, cardViewWidth));

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
                textView.setText("O");
            } else if (value == 2) {
                textView.setText("X");
            }
        }

        @Override
        public void onClick(View v) {
            mGameGridListener.textOnClick(v, row, col);
        }
    }
}
