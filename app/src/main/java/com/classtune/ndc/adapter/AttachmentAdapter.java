package com.classtune.ndc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtune.ndc.R;
import com.classtune.ndc.model.AttachmentModel;
import com.classtune.ndc.model.CMModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by RR on 14-Nov-17.
 */

public class AttachmentAdapter extends BaseAdapter {
    private Context context;
    private List<AttachmentModel> studentList;
    private boolean isClickable = false;
    private boolean isUpdate = false;
    private boolean isAllPresent = false;
    private Set<String> listStudentStatusNew;
    private List<String> mList;
    private List<String> dTempList;

    public AttachmentAdapter(Context context, List<AttachmentModel> studentList) {
        this.context = context;
        this.studentList = studentList;

        this.listStudentStatusNew = new HashSet<>();
        this.mList = new ArrayList<>();
        this.dTempList = new ArrayList<>();
    }

    public AttachmentAdapter() {
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
            rowView = inflater.inflate(R.layout.attachment_list_row, null);

            holder.cmName = (TextView) rowView.findViewById(R.id.fileName);
            holder.delete =  rowView.findViewById(R.id.delete);

//            holder.selectCM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    studentList.get(index).setDefaulter("1");
//                }
//            });

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }


//        holder.selectCM.setTag(index);
        if (studentList.get(index).getFileName().isEmpty() || studentList.get(index).getFileName() == null)
            holder.cmName.setText("-");
        else
            holder.cmName.setText(studentList.get(index).getFileName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.remove(index);
                notifyDataSetChanged();
            }
        });

//        if (studentList.get(index).getDefaulter().equals("1") || studentList.get(index).isSelected()) {
////            if(dTempList.contains(studentList.get(index).getStudent_id()))
////            dTempList.add(studentList.get(index).getStudent_id());
//            holder.selectCM.setChecked(true);
//            studentList.get(index).setSelected(true);
//        }
//        else {
//            studentList.get(index).setSelected(false);
//            holder.selectCM.setChecked(false);
//        }
//
//
//
//        holder.selectCM.setChecked(studentList.get(index).isSelected());
        return rowView;
    }


    private static class ViewHolder {
        private TextView cmName;
        private TextView studentRoll;
        private TextView studentStatus;
        private CheckBox selectCM;
        private LinearLayout defaulterLayout;
        private RelativeLayout checkboxLayout;
        ImageButton delete;
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
