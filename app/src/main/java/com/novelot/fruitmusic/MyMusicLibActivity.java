package com.novelot.fruitmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.novelot.fruitmusic.core.IOnServiceConnectComplete;
import com.novelot.fruitmusic.core.ServiceManager;


public class MyMusicLibActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private ServiceManager mServiceManager;
    private static final boolean TEST = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TEST) {
            setContentView(R.layout.activity_test);

        } else {
            setContentView(R.layout.activity_my_music_lib);
            initToolbar();
            initPager();
            initMusicService();
        }
    }

    private void initMusicService() {
        mServiceManager = new ServiceManager(this);
        mServiceManager
                .setOnServiceConnectComplete(new IOnServiceConnectComplete() {

                    @Override
                    public void OnServiceConnectComplete() {
                    }
                });
        mServiceManager.connectService();
    }

    private View mTopView;
    private float y0;
    private View mBackView;
    private float x0;
    private View vContraler;

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent event) {
//
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			y0 = event.getY();
//			x0 = event.getX();
//
//			Rect outRect = new Rect();
//			vContraler.getHitRect(outRect);
//			if (outRect.contains((int) x0, (int) y0)) {
//				return true;
//			}
//			break;
//		case MotionEvent.ACTION_MOVE:
//
//			float dY = event.getY() - y0;
//			Log.v("move", dY + "");
//			if (dY < 0) {
//				/* 向上滑动 */
//				if (mTopView.getY() > -mTopView.getHeight()
//						&& mTopView.getY() <= 0) {
//					mTopView.setY(Math.max(mTopView.getY() + dY,
//							-mTopView.getHeight()));
//
//				}
//
//				if (mBackView.getY() > -mBackView.getHeight()
//						&& mBackView.getY() <= 0) {
//					mBackView.setY(Math.max(mBackView.getY() + dY * 0.6f,
//							-mBackView.getHeight()));
//				}
//			} else {
//				/* 向下滑动 */
//				if (mTopView.getY() >= -mTopView.getHeight()
//						&& mTopView.getY() < 0) {
//					mTopView.setY(Math.min(mTopView.getY() + dY, 0));
//				}
//
//				if (mBackView.getY() >= -mBackView.getHeight()
//						&& mBackView.getY() < 0) {
//					mBackView.setY(Math.min(mBackView.getY() + dY * 0.6f, 0));
//				}
//			}
//
//			y0 = event.getY();
//
//			// x
//			// float dX = event.getX() - x0;
//			// if (Math.abs(dX) > 10) {
//			// ObjectAnimator.ofFloat(mTopView, "Y", 0).setDuration(200)
//			// .start();
//			// }
//
//			break;
//		case MotionEvent.ACTION_UP:
//			if (mTopView.getY() >= -mTopView.getHeight()
//					&& mTopView.getY() < -mTopView.getHeight() / 2) {
//				// mTopView.setY(-mTopView.getHeight());
//				ObjectAnimator.ofFloat(mTopView, "Y", -mTopView.getHeight())
//						.setDuration(100).start();
//			} else {
//				// mTopView.setY(0);
//				ObjectAnimator.ofFloat(mTopView, "Y", 0).setDuration(200)
//						.start();
//			}
//			break;
//		}
//		// return v.onTouchEvent(event);
//
//		return super.dispatchTouchEvent(event);
//	}

    private void initPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 1:
                        return new ArtistFragment();
                    case 2:
                        return new AlbumFragment();
                    case 0:
                        return new MusicListFragment();
                    default:
                        return new MusicListFragment();
                }

            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "歌曲";
                    case 1:
                        return "音乐人";
                    case 2:
                        return "专辑";
                    case 3:
                        return "流派";
                }
                return null;
            }
        };
        mViewPager.setAdapter(mAdapter);
//		mIndicator.setViewPager(mViewPager);
//		mIndicator.setFades(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_music_lib, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        vContraler = findViewById(R.id.contraler);
//		mIndicator = (UnderlinePageIndicator) findViewById(R.id.titles);
        mTopView = findViewById(R.id.t);
        mBackView = findViewById(R.id.b);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.title_activity_my_music_lib);// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        drawerToggle.syncState();
        mDrawerLayout.setDrawerListener(drawerToggle);

        //

        // ActionBar actionBar = getSupportActionBar();
        // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // for (int i = 0; i < mAdapter.getCount(); i++) {
        // actionBar.addTab(
        // actionBar.newTab().setText(mAdapter.getPageTitle(i))
        // /* .setTabListener(this) */, i);
        // }
    }


    public void play(View v) {
        sendBroadcast(new Intent(PlayAction.PLAY));
    }

    public void pause(View v) {
        sendBroadcast(new Intent(PlayAction.PAUSE));
    }
}
