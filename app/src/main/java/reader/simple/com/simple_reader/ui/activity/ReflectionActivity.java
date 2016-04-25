package reader.simple.com.simple_reader.ui.activity;

import android.widget.TextView;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import butterknife.InjectView;
import reader.simple.com.simple_reader.R;
import reader.simple.com.simple_reader.ui.activity.base.BaseActivity;

public class ReflectionActivity extends BaseActivity {

    @InjectView(R.id.refection_content)
    TextView refectionContent;

    @Override
    protected boolean pendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getTransitionMode() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_reflection;
    }

    @Override
    protected void initViewsAndEvents() {

        Class<?> clazz = MainActivity.class;
        Method[] methods = clazz.getMethods();
        StringBuilder mStringBuilder = new StringBuilder();
        for (Method method : methods) {
            mStringBuilder.append(Modifier.toString(method.getModifiers())).append("#");
            mStringBuilder.append(method.getReturnType()).append("#");
            mStringBuilder.append(method.getName()).append("#");
            Class<?>[] params = method.getParameterTypes();
            if (params != null) {
                for (Class<?> mClass : params) {
                    mStringBuilder.append(mClass.getSimpleName()).append(",");
                }
            }
            mStringBuilder.append("\r\n\n");
        }
        refectionContent.setText(mStringBuilder.toString());
    }

}
