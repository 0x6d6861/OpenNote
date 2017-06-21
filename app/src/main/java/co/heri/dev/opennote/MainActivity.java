package co.heri.dev.opennote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.heri.dev.opennote.Model.Task;

public class MainActivity extends AppCompatActivity {
    private List<Task> taskList = new ArrayList<>();
    private RecyclerView task_recycler;
    private ListView task_ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task_ListView = (ListView) findViewById(R.id.task_list_view);
        



        // TODO: Read shared toekn and email from shared perefence.
        Intent intent = getIntent();
        String ueamil = intent.getStringExtra(LoginActivity.USER_TOKEN);

        TextView textView = (TextView) findViewById(R.id.hello_message);

        textView.setText("Token Details: " + ueamil);
    }


    private void prepareTaskData() {
        // load the tasks
    }
}
