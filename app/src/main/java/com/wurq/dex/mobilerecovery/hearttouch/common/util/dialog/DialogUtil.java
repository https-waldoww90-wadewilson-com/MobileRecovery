package com.wurq.dex.mobilerecovery.hearttouch.common.util.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.wurq.dex.mobilerecovery.R;
import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;
import com.wurq.dex.mobilerecovery.hearttouch.common.util.ScreenUtil;
import com.wurq.dex.mobilerecovery.hearttouch.common.view.progressdialog.LoadDataProgressDialog;

import java.util.WeakHashMap;

//import com.wurq.dex.mobilerecovery.recoveryapp.module.mainpage.activity.MainPageActivity;

/**
 * Created by ht-template
 **/
public class DialogUtil {
    private static final String PACKAGE_URL_SCHEME = "package:";

    static AlertDialog loadingDialog;
    private static WeakHashMap<Context, LoadDataProgressDialog> progressDlgs = new WeakHashMap<>();

//    public static void makeLoginFailedAlert(Context context,
//                                            final IClick positiveBtnClick,
//                                            final IClick neutralBtnClick) {
//        makeAlert(context,
//                ResourcesUtil.getString(string.login_failed),
//                ResourcesUtil.getString(string.login_meet_problem),
//                ResourcesUtil.getString(string.login_retry),
//                ResourcesUtil.getString(string.cancel),
//                positiveBtnClick,
//                neutralBtnClick);
//    }
//
//    public static void makeRegisterFailedAlert(Context context,
//                                               final IClick positiveBtnClick,
//                                               final IClick neutralBtnClick) {
//        makeAlert(context,
//                ResourcesUtil.getString(string.register_failed),
//                ResourcesUtil.getString(string.register_meet_problem),
//                ResourcesUtil.getString(string.register_retry),
//                ResourcesUtil.getString(string.cancel),
//                positiveBtnClick,
//                neutralBtnClick);
//    }

    /**
     * @param context             上下文
     * @param titleRes            标题
     * @param contentRes          内容
     * @param positiveBtnTextRes  按钮文本
     * @param positiveBtnColorRes 按钮文本颜色
     * @param neutralBtnTextRes   按钮文本
     * @param negativeBtnColorRes 按钮文本颜色
     * @param positiveBtnClick    点击事件
     * @param negativeBtnClick    点击事件
     * @param id                  操作
     */
    public static void makeAlert(Context context,
                                 @StringRes int titleRes,
                                 @StringRes int contentRes,
                                 @StringRes int positiveBtnTextRes,
                                 @ColorRes int positiveBtnColorRes,
                                 @StringRes int neutralBtnTextRes,
                                 @ColorRes int negativeBtnColorRes,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick,
                                 boolean backCancel,
                                 boolean outsideCancel,
                                 boolean isOnly,
                                 final int id) {
        makeAlert(context,
                ResourcesUtil.getString(titleRes),
                ResourcesUtil.getString(contentRes),
                ResourcesUtil.getString(positiveBtnTextRes),
                ResourcesUtil.getColor(positiveBtnColorRes),
                ResourcesUtil.getString(neutralBtnTextRes),
                ResourcesUtil.getColor(negativeBtnColorRes),
                positiveBtnClick,
                negativeBtnClick,
                backCancel,
                outsideCancel,
                isOnly,
                id);
    }

    /**
     * @param context          上下文
     * @param title            标题
     * @param content          内容
     * @param positiveBtnText  按钮文本
     * @param positiveBtnColor 按钮文本颜色
     * @param neutralBtnText   按钮文本
     * @param negativeBtnColor 按钮文本颜色
     * @param positiveBtnClick 点击事件
     * @param negativeBtnClick 点击事件
     */
    public static void makeAlert(Context context,
                                 String title,
                                 String content,
                                 String positiveBtnText,
                                 @ColorInt int positiveBtnColor,
                                 String neutralBtnText,
                                 @ColorInt int negativeBtnColor,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick,
                                 boolean backCancel,
                                 boolean outsideCancel,
                                 boolean isOnly,
                                 final int operatorId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.alert_dialog);
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_alert_common, null);
        builder.setView(rootView);
        final AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = ScreenUtil.getDisplayWidth() - 2 * ResourcesUtil.getDimenPxSize(R.dimen.alert_dialog_window_margin_left_right);
        window.setAttributes(params);
