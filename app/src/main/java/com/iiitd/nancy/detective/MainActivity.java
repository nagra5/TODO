package com.iiitd.nancy.detective;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Nancy on 9/5/15.
 */
public class MainActivity extends Activity {

    private TextView mainScreenText;
    private static final String TAG = "MainActivity";
    private final String ACTION_RINGER_CHANGED = "android.media.RINGER_MODE_CHANGED";
    private final String WIFI_STATE_CHANGED = "android.net.wifi.WIFI_STATE_CHANGED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Entered onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainScreenText = (TextView)findViewById(R.id.txt1);
        mainScreenText.setText("This app detects various phone states.");
        //this.registerReceiver(this.batteryInformationReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        this.registerReceiver(this.batteryLowReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        this.registerReceiver(this.batteryOkayReceiver, new IntentFilter(Intent.ACTION_BATTERY_OKAY));
        this.registerReceiver(this.powerConnectedReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        this.registerReceiver(this.powerDisconnectedReceiver, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        this.registerReceiver(this.ringerChangedReceiver, new IntentFilter(ACTION_RINGER_CHANGED));
        this.registerReceiver(this.wifiStateChangedReceiver, new IntentFilter(WIFI_STATE_CHANGED));
        Log.i(TAG, "Exited onCreate");
    }

    /**
     * This section contains the broadcast receivers.
     */
    //WIFI_STATE_CHANGED
    private BroadcastReceiver wifiStateChangedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Entered onReceive of wifiStateChangedReceiver");
            int  wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,-1);

            String action = intent.getAction();
            if(WIFI_STATE_CHANGED.equals(action))
            {
                //displayAlert(5);
                switch(wifiState){
                    case 0: displayAlert(6); //wifi state disabling
                        break;
                    case 1: displayAlert(7); //wifi state disabled
                        break;
                    case 2: displayAlert(8); //wifi state enabling
                        break;
                    case 3: displayAlert(9); //wifi state enabled
                        break;
                    case 4: displayAlert(10); //wifi state unknown
                }
            }
            Log.i(TAG, "Exited onReceive of wifiStateChangedReceiver");
        }
    };

    //ACTION_POWER_DISCONNECTED
    private BroadcastReceiver powerDisconnectedReceiver= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Entered onReceive of powerDisconnectedReceiver");
            String action = intent.getAction();
            if(Intent.ACTION_POWER_DISCONNECTED.equals(action))
            {
                displayAlert(0);
            }
            Log.i(TAG, "Exited onReceive of powerDisconnectedReceiver");
        }
    };

    //ACTION_POWER_CONNECTED
    private BroadcastReceiver powerConnectedReceiver= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Entered onReceive of powerConnectedReceiver");
            String action = intent.getAction();
            if(Intent.ACTION_POWER_CONNECTED.equals(action))
            {
                displayAlert(1);
            }
            Log.i(TAG, "Exited onReceive of powerConnectedReceiver");
        }
    };

    //RINGER_STATE_CHANGED
    private BroadcastReceiver ringerChangedReceiver= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Entered onReceive of ringerChangedReceiver");
            int  audioState = intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE,-1);

            String action = intent.getAction();
            if(ACTION_RINGER_CHANGED.equals(action))
            {
                //displayAlert(4);
                switch(audioState){
                    case 0: displayAlert(11); //ringer mode silent
                        break;
                    case 1: displayAlert(12); //ringer mode vibrate
                        break;
                    case 2: displayAlert(13); //ringer mode normal
                        break;
                    default: //do nothing
                        break;
                }
            }
            Log.i(TAG, "Exited onReceive of ringerChangedReceiver");
        }
    };

    //ACTION_BATTERY_LOW
    private BroadcastReceiver batteryLowReceiver= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Entered onReceive of batteryLowReceiver");
            String action = intent.getAction();
            if(Intent.ACTION_BATTERY_LOW.equals(action))
            {
                displayAlert(2);
            }
            Log.i(TAG, "Exited onReceive of batteryLowReceiver");
        }
    };

    //ACTION_BATTERY_OKAY
    private BroadcastReceiver batteryOkayReceiver= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Entered onReceive of batteryOkayReceiver");
            String action = intent.getAction();
            if(Intent.ACTION_BATTERY_OKAY.equals(action))
            {
                displayAlert(3);
            }
            Log.i(TAG, "Exited onReceive of batteryOkayReceiver");
        }
    };


    /**
     *  This method is used to display alert messages.
     *  The dialog box disappears when user clicks Ok.
     */
        private void displayAlert(int status)
        {
            Log.i(TAG, "Entered displayAlert");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            StringBuffer buffer = new StringBuffer("");
            switch(status){
                case 0: buffer.replace(0,buffer.length(),"Power Disconnected.");
                    break;
                case 1: buffer.replace(0,buffer.length(),"Power Connected.");
                    break;
                case 2: buffer.replace(0,buffer.length(),"Battery Low!");
                    break;
                case 3: buffer.replace(0,buffer.length(),"Battery Okay.");
                    break;
                /*case 4: buffer.replace(0,buffer.length(),"Ringer Mode Changed.");
                    break;
                case 5: buffer.replace(0,buffer.length(),"Wifi State Changed.");
                    break;*/
                case 6: buffer.replace(0,buffer.length(),"Wifi is being disabled.");
                    break;
                case 7: buffer.replace(0,buffer.length(),"Wifi State Disabled.");
                    break;
                case 8: buffer.replace(0,buffer.length(),"Wifi is being enabled.");
                    break;
                case 9: buffer.replace(0,buffer.length(),"Wifi State Enabled.");
                    break;
                case 10: buffer.replace(0,buffer.length(),"Wifi State Unknown.");
                    break;
                case 11: buffer.replace(0,buffer.length(),"Ringer State Silent.");
                    break;
                case 12: buffer.replace(0,buffer.length(),"Ringer State Vibrate.");
                    break;
                case 13: buffer.replace(0,buffer.length(),"Ringer State Normal.");
                    break;
                default:buffer.replace(0,buffer.length(),"Some Error Occurred!");
                    break;
            }

            builder.setMessage(buffer).setCancelable(
                    false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            Log.i(TAG, "Exited displayAlert");
        }

    /**
     * This section contains reference code.
     * Handy code for battery status tracking.
     */
    /*private BroadcastReceiver batteryInformationReceiver= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            if(plugged == 0){
                displayAlert(0);
            } else {
                displayAlert(1);
            }

            //boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            //int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            //String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            //int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            //int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

            tv.setText("------------My Battery Information------------" + "\n" +
                    //"Health: "+health+"\n"+
                    //"Level: "+level+"\n"+
                    "Plugged: "+plugged+"\n");
                    //+ "Present: "+present+"\n"+
                    //"Status: "+status+"\n"
                    // + "Technology: "+technology+"\n"+
                    //"Temperature: "+temperature+"\n"+
                    //"Voltage: "+voltage+"\n");

        }
    };

    IntentFilter ringerFilter = new IntentFilter();
        ringerFilter.addAction(ACTION_RINGER_CHANGED);
        this.registerReceiver(this.ringerChangedReceiver, ringerFilter);

    */

    /**
     * This section contains the commented default code
     */
   /* @Override
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
    }*/

    /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
    */

}
