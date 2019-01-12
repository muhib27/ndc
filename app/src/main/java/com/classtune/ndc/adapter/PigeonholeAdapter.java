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
import android.widget.Toast;

//import com.bumptech.glide.DrawableRequestBuilder;
import com.classtune.ndc.R;
import com.classtune.ndc.activity.MainActivity;
import com.classtune.ndc.apiresponse.menu_api.UserPermission;
import com.classtune.ndc.apiresponse.pigeonhole_api.PHTask;
import com.classtune.ndc.fragment.InsTructorTaskAssignFragment;
import com.classtune.ndc.fragment.InstructorDetailsFragment;
import com.classtune.ndc.retrofit.RetrofitApiClient;
import com.classtune.ndc.utils.AppSharedPreference;
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

public class PigeonholeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;
    private static final int ADD = 5;


    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<PHTask> pigeonholeDataModelList;
    private List<PHTask> phTasks = new ArrayList<>();
    private Context context;
    PHTask editPHTask;
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
    public PigeonholeAdapter(Context context, PaginationAdapterCallback mCallback) {
        this.context = context;
        this.mCallback = mCallback;
        pigeonholeDataModelList = new ArrayList<>();
        //this.mOrderProcessCallback = orderProcessCallback;

    }

    public PigeonholeAdapter(Context context) {
        this.context = context;
        pigeonholeDataModelList = new ArrayList<>();
        uiHelper = new UIHelper((MainActivity)context);
    }

    public PigeonholeAdapter(Context context, ArrayList<PHTask> strList) {
        this.context = context;
        this.mCallback = mCallback;
        this.phTasks = strList;

    }


    public List<PHTask> getMovies() {
        return pigeonholeDataModelList;
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
                View viewItem = inflater.inflate(R.layout.pigeonhole_single_row, parent, false);
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        OrderListModel result = orderList.get(position); // Movie
        String phoneString = "";
        final Bundle bundle = new Bundle();
        switch (getItemViewType(position)) {
//            case HERO:
//                final HeroVH topItem = (HeroVH) holder;
//                topItem.cardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int next=0;
//                        if(position>4)
//                            next = 5;
//                        else
//                            next = 1;
//                        ArrayList<CategoryModel> childList = new ArrayList<CategoryModel>(orderList.subList(position, position+next));
//                        String str = new Gson().toJson(childList);
//                        bundle.putString("childList", str);
//                        gotoSingleNewsFragment(bundle);
//                    }
//                });
//
//                topItem.mMovieTitle.setText(result.getTitle().getRendered());
//                topItem.mMovieDesc.setText(android.text.Html.fromHtml(result.getExcerptModel().getRendered()).toString());
//                loadImage(result.getEmbedded().getFeatureMedia().get(0).get("source_url").getAsString()).into(topItem.mPosterImg);
//                break;
            case ITEM:
                final PigeonholeListItem itemHolder = (PigeonholeListItem) holder;
                int total = phTasks.size();
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemHolder.title.setText(pigeonholeDataModelList.get(position).getTitle());
                itemHolder.description.setText(pigeonholeDataModelList.get(position).getDescription());
                itemHolder.assign_date.setText(Html.fromHtml("Assign Date: " + "<font color = #3F86A0><strong>" + dateTimeParse(pigeonholeDataModelList.get(position).getCreatedAt()) + "<strong></font>"));
                if(pigeonholeDataModelList.get(position).getDueDate()!=null && !pigeonholeDataModelList.get(position).getDueDate().isEmpty())
                itemHolder.due_date.setText(Html.fromHtml("Due Date: " + "<font color=#3F86A0><strong>" + dateTimeParse(pigeonholeDataModelList.get(position).getDueDate()) + "<strong></font>"));
                else
                    itemHolder.due_date.setText("");
                if(pigeonholeDataModelList.get(position).getAttachmentId()!=null)
                    itemHolder.attachment.setVisibility(View.VISIBLE);
                else
                    itemHolder.attachment.setVisibility(View.INVISIBLE);
//                itemHolder.name.setText("Pizza");
//                itemHolder.quantity.setText("Total ietm 10");
                //itemHolder.itemNameLayout.removeAllViews();
                String customerName = "";
                int totalItem = 0;

//                itemHolder.dotMenu.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        PopupMenu popup = new PopupMenu(context, itemHolder.dotMenu);
//
//                        popup.inflate(R.menu.pigeonhole_cell_menu);
//
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.delete:
//
////                                        integerList.remove(holder.getAdapterPosition());
////                                        notifyItemRemoved(holder.getAdapterPosition());
//                                        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
//                                        return true;
//                                    case R.id.edit:
//
////                                        integerList.remove(holder.getAdapterPosition());
////                                        notifyItemRemoved(holder.getAdapterPosition());
//                                        Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
//                                        return true;
//                                }
//                                return false;
//                            }
//                        });
//
//                        popup.show();
//                    }
//                });
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
                        gotoInstructorDetailsFragment(pigeonholeDataModelList.get(position).getId());

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
                        initEditApi(pigeonholeDataModelList.get(position).getId(), position);
                        break;
                    case R.id.delete:
                        //Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                        callPigeonholeDeleteApi(pigeonholeDataModelList.get(position).getId(),position);
                        //CommonApiCall commonApiCall = new CommonApiCall(context);
                        //boolean b = commonA piCall.callPigeonholeDeleteApi(pigeonholeDataModelList.get(position).getId());
                        break;
                }
                return false;
            }
        });
    }


    public void callPigeonholeDeleteApi(String id, final int position) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(context, "No Connectivity", Toast.LENGTH_SHORT).show();

        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().pigeonholeDelete(id, AppSharedPreference.getApiKey())

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
                            pigeonholeDataModelList.remove(position);
                            notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "Something went wrong. Please try later", Toast.LENGTH_SHORT).show();
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

    private void initEditApi(String id, int pos) {
        editPHTask = pigeonholeDataModelList.get(pos);
        String i = editPHTask.getId();

        gotoInstructorTaskAssignFragment(editPHTask);
    }


    private void gotoInstructorTaskAssignFragment(PHTask editPHTask) {
        Bundle bundle=new Bundle();
//        bundle.putSerializable("phTask",editPHTask);
        bundle.putString("id", editPHTask.getId());

        InsTructorTaskAssignFragment insTructorTaskAssignFragment = new InsTructorTaskAssignFragment();
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        insTructorTaskAssignFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, insTructorTaskAssignFragment, "insTructorTaskAssignFragment").addToBackStack(null);
        transaction.commit();
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

    public void add(PHTask r) {
        pigeonholeDataModelList.add(r);
        notifyItemInserted(pigeonholeDataModelList.size() - 1);
    }

    public void addAllData(List<PHTask> pigeonholeDataModels) {
        for (PHTask result : pigeonholeDataModels) {
            add(result);
        }

    }

    public void addAllNewData(List<PHTask> moveResults) {
        pigeonholeDataModelList.clear();
        pigeonholeDataModelList.addAll(moveResults);
        notifyDataSetChanged();
    }

    public void clearList() {
        pigeonholeDataModelList.clear();
    }


    public void remove(PHTask r) {
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
        add(new PHTask());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pigeonholeDataModelList.size() - 1;
        PHTask result = getItem(position);

        if (result != null) {
            pigeonholeDataModelList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public PHTask getItem(int position) {
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
        private TextView description;
        private ImageView attachment;

        private TextView menuOption;
        private LinearLayout itemLayout;

        private TextView assign_date, due_date;
        ImageButton dotMenu;

        public PigeonholeListItem(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            assign_date = (TextView) itemView.findViewById(R.id.assign_date);
            due_date = (TextView) itemView.findViewById(R.id.due_date);
            attachment = itemView.findViewById(R.id.attachment);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.itemLayout);
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


    private void gotoInstructorDetailsFragment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        InstructorDetailsFragment instructorDetailsFragment = new InstructorDetailsFragment();
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        instructorDetailsFragment.setArguments(bundle);
        transaction.replace(R.id.main_acitivity_container, instructorDetailsFragment, "instructorDetailsFragment").addToBackStack(null);
        ///if viewpager not active
        //transaction.replace(R.id.main_acitivity_container, instructorDetailsFragment, "instructorDetailsFragment").addToBackStack(null);
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
