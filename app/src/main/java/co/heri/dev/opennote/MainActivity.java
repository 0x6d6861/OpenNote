package co.heri.dev.opennote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Read shared toekn and email from shared perefence.
        Intent intent = getIntent();
        String ueamil = intent.getStringExtra(LoginActivity.USER_TOKEN);

        TextView textView = (TextView) findViewById(R.id.hello_message);

        textView.setText("Token Details: " + ueamil);
    }
}
