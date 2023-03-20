package com.tech.apexnews;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Formatter;
import java.util.Locale;

public class CustomMediaController extends MediaController {
    private MediaPlayerControl mPlayer;
    private Context mContext;
    private View mAnchorView;
    private ProgressBar mProgress;
    private TextView mEndTime, mCurrentTime;
    private boolean mShowing;
    private ImageButton mPauseButton;
    private ImageButton mFullscreenButton;
    private static final int sDefaultTimeout = 3000;

    public CustomMediaController(Context context, View anchorView) {
        super(context);
        mContext = context;
        mAnchorView = anchorView;
        init();
    }

    private void init() {
        mPauseButton = new ImageButton(mContext);
        mPauseButton.setImageResource(R.drawable.ic_pause);
        mPauseButton.setBackgroundResource(R.drawable.bg_button);
        mPauseButton.setOnClickListener(mPauseListener);

        mFullscreenButton = new ImageButton(mContext);
        mFullscreenButton.setImageResource(R.drawable.ic_fullscreen);
        mFullscreenButton.setBackgroundResource(R.drawable.bg_button);
        mFullscreenButton.setOnClickListener(mFullscreenListener);

        mProgress = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        mProgress.setMax(100);
        mProgress.setVisibility(View.GONE);

        addView(mPauseButton, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mProgress, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mFullscreenButton, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mCurrentTime = new TextView(mContext);
        mCurrentTime.setTextColor(Color.WHITE);
        mCurrentTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mCurrentTime.setPadding(0, 0, 10, 0);
        mCurrentTime.setText("00:00");

        mEndTime = new TextView(mContext);
        mEndTime.setTextColor(Color.WHITE);
        mEndTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mEndTime.setText("00:00");

        addView(mCurrentTime, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mEndTime, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setMediaPlayer(MediaPlayerControl player) {
        mPlayer = player;
        updatePausePlay();
        updateFullScreen();
    }

    public void show(int timeout) {
        if (!mShowing && mAnchorView != null) {
            setProgress();
            mPauseButton.requestFocus();
            bringToFront();
            setVisibility(View.VISIBLE);
            mShowing = true;
        }
        updatePausePlay();
        updateFullScreen();
        removeCallbacks(mFadeOut);
        if (timeout != 0) {
            postDelayed(mFadeOut, timeout);
        }
    }

    public void hide() {
        if (mShowing) {
            try {
                removeCallbacks(mFadeOut);
                setVisibility(View.GONE);
            } catch (IllegalArgumentException ex) {
                Log.d("CustomMediaController", "Already removed");
            }
            mShowing = false;
        }
    }

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final OnClickListener mPauseListener = new OnClickListener() {
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    private final OnClickListener mFullscreenListener = new OnClickListener() {
        public void onClick(View v) {
            doToggleFullscreen();
            show(sDefaultTimeout);
        }
    };

    private void updatePausePlay() {
        if (mPlayer.isPlaying()) {
            mPauseButton.setImageResource(R.drawable.ic_pause);
        } else {
            mPauseButton.setImageResource(R.drawable.ic_play);
        }
    }

    private void updateFullScreen() {
        if (mPlayer.isFullScreen()) {
            mFullscreenButton.setImageResource(R.drawable.ic_exit_fullscreen);
        } else {
            mFullscreenButton.setImageResource(R.drawable.ic_fullscreen);
        }
    }

    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }

    private void doToggleFullscreen() {
        mPlayer.toggleFullScreen();
    }

    private int setProgress() {
        if (mPlayer == null) {
            return 0;
        }

        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                long pos = 100L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (mEndTime != null) {
            mEndTime.setText(stringForTime(duration));
        }
        if (mCurrentTime != null) {
            mCurrentTime.setText(stringForTime(position));
        }

        return position;
    }

    private static String stringForTime(int timeMs) {
        Formatter formatter = new Formatter(Locale.getDefault());
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return formatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
    }

    @Override
    public void show() {
        super.show(0);
    }

    public interface MediaPlayerControl {
        void start();

        void pause();

        int getDuration();

        int getCurrentPosition();

        void seekTo(int pos);

        boolean isPlaying();

        int getBufferPercentage();

        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        boolean isFullScreen();

        void toggleFullScreen();
    }


}
