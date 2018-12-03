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
import com.classtune.ndc.apiresponse.pigeonhole_api.Student;
import com.classtune.ndc.model.CMModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.classtune.ndc.fragment.InsTructorTaskAssignFragment.selectedList;

/**
 * Created by RR on 14-Nov-17.
 */

public class UserTaskAssignAdapter extends BaseAdapter {
    private Context context;
    private List<Student> studentList;
    private boolean isClickable = false;
    private boolean isUpdate = false;
    private boolean isAllPresent = false;
    private Set<String> listStudentStatusNew;
    private List<String> mList;
    private List<String> dTempList;
    String currentCourse="";
    public List<String> selectedList= new ArrayList<>();

    public UserTaskAssignAdapter(Context context, List<Student> studentList, String currentCourse) {
        this.context = context;
        this.studentList = studentList;

        this.listStudentStatusNew = new HashSet<>();
        this.mList = new ArrayList<>();
        this.dTempList = new ArrayList<>();
        this.currentCourse = currentCourse;
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

    public void setData(List<Student> studentList){
        this.studentList = studentList;
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
            holder.layout = rowView.findViewById(R.id.layout_check);


            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }


        holder.selectCM.setTag(index);
        if (studentList.get(index).getName().isEmpty() || studentList.get(index).getName() == null)
            holder.cmName.setText("-");
        else
            holder.cmName.setText(studentList.get(index).getName());

        if (selectedList.contains(studentList.get(index).getId())) {
//            if(dTempList.contains(studentList.get(index).getStudent_id()))
//            dTempList.add(studentList.get(index).getStudent_id());
            holder.selectCM.setChecked(true);
            //studentList.get(index).setSelected(true);
        } else {
            //studentList.get(index).setSelected(false);
            holder.selectCM.setChecked(false);
        }

        holder.selectCM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

//                if(!studentList.get(index).isSelected())
//                    studentList.get(index).setSelected(true);
//                else
//                    studentList.get(index).setSelected(false);
                if(!selectedList.contains(studentList.get(index).getId())) {
                    compoundButton.setChecked(true);
                    selectedList.add(studentList.get(index).getId());
                }
                else {
                    selectedList.remove(studentList.get(index).getId());
                    compoundButton.setChecked(false);
                }
            }
        });

//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!studentList.get(index).isSelected())
//                    studentList.get(index).setSelected(true);
//                else
//                    studentList.get(index).setSelected(false);
//                if(!selectedList.contains(studentList.get(index).getId())) {
//                    holder.selectCM.setChecked(true);
//                    selectedList.add(studentList.get(index).getId());
//                }
//                else {
//                    selectedList.remove(studentList.get(index).getId());
//                    holder.selectCM.setChecked(false);
//                }
//            }
//        });

//        holder.selectCM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // studentList.get(index).setDefaulter("1");
//
//
////                    if(!courseList.contains(currentCourse))
////                        courseList.add(currentCourse);
////                    else
//
//            }
//        });
        holder.selectCM.setChecked(studentList.get(index).isSelected());

        return rowView;
    }


    private static class ViewHolder {
        private TextView cmName;
        private TextView studentRoll;
        private TextView studentStatus;
        private CheckBox selectCM;
        private LinearLayout layout;
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
