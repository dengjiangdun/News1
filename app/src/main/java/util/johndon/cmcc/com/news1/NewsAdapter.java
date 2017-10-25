package util.johndon.cmcc.com.news1;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * Created by root on 17-10-23.
 */

public class NewsAdapter extends BaseAdapter<News> {
    private List<News> mListNews;
    private Context mContext;
    private final String HTML_FORMAT = "<html><a  href=\"%s\">%s</a><html>";
    private FinalBitmap mFinalBitmap;
    private LinkClickListener mLinkClickListener;
    private String type;
    public NewsAdapter(Context context, int layoutId, List<News> datas,LinkClickListener listener,String type) {
        super(context, layoutId, datas);
        mContext = context;
        mListNews = datas;
        mFinalBitmap = FinalBitmap.create(mContext);
        mLinkClickListener = listener;
        this.type = type;
    }

    @Override
    public void convert(ViewHolder holder, final News news) {
        holder.setText(R.id.tv_title,news.getTitle());
        String str = news.getCategory();
        if (TextUtils.isEmpty(str)) {
            str = type;
        }
        holder.setText(R.id.tv_category_date,str+"  "+news.getDate());
        ((TextView)holder.getView(R.id.tv_source_link)).setText(Html.fromHtml(String.format(HTML_FORMAT,news.getUrl(),news.getAuthor_name())));
        holder.getView(R.id.tv_source_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinkClickListener.clickLink(news.getUrl());
            }
        });
        String url1 = news.getThumbnail_pic_s();
        String url2 = news.getThumbnail_pic_s02();
        String url3 = news.getThumbnail_pic_s03();
        if (! TextUtils.isEmpty(url1)) {
            mFinalBitmap.display(holder.getView(R.id.iv_pic_1),url1);
        } else {
            holder.getView(R.id.iv_pic_1).setVisibility(View.GONE);
        }

        if (! TextUtils.isEmpty(url2)) {
            Log.d("TAG", "convert: "+url2);
            mFinalBitmap.display(holder.getView(R.id.iv_pic_2),url2);
        } else {
            holder.getView(R.id.iv_pic_2).setVisibility(View.GONE);
        }

        if (! TextUtils.isEmpty(url3)) {
            mFinalBitmap.display(holder.getView(R.id.iv_pic_3),url3);
        } else {
            holder.getView(R.id.iv_pic_3).setVisibility(View.GONE);
        }
    }

    public interface LinkClickListener {
        void clickLink(String link);
    }
}
