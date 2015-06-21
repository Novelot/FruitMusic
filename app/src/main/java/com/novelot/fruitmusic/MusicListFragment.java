package com.novelot.fruitmusic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MusicListFragment extends ListFragment {

	private MediaPlayer mPlayer;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<String> mData = new ArrayList<String>();
		Cursor cursor = getActivity().getContentResolver().query(
				Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		if (cursor != null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				mData.add(cursor.getString(cursor
						.getColumnIndex(Media.DISPLAY_NAME)));

			}
			cursor.close();
			cursor = null;
		}

		setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item,
				android.R.id.text1, mData));
		//
		mPlayer = new MediaPlayer();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String name = (String) l.getItemAtPosition(position);
		// byte[] path = cursor.getBlob(cursor.getColumnIndex(Media.DATA));
		Cursor cursor = getActivity().getContentResolver().query(
				Media.EXTERNAL_CONTENT_URI, null,
				Media.DISPLAY_NAME + "=?", new String[] { name }, null);
		String path = null;
		if (cursor != null && cursor.moveToFirst() && !cursor.isAfterLast()) {
			byte[] bPath = cursor.getBlob(cursor.getColumnIndex(Media.DATA));
			path = new String(bPath, 0, bPath.length - 1);
			cursor.close();
			cursor = null;
		}
		if (!TextUtils.isEmpty(path)) {
			try {
				if (mPlayer.isPlaying()) {
					mPlayer.stop();
					mPlayer.reset();
				}
				mPlayer.setDataSource(path);
				mPlayer.prepare();
				mPlayer.start();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_music_list, null);
		return v;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPlayer.isPlaying()) {
			mPlayer.stop();
		}
		mPlayer.release();
		mPlayer = null;
	}
}
