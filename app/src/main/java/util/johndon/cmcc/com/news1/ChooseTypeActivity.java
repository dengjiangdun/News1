package util.johndon.cmcc.com.news1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by root on 17-10-25.
 */

public class ChooseTypeActivity extends Activity implements View.OnClickListener{
    private String type = "top";
    private static final String TYPE_KEY = "type";
    private static final String CATEGORY_KEY = "CATEGORY";
    private String str;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        findViewById(R.id.ll_top).setOnClickListener(this);
        findViewById(R.id.ll_social).setOnClickListener(this);
        findViewById(R.id.ll_national).setOnClickListener(this);
        findViewById(R.id.ll_world).setOnClickListener(this);
        findViewById(R.id.ll_entertainment).setOnClickListener(this);
        findViewById(R.id.ll_sport).setOnClickListener(this);
        findViewById(R.id.ll_military).setOnClickListener(this);
        findViewById(R.id.ll_science).setOnClickListener(this);
        findViewById(R.id.ll_finance).setOnClickListener(this);
        findViewById(R.id.ll_fashion).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
        switch (view.getId()) {
            case R.id.ll_top:
                type = "top";
                str = "头条";
                break;

            case R.id.ll_social:
                type = "shehui";
                str = "社会";
                break;

            case R.id.ll_national:
                type = "guonei";
                str = "国内";
                break;

            case R.id.ll_world:
                type = "guoji";
                str = "国际";
                break;

            case R.id.ll_entertainment:
                type = "yule";
                str = "娱乐";
                break;

            case R.id.ll_sport:
                type = "tiyu";
                str = "体育";
                break;

            case R.id.ll_military:
                type = "junshi";
                str = "军事";
                break;

            case R.id.ll_science:
                type = "keji";
                str = "科技";
                break;

            case R.id.ll_finance:
                type = "caijing";
                str = "财经";
                break;

            case R.id.ll_fashion:
                type = "shishang";
                str = "时尚";
                break;

            default:break;
        }

        Intent intent = new Intent(ChooseTypeActivity.this,MainActivity.class);
        intent.putExtra(TYPE_KEY,type);
        intent.putExtra(CATEGORY_KEY,str);
        startActivity(intent);
    }
}
