package com.classtune.ndc.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.ndc.R;
import com.classtune.ndc.apiresponse.course_calendar_api.EventList;
import com.classtune.ndc.apiresponse.course_calendar_api.Routine;
import com.classtune.ndc.fragment.ClassScheduleDetailsFragment;
import com.classtune.ndc.utils.AppUtility;
import com.classtune.ndc.utils.PaginationAdapterCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Muhib on 20/11/18.
 */

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // View Types
    private static final int ITEM = 0;
    private static final int ITEM_1 = 1;
    private static final int LOADING = 4;
    private static final int NOTICE = 2;
    private static final int ADD = 5;
    int viewparameter = 0;


    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<EventList> eventLists;
    private List<EventList> strList = new ArrayList<>();
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

    //
    public EventsAdapter(Context context, PaginationAdapterCallback mCallback) {
        this.context = context;
        this.mCallback = mCallback;
        eventLists = new ArrayList<>();
        //this.mOrderProcessCallback = orderProcessCallback;

    }

    public EventsAdapter(Context context) {
        this.context = context;
        eventLists = new ArrayList<>();
        this.viewparameter = viewparameter;
    }

    public EventsAdapter(Context context, ArrayList<EventList> strList) {
        this.context = context;
        this.mCallback = mCallback;
        this.strList = strList;

    }

    public void setData(List<EventList> list) {
        eventLists = list;
    }


    public List<EventList> getMovies() {
        return eventLists;
    }

//    public void setMovies(List<PigeonholeDataModel> orderList) {
//        this.orderList = orderList;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.single_events_row, parent, false);
                viewHolder = new NoticeListItem(viewItem);
//                View viewItem = inflater.inflate(R.layout.dashboard_single_class_schedule_row, parent, false);
//                viewHolder = new NoticeListItem(viewItem);
                break;
            case ITEM_1:
                View viewItem_card = inflater.inflate(R.layout.single_events_row, parent, false);
                viewHolder = new NoticeListItem(viewItem_card);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        OrderListModel result = orderList.get(position); // Movie
        String phoneString = "";
        final Bundle bundle = new Bundle();
        switch (getItemViewType(position)) {

            case ITEM:
                final NoticeListItem itemHolder = (NoticeListItem) holder;
                int total = strList.size();
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(eventLists.get(position).getTitle()!=null)
                itemHolder.orderTitle.setText(eventLists.get(position).getTitle());
                if(eventLists.get(position).getDescription()!=null)
                    itemHolder.description.setText(eventLists.get(position).getDescription());

                if(eventLists.get(position).getStart()!=null)
                itemHolder.startDate.setText(Html.fromHtml("Start Date: " + "<font color = #3F86A0><strong>" + dateTimeParse(eventLists.get(position).getStart()) + "<strong></font>"));
                if(!time.isEmpty()) {
                    itemHolder.time.setVisibility(View.VISIBLE);
                    itemHolder.time.setText(time);
                }
                else
                    itemHolder.time.setVisibility(View.GONE);
                if(eventLists.get(position).getEnd()!=null && !eventLists.get(position).getEnd().equals("0000-00-00 00:00:00")) {
                    itemHolder.endDate.setVisibility(View.VISIBLE);
                    itemHolder.endDate.setText(Html.fromHtml("End Date: " + "<font color = #3F86A0><strong>" + dateTimeParse(eventLists.get(position).getEnd()) + "<strong></font>"));
                }
                else
                    itemHolder.endDate.setVisibility(View.INVISIBLE);

                if(eventLists.get(position).getIsAcademid()!=null){
                    itemHolder.isAcademic.setVisibility(View.VISIBLE);
                    itemHolder.colorView.setBackgroundColor(context.getResources().getColor(R.color.blue_color));
                }
                else
                    itemHolder.isAcademic.setVisibility(View.GONE);
//
//                if(routineList.get(position).getMode()!=null)
//                    itemHolder.mode.setText(routineList.get(position).getMode());
                if(!eventLists.get(position).getIsHoliday().isEmpty()){
                    itemHolder.colorView.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                }


//                itemHolder.name.setText("Pizza");
//                itemHolder.quantity.setText("Total ietm 10");
                //itemHolder.itemNameLayout.removeAllViews();
                String customerName = "";
                int totalItem = 0;


                itemHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //gotoOrderDetailsFragment(orderList.get(position).getId());
//                        String str = new Gson().toJson(orderList.get(position));
                        //bundle.putString("products", str);
//                        bundle.putString("order_id", orderList.get(position).getId());
                        gotoDetailsFragment();

                    }
                });
                break;
