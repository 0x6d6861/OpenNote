package co.heri.dev.opennote;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    String name, email, password;

    EditText email_edit, password_edit, full_name_edit;

    String SERVER_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences prefs = getSharedPreferences("open_settings", MODE_PRIVATE);
        String restoredSetting = prefs.getString("ip", null);
        if (restoredSetting != null) {
            String ip_set = prefs.getString("ip", "No name defined");//"No name defined" is the default value.
            int port_set = prefs.getInt("port", 9292); //0 is the default value.
            SERVER_ADDRESS = ip_set + ":" + Integer.toString(port_set);
        }else{
            startActivity(new Intent(RegisterActivity.this, ServerSetActivity.class));
        }

         email_edit = (EditText) findViewById(R.id.email_edit);
         password_edit = (EditText) findViewById(R.id.pass_edit);
         full_name_edit = (EditText) findViewById(R.id.name_edit);

        findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(), MainActivity.class));

                email = email_edit.getText().toString();
                password = password_edit.getText().toString();
                name = full_name_edit.getText().toString();

                register(name, email, password);
            }
        });
    }



    private void register(String name, final String email, String password) {


        class RegisterAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(RegisterActivity.this, "Please wait", "Loading...");

            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String uemail = params[1];
                String pass = params[2];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", uname));
                nameValuePairs.add(new BasicNameValuePair("email", uemail));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://" + SERVER_ADDRESS + "/register"); // login link to be changed
//                    httpPost.setHeader("Content-type", "application/json");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                loadingDialog.dismiss();
                JSONObject jsonObj = null;
                JSONObject token = null;
                Boolean error = null;
                loadingDialog.dismiss();

                try {
                    jsonObj = new JSONObject(result);
                    error = jsonObj.getBoolean("error");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextView resultText = (TextView) findViewById(R.id.response_text);

                if (error) try {
                    resultText.setText(jsonObj.getString("message"));
                    Toast.makeText(getBaseContext(), jsonObj.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                else {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    Toast.makeText(getBaseContext(), "Registrations Successful", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(intent);
                }
            }
        }

        RegisterAsync Ra = new RegisterAsync();

        Ra.execute(name, email, password);

    }
}
