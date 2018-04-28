package com.gaoda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoda.bean.UserBean;
import com.gaoda.philips.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    Context context;
    List<UserBean> datas;


    public void setClick(ItemClick click) {
        this.click = click;
    }

    ItemClick click;


    public UserAdapter(Context context, List<UserBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context).inflate(R.layout.item_user,parent,false));
    }

    @Override
    public void onBindViewHolder(final UserHolder holder, final int position) {
        Log.w("xxxx",position+"");
        holder.textView.setText(datas.get(position).getPhone_number()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click==null){
                    return;
                }
               click.onClick();
            }
        });
        holder.d_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.d_lay.setVisibility(View.GONE);
                holder.r_lay.setVisibility(View.VISIBLE);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click==null){
                    return;
                }
                click.onDelelete(position);
            }
        });
        holder.admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click==null){
                    return;
                }
                click.onAdmin(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout d_lay;
        LinearLayout r_lay;
        TextView delete;
        TextView admin;
        public UserHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.username);
            d_lay = itemView.findViewById(R.id.d_lay);
            r_lay = itemView.findViewById(R.id.r_lay);
            delete = itemView.findViewById(R.id.delete);
            admin = itemView.findViewById(R.id.admin);
        }
    }

    public interface ItemClick{
        void onClick();
        void onDelelete(int position);
        void onAdmin(int position);
    }
}
