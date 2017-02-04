package common.selector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gunlei.app.R;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.gunlei.app.ui.util.StringUtil;
import com.gunlei.app.ui.util.ViewFinderUtil;


/**
 * 字符串单选Activity.
 * Usage:
 *                 startActivityForResult(StringSelectorActivity.createSelectorIntent(getActivity(),
 new String[]{"Java", "PHP", "Android"}, "PHP", ListView.CHOICE_MODE_SINGLE, "语言", "请选择您最擅长的语言", "设定好友可以查看的最擅长语言"
 ), SELECT_LANGUAGE);
 * Created by liuchangjiong on 15/5/31.
 */
public class StringSelectorActivity extends BaseTitleActivity {
    private static final String TAG = StringSelectorActivity.class.getSimpleName();
    public static final String ACTIVITY_TITLE = "ACTIVITY_TITLE";
    public static final String ITEMS = "ITEMS";
    public static final String SELECTED_STRING = "SELECTED_STRING";
    public static final String SELECTED_INDEX = "SELECTED_INDEX";
    public static final String SELECTION_MODE = "SELECTION_MODE";// Values of ListView.CHOICE_MODE_SINGLE
    public static final String INITIAL_SELECTION = "INITIAL_SELECTION";// 初识选择
    public static final String LIST_TITLE = "LIST_TITLE";// 列表区的标题
    public static final String LIST_DESCRIPTION = "LIST_DESCRIPTION";// 列表区的详细说明

    //@InjectView(R.id.sub_title)
    TextView subTitle;
    //@InjectView(R.id.item_list)
    ListView listView;
    //@InjectView(R.id.detail)
    TextView detail;


    private String selectedItem = "1";
    private String[] items = new String[]{"2", "1", "3"};
    private String initialSelection;
    private int selectionMode;// 选择模式

//    protected ListAdapter listAdapter = null;

    /**
     * 创建选择器动作。
     *
     * @param context          起始包
     * @param items            项目列表，不能为空
     * @param initialSelection 初始选择，可为空
     * @param selectionMode    选择模式, 不置顶为单选
     * @return 动作
     * @see ListView#CHOICE_MODE_SINGLE
     */
    public static Intent createSelectorIntent(Context context, String[] items, String initialSelection, int selectionMode, String title) {
        return createSelectorIntent(context,items, initialSelection, selectionMode, title, null, null);
    }

    /**
     * 创建选择器动作。
     *
     * @param context          起始包
     * @param items            项目列表，不能为空
     * @param initialSelection 初始选择，可为空
     * @param selectionMode    选择模式, 不置顶为单选
     * @return 动作
     * @see ListView#CHOICE_MODE_SINGLE
     */
    public static Intent createSelectorIntent(Context context, String[] items, String initialSelection,
                                              int selectionMode, String title, String listTitle, String listDescription) {
        Intent intent = new Intent(context, StringSelectorActivity.class);
        intent.putExtra(INITIAL_SELECTION, initialSelection);
        intent.putExtra(ITEMS, items);
        intent.putExtra(SELECTION_MODE, selectionMode);
        intent.putExtra(ACTIVITY_TITLE, title);
        intent.putExtra(LIST_TITLE, listTitle);
        intent.putExtra(LIST_DESCRIPTION, listDescription);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable(ITEMS, items);
//        bundle.putSerializable(INITIAL_SELECTION, initialSelection);
//        bundle.putInt(SELECTION_MODE, selectionMode);
//        intent.putExtras(bundle);

        return intent;
    }

