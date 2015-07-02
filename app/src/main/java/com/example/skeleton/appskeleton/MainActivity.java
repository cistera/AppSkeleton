package com.example.skeleton.appskeleton;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.skeleton.appskeleton.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //TODO: POST somewhere


    public void getMessage(View view) {

        //EditText editText = (EditText) findViewById(R.id.edit_message);


        //String message = getSSID();//editText.getText().toString();

        GetMesssageTask asyncGet = new GetMesssageTask();
        asyncGet.execute();



    }

    public String getSSID() {
        WifiManager m = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = m.getConnectionInfo();
        return info.getSSID();
    }

    public void button() {
        //Fill this in, send the currently connected SSID (with some text) to the cloud server
        //Provide ... what? HTTP (json?) Post method, blank activity w/ hello world (or text box), getSSID skeleton
        //Do not provide .. getSSID implementation, manifest permissions for wifi, the button, link between button and text box
    }

    public void switchActivity(String response){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, response);
        startActivity(intent);
    }

//TODO: should they touch this to post?
    private class GetMesssageTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return HttpUtil.DoHttpGetRequest(HttpUtil.getApiUrl() + "data", "");
        }

        @Override
        protected void onPostExecute(String response){
            switchActivity(response);
        }
    }
}
