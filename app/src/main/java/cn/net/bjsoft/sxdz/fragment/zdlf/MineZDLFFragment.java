package cn.net.bjsoft.sxdz.fragment.zdlf;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.net.bjsoft.sxdz.R;
import cn.net.bjsoft.sxdz.activity.EmptyActivity;
import cn.net.bjsoft.sxdz.activity.welcome.SplashActivity;
import cn.net.bjsoft.sxdz.app_utils.HttpPostUtils;
import cn.net.bjsoft.sxdz.bean.app.AppBean;
import cn.net.bjsoft.sxdz.bean.app.LoginBean;
import cn.net.bjsoft.sxdz.bean.app.user.UserBean;
import cn.net.bjsoft.sxdz.bean.app.user.UserOrganizationBean;
import cn.net.bjsoft.sxdz.dialog.PickerScrollViewPopupWindow;
import cn.net.bjsoft.sxdz.fragment.BaseFragment;
import cn.net.bjsoft.sxdz.utils.AddressUtils;
import cn.net.bjsoft.sxdz.utils.Constants;
import cn.net.bjsoft.sxdz.utils.GsonUtil;
import cn.net.bjsoft.sxdz.utils.MD5;
import cn.net.bjsoft.sxdz.utils.MyBase64;
import cn.net.bjsoft.sxdz.utils.MyBitmapUtils;
import cn.net.bjsoft.sxdz.utils.MyToast;
import cn.net.bjsoft.sxdz.utils.SPUtil;
import cn.net.bjsoft.sxdz.utils.UrlUtil;
import cn.net.bjsoft.sxdz.utils.function.PhotoOrVideoUtils;
import cn.net.bjsoft.sxdz.view.CircleImageView;
import cn.net.bjsoft.sxdz.view.WindowRecettingPasswordView;
import cn.net.bjsoft.sxdz.view.picker_scroll_view.Pickers;

import static cn.net.bjsoft.sxdz.utils.AddressUtils.http_shuxinyun_url;


/**
 * 裕隆亚东药业的我的页面   - -   我的页面
 * Created by 靳宁宁 on 2017/3/8.
 */
@ContentView(R.layout.fragment_mine_zdlf)
public class MineZDLFFragment extends BaseFragment {
    @ViewInject(R.id.title_back)
    private ImageView back;//返回
    @ViewInject(R.id.title_title)
    private TextView title;//标题

    @ViewInject(R.id.mine_zdlf_name)
    private TextView name;
    @ViewInject(R.id.mine_zdlf_icon)
    private CircleImageView avatar;
    @ViewInject(R.id.mine_zdlf_company)
    private TextView company;
    @ViewInject(R.id.mine_zdlf_department)
    private TextView department;

    private PickerScrollViewPopupWindow pickerPopupWindow;
    private ArrayList<Pickers> pickersCacheList;
    private ArrayList<Pickers> pickersItemList;
    private int pickerSelecect = 0;

    private WindowRecettingPasswordView passwordView;
    private Dialog dialog;
    private String password = "";
    private String old_password = "";


    //    private DatasBean mDatasBean = null;
//    private DatasBean.UserDao mUserDao;
    private AppBean appBean;
    private LoginBean loginBean;
    private UserBean userBean;
    private UserOrganizationBean userOrganizationBean;

    private BitmapUtils bitmapUtils;

    @Override
    public void initData() {
        title.setText("我的");


        if (pickersCacheList == null) {
            pickersCacheList = new ArrayList<>();
        }
        pickersCacheList.clear();
        if (pickersItemList == null) {
            pickersItemList = new ArrayList<>();
        }
        pickersItemList.clear();

        setPickers();
        department.setText(pickersItemList.get(pickerSelecect).getShowConetnt());
        pickerPopupWindow = new PickerScrollViewPopupWindow();
        pickerPopupWindow.setOnData(new PickerScrollViewPopupWindow.OnGetData() {
            @Override
            public void onDataCallBack(int select) {
                //pickerSelecect = Integer.parseInt(pickers.getShowId());
                pickerSelecect = select;
                department.setText(pickersItemList.get(pickerSelecect).getShowConetnt());
            }
        });

        bitmapUtils = new BitmapUtils(getActivity(), AddressUtils.img_cache_url);
        bitmapUtils.configDefaultLoadingImage(R.drawable.get_back_passwoed);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.get_back_passwoed);

