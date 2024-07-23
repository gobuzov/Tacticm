package ru.tacticm;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    Switch switch1, switch2;
    TextView example, text_size;
    SeekBar seekBar;
    int progress;

    public SettingsFragment() {}// Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings, container, false);
        switch1 = v.findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);
        switch1.setChecked(1==App.getInstance().getInt(c.option1, 1));
        switch2 = v.findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(this);
        switch2.setChecked(1==App.getInstance().getInt(c.option2, 0));
        example = v.findViewById(R.id.text_example);
        text_size = v.findViewById(R.id.text_size);
        seekBar = v.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float fs = Tools.getFontSize(progress);
                example.setTextSize(fs);
                SettingsFragment.this.progress = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                App.getInstance().putInt(c.FONT_SZ, progress);
            }
        });
        seekBar.setMax(100);
        progress = App.getInstance().getInt(c.FONT_SZ, c.DEF_FONT_PROGRESS);
        seekBar.setProgress(progress);
        return v;
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (R.id.switch1==id) {
            App.getInstance().putInt(c.option1, isChecked ? 1 : 0);
        }else if (R.id.switch2==id){
            App.getInstance().putInt(c.option2, isChecked ? 1 : 0);
        }
    }
}
