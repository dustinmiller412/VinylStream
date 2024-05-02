package com.example.vinylstream;

import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private TextView streamStatusText;
    private TextView castStatusText;  // TextView for displaying the cast status
    private CastContext castContext;
    private CastStateListener castStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        setupMediaPlayer();

        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);
        streamStatusText = findViewById(R.id.stream_status);
        castStatusText = findViewById(R.id.cast_status);  // Initialize the cast status TextView

        playButton.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });

        pauseButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        stopButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                setupMediaPlayer();
            }
        });

        // Initialize the cast context
        try {
            castContext = CastContext.getSharedInstance(this);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to load CastContext", e);
        }

        // Cast state listener to update UI based on casting status
        castStateListener = newState -> {
            String status;
            switch (newState) {
                case CastState.NO_DEVICES_AVAILABLE:
                    status = "No Cast devices available";
                    break;
                case CastState.NOT_CONNECTED:
                    status = "Cast device not connected";
                    break;
                case CastState.CONNECTING:
                    status = "Connecting to Cast device";
                    break;
                case CastState.CONNECTED:
                    status = "Cast device connected";
                    break;
                default:
                    status = "Status Unknown";
                    break;
            }
            final String finalStatus = status;
            runOnUiThread(() -> castStatusText.setText(finalStatus));
        };

        checkStreamStatus();
    }

    private void setupMediaPlayer() {
        try {
            mediaPlayer.setDataSource("http://192.168.1.67:8000/stream");
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            streamStatusText.setText("Error setting data source");
            streamStatusText.setTextColor(Color.RED);
        }

        mediaPlayer.setOnPreparedListener(mp -> {
            streamStatusText.setText("Stream ready");
            streamStatusText.setTextColor(Color.GREEN);
            playButton.setEnabled(true);
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            streamStatusText.setText("Playback Error");
            streamStatusText.setTextColor(Color.RED);
            mediaPlayer.reset();
            setupMediaPlayer();
            return true;
        });
    }

    private void checkStreamStatus() {
        new Thread(() -> {
            try {
                URL url = new URL("http://192.168.1.67:8000/stream");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                Log.d("StreamCheck", "HTTP response code: " + responseCode);
                if (responseCode == 200) {
                    runOnUiThread(() -> {
                        streamStatusText.setText("Stream is active and ready");
                        streamStatusText.setTextColor(Color.GREEN);
                    });
                } else {
                    runOnUiThread(() -> {
                        streamStatusText.setText("Stream is not available");
                        streamStatusText.setTextColor(Color.RED);
                    });
                }
            } catch (Exception e) {
                Log.e("StreamCheck", "Error checking stream status", e);
                runOnUiThread(() -> {
                    streamStatusText.setText("Error checking stream status");
                    streamStatusText.setTextColor(Color.RED);
                });
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        castContext.addCastStateListener(castStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        castContext.removeCastStateListener(castStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
