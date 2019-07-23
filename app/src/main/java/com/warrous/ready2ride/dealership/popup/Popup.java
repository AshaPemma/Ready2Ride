package com.warrous.ready2ride.dealership.popup;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.warrous.ready2ride.R;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.mydealerships.MyDealershipsActivity;
import com.warrous.ready2ride.framework.AlertUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asha on 12/3/18.
 */

public class Popup extends PopupWindow {
    Context context;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private int dx;
    private int dy;
    private OnSubmitListener mListener;
    PopupAdapter popupAdapter;
    ArrayList<DealershipResponse> categoriesList = new ArrayList<>();
    DealershipResponse dealershipResponse;
    MyDealershipsActivity activity;
    LinearLayoutManager memberLinearLayoutManager;


    public Popup(Context ctx, OnSubmitListener listener) {
        super(ctx);

        context = ctx;
        mListener = listener;


        setContentView(LayoutInflater.from(context).inflate(R.layout.activity_pop_up_window, null));
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        final View popupView = getContentView();
        ButterKnife.bind(this, popupView);
        setFocusable(false);


//        for (int i = 0; i < DataManager.getInstance().getDealerLists().size(); i++) {
//            categoriesList.add(DataManager.getInstance().getDealerLists().get(i).getName());
//
//        }
        categoriesList=new ArrayList<>();
        categoriesList=DataManager.getInstance().getDealerLists();

        memberLinearLayoutManager = new LinearLayoutManager(activity);
        rvCategories.setLayoutManager(memberLinearLayoutManager);



        btnOk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
              //  selectedCategories = DataManager.getInstance().getSelectedCategories();
                dealershipResponse=new DealershipResponse();
                dealershipResponse=popupAdapter.setSelectedDealerId();
                mListener.valueChanged(dealershipResponse);
                dismiss();
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

//                    case MotionEvent.ACTION_DOWN:
//                        dx = (int) motionEvent.getRawX();
//                        dy = (int) motionEvent.getRawY();
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        int x = (int) motionEvent.getRawX();
//                        int y = (int) motionEvent.getRawY();
//                        int left = (x - dx);
//                        int top = (y - dy);
//                        update(left, top, -1, -1);
//                        break;
                }
                return false;
            }
        });
    }

    public void setActivity(MyDealershipsActivity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void show(View v) {
        popupAdapter = new PopupAdapter(activity, categoriesList, new PopupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                String message = "Do you want to select dealer as Default Dealer";
                AlertUtil.showSaveAlert(LayoutInflater.from(btnOk.getContext()).inflate(R.layout.dialog_save, null),
                        message, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int dealerPos=position;
                                dealershipResponse=new DealershipResponse();
                                dealershipResponse=categoriesList.get(dealerPos);
                                mListener.valueChanged(dealershipResponse);
                                dismiss();
                            }
                        });

             //   Toast.makeText(activity, "Selected" + categoriesList.get(position), Toast.LENGTH_LONG).show();
            }
        });
        rvCategories.setAdapter(popupAdapter);
        showAsDropDown(v, 0, 0, Gravity.TOP);    }


    public interface OnSubmitListener {
        void valueChanged(DealershipResponse dealershipResponse);
    }
}
