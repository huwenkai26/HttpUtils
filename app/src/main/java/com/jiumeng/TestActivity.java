package com.jiumeng;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jiumeng.httputils.R;
import com.jiumeng.image.ImageLoadListener;
import com.jiumeng.image.ImageLoader;

/**
 * Created by jiumeng on 2017/5/27.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private String[] images;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496480983&di=c77bac28dae259a171c4f4ba3661d118&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201408%2F30%2F20140830185456_Eijik.jpeg";
        String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495886264663&di=4f69354ac97ae03fda0daa171aaf3a82&imgtype=0&src=http%3A%2F%2Fimg.bimg.126.net%2Fphoto%2Ff5SVE9uVjQvVHD_kkn15Ag%3D%3D%2F5429370825788715971.jpg";
        String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495886264663&di=304755aa28a041bf469211140974cdf3&imgtype=0&src=http%3A%2F%2Fimg.sj33.cn%2Fuploads%2Fallimg%2F201005%2F20100509135319416.jpg";
        String url4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495886264662&di=e8540b7adecb8272fdf67f4ad86fa035&imgtype=0&src=http%3A%2F%2Fimage48.360doc.com%2FDownloadImg%2F2011%2F12%2F2310%2F20235268_9.jpg";
        String url5 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496483197&di=ad15e6021cda39e9ac8fb350e776cfc9&imgtype=jpg&er=1&src=http%3A%2F%2Fimgk.zol.com.cn%2Fdcbbs%2F10589%2Fa10588060.jpg";
        String url6 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495888522574&di=60969da2459b1de14ecb7c94df097af2&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201705%2F24%2F050224z4muhck6gzi2mckh.jpg";
        String url7 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495888522574&di=4f52878dfd73b624b50d175f84f7247b&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F1523%2Fntk-1523-18289.jpg";
        String url8 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495888522574&di=27ee2e3d03cb94665f99d605c7cb91cb&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201203%2F05%2F20120305142751_WCL8C.jpeg";
        String url9 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495888522573&di=56b58380c5c103d5715ead7b0cf08e16&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F2040%2Fntk-2040-18029.jpg";
        imageView = (ImageView) findViewById(R.id.ivImage);
        Button btnLoadImage = (Button) findViewById(R.id.btnLoadImage);
        images = new String[]{url, url2, url3, url4, url5, url6, url7, url8, url9};

        btnLoadImage.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (index >= images.length) {
            index = 0;
        }
        ImageLoader.loadImage(this, imageView, images[index++], new ImageLoadListener() {
            @Override
            public void start() {
                System.out.println("devin----start");
            }

            @Override
            public void error(String errorMsg) {
                System.out.println("devin----error:" + errorMsg);
            }

            @Override
            public void complete() {
                System.out.println("devin----complete");
            }
        });
    }
}