//        window.setLayout(ScreenUtil.getDisplayWidth() - 2 * ResourcesUtil.getDimenPxSize(R.dimen.alert_dialog_window_margin_left_right),ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.show();
        alertDialog.setCancelable(backCancel);
        alertDialog.setCanceledOnTouchOutside(outsideCancel);
        // 设置dialog的宽度

        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_alert_title);
        TextView tvContent = (TextView) rootView.findViewById(R.id.edt_alert_content);
        Button btnNegative = (Button) rootView.findViewById(R.id.btn_alert_negative);
        Button btnPositive = (Button) rootView.findViewById(R.id.btn_alert_positive);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }

        tvContent.setText(content);
        if (positiveBtnColor != -1)
            btnPositive.setTextColor(positiveBtnColor);
        btnPositive.setText(positiveBtnText);
        if (negativeBtnColor != -1)
            btnNegative.setTextColor(negativeBtnColor);
        btnNegative.setText(neutralBtnText);

        if (isOnly) {
            btnNegative.setVisibility(View.GONE);
            rootView.findViewById(R.id.line).setVisibility(View.GONE);
        } else {
            btnNegative.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.line).setVisibility(View.VISIBLE);
        }
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDismiss = (positiveBtnClick != null) ? positiveBtnClick.onDialogClick(v.getId(), operatorId) : true;
                if (isDismiss) alertDialog.dismiss();
            }
        });
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDismiss = (negativeBtnClick != null) ? negativeBtnClick.onDialogClick(v.getId(), operatorId) : true;
                if (isDismiss) alertDialog.dismiss();
            }
        });

    }

    public static void makeAlert(Context context,
                                 String title,
                                 String content,
                                 String positiveBtnText,
                                 String neutralBtnText,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick,
                                 boolean backCancel,
                                 boolean outsideCancel) {
        makeAlert(context, title, content, positiveBtnText, -1, neutralBtnText, -1, positiveBtnClick, negativeBtnClick, backCancel, outsideCancel, false, 0);

    }


    public static void makeAlert(Context context,
                                 String title,
                                 String content,
                                 String positiveBtnText,
                                 String neutralBtnText,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick) {
        makeAlert(context, title, content, positiveBtnText, -1, neutralBtnText, -1, positiveBtnClick, negativeBtnClick, true, true, false, 0);

    }

    public static void makeAlert(Context context,
                                 String title,
                                 String content,
                                 String positiveBtnText,
                                 @ColorRes int positiveBtnColorRes,
                                 String negativeBtnText,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick) {
        makeAlert(context,
                title,
                content,
                positiveBtnText,
                ResourcesUtil.getColor(positiveBtnColorRes),
                negativeBtnText,
                -1,
                positiveBtnClick,
                negativeBtnClick, true, true, false, 0);
    }

    public static void makeAlert(Context context,
                                 @StringRes int titleRes,
                                 @StringRes int contentRes,
                                 @StringRes int positiveBtnTextRes,
                                 @ColorRes int positiveBtnColorRes,
                                 @StringRes int negativeBtnTextRes,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick) {
        makeAlert(context,
                ResourcesUtil.getString(titleRes),
                ResourcesUtil.getString(contentRes),
                ResourcesUtil.getString(positiveBtnTextRes),
                ResourcesUtil.getColor(positiveBtnColorRes),
                ResourcesUtil.getString(negativeBtnTextRes),
                -1,
                positiveBtnClick,
                negativeBtnClick, true, true, false, 0);
    }

    public static void makeAlert(Context context,
                                 @StringRes int titleRes,
                                 @StringRes int contentRes,
                                 @StringRes int positiveBtnTextRes,
                                 @StringRes int negativeBtnTextRes,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick) {
        makeAlert(context,
                ResourcesUtil.getString(titleRes),
                ResourcesUtil.getString(contentRes),
                ResourcesUtil.getString(positiveBtnTextRes),
                -1,
                ResourcesUtil.getString(negativeBtnTextRes),
                -1,
                positiveBtnClick,
                negativeBtnClick, true, true, false, 0);
    }

    /**
     * @param id 传入操作id值
     */
    public static void makeAlert(Context context,
                                 @StringRes int titleRes,
                                 @StringRes int contentRes,
                                 @StringRes int positiveBtnTextRes,
                                 @StringRes int negativeBtnTextRes,
                                 final IClick positiveBtnClick,
                                 final IClick negativeBtnClick, final int id) {
        makeAlert(context,
                ResourcesUtil.getString(titleRes),
                ResourcesUtil.getString(contentRes),
                ResourcesUtil.getString(positiveBtnTextRes),
                -1,
                ResourcesUtil.getString(negativeBtnTextRes),
                -1,
                positiveBtnClick,
                negativeBtnClick, true, true, false, id);
    }

    /**
     * 只有一个确认按钮
     */
    public static void makeAlert(Context context,
                                 @StringRes int titleRes,
                                 @StringRes int contentRes,
                                 @StringRes int positiveBtnTextRes,
                                 final IClick positiveBtnClick,
                                 final int id) {
        makeAlert(context,
                ResourcesUtil.getString(titleRes),
                ResourcesUtil.getString(contentRes),
                ResourcesUtil.getString(positiveBtnTextRes),
                -1,
                "",
                -1,
                positiveBtnClick,
                null, true, true, true, id);

    }

    /**
     * 只有一个确认按钮
     */
    public static void makeAlert(Context context,
                                 String title,
                                 String content,
                                 String positiveBtnText,
                                 final IClick positiveBtnClick,
                                 final int id) {
        makeAlert(context,
                title,
                content,
                positiveBtnText,
                -1,
                "",
                -1,
                positiveBtnClick, null,
                true, true, true, id);

    }


    public static void showLoadProgressDialog(@NonNull Activity activity) {
        showProgressDialog(activity, -1, false);
    }


