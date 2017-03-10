package com.youjuke.buildingmaterialmall.widgets;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.Constant;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.HouseType;
import com.youjuke.swissgearlibrary.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 户型选择器
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-16 17:22
 */

public class HouseTypePicker extends Dialog {

    private RecyclerView recyclerView;
    private List<HouseType> houseTypes = new ArrayList<>();
    private View.OnClickListener onItemCheckLinstener;
    PickerView minute_pv;
    private final Button sure;
    private final Button cancel;
    int position=6;
    public HouseTypePicker(final Context context) {
        super(context, R.style.MyDialogStyles);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_house_type_picker, null));
        setCanceledOnTouchOutside(true);
        houseTypes = new Gson().fromJson(Constant.HOUSE_TYPE_JSON, new TypeToken<List<HouseType>>() {
        }.getType());
       final ArrayList<String> data=new ArrayList();
       for (int i=0;i<houseTypes.size();i++){
           data.add(houseTypes.get(i).getName());
       }
        minute_pv = (PickerView) findViewById(R.id.minute_pv);
        minute_pv.setData(data);

        minute_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String  text)
            {
                for (int i=0;i<houseTypes.size();i++){
                    if (houseTypes.get(i).getName().trim().equals(text)){
                        position=i;
                        return;
                    }
                }
            }
        });
        cancel = (Button) findViewById(R.id.btn_cancel);
        sure = (Button) findViewById(R.id.btn_sure);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.get().post("setHouseType", houseTypes.get(position));
                dismiss();
            }
        });
    }


//    class HouseTypeAdatper extends RecyclerView.Adapter<HouseTypeAdatper.HouseTypeVH> {
//
//        @Override
//        public HouseTypeVH onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new HouseTypeVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_house_type, null));
//        }
//
//        @Override
//        public void onBindViewHolder(HouseTypeVH holder, final int position) {
//            holder.tv_type_name.setText(houseTypes.get(position).getName());
//            holder.tv_type_name.setTag(houseTypes.get(position).getId());
//            holder.view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    RxBus.get().post("setHouseType", houseTypes.get(position));
//                    dismiss();
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return houseTypes.size();
//        }
//
//        class HouseTypeVH extends RecyclerView.ViewHolder {
//
//            private TextView tv_type_name;
//            private View view;
//
//            public HouseTypeVH(View itemView) {
//                super(itemView);
//                view = itemView;
//                tv_type_name = (TextView) itemView.findViewById(R.id.tv_type_name);
//            }
//        }
//    }


}
