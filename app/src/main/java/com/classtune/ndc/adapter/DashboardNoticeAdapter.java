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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.bumptech.glide.DrawableRequestBuilder;
import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.apiresponse.NoticeApi.Notice;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.fragment.NoticeAddFragment;
import com.classtune.ndc.fragment.NoticeDetailsFragment;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
import com.classtune.ndc.utils.AppUtility;
import com.classtune.ndc.utils.NetworkConnection;
import com.classtune.ndc.utils.PaginationAdapterCallback;
import com.classtune.ndc.viewhelpers.UIHelper;
import com.google.gson.JsonElement;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


/**
 * Created by Muhib on 20/11/18.
 */

public class DashboardNoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // View Types
    private static final int ITEM = 0;
    private static final int ITEM_1 = 1;
    private static final int LOADING = 4;
    private static final int NOTICE = 2;
    private static final int ADD = 5;
    int viewparameter = 0;


    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<Notice> noticeModelList;
    private List<Notice> strList = new ArrayList<>();
    private Context context;
    Notice editNotice;
    UIHelper uiHelper;

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
    public DashboardNoticeAdapter(Context context, PaginationAdapterCallback mCallback) {
        this.context = context;
        this.mCallback = mCallback;
        noticeModelList = new ArrayList<>();
        //this.mOrderProcessCallback = orderProcessCallback;

    }

    public DashboardNoticeAdapter(Context context, int viewparameter) {
        this.context = context;
        noticeModelList = new ArrayList<>();
        this.viewparameter = viewparameter;
        uiHelper = new UIHelper((MainActivity)context);
    }

    public DashboardNoticeAdapter(Context context, ArrayList<Notice> strList) {
        this.context = context;
        this.mCallback = mCallback;
        this.strList = strList;

    }

    public void setData(List<Notice> list) {
        noticeModelList = list;


    }


    public List<Notice> getMovies() {
        return noticeModelList;
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
                View viewItem = inflater.inflate(R.layout.single_notice_row, parent, false);
                viewHolder = new NoticeListItem(viewItem);
                break;
            case ITEM_1:
                View viewItem_card = inflater.inflate(R.layout.single_notice_row, parent, false);
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
                itemHolder.orderTitle.setText(noticeModelList.get(position).getTitle());
                itemHolder.description.setText(noticeModelList.get(position).getDescription());
                if(noticeModelList.get(position).getIsImportant()!=null && noticeModelList.get(position).getIsImportant().equals("1") ){
                    itemHolder.colorView.setBackgroundColor(context.getResources().getColor(R.color.red));
                }
                itemHolder.publishDate.setText(Html.fromHtml("Publish Date: " + "<font color = #3F86A0><strong>" + AppUtility.getDateString(noticeModelList.get(position).getCreatedAt(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER) + "<strong></font>"));
//                itemHolder.name.setText("Pizza");
//                itemHolder.quantity.setText("Total ietm 10");
                //itemHolder.itemNameLayout.removeAllViews();
                String customerName = "";
                int totalItem = 0;

                UserPermission userPermission = AppSharedPreference.getUserPermission();
                if(userPermission.isTasksEdit() && userPermission.isTasksDelete())
                    itemHolder.dotMenu.setVisibility(View.VISIBLE);
                else
                    itemHolder.dotMenu.setVisibility(View.INVISIBLE);

                itemHolder.dotMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopupMenu(itemHolder.dotMenu, position);
                    }
                });

                itemHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //gotoOrderDetailsFragment(orderList.get(position).getId());
//                        String str = new Gson().toJson(orderList.get(position));
                        //bundle.putString("products", str);
//                        bundle.putString("order_id", orderList.get(position).getId());
                        gotoDetailsFragment(noticeModelList.get(position).getId());

                    }
                });
                break;
            case ITEM_1:
                final NoticeListItem itemHolder_ = (NoticeListItem) holder;
//                int total = strList.size();
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemHolder_.orderTitle.setText(noticeModelList.get(position).getTitle());
                itemHolder_.description.setText(noticeModelList.get(position).getDescription());
//                itemHolder.name.setText("Pizza");
//                itemHolder.quantity.setText("Total ietm 10");
                //itemHolder.itemNameLayout.removeAllViews();
//                String customerName = "";
//                int totalItem = 0;


                itemHolder_.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //gotoOrderDetailsFragment(orderList.get(position).getId());
//                        String str = new Gson().toJson(orderList.get(position));
                        //bundle.putString("products", str);
//                        bundle.putString("order_id", orderList.get(position).getId());
                        gotoDetailsFragment(noticeModelList.get(position).getId());

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
      //  return strList == null ? 0 : strList.size();
        return noticeModelList == null ? 0 : noticeModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
//

