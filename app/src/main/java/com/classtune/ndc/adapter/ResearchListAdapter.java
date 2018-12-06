package com.classtune.ndc.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.classtune.ndc.R;
import com.classtune.ndc.fragment.InstructorDetailsFragment;
import com.classtune.ndc.fragment.ResearchTopicListFragment;
import com.classtune.ndc.model.PigeonholeDataModel;
import com.classtune.ndc.model.ResearcherModel;
import com.classtune.ndc.utils.PaginationAdapterCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Muhib on 20/11/18.
 */

public class ResearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;
    private static final int ADD = 5;


    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<ResearcherModel> researcherModelList;
    private List<ResearcherModel> strList = new ArrayList<>();
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
    public ResearchListAdapter(Context context, PaginationAdapterCallback mCallback) {
        this.context = context;
        this.mCallback = mCallback;
        researcherModelList = new ArrayList<>();
        //this.mOrderProcessCallback = orderProcessCallback;

    }

    public ResearchListAdapter(Context context) {
        this.context = context;
        researcherModelList = new ArrayList<>();
    }

    public ResearchListAdapter(Context context, ArrayList<ResearcherModel> strList) {
        this.context = context;
        this.mCallback = mCallback;
        this.researcherModelList = strList;

    }

    public List<ResearcherModel> getMovies() {
        return researcherModelList;
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
                View viewItem = inflater.inflate(R.layout.research_single_row, parent, false);
                viewHolder = new PigeonholeListItem(viewItem);
                break;
//            case LOADING:
//                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
//                viewHolder = new LoadingVH(viewLoading);
//                break;
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        OrderListModel result = orderList.get(position); // Movie
        String phoneString = "";
        final Bundle bundle = new Bundle();
        switch (getItemViewType(position)) {

            case ITEM:
                final PigeonholeListItem itemHolder = (PigeonholeListItem) holder;
                int total = strList.size();
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemHolder.name.setText(researcherModelList.get(position).getName());
                itemHolder.email.setText(researcherModelList.get(position).getEmail());

//                itemHolder.orderTitle.setText(strList.get(position));
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
                        gotoITopicListFragment();

                    }
                });
                break;


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
       // return strList == null ? 0 : strList.size();
        return researcherModelList == null ? 0 : researcherModelList.size();
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
        return (position == researcherModelList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
//        }
    }


//
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

    public void add(ResearcherModel r) {
        researcherModelList.add(r);
        notifyItemInserted(researcherModelList.size() - 1);
    }

    public void addAllData(List<ResearcherModel> moveResults) {
        for (ResearcherModel result : moveResults) {
            add(result);
        }

    }

    public void addAllNewData(List<ResearcherModel> moveResults) {
        researcherModelList.clear();
        researcherModelList.addAll(moveResults);
        notifyDataSetChanged();
    }

    public void clearList() {
        researcherModelList.clear();
    }


    public void remove(ResearcherModel r) {
        int position = researcherModelList.indexOf(r);
        if (position > -1) {
            researcherModelList.remove(position);
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
        add(new ResearcherModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = researcherModelList.size() - 1;
        ResearcherModel result = getItem(position);

        if (result != null) {
            researcherModelList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ResearcherModel getItem(int position) {
        return researcherModelList.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(researcherModelList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }






    protected class PigeonholeListItem extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView email;
      //  private TextView name, quantity, totalPay, totalPayText, orderDate; // displays "year | language"
        private ImageView itemImage;
        private ProgressBar mProgress;
        private TextView menuOption;
        private LinearLayout itemLayout;
        private LinearLayout itemNameLayout;
        private TextView rejected;
        private TextView status, customerNameText, totalItemText, deliveryTime, delivery;

        public PigeonholeListItem(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
            email = (TextView) itemView.findViewById(R.id.email);




        }
    }




    private String dateTimeParse(String dateTime, String timeToAdd) {
        String[] timeToAddParts = timeToAdd.split(" ");
        String parsedString = "";
        if (dateTime.contains("T")) {
            String[] parts = dateTime.split("T");
            if (!parts[0].isEmpty() && parts[0] != null)
                parsedString = parsedString + dateReverse(parts[0], parts[1], Integer.valueOf(timeToAddParts[0]));
//            if(!parts[1].isEmpty() && parts[1]!=null)
//                parsedString = parsedString + "  "+ parts[1].substring(0, parts[1].lastIndexOf(":"));
        }

        return parsedString;
    }

    public static String dateReverse(String duedate, String times, int timeToAdd) {

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
            c.add(Calendar.MINUTE, timeToAdd);
            String tt = c.getTime().toString();


            // result = ( new SimpleDateFormat( "dd-MM-yyyy' 'HH:mm" ) ).format( c.getTime()).toString();;
            result = format1.format(c.getTime());
            String[] lastParse;
            if (result.contains("-")) {
                try {
                    lastParse = result.split("-");
                    returnResult = lastParse[0] + " at " + lastParse[1];
                }catch (Exception e) {

                }
            }

        }
        return returnResult;
    }

    private void gotoITopicListFragment() {
        ResearchTopicListFragment researchTopicListFragment = new ResearchTopicListFragment();
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_acitivity_container, researchTopicListFragment, "researchTopicListFragment").addToBackStack(null);;
        transaction.commit();
    }

}
