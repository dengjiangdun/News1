package util.johndon.cmcc.com.news1;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 17-10-23.
 */

public class NewsFragmentDialog extends DialogFragment implements View.OnClickListener{
    //top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    private SeletTypeListener seletTypeListener = null;
    private String type = "top";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.fragment_dialog_layout,container);
        view.findViewById(R.id.ll_top).setOnClickListener(this);
        view.findViewById(R.id.ll_social).setOnClickListener(this);
        view.findViewById(R.id.ll_national).setOnClickListener(this);
        view.findViewById(R.id.ll_world).setOnClickListener(this);
        view.findViewById(R.id.ll_entertainment).setOnClickListener(this);
        view.findViewById(R.id.ll_sport).setOnClickListener(this);
        view.findViewById(R.id.ll_military).setOnClickListener(this);
        view.findViewById(R.id.ll_science).setOnClickListener(this);
        view.findViewById(R.id.ll_finance).setOnClickListener(this);
        view.findViewById(R.id.ll_fashion).setOnClickListener(this);

        if (getActivity() instanceof SeletTypeListener) {
            seletTypeListener = (SeletTypeListener) getActivity();
        }


        return view;
    }

    @Override
    public void onClick(View view) {
        if (seletTypeListener == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.ll_top:
                type = "top";
                break;

            case R.id.ll_social:
                type = "shehui";
                break;

            case R.id.ll_national:
                type = "guonei";
                break;

            case R.id.ll_world:
                type = "guoji";
                break;

            case R.id.ll_entertainment:
                type = "yule";
                break;

            case R.id.ll_sport:
                type = "tiyu";
                break;

            case R.id.ll_military:
                type = "junshi";
                break;

            case R.id.ll_science:
                type = "keji";
                break;

            case R.id.ll_finance:
                type = "caijing";
                break;

            case R.id.ll_fashion:
                type = "shishang";
                break;

            default:break;
        }

        seletTypeListener.setType(type);
        dismiss();
    }

    public interface SeletTypeListener{
        public void setType(String type);
    }

}
