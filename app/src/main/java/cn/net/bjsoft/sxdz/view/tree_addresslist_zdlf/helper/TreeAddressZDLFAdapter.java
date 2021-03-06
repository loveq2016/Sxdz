package cn.net.bjsoft.sxdz.view.tree_addresslist_zdlf.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import cn.net.bjsoft.sxdz.R;
import cn.net.bjsoft.sxdz.utils.AddressUtils;
import cn.net.bjsoft.sxdz.view.CircleImageView;


public class TreeAddressZDLFAdapter<T> extends TreeListViewAdapter<T> {

    private BitmapUtils bitmapUtils;
    private Context context;

    public TreeAddressZDLFAdapter(ListView mTree
            , Context context
            , List<T> datas
            , int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
        this.context = context;
        bitmapUtils = new BitmapUtils(context, AddressUtils.img_cache_url);//初始化头像
        bitmapUtils.configDefaultLoadingImage(R.drawable.tab_me_n);//初始化头像
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.tab_me_n);//初始化头像
    }

    @Override
    public View getConvertView(final TreeNode node, int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_address_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.parent_ll = (LinearLayout) convertView
                    .findViewById(R.id.item_address_list_parent);
            viewHolder.child_ll = (LinearLayout) convertView
                    .findViewById(R.id.item_address_list_child);
            viewHolder.parent_icon = (ImageView) convertView
                    .findViewById(R.id.item_address_list_parent_icon);
            viewHolder.parent_label = (TextView) convertView
                    .findViewById(R.id.item_address_list_parent_name);
            viewHolder.child_icon = (CircleImageView) convertView
                    .findViewById(R.id.item_address_list_child_icon);
            viewHolder.child_name = (TextView) convertView
                    .findViewById(R.id.item_address_list_child_name);
            viewHolder.child_num = (TextView) convertView
                    .findViewById(R.id.item_address_list_child_number);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (node.getAddressDeptsBean() != null) {//空的话为公司

            if (node.getAddressDeptsBean().positionsBean != null) {//空的话为部门
                viewHolder.parent_ll.setVisibility(View.GONE);
                viewHolder.child_ll.setVisibility(View.VISIBLE);
                String avatar_url = "";
                if (node.getAddressDeptsBean().positionsBean.employee.user != null) {
                    //防止后端给的数据是残疾的--要判空
                    avatar_url = node.getAddressDeptsBean().positionsBean.employee.user.avatar;
                }
                bitmapUtils.display(viewHolder.child_icon, avatar_url);
                //x.image().bind(viewHolder.child_icon, node.getAvatar_url());
                viewHolder.child_name.setText(node.getName());
                viewHolder.child_num.setText(node.getAddressDeptsBean().positionsBean.employee.phone);

            }
            else {
                viewHolder.parent_ll.setVisibility(View.VISIBLE);
                viewHolder.child_ll.setVisibility(View.GONE);
                viewHolder.parent_label.setText(node.getName());
            }
        } else {
            viewHolder.parent_ll.setVisibility(View.VISIBLE);
            viewHolder.child_ll.setVisibility(View.GONE);
            viewHolder.parent_label.setText(node.getName());
        }


//
//        if (node.getCompanysBean().deptsBean == null) {//空的话就是部门
//
//            viewHolder.parent_ll.setVisibility(View.VISIBLE);
//            viewHolder.child_ll.setVisibility(View.GONE);
//            viewHolder.parent_label.setText(node.getName());
//
//        } else {//员工
//            viewHolder.parent_ll.setVisibility(View.GONE);
//            viewHolder.child_ll.setVisibility(View.VISIBLE);
//
//            String avatar_url = "";
//            if (node.getCompanysBean().deptsBean.positionsBean.employee.user != null) {
//                //防止后端给的数据是残疾的--要判空
//                avatar_url = node.getCompanysBean().deptsBean.positionsBean.employee.user.avatar;
//            }
//
//            bitmapUtils.display(viewHolder.child_icon, avatar_url);
//            //x.image().bind(viewHolder.child_icon, node.getAvatar_url());
//            viewHolder.child_name.setText(node.getName());
//            viewHolder.child_num.setText(node.getCompanysBean().deptsBean.positionsBean.employee.phone);
//        }
//

        if (node.getIcon() == -1) {
            viewHolder.parent_icon.setVisibility(View.INVISIBLE);
            //x.image().bind(viewHolder.icon, "http://192.168.1.119:8080/android/form/form_1.jpg");
        } else {
            viewHolder.parent_icon.setVisibility(View.VISIBLE);
            viewHolder.parent_icon.setImageResource(node.getIcon());
        }

        return convertView;
    }

    private final class ViewHolder {
        public LinearLayout parent_ll;
        public ImageView parent_icon;
        public TextView parent_label;

        public LinearLayout child_ll;
        public CircleImageView child_icon;
        public TextView child_name, child_num;

    }

}
