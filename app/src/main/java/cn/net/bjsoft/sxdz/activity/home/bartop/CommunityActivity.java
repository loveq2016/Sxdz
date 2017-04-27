package cn.net.bjsoft.sxdz.activity.home.bartop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.net.bjsoft.sxdz.R;
import cn.net.bjsoft.sxdz.activity.BaseActivity;
import cn.net.bjsoft.sxdz.bean.DatasBean;
import cn.net.bjsoft.sxdz.bean.PushBean;
import cn.net.bjsoft.sxdz.fragment.BaseFragment;
import cn.net.bjsoft.sxdz.utils.GsonUtil;
import cn.net.bjsoft.sxdz.utils.function.InitFragmentUtil;

/**
 * 互助页面
 * |直播
 * |帮助
 * |建议
 * |报错
 * |聊天-社区
 * Created by Zrzc on 2017/1/10.
 */

@ContentView(R.layout.activity_communtiy)
public class CommunityActivity extends BaseActivity {
    @ViewInject(R.id.community_content)
    private FrameLayout content;

    @ViewInject(R.id.community_bar)
    private LinearLayout bar;


    //直播
    @ViewInject(R.id.community_live)
    private RelativeLayout live;
    @ViewInject(R.id.community_live_icon)
    private ImageView live_icon;
    @ViewInject(R.id.community_live_text)
    private TextView live_text;
    @ViewInject(R.id.community_live_num)
    private TextView live_num;
    @ViewInject(R.id.community_live_img)
    private ImageView live_img;

    //帮助
    @ViewInject(R.id.community_help)
    private RelativeLayout help;
    @ViewInject(R.id.community_help_icon)
    private ImageView help_icon;
    @ViewInject(R.id.community_help_text)
    private TextView help_text;
    @ViewInject(R.id.community_help_num)
    private TextView help_num;
    @ViewInject(R.id.community_help_img)
    private ImageView help_img;


    //建议
    @ViewInject(R.id.community_proposal)
    private RelativeLayout proposal;
    @ViewInject(R.id.community_proposal_icon)
    private ImageView proposal_icon;
    @ViewInject(R.id.community_proposal_text)
    private TextView proposal_text;
    @ViewInject(R.id.community_proposal_num)
    private TextView proposal_num;
    @ViewInject(R.id.community_proposal_img)
    private ImageView proposal_img;

    //解惑
    @ViewInject(R.id.community_disabuse)
    private RelativeLayout disabuse;
    @ViewInject(R.id.community_disabuse_icon)
    private ImageView disabuse_icon;
    @ViewInject(R.id.community_disabuse_text)
    private TextView disabuse_text;
    @ViewInject(R.id.community_disabuse_num)
    private TextView disabuse_num;
    @ViewInject(R.id.community_disabuse_img)
    private ImageView disabuse_img;

    //社区
    @ViewInject(R.id.community_community)
    private RelativeLayout community;
    @ViewInject(R.id.community_community_icon)
    private ImageView community_icon;
    @ViewInject(R.id.community_community_text)
    private TextView community_text;
    @ViewInject(R.id.community_community_num)
    private TextView community_num;
    @ViewInject(R.id.community_community_img)
    private ImageView community_img;

    private HashMap<String, Integer> pushNum;
    private int mTrain,mKnowledge,mProposal,mBug,mCommunity;
    /**
     * 广播
     */
    //private MyReceiver receiver = new MyReceiver();


    private ArrayList<RelativeLayout> mBarList;
    private ArrayList<BaseFragment> mFragmentsList;
    private String mJson = "";
    private DatasBean mDatasBean;
    private DatasBean.PushdataDao pushdataDao;

    private String openTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mFragmentsList = new ArrayList<BaseFragment>();
        mJson = getIntent().getStringExtra("json");
        openTag = getIntent().getStringExtra("opentag");
        LogUtil.e("community接收到的opentag为====="+openTag);
        mDatasBean = GsonUtil.getDatasBean(mJson);
        pushdataDao = mDatasBean.data.pushdata;
        mBarList = new ArrayList<>();

