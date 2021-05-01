package com.example.sshconnection.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sshconnection.R;
import com.example.sshconnection.api.APIService;
import com.example.sshconnection.api.ApiUtils;
import com.example.sshconnection.ui.adapter.SSHCommandAdapter;
import com.example.sshconnection.ui.object.SSHCommandObject;
import com.example.sshconnection.utils.Connectivity;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SSHCommandAdapter.ItemClickListener {

  // initializing
  private RecyclerView commandList;
  private SSHCommandAdapter adapter;
  private TextView tvCommandResult;

  // url to hit
  private final static String API_URL = "https://6088cfd3a6f4a30017426f6c.mockapi.io/api/v1/ssh";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //init views
    commandList = findViewById(R.id.commandList);
    tvCommandResult = findViewById(R.id.tvCommandResult);
    tvCommandResult.setMovementMethod(new ScrollingMovementMethod());
    LinearLayoutManager llm = new GridLayoutManager(this, 2);
    commandList.setLayoutManager(llm);
    commandList.setHasFixedSize(true);
    commandList.setClipToPadding(true);
    adapter = new SSHCommandAdapter(this, new ArrayList<>(), this);
    commandList.setAdapter(adapter);

    //fetch from api and populate list
    fetchCommandList();
  }

  private void fetchCommandList() {

    if (Connectivity.isConnected(this)) {

      APIService mApiService = ApiUtils.getAPIService();
      mApiService.getCommandSSH(API_URL)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new DisposableObserver<String>() {
            @Override
            public void onNext(String result) {

              Log.e("response", result.toString());

              if (result != null && !result.equalsIgnoreCase("")) {
                try {

                  ArrayList<SSHCommandObject> commandList = new ArrayList<>();

                  JSONArray jsonArr = new JSONArray(result);

                  for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject obj = jsonArr.getJSONObject(i);
                    int id = obj.getInt("id");
                    String name = obj.getString("name");
                    String host = obj.getString("host");
                    String port = obj.getString("port");
                    String username = obj.getString("username");
                    String password = obj.getString("password");
                    String command = obj.getString("command");
                    String createdAt = obj.getString("createdAt");
                    String updatedAt = obj.getString("updatedAt");
                    int status = obj.getInt("status");

                    SSHCommandObject sshCommand = new SSHCommandObject();
                    sshCommand.setId(id);
                    sshCommand.setName(name);
                    sshCommand.setHost(host);
                    sshCommand.setPort(port);
                    sshCommand.setUsername(username);
                    sshCommand.setPassword(password);
                    sshCommand.setCommand(command);
                    sshCommand.setCreatedAt(createdAt);
                    sshCommand.setUpdatedAt(updatedAt);
                    sshCommand.setStatus(status);

                    commandList.add(sshCommand);
                  }

                  if (commandList.size() > 0) {
                    adapter.addData(commandList);
                  }
                } catch (Exception e) {

                  Toast.makeText(MainActivity.this, "" + e.getLocalizedMessage(),
                      Toast.LENGTH_SHORT).show();
                }
              } else {
                Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onError(Throwable e) {
              Toast.makeText(MainActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                  .show();
              e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
          });
    } else {
      Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
    }
  }

   // perform click on each commands
  @Override public void onItemClick(int position, SSHCommandObject sshCommand) {
    Thread thread = new Thread(new Runnable(){
      @Override
      public void run() {
        try {
          executeSSHcommand(sshCommand);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }

  @SuppressLint("SetTextI18n") public void executeSSHcommand(SSHCommandObject sshCommandObject) {

    String user = sshCommandObject.getUsername();
    String password = sshCommandObject.getPassword();
    String host = sshCommandObject.getHost();
    int port = Integer.parseInt(sshCommandObject.getPort());

    Log.e("command", sshCommandObject.getCommand());

    try {

      // create session connections
      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, port);
      session.setPassword(password);
      session.setConfig("StrictHostKeyChecking", "no");
      session.setTimeout(10000);
      session.connect();

      // create command channels
      ChannelExec channel = (ChannelExec) session.openChannel("exec");
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      channel.setOutputStream(baos);
      channel.setCommand(sshCommandObject.getCommand());
      channel.connect();
      try {
        Thread.sleep(1000);
      } catch (Exception ee) {
      }
      String commandResult = new String(baos.toByteArray());

      channel.disconnect();

      // show the command result in console ui
      tvCommandResult.setText(commandResult + "");
    } catch (JSchException e) {
      // show the error in console ui
      tvCommandResult.setText(e.getMessage() + "");
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (adapter != null) {
      adapter = null;
    }

    tvCommandResult.setText("");
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
  }
}