        getUserData();
    }


    private void getUserData() {
        showProgressDialog();
        LogUtil.e("json" + mJson);
        appBean = GsonUtil.getAppBean(mJson);


        HttpPostUtils httpPostUtil = new HttpPostUtils();
        String url = "";
        url = appBean.api_user + "/" + SPUtil.getUserId(mActivity) + "/" + "my.json";
        RequestParams params = new RequestParams(url);
        httpPostUtil.get(mActivity, params);

        httpPostUtil.OnCallBack(new HttpPostUtils.OnSetData() {
            @Override
            public void onSuccess(String strJson) {
                SPUtil.setUserJson(mActivity, strJson);//缓存用户信息

                userBean = GsonUtil.getUserBean(strJson);
                userOrganizationBean = userBean.organization;


                LogUtil.e("我的页面json");
                LogUtil.e(SPUtil.getUserJson(mActivity));
                setUserData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("我的页面json-----错误" + ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });

//

    }

    /**
     * 设置用户数据
     */
    private void setUserData() {

        bitmapUtils.display(avatar, userBean.avatar);

        name.setText(userBean.name);

        if (userOrganizationBean != null) {
            company.setText(userOrganizationBean.root_company_name + "");//+""是为了防止userOrganizationBean为空
            getOrganizationData();
        }


    }

    private void getOrganizationData() {

        showProgressDialog();
        HttpPostUtils httpPostUtil = new HttpPostUtils();
        String url = "";
        url = http_shuxinyun_url + userOrganizationBean.url;
        RequestParams params = new RequestParams(url);
        httpPostUtil.get(mActivity, params);

        httpPostUtil.OnCallBack(new HttpPostUtils.OnSetData() {
            @Override
            public void onSuccess(String strJson) {

                SPUtil.setUserOrganizationJson(mActivity, strJson);//缓存公司架构信息

                LogUtil.e("我的页面json");
                LogUtil.e(SPUtil.getUserOrganizationJson(mActivity));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("我的页面json-----错误" + ex);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });

    }


    /**
     * 设置切换岗位数据源
     */
    private void setPickers() {
        //这里先静态设置
        for (int i = 0; i < 5; i++) {
            pickersCacheList.add(new Pickers("职位" + i, (i + 0) + ""));
            pickersItemList.add(new Pickers("职位" + i, (i + 0) + ""));
        }

    }


    /**
     * 待我审批，审批中，审批历史切换事件
     *
     * @param view
     */
    @Event(value = {R.id.title_back
            , R.id.mine_zdlf_address_list
            , R.id.mine_zdlf_personnel_file
            , R.id.mine_zdlf_reset_password
            , R.id.mine_zdlf_logout
            , R.id.mine_zdlf_icon
            , R.id.mine_zdlf_department})
    private void approveChangeOnClick(View view) {
        Intent intent = new Intent(mActivity, EmptyActivity.class);
        switch (view.getId()) {
            case R.id.title_back://返回
                mActivity.finish();
                break;

            case R.id.mine_zdlf_address_list://通讯录
                intent.putExtra("fragment_name", "addressList");

                Bundle bundle = new Bundle();
                bundle.putString("organization_url", http_shuxinyun_url + userOrganizationBean.url);
                intent.putExtra("organization_url",bundle);
                startActivity(intent);
                break;


            case R.id.mine_zdlf_personnel_file://人事管理

                intent.putExtra("fragment_name", "TestFragment_1");
                mActivity.startActivity(intent);
                break;


            case R.id.mine_zdlf_reset_password://重置密码
//                passwordView = new WindowRecettingPasswordView(mActivity);
//                showRecettingPassword(mActivity,passwordView);
                intent.putExtra("fragment_name", "resetting_password");
                startActivity(intent);
                break;

            case R.id.mine_zdlf_logout://退出登录
                SPUtil.setUserUUID(getActivity(), "");
                Intent i = new Intent(getActivity(), SplashActivity.class);
                startActivity(i);
                getActivity().finish();
                break;

            case R.id.mine_zdlf_icon://更改头像
                PhotoOrVideoUtils.doPhoto(mActivity, this, view);
                break;

            case R.id.mine_zdlf_department://切换岗位
                //LogUtil.e("点击select¥¥¥"+pickerSelecect+"::getShowConetnt:"+pickersList.get(pickerSelecect).getShowConetnt());
                LogUtil.e("点击select¥¥¥" + pickerSelecect + "::getShowConetnt:" + pickerSelecect);
                pickersCacheList.clear();
                pickersCacheList.addAll(pickersItemList);
                pickerPopupWindow.setPickerScrollViewPopupWindow(mActivity, pickersCacheList, pickerSelecect, department);
                break;

        }

    }


    /**
     * 弹出修改密码窗口
     *
     * @param context
     * @param view
     */
    public void showRecettingPassword(Context context, final WindowRecettingPasswordView view) {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.MIDialog);

            view.getConfirm().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.submit();//获取到的数据
                    if (!view.getPassword().equals("")) {
                        password = view.getPassword();
                        old_password = view.getOldPassword();
                        LogUtil.e("获取到的密码为====" + password);
                        LogUtil.e("获取到的原始密码为====" + old_password);
                        setPassword();//设置密码
                        dialog.dismiss();
                    }
                }
            });

            view.getCancle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
            dialog.setContentView(view);
            dialog.setCancelable(false);
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 访问网络进行修改密码
     */
    private void setPassword() {
        final RequestParams params = new RequestParams(UrlUtil.baseUrl);
        params.addBodyParameter("client_name", Constants.app_name);
        params.addBodyParameter("phone_uuid", SPUtil.getUserPUUID(getActivity()));
        params.addBodyParameter("randcode", SPUtil.getUserRandCode(getActivity()));
        params.addBodyParameter("uuid", SPUtil.getUserUUID(getActivity()));
        params.addBodyParameter("user_id", SPUtil.getUserId(getActivity()));
        params.addBodyParameter("action", "submit");


        params.addBodyParameter("method", "edit_password");
        params.addBodyParameter("old_password", MD5.getMessageDigest(old_password.getBytes()));
        params.addBodyParameter("password", MD5.getMessageDigest(password.getBytes()));

        //LogUtil.e("params"+params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("修改密码结果为！！！！======" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean success = jsonObject.optBoolean("success", false);
                    if (!success) {
                        MyToast.showShort(getActivity(), jsonObject.optString("feedback", "密码修改失败"));
                    } else {
                        SPUtil.setUserUUID(mActivity, jsonObject.optString("uuid", ""));
                        MyToast.showShort(getActivity(), "修改密码成功！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("失败原因为！！！！======" + ex);
                MyToast.showShort(getActivity(), "上传失败，网络超时");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = PhotoOrVideoUtils.getFileUri(requestCode, resultCode, data);
        if (uri != null) {
            String imagePath = PhotoOrVideoUtils.getPath(mActivity, uri);
            upLoadFile(imagePath, "", "");
        }

    }

    /**
     * 上传动作
     *
     * @param filepath 要上传的文件路径
     * @param number   要上传文件的对应的编号
     */
    public void upLoadFile(String filepath, String number, String ext) {
        LogUtil.e("传入的路径名==" + filepath);
        String io = MyBase64.file2String(filepath);
        dialog = ProgressDialog.show(getActivity(), "更新头像", "正在上传新头像", true, false);
        final RequestParams params = new RequestParams(UrlUtil.baseUrl);
        params.addBodyParameter("client_name", Constants.app_name);
        params.addBodyParameter("phone_uuid", SPUtil.getUserPUUID(getActivity()));
        params.addBodyParameter("randcode", SPUtil.getUserRandCode(getActivity()));
        params.addBodyParameter("uuid", SPUtil.getUserUUID(getActivity()));

        params.addBodyParameter("user_id", SPUtil.getUserId(getActivity()));

        params.addBodyParameter("action", "submit");
        params.addBodyParameter("method", "set_user_avatar_asname");
        params.addBodyParameter("asname", "asname");
        params.addBodyParameter("name", "asname");

        params.addBodyParameter("avatar", io);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("上传结果为！！！！======" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean success = jsonObject.optBoolean("success", false);
                    if (!success) {
                        MyToast.showShort(getActivity(), "上传失败，请联系管理员");
                        LogUtil.e("上传失败，请联系管理员！！！！======");
                    } else {
                        MyToast.showShort(getActivity(), "上传成功！");
                        LogUtil.e("上传成功！！！！======");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("失败原因为！！！！======" + ex);
                MyToast.showShort(getActivity(), "上传失败，网络超时");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                MyBitmapUtils.getInstance(getActivity()).clearCache(userBean.avatar);
                bitmapUtils.display(avatar, userBean.avatar);
//                if (getActivity() instanceof MainActivity) {
//                    MainActivity a = (MainActivity) getActivity();
//                    a.setUserIcon();
//                }
                dialog.dismiss();
            }
        });

    }
}
