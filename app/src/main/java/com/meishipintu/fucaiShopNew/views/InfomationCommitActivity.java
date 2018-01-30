package com.meishipintu.fucaiShopNew.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.meishipintu.fucaiShopNew.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

public class InfomationCommitActivity extends Activity {

    private static final int CHOOSE_IDENTITY = 01;
    private static final int CHOOSE_BANK_FRONT = 02;
    private static final int CHOOSE_BANK_BACK = 03;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.iv_ID)
    ImageView ivID;
    @BindView(R.id.et_card_num)
    EditText etCardNum;
    @BindView(R.id.iv_front)
    ImageView ivFront;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private String iDPicFile = null;
    private String bankFrontPicFile = null;
    private String bankBackPicFile = null;

    private RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_commit);
        ButterKnife.bind(this);
        glide = Glide.with(this);
    }

    @OnClick({R.id.bt_return,R.id.iv_ID, R.id.iv_front, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                onBackPressed();
                break;
            case R.id.iv_ID:
                //启动选择图片
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(this, CHOOSE_IDENTITY);
                break;
            case R.id.iv_front:
                //启动选择图片
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(this, CHOOSE_BANK_FRONT);
                break;
            case R.id.iv_back:
                //启动选择图片
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(this, CHOOSE_BANK_BACK);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE_IDENTITY) {
                //选择身份证
                if(data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    //拿取图片
                    iDPicFile = photos.get(0);
                    Log.d("test", "idPic:" + iDPicFile);
                    glide.load(iDPicFile).into(ivID);
                }
            } else if (requestCode == CHOOSE_BANK_FRONT) {
                //银行卡正面
                if(data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    //拿取图片
                    bankFrontPicFile = photos.get(0);
                    Log.d("test", "frontPic:" + bankFrontPicFile);
                    glide.load(bankFrontPicFile).into(ivFront);
                }
            } else if (requestCode == CHOOSE_BANK_BACK) {
                //银行卡背面
                if(data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    //拿取图片
                    bankBackPicFile = photos.get(0);
                    Log.d("test", "backPic:" + bankBackPicFile);
                    glide.load(bankBackPicFile).into(ivBack);
                }
            }
        }
    }
}
