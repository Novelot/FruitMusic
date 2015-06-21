package com.novelot.fruitmusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.novelot.fruitmusic.core.MusicData;
import com.novelot.fruitmusic.core.ServiceManager;

import java.util.ArrayList;
import java.util.List;

public class PlayControlReceiver extends BroadcastReceiver {


    public PlayControlReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null && intent != null) {

            if (PlayAction.PLAY.equals(intent.getAction())) {
                Controller.play();
            } else if (PlayAction.PAUSE.equals(intent.getAction())) {
                Controller.pause();
            } else if (PlayAction.STOP.equals(intent.getAction())) {
                Controller.stop();
            } else if (PlayAction.PLAY_NEXT.equals(intent.getAction())) {
                Controller.playNext();
            } else if (PlayAction.PLAY_PRE.equals(intent.getAction())) {
                Controller.playPre();
            }
        }
    }


    private static class Controller {
        static int mCurrentIndex = 0;

        private static void play() {
            MyApplication.manager.play(mCurrentIndex);
        }

        private static void pause() {
            MyApplication.manager.pause();
        }

        private static void stop() {
            MyApplication.manager.stop();
        }

        private static void playNext() {
            mCurrentIndex++;
            if (MyApplication.fileList != null && !MyApplication.fileList.isEmpty()) {
                if (mCurrentIndex > (MyApplication.fileList.size() - 1)) {
                    mCurrentIndex = 0;
                }
                MyApplication.manager.play(mCurrentIndex);
            }
        }

        private static void playPre() {
            mCurrentIndex--;
            if (mCurrentIndex < 0)
                mCurrentIndex = 0;
            MyApplication.manager.play(mCurrentIndex);
        }

    }


}
