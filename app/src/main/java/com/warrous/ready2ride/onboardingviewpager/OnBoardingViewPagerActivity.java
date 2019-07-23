package com.warrous.ready2ride.onboardingviewpager;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.auth.login.LoginActivity;
import com.warrous.ready2ride.auth.signup.SignupActivity;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.framework.ActivityUtils;

import butterknife.BindView;


public class OnBoardingViewPagerActivity extends BaseActivity implements View.OnClickListener{
    private ImageView[] indicators;
    private SectionsPagerAdapter mSectionsPagerAdapter;
   public int viewPosition=0;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private int[] colorList;
    private ArgbEvaluator evaluator;
//    TextView tvLogin,tvHeader,tvDescp;
TextView tvLogin;
    Button btnSignup;
    RelativeLayout rlMainLayout;
  //  ImageView ivLogo;
    @BindView(R.id.iv_logof)
    ImageView ivLogoF;

    @BindView(R.id.tv_header_mainf)
    TextView tvHeaderF;

    @BindView(R.id.tv_descpf)
    TextView tvDecsF;





    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager_test);


        injectButterKnife(this);

//        btnSkip = (Button) findViewById(R.id.footer_control_button_skip);
//        btnFinish = (Button) findViewById(R.id.footer_control_button_finish);
//        btnNext =  findViewById(R.id.footer_control_button_next);
//        tvName=findViewById(R.id.section_label);
       tvLogin=findViewById(R.id.tv_login);


      //  tvLogin=findViewById(R.id.tv_login);
        btnSignup=findViewById(R.id.btn_signup);
        rlMainLayout=findViewById(R.id.rl_main_layout);
        //ivLogo=findViewById(R.id.iv_logo);

       // tvHeader=findViewById(R.id.tv_header_main);
        //tvDescp=findViewById(R.id.tv_descp);


        indicators = new ImageView[]{
               findViewById(R.id.footer_control_indicator_1),
               findViewById(R.id.footer_control_indicator_2),
                findViewById(R.id.footer_control_indicator_3),
              findViewById(R.id.footer_control_indicator_4)};



        colorList = new int[]{
                Color.parseColor("#FFFFFF"),
                Color.parseColor("#FFFFFF"),
                Color.parseColor("#FFFFFF"),
                Color.parseColor("#FFFFFF")};
        evaluator = new ArgbEvaluator();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ivLogoF.setVisibility(View.VISIBLE);
        tvDecsF.setVisibility(View.GONE);
        tvHeaderF.setVisibility(View.GONE);
        // Set up the ViewPager with the sections adapter.
        mViewPager =  findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setBackgroundColor(getResources().getColor(R.color.coloronboard1));
        mViewPager.setBackgroundResource(R.drawable.ic_onboard1);
        mViewPager.setClipToPadding(false);

       // mViewPager.setPadding(130,0,130,0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // int colorUpdate = (Integer) evaluator.evaluate(positionOffset,
                        //colorList[position], position == colorList.length-1 ? colorList[position] : colorList[position+1]);
              //  mViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                // update indicator
                page = position;
                updateIndicator(position);
             //   mViewPager.setBackgroundColor(colorList[position]);
                viewPosition=position;



//                btnSkip.setVisibility(position == 2? View.INVISIBLE: View.VISIBLE);
//                btnNext.setVisibility(position == 2? View.GONE : View.VISIBLE);
//                btnFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                if(position==0){

