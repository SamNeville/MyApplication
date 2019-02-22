
package com.example.myapplication.Activities;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private Button quickGameButton;
    private Button createGameButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize(){
        quickGameButton = findViewById(R.id.quickGameButton);
        createGameButton = findViewById(R.id.createGameButton);
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

    }

    public void openQuickGame(){
        Intent intent = new Intent(this, QuickGameActivity.class);
        startActivity(intent);
    }

    public void openCreateGame(){
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
    }


}
