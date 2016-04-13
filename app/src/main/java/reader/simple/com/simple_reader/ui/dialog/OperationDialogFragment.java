package reader.simple.com.simple_reader.ui.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.common.DeviceUtil;
import reader.simple.com.simple_reader.ui.widget.DragViewLayout;

/**
 * ==================================================
 * 项目名称：mobike
 * 创建人：wangxiaolong
 * 创建时间：16/3/31 下午7:28
 * 修改时间：16/3/31 下午7:28
 * 修改备注： 替换operationActivity operationDialog
 * Version：
 * ==================================================
 */
public class OperationDialogFragment extends DialogFragment implements DragViewLayout.DrageViewClampVerticalListener, DialogInterface
        .OnKeyListener {
    @InjectView(R.id.notify_image)
    ImageView notifyImage;
    @InjectView(R.id.root_drag_layout)
    DragViewLayout rootDragLayout;
    private boolean isMoveWithAnim;
    private static OperationDialogFragment mFragmentDialog;
    private ObjectAnimator sliedUpAnimator;
    private ObjectAnimator sliedDownAnimator;

    ///////////////////////////////////////////////////////////////////////////
    // 用来传递参数
    ///////////////////////////////////////////////////////////////////////////
    public static OperationDialogFragment newIntance(Bundle bundle) {
        mFragmentDialog = new OperationDialogFragment();
        mFragmentDialog.setArguments(bundle);
        return mFragmentDialog;
    }

    public static OperationDialogFragment getInstance() {
        if (mFragmentDialog == null) {
            mFragmentDialog = new OperationDialogFragment();
        }
        return mFragmentDialog;

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationOnlyIn;
        setCancelable(false);
        dialog.setOnKeyListener(this);
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.operation_dialog_laouyt, container);
        ButterKnife.inject(this, view);
        rootDragLayout.setmListener(this);
        sliedUpAnimator = ObjectAnimator.ofPropertyValuesHolder(notifyImage, PropertyValuesHolder.ofFloat("translationY",
                0 - DeviceUtil.getScreenHeight(getActivity())));
        sliedDownAnimator = ObjectAnimator.ofPropertyValuesHolder(notifyImage, PropertyValuesHolder.ofFloat("translationY",
                DeviceUtil.getScreenHeight(getActivity())));


        return view;

    }


    public void dismiss() {
        if (!isMoveWithAnim) {
            doAnimatorWithDirection(false);
        } else {
            mFragmentDialog = null;
            super.dismiss();
        }
    }


    @Override
    public void onVerticalMoving() {

    }

    @Override
    public void onVerticalMoveDirection(boolean isUp) {
        doAnimatorWithDirection(isUp);
    }

    @Override
    public void onVerticalBack() {
    }

    private void doAnimatorWithDirection(boolean isUp) {
        if (isUp) {
            setAnimatorListener(sliedUpAnimator);
        } else {
            setAnimatorListener(sliedDownAnimator);
        }
    }

    private void setAnimatorListener(ObjectAnimator sliedDownOrUp) {
        setAnimatorListener(sliedDownOrUp, null);
    }

    private void setAnimatorListener(ObjectAnimator sliedDownOrUp, AnimatorListenerAdapter animatorListenerAdapter) {
        isMoveWithAnim = true;
        if (null == animatorListenerAdapter) {
            sliedDownOrUp.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    dismiss();
                }
            });
        } else {
            sliedDownOrUp.addListener(animatorListenerAdapter);
        }

        sliedDownOrUp.setDuration(500);
        sliedDownOrUp.start();
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
            dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
