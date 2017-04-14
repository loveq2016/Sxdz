package cn.net.bjsoft.sxdz.adapter.zdlf;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import cn.net.bjsoft.sxdz.R;
import cn.net.bjsoft.sxdz.view.tree_task_underling.helper.NodeTaskUnderling;
import cn.net.bjsoft.sxdz.view.tree_task_underling.helper.TreeTaskListViewAdapter;

public class TaskUnderlingTreeAdapter<T> extends TreeTaskListViewAdapter<T> {

    private BitmapUtils bitmapUtils;
    private Context context;

    public TaskUnderlingTreeAdapter(ListView mTree
            , Context context
            , List<T> datas
            , int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
        this.context = context;
    }

    @Override
    public View getConvertView(NodeTaskUnderling node, int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_address_list, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.background = (FrameLayout) convertView
                    .findViewById(R.id.item_task_underling_number_background);
            viewHolder.department = (TextView) convertView
                    .findViewById(R.id.item_task_underling_department);
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.item_task_underling_label);
            viewHolder.number = (TextView) convertView
                    .findViewById(R.id.item_task_underling_number);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.department.setText(node.getDepartment());
        viewHolder.label.setText(node.getName());
        if (!(Integer.parseInt(node.getTask_num())<1)) {
            viewHolder.background.setVisibility(View.VISIBLE);
            viewHolder.number.setText(node.getTask_num());
        }
        else {
            viewHolder.background.setVisibility(View.GONE);
        }


        return convertView;
    }

    private final class ViewHolder {
        public FrameLayout background;
        public TextView department
                ,label
                ,number;
    }

}
