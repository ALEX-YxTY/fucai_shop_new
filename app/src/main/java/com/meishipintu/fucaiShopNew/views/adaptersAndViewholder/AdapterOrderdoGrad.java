package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.bean.Notice;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26 0026.
 */
public class
        AdapterOrderdoGrad extends BaseAdapter {

    private Context context;
    private List<Notice> data;
    private View view;
    private int state = 1;
    private OnGradListener onGradListener;
    private OnCallListener onCallListener;
    private OnIgnoreListener onIgnoreListener;
    public AdapterOrderdoGrad(Context context, List<Notice> data) {
        this.context = context;
        this.data = data;
    }
    public interface OnGradListener{
        void onGrad(Notice orderdoGrad);
    }
    public void setOnGradListener(OnGradListener onGradListener) {
        this.onGradListener = onGradListener;
    }
    public interface OnCallListener{
        void onCall(Notice orderdoGrad);
    }
    public void setOnCallListener(OnCallListener onCallListener){
        this.onCallListener=onCallListener;
    }
    public interface OnIgnoreListener{
        void onIgnore(Notice orderdoGrad, String msg);
    }
    public void setOnIgnoreListener(OnIgnoreListener onIgnoreListener){
        this.onIgnoreListener=onIgnoreListener;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Notice orderdoGrad=data.get(position);
        if(convertView==null){
            holder = new ViewHolder();
            view=View.inflate(context, R.layout.item_order_dograd,null);
            holder.order_name= (TextView) view.findViewById(R.id.order_name);
            holder.phonenumber= (TextView) view.findViewById(R.id.phonenumber);
            holder.sum= (TextView) view.findViewById(R.id.sum);
            holder.kind= (TextView) view.findViewById(R.id.kind);
            holder.call= (Button) view.findViewById(R.id.call);
            holder.bt_dograd= (Button) view.findViewById(R.id.bt_dograd);
            holder.bt_ignored= (Button) view.findViewById(R.id.bt_ignored);
            holder.address= (TextView) view.findViewById(R.id.address);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.order_name.setText("用户"+orderdoGrad.getUsername()+"需要买"+orderdoGrad.getMoney()+"元种类"+orderdoGrad.getName()+"的彩票");
        holder.phonenumber.setText(orderdoGrad.getTel());
        holder.kind.setText(orderdoGrad.getName());
        holder.sum.setText(orderdoGrad.getMoney()+"元");
        holder.address.setText(orderdoGrad.getAddress());
        holder.bt_dograd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onGradListener != null) {
                    onGradListener.onGrad(orderdoGrad);
                }
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCallListener!=null){
                    onCallListener.onCall(orderdoGrad);
                }
            }
        });
        holder.bt_ignored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onIgnoreListener!=null){
                    onIgnoreListener.onIgnore(orderdoGrad,"是否忽略该订单");
                }
            }
        });
        return view;
    }

    private static class ViewHolder{
        private TextView order_name;
        private TextView phonenumber;
        private TextView sum;
        private TextView kind;
        private Button call;
        private Button bt_dograd;
        private Button bt_ignored;
        private TextView address;
    }
}
