
package com.example.myapplication.Activities;

        import android.content.Intent;
        import android.os.StrictMode;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.webkit.WebChromeClient;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.VideoView;

        import com.example.myapplication.R;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

        import static android.net.Uri.decode;

public class MainActivity extends AppCompatActivity {
    private Button quickGameButton;
    private Button createGameButton;
    private Button importButton;
    private Button resumeGameButton;
    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void initialize(){
        quickGameButton = findViewById(R.id.quickGameButton);
        createGameButton = findViewById(R.id.createGameButton);
        importButton = findViewById(R.id.importButton);
        resumeGameButton = findViewById(R.id.resumeGameButton);
        quickGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openQuickGame();
            }
        });
        createGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openCreateGame();
            }
        });
        importButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openImport();
            }
        });
        resumeGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openResumeGame();
            }
        });

    }

    public void openQuickGame(){
        Intent intent = new Intent(this, QuickGameActivity.class);
        startActivity(intent);
    }

    public void openCreateGame(){
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
    }

    public void openImport(){
        Intent intent = new Intent(this, ImportDataActivity.class);
        startActivity(intent);
    }

    public void openCreateAccount(){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void openSignIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void openResumeGame(){
        Intent intent = new Intent(this, ResumeGameActivity.class);
        startActivity(intent);
    }



}