    // 读取传递来的参数
    private void readIntentData() {
        String[] _items = getIntent().getStringArrayExtra(ITEMS);
        if (_items != null) {
            items = _items;
        }

        String _initialSelection = getIntent().getStringExtra(INITIAL_SELECTION);
        if (_initialSelection != null) {
            initialSelection = _initialSelection;
        }

        int _selectionMode = getIntent().getIntExtra(SELECTION_MODE, ListView.CHOICE_MODE_SINGLE);
        selectionMode = _selectionMode;

        String _title = getIntent().getStringExtra(ACTIVITY_TITLE);
        if (_title != null)
            setTitleText(_title);
        String _listTitle = getIntent().getStringExtra(LIST_TITLE);
        if (_listTitle != null) {
            subTitle.setText(_listTitle);
        } else {
            subTitle.setVisibility(View.INVISIBLE);
        }

        String _listDescription = getIntent().getStringExtra(LIST_DESCRIPTION);
        if (_listDescription != null) {
            detail.setText(_listDescription);
        } else {
            detail.setVisibility(View.INVISIBLE);
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.setLayoutParams(params);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_selector_string);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        listView = ViewFinderUtil.findViewById(this, R.id.item_list);
        subTitle = ViewFinderUtil.findViewById(this, R.id.sub_title);
        detail = ViewFinderUtil.findViewById(this, R.id.detail);

        readIntentData();

        listView.setChoiceMode(selectionMode);
//
//        listAdapter = new ListAdapter();
//        listAdapter.selectedIndex = 1;
        ArrayAdapter<String> directoryAdapter = new ArrayAdapter<String>(this, R.layout.selector_string_list_item, items);
//        listView.setAdapter(listAdapter);
        listView.setAdapter(directoryAdapter);
//        listView.setSelector(R.drawable.list_selector);
//        listView.setItemChecked(1, true);
//        listView.setSelection(1);
        setSelectedItem(initialSelection);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                CheckBox rdBtn = (CheckBox) ((RelativeLayout) view).getChildAt(0);
//                rdBtn.setChecked(!rdBtn.isChecked());
//                listAdapter.selectedIndex = position;
//                CheckableRelativeLayout curView = (CheckableRelativeLayout)view;
//                curView.toggle();

//                ToastUtil.show(StringSelectorActivity.this, listView.getCheckedItemPosition() + " getCheckedItemPosition ");
                ok();
            }
        });

//        setListViewHeightBasedOnChildren(listView);

//        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0 && items != null) {
//                    selectedItem = items[position];
//                }
//
//                ToastUtil.show(StringSelectorActivity.this, selectedItem);
//                ok();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selectedItem = null;
//                ok();
//            }
//        });
    }

    public String getSelectedItem() {
        if (listView.getCheckedItemPosition() >= 0) {
            selectedItem = items[listView.getCheckedItemPosition()];
        }

        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
        if (StringUtil.isEmpty(selectedItem)) {
            listView.clearChoices();
        } else if (items != null) {
            for (int i = 0; i < items.length; i++) {
                listView.setItemChecked(i, selectedItem.equals(items[i]));
            }
        }
    }

    public void ok() {
        Log.d(TAG, "Upload clicked, finishing activity");

        if (items == null) {
            Log.d(TAG, "No selection");
            finish();
        }

        Intent result = this.getIntent();
        result.putExtra(SELECTED_STRING, getSelectedItem());
        result.putExtra(SELECTED_INDEX, listView.getCheckedItemPosition());
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
//        ButterKnife.inject(this);
    }


    /**
     * customizing adapter.
     *
     * @author liuchangjiong
     */
    /*
    public class ListAdapter extends BaseAdapter {
        public int selectedIndex;

        public ListAdapter() {
        }

        public void updateList() {
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items == null? 0 : items.length;
        }

        @Override
        public Object getItem(int position) {
            return  items == null? "": items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String str = items[position];
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.selector_string_list_item, parent, false);
            Viewholder holder = (Viewholder) convertView.getTag();
            if (holder == null) {
                holder = new Viewholder();
                convertView.setTag(holder);
//                holder.radioButton = ViewFinderUtil.findViewById(convertView, R.id.radioButton);
            }

//            holder.radioButton.setChecked(position == selectedIndex);
//            holder.radioButton.setText(str);
//            holder.radioButton.setSelected(str.equals(selectedItem));
            return convertView;
        }

    }
    */

}
