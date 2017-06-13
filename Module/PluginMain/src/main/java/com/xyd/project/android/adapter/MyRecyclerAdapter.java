package com.xyd.project.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.project.R;
import com.xyd.project.common.callback.ItemClickListener;
import com.xyd.project.common.callback.ItemLongClickListener;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyRecyclerViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private String[] mData;
    private int[] ivData;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public MyRecyclerAdapter(Context context, String[] mData, int[] ivData) {
        this.context = context;
        this.mData = mData;
        this.ivData = ivData;
        inflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyRecyclerViewHolder(inflater.inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
        ImageView itemImg = holder.getView(R.id.item_img);
        itemImg.setImageResource(ivData[position]);
        holder.setText(R.id.item_tv, mData[position]);
        View itemLayout = holder.getView(R.id.item_layout);

        itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.itemClick(position);
                }
            }
        });
        itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemLongClickListener != null)
                    itemLongClickListener.itemLongClick(position);
                return false;
            }
        });
    }


    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        SparseArray<View> views;

        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<>();
        }

        public <T extends View> T getView(int id) {
            View view = views.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return (T) view;
        }

        public MyRecyclerViewHolder setText(int id, CharSequence sequence) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(sequence);
            } else if (view instanceof Button) {
                ((Button) view).setText(sequence);
            }
            return this;
        }
    }
}
