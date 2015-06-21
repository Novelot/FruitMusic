package com.novelot.fruitmusic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * 专辑
 * 
 * @author V
 * 
 */
public class AlbumFragment extends Fragment {

	private GridView mGridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_artist, null);
		mGridView = (GridView) v.findViewById(R.id.list);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Set<String> mData = new HashSet<String>();
		Cursor cursor = getActivity().getContentResolver().query(
				Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		if (cursor != null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				mData.add(cursor.getString(cursor.getColumnIndex(Media.ALBUM)));

			}
			cursor.close();
			cursor = null;
		}

		mGridView
				.setAdapter(new ArrayAdapter<String>(getActivity(),
						R.layout.item, android.R.id.text1,
						new ArrayList<String>(mData)));
	}
}
