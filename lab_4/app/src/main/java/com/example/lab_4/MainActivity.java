package com.example.lab_4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private VideoView videoView;
    private Uri selectedFileUri;
    private MediaController mediaController;
    private int currentPosition = 0;
    private boolean isPlaying = false;
    private boolean isVideoVisible = false;
    private boolean isFileChooserOpen = false;
    private ImageView musicIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("videoPosition", 0);
            isPlaying = savedInstanceState.getBoolean("isPlaying", false);
            selectedFileUri = savedInstanceState.getParcelable("videoUri");
        }

        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        Button btnChooseFile = findViewById(R.id.btnChooseFile);
        Button btnStream = findViewById(R.id.btnStream);
        EditText urlInput = findViewById(R.id.urlInput);
        musicIcon = findViewById(R.id.musicIcon);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        btnChooseFile.setOnClickListener(v -> {
            isFileChooserOpen = true;
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            String[] mimeTypes = {"audio/*", "video/*"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, REQUEST_CODE);
        });

        btnStream.setOnClickListener(v -> {
            String url = urlInput.getText().toString();
            if (!url.isEmpty()) {
                Uri videoUri = Uri.parse(url);
                playVideo(videoUri);
            } else {
                Toast.makeText(this, "Введіть URL", Toast.LENGTH_SHORT).show();
            }
        });

        if (selectedFileUri != null) {
            videoView.setVisibility(View.VISIBLE);
            findViewById(R.id.controlPanel).setVisibility(View.GONE);
            videoView.setVideoURI(selectedFileUri);
            videoView.setOnPreparedListener(mp -> {
                videoView.seekTo(currentPosition);
                if (isPlaying) {
                    videoView.start();
                } else {
                    videoView.pause();
                }
            });
            isVideoVisible = true;
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isVideoVisible) {
                    videoView.stopPlayback();
                    videoView.setVisibility(View.GONE);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    isVideoVisible = false;
                    findViewById(R.id.controlPanel).setVisibility(View.VISIBLE);
                    findViewById(R.id.musicIcon).setVisibility(View.GONE);
                    EditText urlInput = findViewById(R.id.urlInput);
                    urlInput.setText("");
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                playVideo(selectedFileUri);
            }
        }
    }

    private void playVideo(Uri uri) {
        currentPosition = 0;
        videoView.setVisibility(View.VISIBLE);
        isVideoVisible = true;
        videoView.setVideoURI(uri);

        String mimeType;
        if (uri.getScheme() != null && uri.getScheme().startsWith("http")) {
            mimeType = URLConnection.guessContentTypeFromName(uri.toString());
        } else {
            mimeType = getContentResolver().getType(uri);
        }

        if (mimeType != null && mimeType.startsWith("audio")) {
            musicIcon.setVisibility(View.VISIBLE);
        } else {
            musicIcon.setVisibility(View.GONE);
        }

        videoView.setOnPreparedListener(mp -> {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            videoView.start();
            isPlaying = true;
        });

        findViewById(R.id.controlPanel).setVisibility(View.GONE);
        Toast.makeText(this, "Відтворення...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("videoPosition", currentPosition);
        outState.putBoolean("isPlaying", isPlaying);
        outState.putParcelable("videoUri", selectedFileUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null && isVideoVisible) {
            currentPosition = videoView.getCurrentPosition();
            isPlaying = videoView.isPlaying();
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null && isVideoVisible && selectedFileUri != null) {
            videoView.setVideoURI(selectedFileUri);
            videoView.setOnPreparedListener(mp -> {
                videoView.seekTo(currentPosition);
                if (isPlaying) {
                    videoView.start();
                }
                if (isFileChooserOpen) {
                    videoView.start();
                    isFileChooserOpen = false;
                }
            });
        }
    }
}
