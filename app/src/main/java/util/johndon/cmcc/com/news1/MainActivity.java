package util.johndon.cmcc.com.news1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import util.cmcc.johndon.com.show.CMCCProgressDialog;

public class MainActivity extends AppCompatActivity implements NewsAdapter.LinkClickListener ,NewsFragmentDialog.SeletTypeListener{
    private PullToRefreshRecyclerView mRvNews;
    private ImageView mIvChoose;

    private List<News> mListNews;
    private List<News> mListTotalNews;
    private NewsAdapter mNewsAdapter;
    private FinalHttp finalHttp;
    private CMCCProgressDialog mCMCCProgressDialog;

    private static final String GET_NEWS_URL = " http://api.avatardata.cn/TouTiao/Query?key=1c9887e2cb564d90bb01883d6e73df32&type=%s";
    private String type = "top";
    private String category;
    private int index = 0;
    private int COLOW = 6;
    private static final String TYPE_KEY = "type";
    private static final String CATEGORY_KEY = "CATEGORY";
    //top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRvNews = (PullToRefreshRecyclerView) findViewById(R.id.rv_news);
        mIvChoose = (ImageView) findViewById(R.id.iv_choose);
        mRvNews.setHasFixedSize(true);
        type = getIntent().getStringExtra(TYPE_KEY);
        category = getIntent().getStringExtra(CATEGORY_KEY);
        initData();
    }

    private void initData() {
        /*mIvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewsFragmentDialog().show(getFragmentManager(),"fragment_dialog");
            }
        });*/
        mIvChoose.setVisibility(View.INVISIBLE);

        mCMCCProgressDialog = new CMCCProgressDialog(this);
        finalHttp = new FinalHttp();
        mListNews = new ArrayList<>();
        mListTotalNews = new ArrayList<>();
        mNewsAdapter = new NewsAdapter(this, R.layout.news_item, mListNews,this,category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvNews.setLayoutManager(linearLayoutManager);
        mRvNews.setAdapter(mNewsAdapter);

        loadData();

        mRvNews.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                reFreshData();
            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });

    }

    private void reFreshData() {
        mListNews.clear();
        loadData();
        index = 0;
        mRvNews.setLoadingMoreEnabled(true);
    }

    private void loadMoreData(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRvNews.setLoadMoreComplete();
                        putNewsData();
                    }
                });
            }
        },1500);
    }


    private void loadData() {
        mCMCCProgressDialog.show();
        finalHttp.get(String.format(GET_NEWS_URL, type), new AjaxCallBack<String>() {
            @Override
            public void onSuccess(final String s) {
                super.onSuccess(s);
                Context context = getBaseContext();
                if (context != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dealData(s);
                            mRvNews.setRefreshComplete();
                            mCMCCProgressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, final String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Context context = getBaseContext();
                if (context != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showShortToast(strMsg);
                            mRvNews.setRefreshComplete();
                            mCMCCProgressDialog.dismiss();
                        }
                    });
                }
            }
        });

    }


    private void dealData(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        int code = jsonObject.getIntValue("error_code");
        String mgs = jsonObject.getString("reason");
        if (code == 0) {
            mListTotalNews.clear();
            String  result = jsonObject.getString("result");
            JSONObject jsonObjectdata = JSON.parseObject(result);
            mListTotalNews.addAll(JSON.parseArray(jsonObjectdata.getString("data"),News.class));
            putNewsData();
        } else {
            showShortToast(mgs);
        }

    }

    private void putNewsData() {
        int size = mListTotalNews.size();
        index = mListNews.size();
        for (int i = index ; i < size && i < index +COLOW ; ++i ) {
            mListNews.add(mListTotalNews.get(i));
            mNewsAdapter.notifyDataSetChanged();
        }

        if (mListNews.size() >= mListTotalNews.size()) {
            mRvNews.setLoadingMoreEnabled(false);
            showShortToast(getResources().getString(R.string.no_more_load));
        }
    }

    private void showShortToast(String message) {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickLink(String link) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(link);
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    public void setType(String type) {
        this.type = type;
        reFreshData();
    }
}
