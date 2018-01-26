package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.bean.OrderdoGrad;

import java.util.List;

/**
 * Created by Administrator on 2016/5/6 0006.
 */
public class AdapterOrder extends BaseAdapter {
    private Context context;
    private List<OrderdoGrad> data;
    private View view;
    private OnCallListener onCallListener;
    public int state=2;
    public AdapterOrder(Context context, List<OrderdoGrad> data) {
        this.context = context;
        this.data = data;
    }
    public interface OnCallListener{
        void onCall(OrderdoGrad orderdoGrad);
    }
    public void setOnCallListener(OnCallListener onCallListener){
        this.onCallListener=onCallListener;
    }
    public void setState(int state){
        this.state=state;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        final OrderdoGrad orderdoGrad=data.get(position);
        if(convertView==null){
            holder = new ViewHolder();
            view=View.inflate(context, R.layout.item_order,null);
            holder.order_name= (TextView) view.findViewById(R.id.order_name);
            holder.phonenumber= (TextView) view.findViewById(R.id.phonenumber);
            holder.sum= (TextView) view.findViewById(R.id.sum);
            holder.kind= (TextView) view.findViewById(R.id.kind);
            holder.call= (Button) view.findViewById(R.id.call);
            holder.order_state= (TextView) view.findViewById(R.id.order_state);
            holder.address= (TextView) view.findViewById(R.id.address);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.order_name.setText("用户"+orderdoGrad.getUsername()+"需要买"+orderdoGrad.getMoney()+"元种类"+orderdoGrad.getName()+"的彩票");
        holder.phonenumber.setText(orderdoGrad.getTel());
        holder.kind.setText(orderdoGrad.getName());
        holder.sum.setText(orderdoGrad.getMoney() + "元");
        holder.address.setText(orderdoGrad.getMaddress());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCallListener!=null){
                    onCallListener.onCall(orderdoGrad);
                }
            }
        });
        if(state==2){
            holder.order_state.setText("进行中");
            holder.order_state.setBackgroundResource(R.drawable.bg_normal_strip);
        }else if(state==3){
            holder.order_state.setText("已取消");
            holder.order_state.setBackgroundResource(R.drawable.bg_cancel_strip);
        }else if(state==4){
            holder.order_state.setText("已完成");
            holder.order_state.setBackgroundResource(R.drawable.bg_normal_strip);
        }
        return view;
    }
    private static class ViewHolder{
        private TextView order_name;
        private TextView phonenumber;
        private TextView sum;
        private TextView kind;
        private Button call;
        private TextView order_state;
        private TextView address;
    }
}
