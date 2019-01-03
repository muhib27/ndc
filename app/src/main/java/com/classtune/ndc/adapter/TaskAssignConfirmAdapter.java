package com.classtune.ndc.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.ndc.R;
import com.classtune.ndc.apiresponse.pigeonhole_api.Student;
import com.classtune.ndc.fragment.InstructorDetailsFragment;
import com.classtune.ndc.utils.PaginationAdapterCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.classtune.ndc.fragment.InsTructorTaskAssignFragment.afwcCount;
import static com.classtune.ndc.fragment.InsTructorTaskAssignFragment.capstonCount;
import static com.classtune.ndc.fragment.InsTructorTaskAssignFragment.ndcCount;
import static com.classtune.ndc.fragment.InsTructorTaskAssignFragment.selectedList;

//import com.bumptech.glide.DrawableRequestBuilder;


/**
 * Created by Muhib on 20/11/18.
 */

public class TaskAssignConfirmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String current;
    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;
    private static final int ADD = 5;


    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<Student> pigeonholeDataModelList = new ArrayList<>();
    private List<Student> phTasks = new ArrayList<>();
    private List<String> editSelectedList = new ArrayList<>();
    private Context context;
    private String SELECTED_TAB = "";

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;
    //    private OrderProcess mOrderProcessCallback;
    View myView;
    LayoutInflater layoutInflater;
    LinearLayout layout;

    //
    private String errorMsg;
    //HomeFragment homeFragment;




    public TaskAssignConfirmAdapter(Context context , String s) {
        this.context = context;
        pigeonholeDataModelList = new ArrayList<>();
        current = s;
    }

    public TaskAssignConfirmAdapter(Context context) {
        this.context = context;
        pigeonholeDataModelList = new ArrayList<>();
    }

    public TaskAssignConfirmAdapter(Context context, List<Student> strList) {

        this.context = context;
        this.mCallback = mCallback;
        this.pigeonholeDataModelList = strList;

    }



    public List<Student> getMovies() {
        return pigeonholeDataModelList;
    }

    public void setData(List<Student> pigeonholeDataModelList) {
        this.pigeonholeDataModelList = pigeonholeDataModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.task_user_selected_row, parent, false);
                viewHolder = new PigeonholeListItem(viewItem);
                break;

        }
        return viewHolder;
    }

    PigeonholeListItem itemHolder;
    int selectedPosition = -1;
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        OrderListModel result = orderList.get(position); // Movie
        String phoneString = "";
        final Bundle bundle = new Bundle();
        switch (getItemViewType(position)) {

            case ITEM:
                itemHolder = (PigeonholeListItem) holder;
                int total = phTasks.size();
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemHolder.title.setText(pigeonholeDataModelList.get(position).getName());
                int pos = position + 1;
                itemHolder.serial.setText(""+pos);

                break;


        }
    }



    @Override
    public int getItemCount() {
        return pigeonholeDataModelList == null ? 0 : pigeonholeDataModelList.size();
        //return pigeonholeDataModelList == null ? 0 : pigeonholeDataModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;

    }



    /*
        Helpers - Pagination
   _________________________________________________________________________________________________
    */

    public void add(Student r) {
        pigeonholeDataModelList.add(r);
        notifyItemInserted(pigeonholeDataModelList.size() - 1);
    }

    public void addAllData(List<Student> pigeonholeDataModels) {
        for (Student result : pigeonholeDataModels) {
            add(result);
        }


    }

    public void addAllNewData(List<Student> moveResults) {
        pigeonholeDataModelList.clear();
        pigeonholeDataModelList.addAll(moveResults);
        notifyDataSetChanged();
    }

    public void clearList() {
        pigeonholeDataModelList.clear();
    }


    public void remove(Student r) {
        int position = pigeonholeDataModelList.indexOf(r);
        if (position > -1) {
            pigeonholeDataModelList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Student());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pigeonholeDataModelList.size() - 1;
        Student result = getItem(position);

        if (result != null) {
            pigeonholeDataModelList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Student getItem(int position) {
        return pigeonholeDataModelList.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(pigeonholeDataModelList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    protected class PigeonholeListItem extends RecyclerView.ViewHolder {
        private TextView title, serial;
        public CheckBox selectCM;
        private ImageView attachment;

        private TextView menuOption;
        private LinearLayout itemLayout;

        private TextView assign_date, due_date;
        ImageButton dotMenu;

        public PigeonholeListItem(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.cmName);
            serial = itemView.findViewById(R.id.serial);



        }
    }


    private String dateTimeParse(String dateTime) {
//        String[] timeToAddParts = timeToAdd.split(" ");
        String parsedString = "";
        if (dateTime.contains(" ")) {
            String[] parts = dateTime.split(" ");
            if (!parts[0].isEmpty() && parts[0] != null)
                parsedString = parsedString + dateReverse(parts[0]);
//            if(!parts[1].isEmpty() && parts[1]!=null)
//                parsedString = parsedString + "  "+ parts[1].substring(0, parts[1].lastIndexOf(":"));
        }
        else if(!dateTime.isEmpty() && dateTime!=null)
            parsedString = dateReverse(dateTime);


        return parsedString;
    }

    public static String dateReverse(String duedate, String times) {

        SimpleDateFormat format1 = new SimpleDateFormat("dd MMM, yyyy-HH:mm");
        //SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//        format1.setTimeZone(TimeZone.getTimeZone("GMT"));
        String result = "";
        String returnResult = "";
        String dateText = duedate;
        String[] timeparts = times.split(":");

        if (dateText != null && dateText.contains("-")) {
            String[] parts = dateText.split("-");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(parts[0]));
            c.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));
            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeparts[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(timeparts[1]));
//            c.add(Calendar.MINUTE, timeToAdd);
            String tt = c.getTime().toString();


            // result = ( new SimpleDateFormat( "dd-MM-yyyy' 'HH:mm" ) ).format( c.getTime()).toString();;
            result = format1.format(c.getTime());
            String[] lastParse;
            if (result.contains("-")) {
                try {
                    lastParse = result.split("-");
                    returnResult = lastParse[0] + " at " + lastParse[1];
                } catch (Exception e) {

                }
            }

        }
        return returnResult;
    }
    public static String dateReverse(String duedate) {

        SimpleDateFormat format1 = new SimpleDateFormat("dd MMM, yyyy");
        //SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//        format1.setTimeZone(TimeZone.getTimeZone("GMT"));
        String result = "";
        String returnResult = "";
        String dateText = duedate;


        if (dateText != null && dateText.contains("-")) {
            String[] parts = dateText.split("-");
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(parts[0]));
            c.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));

//            c.add(Calendar.MINUTE, timeToAdd);
            String tt = c.getTime().toString();


            // result = ( new SimpleDateFormat( "dd-MM-yyyy' 'HH:mm" ) ).format( c.getTime()).toString();;
            result = format1.format(c.getTime());
            String[] lastParse;
//            if (result.contains("-")) {
//                try {
//                    lastParse = result.split("-");
//                    returnResult = lastParse[0] + " at " + lastParse[1];
//                } catch (Exception e) {
//
//                }
//            }

        }
        return result;
    }





}