//            case ITEM_1:
//                final NoticeListItem itemHolder_ = (NoticeListItem) holder;
////                int total = strList.size();
//                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                itemHolder_.orderTitle.setText(classScheduleModels.get(position).getTitle());
////                itemHolder.name.setText("Pizza");
////                itemHolder.quantity.setText("Total ietm 10");
//                //itemHolder.itemNameLayout.removeAllViews();
////                String customerName = "";
////                int totalItem = 0;
//
//
//                itemHolder_.itemLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //gotoOrderDetailsFragment(orderList.get(position).getId());
////                        String str = new Gson().toJson(orderList.get(position));
//                        //bundle.putString("products", str);
////                        bundle.putString("order_id", orderList.get(position).getId());
//                        gotoDetailsFragment();
//
//                    }
//                });
//                break;


        }
    }


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
        //return strList == null ? 0 : strList.size();
        return eventLists == null ? 0 : eventLists.size();
    }

    @Override
    public int getItemViewType(int position) {
//
 //       {
//            if (position == 23 && isLoadingAdded)
//                return LOADING;
//            else
//                return ADD;
//        } else {
//            if (viewparameter == 0)
                return ITEM;
//            else
           //     return ITEM_1;
       // }

//        if (position!= 24 && position%6 == 0 ) {
//            return HERO;
//        }
//        else if((position%6) == 5)
//        {
//            return ADD;
//        }
//        else {
//        return (position == strList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
//        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    protected class PigeonholeListItem extends RecyclerView.ViewHolder {
        private TextView orderTitle;
        private TextView date;
        private TextView time; // displays "year | language"
        private ImageView itemImage;
        private ProgressBar mProgress;
        private TextView mode;
        private LinearLayout itemLayout;
        private LinearLayout itemNameLayout;
        private TextView rejected;
        private TextView status, customerNameText, totalItemText, deliveryTime, delivery;

        public PigeonholeListItem(View itemView) {
            super(itemView);

            orderTitle = (TextView) itemView.findViewById(R.id.title);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
            date = (TextView) itemView.findViewById(R.id.date);
            mode = (TextView) itemView.findViewById(R.id.mode);
            time = itemView.findViewById(R.id.time);


        }
    }

    protected class NoticeListItem extends RecyclerView.ViewHolder {
        private TextView orderTitle;
        private TextView startDate;
        private TextView time;
        private TextView description; // displays "year | language"
        private ImageView itemImage;
        private ProgressBar mProgress;
        private TextView mode;
        private LinearLayout itemLayout;
        private LinearLayout itemNameLayout;
        private TextView endDate;
        View colorView, isAcademic;
        private TextView status, customerNameText, totalItemText, deliveryTime, delivery;

        public NoticeListItem(View itemView) {
            super(itemView);

            orderTitle = (TextView) itemView.findViewById(R.id.title);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
            startDate = (TextView) itemView.findViewById(R.id.startDate);
            time = itemView.findViewById(R.id.time);
            description = itemView.findViewById(R.id.description);
            mode = (TextView) itemView.findViewById(R.id.mode);
            endDate = (TextView) itemView.findViewById(R.id.endDate);
            colorView = (View) itemView.findViewById(R.id.isHolyday);
            isAcademic = (View) itemView.findViewById(R.id.isAcademic);



        }
    }


    private String dateTimeParse(String dateTime) {
//        String[] timeToAddParts = timeToAdd.split(" ");
        time = "";
        String parsedString = "";
        if (dateTime.contains(" ")) {
            String[] parts = dateTime.split(" ");
            if (!parts[0].isEmpty() && parts[0] != null)
                parsedString = parsedString + dateReverse(parts[0]);
            if(parts[1]!=null && !parts[1].equals("0000-00-00 00:00:00"))
                getTime(parts[1]);

        }
        else if(!dateTime.isEmpty() && dateTime!=null)
            parsedString = dateReverse(dateTime);


        return parsedString;
    }
    String time = "";
    private void getTime(String time){
       time = time.substring(0, time.lastIndexOf(":"));
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
    private void gotoDetailsFragment() {
//        ClassScheduleDetailsFragment classScheduleDetailsFragment = new ClassScheduleDetailsFragment();
//        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.main_acitivity_container, classScheduleDetailsFragment, "classScheduleDetailsFragment").addToBackStack(null);
//        ;
//        transaction.commit();
    }

      /*
        Helpers - Pagination
   _________________________________________________________________________________________________
    */

    public void add(EventList r) {
        eventLists.add(r);
        notifyItemInserted(eventLists.size() - 1);
    }

    public void addAllData(List<EventList> moveResults) {
        for (EventList result : moveResults) {
            add(result);
        }

    }

    public void addAllNewData(List<EventList> moveResults) {
        eventLists.clear();
        eventLists.addAll(moveResults);
        notifyDataSetChanged();
    }

    public void clearList() {
        eventLists.clear();
    }


    public void remove(EventList r) {
        int position = eventLists.indexOf(r);
        if (position > -1) {
            eventLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new EventList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = eventLists.size() - 1;
        EventList result = getItem(position);

        if (result != null) {
            eventLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public EventList getItem(int position) {
        return eventLists.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(eventLists.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

}