//    //没有文字,默认物理返回键禁用
//    public static void showProgressDialog(@NonNull Activity activity) {
//        showProgressDialog(activity, R.string.network_loading, true);
//    }
//
//    //没有文字,默认物理返回键禁用
//    public static void showProgressDialog(@NonNull Activity activity, boolean init) {
//        showProgressDialog(activity, R.string.network_loading, init);
//    }
//
//    //没有文字
//    public static void showProgressDialog(@NonNull Activity activity, @NonNull boolean cancelable, boolean init) {
//        showProgressDialog(activity, R.string.network_loading, cancelable, init);
//    }

    //有文字,默认物理返回键禁用
    public static void showProgressDialog(@NonNull Activity activity, @StringRes int msgId, boolean init) {
        showProgressDialog(activity, msgId, false, init);
    }

    public static void showProgressDialog(@NonNull final Activity activity,
                                          @StringRes int msgId,
                                          @NonNull boolean cancelable, boolean init) {
        LoadDataProgressDialog progressDlg = progressDlgs.get(activity);
        if (progressDlg == null || progressDlg.isCancelable() != cancelable || progressDlg.isInit() != init) {
            if (progressDlg != null && progressDlg.isCancelable() != cancelable) {
                final LoadDataProgressDialog tmpProgressDlg = progressDlg;
                activity.runOnUiThread(new ProgressDialogRunnable(tmpProgressDlg, false));
            }
            progressDlg = new LoadDataProgressDialog(activity, init);
//            if (activity.getClass().getName().equals(MainPageActivity.class.getName()))
            {// TODO: 16/8/26 主界面不能遮住底部
                WindowManager.LayoutParams layoutParams = progressDlg.getWindow().getAttributes();
                layoutParams.height -= ResourcesUtil.getDimenPxSize(R.dimen.activity_main_tab_height);
                progressDlg.getWindow().setAttributes(layoutParams);
            }
            progressDlg.setCancelable(cancelable);
            progressDlg.setCanceledOnTouchOutside(false);
            progressDlgs.put(activity, progressDlg);

        }
        if (msgId < 0) {
            progressDlg.hideMessage();
        } else {
            progressDlg.setMessage(ResourcesUtil.getString(msgId));
        }
        activity.runOnUiThread(new ProgressDialogRunnable(progressDlg, true));
    }

    public static void hideProgressDialog(@NonNull Activity activity) {
        LoadDataProgressDialog progressDlg = progressDlgs.get(activity);
        if (progressDlg != null) {
            activity.runOnUiThread(new ProgressDialogRunnable(progressDlg, false));
        }
    }

    /**
     * 创建弹框
     * 宽度为match_parent，停留在底部
     */
    public static AlertDialog createDialog(Context context, int layout, int animation, boolean showSoftInput, int gravity) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        window.setAttributes(lp);
        window.setWindowAnimations(animation);

        return dialog;
    }

    /**
     * 创建弹框
     * 宽度为match_parent，停留在底部
     */
    public static AlertDialog createAbsoluteWidthDialog(Context context, int layout, int animation, boolean showSoftInput, int gravity) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ScreenUtil.dip2px(324);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        window.setAttributes(lp);
        window.setWindowAnimations(animation);

        return dialog;
    }

