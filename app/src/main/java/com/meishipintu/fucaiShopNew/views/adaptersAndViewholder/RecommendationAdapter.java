package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;
import com.meishipintu.fucaiShopNew.models.bean.QLCRecomend;
import com.meishipintu.fucaiShopNew.models.bean.RecoInfo;
import com.meishipintu.fucaiShopNew.models.bean.SSDRecomend;
import com.meishipintu.fucaiShopNew.models.bean.SSQRecomend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 * <p>
 * 主要功能：
 */

public class RecommendationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = 100;
    private static final int TYPE_NORMAL = 101;

    private static final String TAG = "njfucaiB-recoAdapter";
    private Context mContext;
    private List<RecoInfo> dataList;
    private int type;                                           //标注彩种类型 1-双色球 2-3d 3-七乐彩

    public RecommendationAdapter(Context context, List<RecoInfo> list) {
        this.mContext = context;
        this.dataList = list;
        if (dataList.size() > 0) {
            type = dataList.get(0).getType();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_EMPTY) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_empty, parent, false);
            return new EmptyViewHolder(view);
        } else {
            switch (type) {
                case 1:
                    return new RecommendSSQViewHolder(LayoutInflater.from(mContext)
                            .inflate(R.layout.item_recommendation, parent, false));
                case 2:
                    return new Recommend3DViewHolder(LayoutInflater.from(mContext)
                            .inflate(R.layout.item_recommendation_3d, parent, false));
                default:
                    return new RecommendQLCViewHolder(LayoutInflater.from(mContext)
                            .inflate(R.layout.item_recommendation_7lc, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            RecoInfo info = dataList.get(position);
            ((RecommendationViewHolder) holder).tvPeriod.setText(info.getPeriods()+"期");
            if (type == 1) {
                //双色球
                SSQRecomend situation = (SSQRecomend) info;
                RecommendSSQViewHolder holder1 = (RecommendSSQViewHolder) holder;
                String[] redBall = situation.getRedBalls();
                String[] blueBall = situation.getBlueBalls();
                String[] redResult = situation.getRedResult();
                if (redResult != null && redResult.length > 0) {
                    Arrays.sort(redResult);
                }

                List<TextView> redBallList = new ArrayList<>();
                List<TextView> blueBallList = new ArrayList<>();
                redBallList.add(holder1.red1);
                redBallList.add(holder1.red2);
                redBallList.add(holder1.red3);
                redBallList.add(holder1.red4);
                redBallList.add(holder1.red5);
                redBallList.add(holder1.red6);
                redBallList.add(holder1.red7);
                redBallList.add(holder1.red8);
                redBallList.add(holder1.red9);
                redBallList.add(holder1.red10);
                blueBallList.add(holder1.blue1);
                blueBallList.add(holder1.blue2);
                blueBallList.add(holder1.blue3);
                for (int i = 0; i < redBall.length; i++) {
                    redBallList.get(i).setText(redBall[i]);
                    if (situation.getIsSet() == 1 && !(Arrays.binarySearch(redResult, redBall[i]) <0)) {
                        redBallList.get(i).setEnabled(false);
                    } else {
                        redBallList.get(i).setEnabled(true);
                    }
                }
                for (int i = 0; i < blueBall.length; i++) {
                    blueBallList.get(i).setText(blueBall[i]);
                    if (situation.getIsSet() == 1 && blueBall[i].equals(situation.getBlueResult())) {
                        blueBallList.get(i).setEnabled(false);
                    } else {
                        blueBallList.get(i).setEnabled(true);
                    }
                }
                if (situation.getIsSet() == 1) {
                    holder1.tvPercentage.setText((situation.getMoney() * 100 / 1260) + "%");
                }
            } else if (type == 2) {
                //福彩3d
                SSDRecomend situation = (SSDRecomend) info;
                Recommend3DViewHolder holder1 = (Recommend3DViewHolder) holder;
                String[][] balls = situation.getBalls();
                String[] result = situation.getResult();
                Log.d(TAG, "ball1:" + Arrays.toString(balls[0]) + ",ball2:" + Arrays.toString(balls[1])
                        + ", ball3:" + Arrays.toString(balls[2]) + ",result:" +  Arrays.toString(result));
                List<TextView> redList = new ArrayList<>();
                redList.add(holder1.red1);
                redList.add(holder1.red2);
                List<TextView> blueList = new ArrayList<>();
                blueList.add(holder1.blue1);
                blueList.add(holder1.blue2);
                List<TextView> greenList = new ArrayList<>();
                greenList.add(holder1.green1);
                greenList.add(holder1.green2);
                for (int i = 0; i < balls[0].length; i++) {
                    redList.get(i).setText(balls[0][i].substring(1,2));
                    if (situation.getIsSet() == 1 && result[0].equals(balls[0][i].substring(1,2))) {
                        redList.get(i).setEnabled(false);
                    }
                }
                for (int i = 0; i < balls[1].length; i++) {
                    blueList.get(i).setText(balls[1][i].substring(1,2));
                    if (situation.getIsSet() == 1 && result[1].equals(balls[1][i].substring(1,2))) {
                        blueList.get(i).setEnabled(false);
                    }
                }
                for (int i = 0; i < balls[2].length; i++) {
                    greenList.get(i).setText(balls[2][i].substring(1,2));
                    if (situation.getIsSet() == 1 && result[2].equals(balls[2][i].substring(1,2))) {
                        greenList.get(i).setEnabled(false);
                    }
                }
                if (situation.getIsSet() == 1 && situation.getMoney() > 0) {
                    holder1.tvPercentage.setText("中奖");
                } else if (situation.getIsSet() == 0) {
                    holder1.tvPercentage.setText("未开奖");
                } else {
                    holder1.tvPercentage.setText("未中");
                }
            } else {
                //七乐彩
                QLCRecomend situation = (QLCRecomend) info;
                RecommendQLCViewHolder holder1 = (RecommendQLCViewHolder) holder;
                String[] balls = situation.getBalls();
                String[] redResult = situation.getRedResult();
                String specialBall = situation.getSpecialBall();

                if (redResult != null && redResult.length > 0) {
                    Arrays.sort(redResult);
                }

                List<TextView> redBallList = new ArrayList<>();
                redBallList.add(holder1.red1);
                redBallList.add(holder1.red2);
                redBallList.add(holder1.red3);
                redBallList.add(holder1.red4);
                redBallList.add(holder1.red5);
                redBallList.add(holder1.red6);
                redBallList.add(holder1.red7);
                redBallList.add(holder1.red8);
                redBallList.add(holder1.red9);
                redBallList.add(holder1.red10);
                for (int i = 0; i < balls.length; i++) {
                    redBallList.get(i).setText(balls[i]);
                    if (situation.getIsSet() == 1 && Arrays.binarySearch(redResult, balls[i]) >=0) {
                        redBallList.get(i).setEnabled(false);
                    }
                    if (situation.getIsSet() == 1 && specialBall.equals(balls[i])) {
                        redBallList.get(i).setEnabled(false);
                        redBallList.get(i).setBackgroundResource(R.drawable.selector_circle_blueball_grey);
                    }
                }
                if (situation.getIsSet() == 1) {
                    holder1.tvPercentage.setText((situation.getMoney() * 100 / 240) + "%");
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size() == 0 ? 1 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.size() == 0 && position == 0) {
            return TYPE_EMPTY;
        } else {
            return TYPE_NORMAL;
        }
    }
}
