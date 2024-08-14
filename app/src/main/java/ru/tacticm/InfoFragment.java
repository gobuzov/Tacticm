package ru.tacticm;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Hashtable;

public class InfoFragment extends Fragment implements View.OnClickListener{
    public InfoFragment() {}// Required empty public constructor
    Hashtable<Integer, String> actions = new Hashtable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        v.findViewById(R.id.pic).setOnClickListener(this);
        v.findViewById(R.id.button2).setOnClickListener(this);
        v.findViewById(R.id.button3).setOnClickListener(this);
        v.findViewById(R.id.button4).setOnClickListener(this);
        v.findViewById(R.id.button5).setOnClickListener(this);
        v.findViewById(R.id.button6).setOnClickListener(this);
        v.findViewById(R.id.button7).setOnClickListener(this);
        v.findViewById(R.id.buttongid).setOnClickListener(this);
        v.findViewById(R.id.buttonrutube).setOnClickListener(this);
        actions.put(R.id.button3, getString(R.string.site_link));
        actions.put(R.id.button4, getString(R.string.vk_link));
        actions.put(R.id.button5, getString(R.string.telegram_link));
        actions.put(R.id.button6, getString(R.string.youtube_link));
        actions.put(R.id.buttongid, getString(R.string.gid_link));
        actions.put(R.id.buttonrutube, getString(R.string.rutube_link));
        return v;
    }
    MediaPlayer m = new MediaPlayer();
    int clicks = 0;
    public void playBeep() {
        try {
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }
            AssetFileDescriptor descriptor = getActivity().getAssets().openFd("voice.ogg");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            m.prepare();
            m.setVolume(0.5f, 0.5f);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onClick(View view) {
        Tools.onlyClick(view);
        int id = view.getId();
        if (R.id.pic==id){
            clicks++;
            if (10==clicks) {
                clicks = 0;
                playBeep();
            }
        }if (R.id.button2==id){ // call lessons
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+getString(R.string.phone_link)));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }else if (R.id.button7==id){ // email me
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + getString(R.string.myemail)));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mysubj));
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mybody));
            try {
                startActivity(Intent.createChooser(emailIntent, getString(R.string.sendby)));
            } catch (android.content.ActivityNotFoundException ex) {
                Tools.showSnackbar(view, getString(R.string.noemail));
            }
        }else {
            String link = actions.get(id);
            if (null!=link){
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        }
    }
}
