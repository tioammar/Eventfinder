package inforuh.eventfinder.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import inforuh.eventfinder.R;
import inforuh.eventfinder.sync.SyncAdapter;
import inforuh.eventfinder.ui.fragment.ExhibitionFragment;
import inforuh.eventfinder.ui.fragment.MusicFragment;
import inforuh.eventfinder.ui.fragment.SportFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            TextView titleView = (TextView)findViewById(R.id.app_name);
            String title = getString(R.string.logo_name).toUpperCase();
            titleView.setText(title);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_layout);

        if (viewPager == null){
            return;
        }

        setUpViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SyncAdapter.initialize(this);
    }

    private void setUpViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.add(new MusicFragment(), getString(R.string.music_title));
        adapter.add(new ExhibitionFragment(), getString(R.string.exhibition_title));
        adapter.add(new SportFragment(), getString(R.string.sport_title));
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentsTitle = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void add(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentsTitle.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentsTitle.get(position);
        }
    }
}
