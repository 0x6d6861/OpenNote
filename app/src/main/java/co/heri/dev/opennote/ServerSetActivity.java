package co.heri.dev.opennote;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ServerSetActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_set);
//        this.setFinishOnTouchOutside(false);
        final EditText ip = (EditText) findViewById(R.id.ip_edit);
        final EditText port = (EditText) findViewById(R.id.port_edit);
        Button save_btn = (Button) findViewById(R.id.save_btn);

        SharedPreferences prefs = getSharedPreferences("open_settings", MODE_PRIVATE);
        String restoredSetting = prefs.getString("ip", null);
        if (restoredSetting != null && !restoredSetting.isEmpty()) {
            String ip_set = prefs.getString("ip", "192.168.0.20");//"No name defined" is the default value.
            int port_set = prefs.getInt("port", 9292); //0 is the default value.
            ip.setText(ip_set);
            port.setText( Integer.toString(port_set));

        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("open_settings", MODE_PRIVATE).edit();
                editor.putString("ip", ip.getText().toString());
                editor.putInt("port", Integer.parseInt(port.getText().toString()));
                editor.commit();
                finish();
            }
        });
    }
}