/**
 * Date: 2015-06-16
 * Time: 20:14
 * Author: cf
 * -----------------------------
 * 弹窗工具类
 */

    /**
     * 创建弹框
     * 宽度为match_parent，停留在底部
     */
    public static AlertDialog createDialog(Context context, int layout, int animation, boolean showSoftInput) {
        return createDialog(context, layout, animation, showSoftInput, Gravity.BOTTOM);
    }

//    /**
//     * 创建弹框
//     * 宽度为match_parent，停留在底部
//     *
//     * @param context
//     * @param layout
//     * @param animation
//     * @param showSoftInput
//     * @param gravity
//     * @return
//     */
//    public static AlertDialog createWrapDialog(Context context, int layout, int animation, boolean showSoftInput, int gravity) {
//        AlertDialog dialog = new AlertDialog.Builder(context).setView(layout).create();
//        dialog.show();
////        LayoutInflater inflater = LayoutInflater.from(context);
////        View view = inflater.inflate(layout,null);
////        dialog.setContentView(view,new ViewGroup.LayoutParams(DisplayUtil.dip2px(324), ViewGroup.LayoutParams.WRAP_CONTENT));
//        Window window = dialog.getWindow();
//        if (showSoftInput) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//
//        lp.copyFrom(window.getAttributes());
//        lp.width = DisplayUtil.dip2px(324);
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.x=0;
//        lp.y=0;
//        window.setAttributes(lp);
//
////        WindowManager.LayoutParams lp = window.getAttributes();
////        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
////        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////        lp.gravity = gravity;
////        window.setAttributes(lp);
//        window.setWindowAnimations(animation);
//
//        return dialog;
//    }

    /**
     * 创建弹框
     * 居中，宽度为match_parent加上一定margin
     */
    public static AlertDialog createDialogNormal(Context context, int layout, boolean showSoftInput, int margin) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = size.x - ScreenUtil.dip2px(margin);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        return dialog;
    }

    public static AlertDialog createDialogNormal(Context context, int layout, boolean showSoftInput) {
        return createDialogNormal(context, layout, showSoftInput, 40);
    }

    /**
     * 创建全屏弹框
     */
    public static AlertDialog createDialogFull(Context context, int layout, boolean showSoftInput) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        return dialog;
    }

//    public static void showSetPermissionsDialog(final Context context, View rootView) {
//        String title = ResourcesUtil.getString(R.string.permissions_dialog_title);
//        String content = ResourcesUtil.getString(R.string.permissions_dialog_content);
//        String positiveBtn = ResourcesUtil.getString(R.string.set);
//        String negativeBtn = ResourcesUtil.getString(R.string.cancel);
//
//        showStatusDialog(context, rootView, new IClick() {
//                    @Override
//                    public boolean onDialogClick(int viewId, int operatorId) {
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + context.getPackageName()));
//                        context.startActivity(intent);
//                        return true;
//                    }
//                },
//                new IClick() {
//                    @Override
//                    public boolean onDialogClick(int viewId, int operatorId) {
//                        return true;
//                    }
//                }, title, content, positiveBtn, negativeBtn, -3);
//    }

    public static void showStatusDialog(Context context,
                                        View rootView,
                                        final IClick positiveBtnClick,
                                        String tittle,
                                        String content,
                                        String positivebtn) {
//        showStatusDialog(context, rootView, positiveBtnClick, null, tittle, content, positivebtn, "default", -2);
    }