//            if (position == 23 && isLoadingAdded)
//                return LOADING;
//            else
//                return ADD;
//        } else {

                return ITEM;


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
        private TextView description;
        private TextView name, quantity, totalPay, totalPayText, orderDate; // displays "year | language"
        private ImageView itemImage;
        private ProgressBar mProgress;
        private TextView menuOption;
        private LinearLayout itemLayout;
        private LinearLayout itemNameLayout;
        private TextView publishDate;
        private TextView status, customerNameText, totalItemText, deliveryTime, delivery;

        public PigeonholeListItem(View itemView) {
            super(itemView);

            orderTitle = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
            publishDate = (TextView) itemView.findViewById(R.id.publishDate);


        }
    }

    protected class NoticeListItem extends RecyclerView.ViewHolder {
        private TextView orderTitle;
        private TextView description;
        private ImageButton dotMenu;
        private TextView name, quantity, totalPay, totalPayText, orderDate; // displays "year | language"
        private ImageView itemImage;
        private ProgressBar mProgress;
        private TextView menuOption;
        private LinearLayout itemLayout;
        private LinearLayout itemNameLayout;
        private TextView publishDate;
        private TextView status, customerNameText, totalItemText, deliveryTime, delivery;
        View colorView;

        public NoticeListItem(View itemView) {
            super(itemView);

            orderTitle = (TextView) itemView.findViewById(R.id.title);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
            description = (TextView) itemView.findViewById(R.id.description);
            publishDate = (TextView) itemView.findViewById(R.id.publishDate);
            colorView = (View) itemView.findViewById(R.id.view);
            dotMenu = itemView.findViewById(R.id.dot_menu);

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


    private void gotoDetailsFragment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        NoticeDetailsFragment noticeDetailsFragment = new NoticeDetailsFragment();
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        noticeDetailsFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, noticeDetailsFragment, "noticeDetailsFragment").addToBackStack(null);
        transaction.commit();
    }

      /*
        Helpers - Pagination
   _________________________________________________________________________________________________
    */

    public void add(Notice r) {
        noticeModelList.add(r);
        notifyItemInserted(noticeModelList.size() - 1);
    }

    public void addAllData(List<Notice> moveResults) {
        for (Notice result : moveResults) {
            add(result);
        }

    }

    public void addAllNewData(List<Notice> moveResults) {
        noticeModelList.clear();
        noticeModelList.addAll(moveResults);
        notifyDataSetChanged();
    }

    public void clearList() {
        noticeModelList.clear();
    }


    public void remove(Notice r) {
        int position = noticeModelList.indexOf(r);
        if (position > -1) {
            noticeModelList.remove(position);
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
        add(new Notice());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = noticeModelList.size() - 1;
        Notice result = getItem(position);

        if (result != null) {
            noticeModelList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Notice getItem(int position) {
        return noticeModelList.get(position);
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(noticeModelList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    PopupMenu popupMenu;

    private void showPopupMenu(View view, final int position) {

        // inflate menu
//        PopupMenu popup = new PopupMenu(view.getContext(),view );
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.pigeonhole_cell_menu, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
//        MenuPopupHelper menuPopupHelper = new MenuPopupHelper(context, (MenuBuilder)popup.getMenu(), view);
//        popup.show();
        Context wrapper = new ContextThemeWrapper(context, R.style.popupMenuStyle);
        popupMenu = new PopupMenu(wrapper, view);
        popupMenu.inflate(R.menu.pigeonhole_cell_menu);


// Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {

            popupMenu.show();
            return;
        }


        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.edit:
                        // Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                        initEditApi(noticeModelList.get(position).getId(), position);
                        break;
                    case R.id.delete:
                       // Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                       // CommonApiCall commonApiCall = new CommonApiCall(context);
                        callNoticeDeleteApi(noticeModelList.get(position).getId(),position);
                     //   boolean b = commonApiCall.callPigeonholeDeleteApi(noticeModelList.get(position).getId());
                        break;
                }
                return false;
            }
        });
    }

    private void initEditApi(String id, int pos) {
        editNotice = noticeModelList.get(pos);
        String i = editNotice.getId();
//
        gotoNoticeAddFragment(i);
    }

    private void gotoNoticeAddFragment(String id) {
        Bundle bundle=new Bundle();
        bundle.putString("id", id);

        NoticeAddFragment noticeAddFragment = new NoticeAddFragment();
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        noticeAddFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, noticeAddFragment, "noticeAddFragment").addToBackStack(null);
        transaction.commit();
    }


    public void callNoticeDeleteApi(String id, final int pos) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return ;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().noticeDelete(id, AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();


                        if(value.code() ==200) {

                            noticeModelList.remove(pos);
                            notifyDataSetChanged();
                        }


                    }


                    @Override
                    public void onError(Throwable e) {
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        uiHelper.dismissLoadingDialog();
                    }
                });

        return;
    }


}
