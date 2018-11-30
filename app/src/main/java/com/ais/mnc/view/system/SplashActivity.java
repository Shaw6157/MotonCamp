package com.ais.mnc.view.system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.dao.CampsiteDao;
import com.ais.mnc.db.daoimp.CampsiteDaoImp;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.campsite.CsListActivity;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    ImageView img_logo;
    TextView tv_slogan;
    Runnable mRunnable;

    CampsiteDao lvCampsiteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnc_splash);

        img_logo = findViewById(R.id.img_logo);
        tv_slogan = findViewById(R.id.tv_slogan);

//        //multi thread to init DB
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                initCampsites();
//            }
//        });
//        thread.start();


        //1. local version
//        initCampsites();

        initSplashAnimation();
    }

    private void initSplashAnimation() {
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(500);
        alpha.setFillAfter(true);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MncUtilities.startNextActivity(SplashActivity.this, CsListActivity.class, true);
                Log.d("test >>>", "end jumping");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        img_logo.setAnimation(alpha);
        tv_slogan.setAnimation(alpha);
    }

    //init campstes
    private void initCampsites() {
        lvCampsiteDao = new CampsiteDaoImp(this);
        List<CampBean> lvCamps = lvCampsiteDao.findAll();
        if (lvCamps != null && lvCamps.size() > 3) {
            return;
        }
        CampBean camp1 = new CampBean();
        camp1.setAddress("225 Sandringham Rd, Sandringham, Auckland 1025");
        camp1.setCname("Gribblehirst Park");
        camp1.setInfo("Great Park for the kids variety of equipment but if one is broken would be nice to get it fixed. It is a Hugh park with space for running around playing but the toilet facilities should be checked routinely as in the middle of the day they are disgusting sometimes no toilet paper. Parks are a value place for our children the upkeep needs a plan on a routine for maintenance.");
        camp1.setUrl("https://cityvision.org.nz/our-projects/the-transformation-of-gribblehirst-park/");
//        camp1.setImage("https://tinyurl.com/mnc-campsite0001");
        camp1.setImage("https://cityvision.org.nz/wp-content/uploads/2016/09/WP_20140322_028-1080x675.jpg");
        camp1.setFeatures("Motorhome plug / Potable Water / Toilet");
        camp1.setLAT(-36.880514);
        camp1.setLNG(174.738549);
        camp1.setPhone("0219456111");

        CampBean camp2 = new CampBean();
        camp2.setAddress("27 Summit Dr, Mount Albert, Auckland 1025");
        camp2.setCname("Mount Albert - Owairaka Domain");
        camp2.setInfo("Fun short walk around the crater. There's a dirt path that takes you around the narrow rim or you can follow the road around. There is a number of side tracks you can take, great walk for kids with fantastic views");
        camp2.setUrl("https://maps.me/catalog/recreation/leisure-park/mount-albert-owairaka-domain--9223372036869264309/");
//        camp2.setImage("https://tinyurl.com/mnc-campsite0002");  //TODO
        camp2.setImage("https://aucklandvolcanoes.files.wordpress.com/2015/12/mt-albert1.png?w=768");
        camp2.setFeatures("Motorhome plug / Potable Water / Shower / Toilet / Self Contained OK");
        camp2.setLAT(-36.890308);
        camp2.setLNG(174.720407);
        camp2.setPhone("0219456222");

        CampBean camp3 = new CampBean();
        camp3.setAddress("Heron Park, Waterview, Auckland 1026");
        camp3.setCname("Heron Park");
        camp3.setInfo("A nice tidy and cozy park, with kids playground there.");
        camp3.setUrl("https://cityvision.org.nz/news/planting-for-the-future-city-visions-commitment-to-the-environment/");
        //        camp3.setImage("https://tinyurl.com/mnc-campsite0001");
        camp3.setImage("https://cityvision.org.nz/wp-content/uploads/2016/10/WP_20160730_14_19_50_Pro-Copy-300x169.jpg");
        camp3.setFeatures("Motorhome plug / Potable Water");
        camp3.setLAT(-36.885698);
        camp3.setLNG(174.698070);
        camp3.setPhone("0219456333");
        //-36.885698, 174.698070

        CampBean camp4 = new CampBean();
        camp4.setAddress("34 Binsted Rd, New Lynn, Auckland 0600");
        camp4.setCname("Ken Maunder Park");
        camp4.setInfo("Good for a stop to eat lunch while on the run. Bring your own lunch of course.");
        camp4.setUrl("https://www.eventfinda.co.nz/venue/ken-maunder-park-auckland");
        camp4.setImage("https://i1.wp.com/in-the-back-of-the.net/wp-content/uploads/2012/05/19.jpg");
        camp4.setFeatures("Shower / Toilet");
        camp4.setLAT(-36.900853);
        camp4.setLNG(174.681559);
        camp4.setPhone("02194567444");
        //-36.900853, 174.681559

        lvCampsiteDao.createCampsite(camp1);
        lvCampsiteDao.createCampsite(camp2);
        lvCampsiteDao.createCampsite(camp3);
        lvCampsiteDao.createCampsite(camp4);

        MncUtilities.currentCampList = lvCampsiteDao.findAll();
    }
}
