package com.novelot.fruitmusic;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.novelot.fruitmusic.core.IOnServiceConnectComplete;
import com.novelot.fruitmusic.core.MusicData;
import com.novelot.fruitmusic.core.ServiceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by V on 2015/6/21.
 */
public class MyApplication extends Application {

    public static ServiceManager manager;
    public static List<MusicData> fileList;

    @Override
    public void onCreate() {
        super.onCreate();

        if (manager == null) {
            synchronized (PlayControlReceiver.class) {
                if (manager == null) {
                    manager = new ServiceManager(this);
                    manager.connectService();
                    manager.setOnServiceConnectComplete(new IOnServiceConnectComplete() {
                                                            @Override
                                                            public void OnServiceConnectComplete() {
                                                                fileList = manager.getFileList();
                                                                if (fileList == null || fileList.isEmpty()) {
                                                                    fileList = getMusicFileList(MyApplication.this);
                                                                    manager.refreshMusicList(fileList);
                                                                }
                                                            }
                                                        }

                    );
                }
            }
        }
    }


    private List<MusicData> getMusicFileList(Context context) {
        List<MusicData> list = new ArrayList<MusicData>();

        String[] projection = new String[]{MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ARTIST};

        long time1 = System.currentTimeMillis();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int colNameIndex = cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE);
            int colTimeIndex = cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION);
            int colPathIndex = cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA);
            int colArtistIndex = cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST);

            int fileNum = cursor.getCount();
            for (int counter = 0; counter < fileNum; counter++) {

                MusicData data = new MusicData();
                data.mMusicName = cursor.getString(colNameIndex);
                data.mMusicTime = cursor.getInt(colTimeIndex);
                data.mMusicPath = cursor.getString(colPathIndex);
                data.mMusicAritst = cursor.getString(colArtistIndex);

                list.add(data);
                cursor.moveToNext();
            }

            cursor.close();
        }
        long time2 = System.currentTimeMillis();

        // Log.i(TAG, "seach filelist cost = " + (time2 - time1));
        return list;
    }
}