//                    ivLogo.setVisibility(View.GONE);
//                    tvDescp.setVisibility(View.GONE);
//                    tvHeader.setVisibility(View.GONE);
                    tvHeaderF.setVisibility(View.GONE);
                    tvDecsF.setVisibility(View.GONE);
                    mViewPager.setBackgroundColor(getResources().getColor(R.color.coloronboard1));
                    mViewPager.setBackgroundResource(R.drawable.ic_onboard1);
                    ivLogoF.setVisibility(View.VISIBLE);
                   // tvName.setText(getResources().getString(R.string.try_email_marketing));
                }else if(position==1){
//                    ivLogo.setVisibility(View.GONE);
//                    tvDescp.setVisibility(View.GONE);
//                    tvHeader.setVisibility(View.GONE);
                    ivLogoF.setVisibility(View.GONE);
                    tvDecsF.setVisibility(View.VISIBLE);
                    tvHeaderF.setVisibility(View.VISIBLE);
                    tvHeaderF.setText("Bike Stuff");
                    mViewPager.setBackgroundColor(getResources().getColor(R.color.coloronboard2));
                    mViewPager.setBackgroundResource(R.drawable.ic_onboard2);

                  //  tvName.setText(getResources().getString(R.string.create_n_send_emails));
                }else if(position==2){

//                    ivLogo.setVisibility(View.GONE);
//                    tvDescp.setVisibility(View.GONE);
//                    tvHeader.setVisibility(View.GONE);
                    ivLogoF.setVisibility(View.GONE);
                    //ivLogo.setVisibility(View.GONE);

                    tvDecsF.setVisibility(View.VISIBLE);
                    tvHeaderF.setVisibility(View.VISIBLE);
                    tvHeaderF.setText("Tracking");
                    mViewPager.setBackgroundColor(getResources().getColor(R.color.coloronboard2));
                    mViewPager.setBackgroundResource(R.drawable.ic_onboard3);

                  //  tvName.setText(getResources().getString(R.string.view_reporting));
                }else {
//                    ivLogo.setVisibility(View.GONE);
//                    tvDescp.setVisibility(View.GONE);
//                    tvHeader.setVisibility(View.GONE);
                    ivLogoF.setVisibility(View.GONE);
                   // ivLogo.setVisibility(View.GONE);
                    tvDecsF.setVisibility(View.VISIBLE);
                    tvHeaderF.setVisibility(View.VISIBLE);
                    tvHeaderF.setText("Connect");
                    mViewPager.setBackgroundColor(getResources().getColor(R.color.coloronboard2));
                    mViewPager.setBackgroundResource(R.drawable.ic_onboard4);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//
//        btnNext.setOnClickListener(this);
//        btnSkip.setOnClickListener(this);
//        btnFinish.setOnClickListener(this);
//        tvLogin.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    private void updateIndicator(int position){
        for (int i = 0; i < indicators.length; i++) {
            if(i == position){
                indicators[i].setImageResource(R.drawable.indicator_selected);
            }else
                indicators[i].setImageResource(R.drawable.ic_footer_oval);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_login:
                ActivityUtils.startActivity(OnBoardingViewPagerActivity.this,LoginActivity.class,null);
                break;

            case R.id.btn_signup:
                ActivityUtils.startActivity(OnBoardingViewPagerActivity.this,SignupActivity.class,null);
                break;

//            case R.id.footer_control_button_next:
//                page++;
//                mViewPager.setCurrentItem(page, true);
//                break;
//            case R.id.footer_control_button_finish:
//               // ActivityUtils.startActivity(OnBoardingViewPagerActivity.this,LoginActivity.class,null);
//                break;
//            case R.id.footer_control_button_skip:
//               // ActivityUtils.startActivity(OnBoardingViewPagerActivity.this,LoginActivity.class,null);
//                break;
//            case R.id.tv_login:
//              //  ActivityUtils.startActivity(OnBoardingViewPagerActivity.this,LoginActivity.class,null);
//                break;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_onboarding, container, false);
           TextView headerMain = (TextView) rootView.findViewById(R.id.tv_header_main);
            TextView headerDescription = (TextView) rootView.findViewById(R.id.tv_descp);

            int sectNumb = getArguments().getInt(ARG_SECTION_NUMBER);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_logo);
           // imageView.setVisibility(View.GONE);
           // headerMain.setVisibility(View.VISIBLE);
          //  headerDescription.setVisibility(View.VISIBLE);

           // String [] headerText={"","Bike Stuff","Tracking", "Connect"};
        //    headerMain.setText(headerText[sectNumb]);


           //int[] images = {R.drawable.onboardng_email_icon, R.drawable.onboarding_create_icon, R.drawable.onboarding_report_icon};
          //  imageView.setImageResource(images[sectNumb]);
          //  headerMain.setText(getString(R.string.section_format, sectNumb));

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
