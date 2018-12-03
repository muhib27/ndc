package com.classtune.ndc.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.ndc.R;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTask;
import com.classtune.ndc.apiresponse.pigeonhole_api.Student;
import com.classtune.ndc.fragment.InstructorDetailsFragment;
import com.classtune.ndc.utils.PaginationAdapterCallback;

import java.lang.reflect.Field;
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

public class TaskAssignAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String current;
    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;
    private static final int ADD = 5;


    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<Student> pigeonholeDataModelList;
    private List<Student> phTasks = new ArrayList<>();
    private Context context;

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

    String shippingAddressOne = "";
    String shippingAddressTwo = "";
    String billingAddressOne = "";
    String billingAddressTwo = "";



    public TaskAssignAdapter(Context context , String s) {
        this.context = context;
        pigeonholeDataModelList = new ArrayList<>();
        current = s;
    }

    public TaskAssignAdapter(Context context, List<Student> strList, String s) {
        this.context = context;
        this.mCallback = mCallback;
        pigeonholeDataModelList = new ArrayList<>();

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
                View viewItem = inflater.inflate(R.layout.task_user_selection_row, parent, false);
                viewHolder = new PigeonholeListItem(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
//            case HERO:
//                View viewHero = inflater.inflate(R.layout.item_hero, parent, false);
//                viewHolder = new HeroVH(viewHero);
//                break;
//            case ADD:
//                View viewAdd = inflater.inflate(R.layout.layout, parent, false);
//                viewHolder = new HeroAdd(viewAdd);
//                break;
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
                if(selectedList.contains(pigeonholeDataModelList.get(position).getId()))
                    itemHolder.selectCM.setChecked(true);
//                else
//                    itemHolder.selectCM.setChecked(false);



                itemHolder.selectCM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        selectedPosition = itemHolder.getAdapterPosition();
                        itemHolder.selectCM.setChecked(b);
                        if(!selectedList.contains(pigeonholeDataModelList.get(position).getId())) {
                            selectedList.add(pigeonholeDataModelList.get(position).getId());
                            if(current.equals("ndc"))
                                ndcCount++;
                            else if(current.equals("afwc"))
                                afwcCount++;
                            else if(current.equals("capston"))
                                capstonCount++;
                            if(selectedPosition == position)
                                itemHolder.selectCM.setChecked(true);
                            else
                                itemHolder.selectCM.setChecked(false);

                        }
                        else {
                            selectedList.remove(pigeonholeDataModelList.get(position).getId());
                            if(current.equals("ndc"))
                                ndcCount--;
                            else if(current.equals("afwc"))
                                afwcCount--;
                            else if(current.equals("capston"))
                                capstonCount--;
                            //compoundButton.setChecked(false);
                        }
                    }
                });
                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;


        }
    }

//    myTextView.setText(Html.fromHtml(stringB + "<font color=red>" + stringA + "</font>);


//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        private int position;
//        public MyMenuItemClickListener(int positon) {
//            this.position=positon;
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//
////            case R.id.Not_interasted_catugury:
////                String RemoveCategory=mDataSet.get(position).getCategory();
////                // mDataSet.remove(position);
////                //notifyItemRemoved(position);
////                // notifyItemRangeChanged(position,mDataSet.size());
////
////                mySharedPreferences.saveStringPrefs(Constants.REMOVE_CTAGURY,RemoveCategory,MainActivity.context);
////                Toast.makeText(MainActivity.context, "Add to favourite", Toast.LENGTH_SHORT).show();
////                return true;
//                case R.id.edit:
////                mDataSet.remove(position);
////                notifyItemRemoved(position);
////                notifyItemRangeChanged(position,mDataSet.size());
//                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.delete:
//                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
////                mySharedPreferences.deletePrefs(Constants.REMOVE_CTAGURY,MainActivity.context);
//                default:
//            }
//            return false;
//        }
//    }

    private void gotoOrderDetailsFragment(Bundle bundle) {


//        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
//        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        orderDetailsFragment.setArguments(bundle);
//        transaction.add(R.id.container, orderDetailsFragment, "orderDetailsFragment").addToBackStack(null);
//        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return pigeonholeDataModelList == null ? 0 : pigeonholeDataModelList.size();
        //return pigeonholeDataModelList == null ? 0 : pigeonholeDataModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position % 6 == 0) {
//            return HERO;
//        } else if ((position % 6) == 5) {
//            if (position == 23 && isLoadingAdded)
//                return LOADING;
//            else
//                return ADD;
//        } else {
//            return ITEM;
//        }

//        if (position!= 24 && position%6 == 0 ) {
//            return HERO;
//        }
//        else if((position%6) == 5)
//        {
//            return ADD;
//        }
//        else {
        return (position == pigeonholeDataModelList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
//        }
    }


//    private DrawableRequestBuilder<String> loadImage(@NonNull String posterPath) {
//        return Glide
//                .with(context)
//                .load(posterPath)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
////                .centerCrop()
//                .crossFade();
//    }
//
//    private DrawableRequestBuilder<String> loadThumbImage(@NonNull String posterPath) {
//        return Glide
//                .with(context)
//                .load(posterPath)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
//                .centerCrop()
//                .crossFade();
//    }


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
        private TextView title;
        public CheckBox selectCM;
        private ImageView attachment;

        private TextView menuOption;
        private LinearLayout itemLayout;

        private TextView assign_date, due_date;
        ImageButton dotMenu;

        public PigeonholeListItem(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.cmName);
            selectCM = itemView.findViewById(R.id.selectCM);



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


    private void gotoInstructorDetailsFragment() {
        InstructorDetailsFragment instructorDetailsFragment = new InstructorDetailsFragment();
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, instructorDetailsFragment, "instructorDetailsFragment").addToBackStack(null);
        ;
        transaction.commit();
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

}
