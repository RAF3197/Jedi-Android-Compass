package com.jediupc.helloandroid.musicplayer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.jediupc.helloandroid.R;

import java.util.ArrayList;

public class MusicService extends Service {

    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_STOP = "action_stop";
    public static final String CHANNEL_ID = "NOTIFICATION";
    public static final int NOTIFICATION_ID = 420;
    public static final String ACTION_PERMISSION_GRANTED = "action_permission_granted";

    MediaPlayer mMediaPlayer;
    private ArrayList<MusicModel> mMusic;

    public MusicService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();

        createNotificationChannel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;
        if (intent.getAction() == null) return START_NOT_STICKY;

        if (intent.getAction().equals(ACTION_PERMISSION_GRANTED)){
            //mMusic = ;
        }

        if (intent.getAction().equals(ACTION_PLAY)) {
            if (intent.hasExtra("path")) {
                stopMusic();
                String path = intent.getStringExtra("path");
                Uri uri = Uri.parse(path);
                mMediaPlayer = MediaPlayer.create(this, uri);
                startMusic();
            }
            startMusic();
        }

        if (intent.getAction().equals(ACTION_PAUSE)) {
            pauseMusic();
        }

        if (intent.getAction().equals(ACTION_STOP)) {
            stopMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "default";
            String description = "default";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startMusic(){
        if (!isPlaying()) mMediaPlayer.start();

        updateNotification();
    }

    private void updateNotification() {
        Intent intent = new Intent(this, MusicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String playPauseAction = isPlaying() ? "Pause":"Play";

        PendingIntent piPlayPause = getPendingIntent(isPlaying() ? ACTION_PAUSE : ACTION_PLAY);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID).
                setContentTitle("Music Player").setContentText("Playing X")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)
                .setOngoing(isPlaying())
                .addAction(0,playPauseAction,piPlayPause);

        //Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define

        if (isPlaying()) startForeground(NOTIFICATION_ID,builder.build());
        else{
            stopForeground(true);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }



    private boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    private void pauseMusic(){
        if (isPlaying()){
            mMediaPlayer.pause();
            updateNotification();
        }

    }

    private void stopMusic(){
        if (isPlaying()) mMediaPlayer.stop();
    }

    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(this, 0, intent, 0);
    }
}
