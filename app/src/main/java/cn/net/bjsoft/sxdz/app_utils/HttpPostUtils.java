package cn.net.bjsoft.sxdz.app_utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Zrzc on 2017/4/19.
 */

public class HttpPostUtils {

    private OnSetData mOnCallBack;

    private Callback.CommonCallback callBack = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String result) {
            mOnCallBack.onSuccess(result);
        }


        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            mOnCallBack.onError(ex,isOnCallback);
        }

        @Override
        public void onCancelled(CancelledException cex) {
            mOnCallBack.onCancelled(cex);
        }

        @Override
        public void onFinished() {
            mOnCallBack.onFinished();
        }
    };

    // 数据接口抽象方法
    public interface OnSetData {
        //abstract ArrayList<KnowledgeBean.ItemsDataDao> cacheItemsDataList();

        abstract void onSuccess(String strJson);

        abstract void onError(Throwable ex, boolean isOnCallback);

        abstract void onCancelled(Callback.CancelledException cex);

        abstract void onFinished();

    }

    // 数据接口设置,数据源接口传入
    public void OnCallBack(OnSetData back) {
        mOnCallBack = back;
    }


    public void post(RequestParams params) {

        params.addBodyParameter("token", "");
        params.addBodyParameter("appid", "");
        params.addBodyParameter("secret", "");

        x.http().post(params, callBack);

    }

    public void get(RequestParams params) {

//        params.addBodyParameter("token", "");
//        params.addBodyParameter("appid", "");
//        params.addBodyParameter("secret", "");

        x.http().get(params, callBack);

    }

}