        /**
         * 注册广播
         */
        //registerReceiver(receiver, new IntentFilter("cn.net.bjsoft.sxdz.community"));

        initData();
        setView();
    }

    //TODO 因为Activity每次执行，不管是在前台后台，可见不可见，onStart是必经之路，所以将推送的数据在这里显示最合理
    @Override
    protected void onStart() {

        super.onStart();
        pushNum = app.getmPushNum();

        mTrain = pushNum.get("train");
        mKnowledge = pushNum.get("knowledge");
        mProposal = pushNum.get("proposal");
        mBug = pushNum.get("bug");
        mCommunity = pushNum.get("community");

        setPushNumber(mTrain+"", mKnowledge+"", mProposal+"", mBug+"", mCommunity+"");
        LogUtil.e("社区页面===="+pushNum.get("proposal").toString());
        LogUtil.e("main==onStart");
    }

    public void setPushNumber(String trainNum,String knowledgeNum,String proposalNum,String bugNum,String communityNum){
       //直播
        if (Integer.parseInt(trainNum)>0) {
            live_num.setText(trainNum);
            live_num.setVisibility(View.VISIBLE);
        } else {
            live_num.setVisibility(View.INVISIBLE);
        }
        LogUtil.e("社区页面===="+pushNum.get("proposal").toString());
        //帮助
        if (Integer.parseInt(knowledgeNum)>0) {
            help_num.setText(knowledgeNum);
            help_num.setVisibility(View.VISIBLE);
        } else {
            help_num.setVisibility(View.INVISIBLE);
        }
        //建议
        if (Integer.parseInt(proposalNum)>0) {
            LogUtil.e("设置消息===" +  pushNum.get("proposal").toString());
            proposal_num.setText(proposalNum);
            proposal_num.setVisibility(View.VISIBLE);
        } else {
            proposal_num.setVisibility(View.INVISIBLE);
        }
        //解惑报错
        if (Integer.parseInt(bugNum)>0) {
            disabuse_num.setText(bugNum);
            disabuse_num.setVisibility(View.VISIBLE);
        } else {
            disabuse_num.setVisibility(View.INVISIBLE);
        }
        //社区
        if (Integer.parseInt(bugNum)>0) {
            community_num.setText(communityNum);
            community_num.setVisibility(View.VISIBLE);
        } else {
            community_num.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mFragmentsList = InitFragmentUtil.getCommunityFragments(mJson);
    }

    /**
     * 显示数据
     */
    private void setView() {
        setCommunityVisibility();

        //如果没有发送消息 的任务，那么就让他显示默认的
        if (openTag.equals("") || openTag.equals("defult")) {
            int seclect = mFragmentsList.size() / 2 + mFragmentsList.size() % 2 - 1;
            //onClick(mBarList.get(seclect));//默认显示中间
            onClick(mBarList.get(0));//默认显示第一个
        } else {
            //onClick(mBarList.get(openTag));
            for (int i = 0; i < mFragmentsList.size(); i++) {
//            MyToast.showShort(this,"是否为：："+(v.getTag().toString().equals(mBottonFragmentList.get(i).getArguments().get("tag").toString())));
                //字符串判定   一定要用equals！！！
                if (openTag.equals(mFragmentsList.get(i).getArguments().get("tag").toString())) {
                    onClick(mBarList.get(i));
                }
            }
        }
    }

    /**
     * 设置Community按钮的显示与否
     */
    private void setCommunityVisibility() {
        {
            live.setVisibility(View.GONE);
            live_img.setVisibility(View.GONE);

            help.setVisibility(View.GONE);
            help_img.setVisibility(View.GONE);

            proposal.setVisibility(View.GONE);
            proposal_img.setVisibility(View.GONE);

            disabuse.setVisibility(View.GONE);
            disabuse_img.setVisibility(View.GONE);

            community.setVisibility(View.GONE);
            community_img.setVisibility(View.GONE);
        }
        for (int i = 0; i < mFragmentsList.size(); i++) {

            if ((mFragmentsList.get(i).getArguments().get("tag").toString()).equals((live.getTag().toString()))) {
                live.setVisibility(View.VISIBLE);
                live_img.setVisibility(View.VISIBLE);
                mBarList.add(live);
            } else if ((mFragmentsList.get(i).getArguments().get("tag").toString()).equals((help.getTag().toString()))) {
                help.setVisibility(View.VISIBLE);
                help_img.setVisibility(View.VISIBLE);
                mBarList.add(help);
            } else if ((mFragmentsList.get(i).getArguments().get("tag").toString()).equals((proposal.getTag().toString()))) {
                proposal.setVisibility(View.VISIBLE);
                proposal_img.setVisibility(View.VISIBLE);
                mBarList.add(proposal);
            } else if ((mFragmentsList.get(i).getArguments().get("tag").toString()).equals((disabuse.getTag().toString()))) {
                disabuse.setVisibility(View.VISIBLE);
                disabuse_img.setVisibility(View.VISIBLE);
                mBarList.add(disabuse);
            } else if ((mFragmentsList.get(i).getArguments().get("tag").toString()).equals((community.getTag().toString()))) {
                community.setVisibility(View.VISIBLE);
                community_img.setVisibility(View.VISIBLE);
                mBarList.add(community);
            }
        }

        //如果只有一个页面，那么让底部栏隐藏掉
        if (mFragmentsList.size()==1) {
            bar.setVisibility(View.GONE);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 点击事件
     *
     * @param v
     */
    @Event(type = View.OnClickListener.class,
            value = {R.id.community_live, R.id.community_help, R.id.community_proposal, R.id.community_disabuse, R.id.community_community})
    private void onClick(View v) {

        //MyToast.showShort(context, "点击了" + v.toString());

        for (int i = 0; i < mFragmentsList.size(); i++) {
//            MyToast.showShort(this,"是否为：："+(v.getTag().toString().equals(mBottonFragmentList.get(i).getArguments().get("tag").toString())));
            //字符串判定   一定要用equals！！！
            if (v.getTag().toString().equals(mFragmentsList.get(i).getArguments().get("tag").toString())) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.community_content, mFragmentsList.get(i), "COMMUNITY")
                        .commit();
            }
        }
        setDefault();
        switch (v.getId()) {

            case R.id.community_live:
                live_icon.setImageResource(R.drawable.aid_tab_btn_live_s);
                live_text.setTextColor(getResources().getColor(R.color.blue));
                app.reFreshCommunityPushNumList("train",0-mTrain);
                break;
            case R.id.community_help:
                help_icon.setImageResource(R.drawable.aid_tab_btn_help_s);
                help_text.setTextColor(getResources().getColor(R.color.blue));
                help_num.setText("");
                help_num.setVisibility(View.INVISIBLE);
                app.reFreshCommunityPushNumList("knowledge",0-mKnowledge);
                break;
            case R.id.community_proposal:
                proposal_icon.setImageResource(R.drawable.aid_tab_btn_adcise_s);
                proposal_text.setTextColor(getResources().getColor(R.color.blue));
                proposal_num.setText("");
                proposal_num.setVisibility(View.INVISIBLE);
                app.reFreshCommunityPushNumList("proposal",0-mProposal);
                break;
            case R.id.community_disabuse:
                disabuse_icon.setImageResource(R.drawable.aid_tab_btn_bug_s);
                disabuse_text.setTextColor(getResources().getColor(R.color.blue));
                disabuse_num.setText("");
                disabuse_num.setVisibility(View.INVISIBLE);
                app.reFreshCommunityPushNumList("bug",0-mBug);
                break;
            case R.id.community_community:
                community_icon.setImageResource(R.drawable.aid_tab_btn_live_s);
                community_text.setTextColor(getResources().getColor(R.color.blue));
                community_num.setText("");
                community_num.setVisibility(View.INVISIBLE);
                app.reFreshCommunityPushNumList("community",0-mCommunity);
                break;
        }
    }

    /**
     * 设置底部未选中的图片为默认颜色
     */
    private void setDefault() {
        live_icon.setImageResource(R.drawable.aid_tab_btn_live_n);
        live_text.setTextColor(getResources().getColor(R.color.gray));

        help_icon.setImageResource(R.drawable.aid_tab_btn_help_n);
        help_text.setTextColor(getResources().getColor(R.color.gray));

        proposal_icon.setImageResource(R.drawable.aid_tab_btn_adcise_n);
        proposal_text.setTextColor(getResources().getColor(R.color.gray));

        disabuse_icon.setImageResource(R.drawable.aid_tab_btn_bug_n);
        disabuse_text.setTextColor(getResources().getColor(R.color.gray));

        community_icon.setImageResource(R.drawable.aid_tab_btn_live_n);
        community_text.setTextColor(getResources().getColor(R.color.gray));
    }


    /**
     * 广播接收器
     */
    public class MyReceiver extends BroadcastReceiver {
        /**
         * 接收广播
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String pushJson = intent.getStringExtra("pushjson");
            PushBean bean = GsonUtil.getPushBean(pushJson);
            //LogUtil.e("社区接收到了广播@@@@@,数据为===" + pushJson);


            int approve = bean.approve;
            int bug = bean.bug;
            int community = bean.community;
            int crm = bean.crm;
            int knowledge = bean.knowledge;
            int message = bean.message;
            int myself = bean.myself;
            int payment = bean.payment;
            int proposal = bean.proposal;
            int scan = bean.scan;
            int shoot = bean.shoot;
            int signin = bean.signin;
            int task = bean.task;
            int train = bean.train;
            DatasBean.ToolbarDao toolbarDao = mDatasBean.data.toolbar;

            if (toolbarDao.train){
                app.reFreshCommunityPushNumList("train", train);
            }
            if (toolbarDao.knowledge){
                app.reFreshCommunityPushNumList("knowledge", knowledge);
            }
            if (toolbarDao.proposal){
                app.reFreshCommunityPushNumList("proposal", proposal);
            }
            if (toolbarDao.bug){
                app.reFreshCommunityPushNumList("bug", bug);
            }
            //暂时没有社区
            if (toolbarDao.community){
                app.reFreshCommunityPushNumList("community", community);
            }


            if (toolbarDao.scan){
                app.reFreshFunctionPushNumList("scan", scan);
            }
            if (toolbarDao.shoot){
                app.reFreshFunctionPushNumList("shoot", shoot);
            }
            if (toolbarDao.signin){
                app.reFreshFunctionPushNumList("signin", signin);
            }
            if (toolbarDao.payment.size()>0){
                app.reFreshFunctionPushNumList("payment", payment);
            }

            if (toolbarDao.message){
                app.reFreshMessagePushNumList("message", message);
            }
            if (toolbarDao.task){
                app.reFreshMessagePushNumList("task", task);
            }
            if (toolbarDao.crm){
                app.reFreshMessagePushNumList("crm", crm);
            }
            if (toolbarDao.approve){
                app.reFreshMessagePushNumList("approve", approve);
            }

            if (toolbarDao.myself){
                app.reFreshUesrPushNumList("myself",myself);
            }

            pushNum = app.getmPushNum();
            setPushNumber(pushNum.get("train").toString(), pushNum.get("knowledge").toString(), pushNum.get("proposal").toString(), pushNum.get("bug").toString(),pushNum.get("community").toString());
            LogUtil.e("设置社区页面消息===" +  pushNum.get("proposal").toString());
            //setPushNumber(comm+"", fun+"", mess+"", user+"");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(receiver);
    }
}
