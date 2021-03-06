package cn.net.bjsoft.sxdz.activity;

import android.os.Bundle;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;

import cn.net.bjsoft.sxdz.R;
import cn.net.bjsoft.sxdz.fragment.BaseFragment;
import cn.net.bjsoft.sxdz.fragment.TestFragment_1;
import cn.net.bjsoft.sxdz.fragment.bartop.message.task.TaskDetailFragment;
import cn.net.bjsoft.sxdz.fragment.bartop.message.task.TopAddTaskAddressListFragment;
import cn.net.bjsoft.sxdz.fragment.bartop.message.task.TopAddTaskFragment;
import cn.net.bjsoft.sxdz.fragment.bartop.message.task.TopTaskUnderlingDetailFragment;
import cn.net.bjsoft.sxdz.fragment.zdlf.KnowledgeItemZDLFFragment;
import cn.net.bjsoft.sxdz.fragment.zdlf.KnowledgeNewZDLFFragment;
import cn.net.bjsoft.sxdz.fragment.zdlf.MineAddressListFragment;
import cn.net.bjsoft.sxdz.fragment.zdlf.MineAddressListSearchResultFragment;
import cn.net.bjsoft.sxdz.fragment.zdlf.ResettingPasswordZDLFFragment;

/**
 * Created by Zrzc on 2017/3/20.
 */
@ContentView(R.layout.activity_empty)
public class EmptyActivity extends BaseActivity {
//    @ViewInject(R.id.activity_empty_root)
//    private FrameLayout root;

    private String fragment_name = "";
    private BaseFragment fragment;


    @Override
    protected void onStart() {
        super.onStart();

        fragment_name = getIntent().getStringExtra("fragment_name");

        if (fragment_name != null) {
            if (!fragment_name.equals("")) {
                Bundle bundle = new Bundle();
                if (fragment_name.equals("knowledge_item")) {

                    Bundle b = getIntent().getBundleExtra("knowledge_item_bundle");
                    bundle.putBundle("knowledge_item_bundle", b);

                    fragment = new KnowledgeItemZDLFFragment();//中电联发知识详情条目
                    LogUtil.e("Fragment 的值为" + fragment_name);
                } else if (fragment_name.equals("addressList")) {
                    fragment = new MineAddressListFragment();                   //中电联发联系人条目
                    bundle.putBundle("organization_url", getIntent().getBundleExtra("organization_url"));
                    LogUtil.e("Fragment 的值为" + fragment_name);
                } else if (fragment_name.equals("mine_zdlf_address_search")) {//中电联发搜索联系人结果
                    fragment = new MineAddressListSearchResultFragment();
                    Bundle b = getIntent().getBundleExtra("address_list_search_result_bundle");
                    bundle.putBundle("address_list_search_result_bundle", b);
                } else if (fragment_name.equals("task_detail")) {//中电联发--任务详情
                    fragment = new TaskDetailFragment();
                    Bundle b = getIntent().getBundleExtra("isEdited");
                    bundle.putBundle("isEdited", b);
                } else if (fragment_name.equals("TopAddTaskFragment")) {
                    fragment = new TopAddTaskFragment();
                } else if (fragment_name.equals("resetting_password")) {
                    fragment = new ResettingPasswordZDLFFragment();//重置密码
                }
                else if (fragment_name.equals("KnowledgeNewZDLFFragment")) {
                    fragment = new KnowledgeNewZDLFFragment();
                }
                else if (fragment_name.equals("TopTaskUnderlingDetailFragment")) {
                    fragment = new TopTaskUnderlingDetailFragment();
                }
                else if (fragment_name.equals("TestFragment_1")) {
                    fragment = new TestFragment_1();
                }
                else if (fragment_name.equals("TopAddTaskAddressListFragment")) {
                    fragment = new TopAddTaskAddressListFragment();
                }
                bundle.putString("json", "");
                fragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_empty_root
                                , fragment
                                , fragment_name)
                        .commit();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
