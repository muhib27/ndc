package com.classtune.ndc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.classtune.ndc.R;
import com.classtune.ndc.model.CMModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by RR on 14-Nov-17.
 */

public class UserTaskAssignAdapter extends BaseAdapter {
    private Context context;
    private List<CMModel> studentList;
    private boolean isClickable = false;
    private boolean isUpdate = false;
    private boolean isAllPresent = false;
    private Set<String> listStudentStatusNew;
    private List<String> mList;
    private List<String> dTempList;

    public UserTaskAssignAdapter(Context context, List<CMModel> studentList) {
        this.context = context;
        this.studentList = studentList;

        this.listStudentStatusNew = new HashSet<>();
        this.mList = new ArrayList<>();
        this.dTempList = new ArrayList<>();
    }

    public UserTaskAssignAdapter() {
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public boolean getClickable() {
        return this.isClickable;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isAllPresent() {
        return isAllPresent;
    }

    public void setAllPresent(boolean allPresent) {
        isAllPresent = allPresent;
    }
    ViewHolder holder;

    @Override
    public View getView(final int index, final View convertView, ViewGroup viewGroup) {

        View rowView = convertView;
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        dTempList.clear();
        //final ViewHolder holder;
        if (rowView == null) {

            holder = new ViewHolder();
            rowView = inflater.inflate(R.layout.task_user_selection_row, null);

            holder.cmName = (TextView) rowView.findViewById(R.id.cmName);
            holder.selectCM = (CheckBox) rowView.findViewById(R.id.selectCM);

            holder.selectCM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    studentList.get(index).setDefaulter("1");
                }
            });

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }


        holder.selectCM.setTag(index);
        if (studentList.get(index).getCm_name().isEmpty() || studentList.get(index).getCm_name() == null)
            holder.cmName.setText("-");
        else
            holder.cmName.setText(studentList.get(index).getCm_name());

        if (studentList.get(index).getDefaulter().equals("1") || studentList.get(index).isSelected()) {
//            if(dTempList.contains(studentList.get(index).getStudent_id()))
//            dTempList.add(studentList.get(index).getStudent_id());
            holder.selectCM.setChecked(true);
            studentList.get(index).setSelected(true);
        }
        else {
            studentList.get(index).setSelected(false);
            holder.selectCM.setChecked(false);
        }



        holder.selectCM.setChecked(studentList.get(index).isSelected());
        return rowView;
    }


    private static class ViewHolder {
        private TextView cmName;
        private TextView studentRoll;
        private TextView studentStatus;
        private CheckBox selectCM;
        private LinearLayout defaulterLayout;
        private RelativeLayout checkboxLayout;
    }

//    public List<String> getListStudentDataId() {
//        //int d = dTempList.size();
//
//        List<String> mSet = new ArrayList<>();
//        for(int i = 0; i<studentList.size(); i++)
//        {
//            if(studentList.get(i).isSelected())
//                mSet.add(studentList.get(i).getStudent_id());
//        }
////        for(String str : listStudentStatusNew){
////            str = str.substring(0, str.length()-1);
////            mSet.add(str);
////        }
//        return mSet;
//    }
}