//    public static PopupWindowView showStatusDialog(Context context,
//                                                   View rootView,
//                                                   final IClick positiveBtnClick,
//                                                   final IClick negativeBtnClick,
//                                                   String tittle,
//                                                   String content,
//                                                   String positivebtn,
//                                                   String negtivebtn,
//                                                   final int operatorId) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.alert_dialog);
//        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_show_status_layout, null);
//        builder.setView(dialogView);
//        final PopupWindowView popMenses = new PopupWindowView(context, Gravity.CENTER);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
//        popMenses.addSubView(dialogView, layoutParams);
//
//        TextView tittleview = (TextView) dialogView.findViewById(R.id.dialog_report_tittle);
//        TextView contentview = (TextView) dialogView.findViewById(R.id.dialog_report_content);
//        Button btnNegative = (Button) dialogView.findViewById(R.id.btn_alert_negative);
//        Button btnPositive = (Button) dialogView.findViewById(R.id.btn_alert_positive);
//
//        tittleview.setText(tittle);
//        contentview.setText(content);
//        btnNegative.setText(negtivebtn);
//        btnPositive.setText(positivebtn);
//
//
//        btnPositive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isDismiss = (positiveBtnClick != null) ? positiveBtnClick.onDialogClick(v.getId(), operatorId) : true;
//                if (isDismiss) popMenses.dismiss();
//            }
//        });
//
//        if (negativeBtnClick == null) {
//            btnNegative.setVisibility(View.GONE);
//        } else {
//
//            btnNegative.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean isDismiss = (negativeBtnClick != null) ? negativeBtnClick.onDialogClick(v.getId(), operatorId) : true;
//                    if (isDismiss) popMenses.dismiss();
//                }
//            });
//        }
//        popMenses.showInCenter(rootView, false);
//
//        return popMenses;
//    }


//    public static PopupWindowView showBottomStatusDialog(Context context,
//                                        View rootView,
//                                        final IClick positiveBtnClick,
//                                        final IClick negativeBtnClick,
//                                        String tittle,
//                                        String content,
//                                        String positivebtn,
//                                        String negtivebtn,
//                                        final int operatorId) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.alert_dialog);
//        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_show_status_new_layout, null);
//        builder.setView(dialogView);
//        final PopupWindowView popWindow = new PopupWindowView(context, Gravity.BOTTOM);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.BOTTOM;
//        popWindow.addSubView(dialogView, layoutParams);
//
//        TextView tittleview = (TextView) dialogView.findViewById(R.id.dialog_report_tittle);
//        TextView contentview = (TextView) dialogView.findViewById(R.id.dialog_report_content);
//        Button btnNegative = (Button) dialogView.findViewById(R.id.btn_alert_negative);
//        Button btnPositive = (Button) dialogView.findViewById(R.id.btn_alert_positive);
//
//        tittleview.setText(tittle);
//        contentview.setText(content);
//        btnNegative.setText(negtivebtn);
//        btnPositive.setText(positivebtn);
//
//        btnPositive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isDismiss = (positiveBtnClick != null) ?
//                        positiveBtnClick.onDialogClick(v.getId(), operatorId) : true;
//                if (isDismiss) popWindow.dismiss();
//            }
//        });
//
//        if (negativeBtnClick == null) {
//            btnNegative.setVisibility(View.GONE);
//        } else {
//            btnNegative.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean isDismiss = (negativeBtnClick != null) ?
//                            negativeBtnClick.onDialogClick(v.getId(), operatorId) : true;
//                    if (isDismiss) popWindow.dismiss();
//                }
//            });
//        }
//
//        popWindow.setOutSideTouchListener(new PopupWindowView.OutsideTouchListener() {
//            @Override
//            public void outsideTouchListener() {
//                popWindow.dismiss();
//            }
//        });
//        popWindow.setAnimationStyle(R.style.AnimBottom);
//        popWindow.showAtLocation(rootView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0,true);
//
//        return popWindow;
//    }


//    public static void showNoButtonDialog(Context context,
//                                          View rootView,
//                                          final IClick exitClick,
//                                          final int operatorId,
//                                          String msg){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.alert_dialog);
//        final View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_dialog_detect_time
//                ,null);
//
//        TextView msgView = (TextView)dialogView.findViewById(R.id.tv_dialog_lh_detect_time);
//        msgView.setText(msg);
//
//        builder.setView(dialogView);
//        final PopupWindowView popMenses = new PopupWindowView(context, Gravity.BOTTOM);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//
//        popMenses.addSubView(dialogView, layoutParams);
//        Button btnNegative = (Button) dialogView.findViewById(R.id.btn_show_detect_time);
//
//        btnNegative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isDismiss = (exitClick != null) ? exitClick.onDialogClick(v.getId(), operatorId) : true;
//                if (isDismiss) popMenses.dismiss();
//            }
//        });
//        popMenses.setOutSideTouchListener(new PopupWindowView.OutsideTouchListener() {
//            @Override
//            public void outsideTouchListener() {
//                Log.i("popMenses", "outsideTouchListener: ");
//            }
//        });
//        popMenses.showInCenter(rootView, false);
//    }

