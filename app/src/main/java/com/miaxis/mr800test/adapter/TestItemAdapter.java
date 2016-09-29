package com.miaxis.mr800test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.domain.TestItem;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by xu.nan on 2016/9/13.
 */
public class TestItemAdapter extends RecyclerView.Adapter<TestItemAdapter.ItemViewHolder> {

    private List<TestItem> items;
    private Context context;

    public TestItemAdapter(List<TestItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final TestItem item = items.get(position);
        if(item == null) {
            return;
        }
        if("0".equals(item.getCheck())) {
            holder.cb_test.setChecked(false);
        }else if("1".equals(item.getCheck())) {
            holder.cb_test.setChecked(true);
        }
        holder.cb_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("0".equals(item.getCheck())) {
                    item.setCheck("1");
                }else if("1".equals(item.getCheck())) {
                    item.setCheck("0");
                }
            }
        });
        holder.item_name.setText(item.getName());
        holder.item_status.setText(item.getStatus());
        if("通过".equals(item.getStatus())) {
            holder.item_status.setTextColor(context.getResources().getColor(R.color.green_dark));
        }else if("失败".equals(item.getStatus())) {
            holder.item_status.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            holder.item_status.setTextColor(context.getResources().getColor(R.color.dark));
        }
        holder.item_message.setText(item.getMessage());
        holder.item_opdate.setText(item.getOpdate());
        holder.item_optime.setText(item.getOptime());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.cb_test)
        private CheckBox cb_test;
        @ViewInject(R.id.item_name)
        private TextView item_name;
        @ViewInject(R.id.item_status)
        private TextView item_status;
        @ViewInject(R.id.item_message)
        private TextView item_message;
        @ViewInject(R.id.item_opdate)
        private TextView item_opdate;
        @ViewInject(R.id.item_optime)
        private TextView item_optime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this, itemView);

        }
    }

    public void setItems(List<TestItem> items) {
        this.items = items;
    }
}
