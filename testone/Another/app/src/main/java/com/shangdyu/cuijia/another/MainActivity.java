package com.shangdyu.cuijia.another;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;

public class MainActivity extends Activity {

    SoundPool sp;
    HashMap<Integer, Integer> hm;
    int currStreamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSoundPool();
        Button b1 = (Button) this.findViewById(R.id.button);
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1, 0);

                Toast.makeText(getBaseContext(), "播放及时音效",
                        Toast.LENGTH_SHORT).show();
            }
        });


        Button b2 = (Button)this.findViewById(R.id.button2);
        b2.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                sp.stop(currStreamId);
                Toast.makeText(getBaseContext(), "停止播放即时音效",
                        Toast.LENGTH_SHORT).show();
            }
        });


        SharedPreferences sp = this.getSharedPreferences("bn", Context.MODE_PRIVATE);
        String lastLoginTime = sp.getString("time", null);
        if (lastLoginTime == null){
            lastLoginTime = "first time";
        }
        else {
            lastLoginTime = "last time " + lastLoginTime;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("time", new Date().toLocaleString());

        editor.commit();
        TextView tv = (TextView)this.findViewById(R.id.textView2);
        tv.setText(lastLoginTime);
    }


    public void initSoundPool(){
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        hm = new HashMap<Integer, Integer>();

        hm.put(1, sp.load(this, R.raw.click, 1));
    }

    public void playSound(int sound, int loop){
        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;
        currStreamId = sp.play(hm.get(sound), volume, volume, 1, loop, 1.0f);
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
}
