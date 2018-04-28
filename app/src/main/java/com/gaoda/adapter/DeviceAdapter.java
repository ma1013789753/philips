package com.gaoda.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaoda.bean.DeviceBean;
import com.gaoda.philips.MainActivity;
import com.gaoda.philips.R;
import com.gaoda.util.StringUtils;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder> {
    Context context;
    List<DeviceBean> datas;


    public void setClick(ItemClick click) {
        this.click = click;
    }

    ItemClick click;


    public DeviceAdapter(Context context, List<DeviceBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceHolder(LayoutInflater.from(context).inflate(R.layout.item_device,parent,false));
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        if(StringUtils.isBlank(datas.get(position).getDevice_alias())){
            holder.name.setText("Air Clear");
        }else{
            holder.name.setText(datas.get(position).getDevice_alias());
        }

        holder.state.setText("is_Online:"+datas.get(position).getDevice_info().isIs_online()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click==null){
                    return;
                }
               click.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView state;
        public DeviceHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.name);
            state = itemView.findViewById(R.id.state);
        }
    }

    public interface ItemClick{
        void onClick(int position);
    }
}