//    public static void showPopsDialog(Context context,
//                                      View rootView,
//                                      final IClick negativeBtnClick,
//                                      final int operatorId,
//                                      int top) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.alert_dialog);
//        final View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_bluetooth_errormsg
//                , null);
//        dialogView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("showPopsDialog", "onClick: ");
//            }
//        });
//        builder.setView(dialogView);
//        final PopupWindowView popMenses = new PopupWindowView(context, Gravity.CENTER_HORIZONTAL);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.topMargin = top;
//        Log.i("PopupWindowView", "showPopsDialog: ");
////        layoutParams.bottomMargin=64;
//
//        layoutParams.height = ScreenUtil.getDisplayHeight() - top - ResourcesUtil.getDimenPxSize(R.dimen.dialog_margin_bottom_height);
//        //   Log.i("DialogUtil", "showPopsDialog:height "+ResourcesUtil.getDimenPxSize(R.dimen.dialog_margin_bottom_height));
//        layoutParams.rightMargin = ResourcesUtil.getDimenPxSize(R.dimen.dialog_margin_right);
//        layoutParams.leftMargin = ResourcesUtil.getDimenPxSize(R.dimen.dialog_margin_left);
//        ;
////         Log.i("DialogUtil", "showPopsDialog: "+String.valueOf(ScreenUtil.px2dip(26)));
////         Log.i("DialogUtil", "showPopsDialog: "+String.valueOf(ScreenUtil.px2dip(64)));
//
//        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
//        popMenses.addSubView(dialogView, layoutParams);
//        Button btnNegative = (Button) dialogView.findViewById(R.id.btn_errorclose);
//
//        btnNegative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isDismiss = (negativeBtnClick != null) ? negativeBtnClick.onDialogClick(v.getId(), operatorId) : true;
//                if (isDismiss) popMenses.dismiss();
//            }
//        });
//        popMenses.setOutSideTouchListener(new PopupWindowView.OutsideTouchListener() {
//            @Override
//            public void outsideTouchListener() {
//                Log.i("popMenses", "outsideTouchListener: ");
//            }
//        });
//        popMenses.showInCenter(rootView, false);
//    }

    public interface IClick {
        /**
         * @param viewId     点击按钮控件的id
         * @param operatorId 操作的id，外面传进来
         * @return true，dismiss当前对话框；false，不dismiss
         */
        boolean onDialogClick(int viewId, int operatorId);
    }

    private static class ProgressDialogRunnable implements Runnable {
        LoadDataProgressDialog progressDialog;
        boolean isShow;

        public ProgressDialogRunnable(LoadDataProgressDialog dialog, boolean isShow) {
            this.progressDialog = dialog;
            this.isShow = isShow;
        }

        @Override
        public void run() {
            if (progressDialog != null) {
                if (isShow && !progressDialog.isShowing()) {
                    progressDialog.show();
                    progressDialog.startRotate();
                } else if (!isShow && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog.stopRotate();
                }
            }
        }
    }

    /**
     * 创建loading对话框
     */
//    public static void showLoadingDialog(Context context, int gravity, int animation) {
//        loadingDialog = new AlertDialog.Builder(context, R.style.loading_theme).create();
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View loadingLayout = inflater.inflate(layout.fragment_loading, null);
//        loadingDialog.show();
//        loadingDialog.setContentView(loadingLayout);
//        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        Window window = loadingDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = gravity;
//        window.setAttributes(lp);
//        window.setWindowAnimations(animation);
//    }
//
//    public static void showLoadingDialogCenter(Context context) {
//        showLoadingDialog(context, Gravity.CENTER, R.style.DialogAlphaAnimation);
//    }
//
//    public static void dismissLoadingDialog() {
//        if (loadingDialog != null) {
//            loadingDialog.dismiss();
//        }
//    }
}
