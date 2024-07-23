package ru.tacticm;

import android.annotation.SuppressLint;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ShowCardActivity extends AppCompatActivity implements View.OnClickListener{
    TextView content;
    static CircleView bookmark, share;
    Card card, prev, next;
/// Show pic mode
    static ImageView pic;
    static CircleView left, right;
    static TextView name;
    static ViewGroup pic_mode, text_mode;
    static ArrayList<Resource> resList;
    static int currPic;
    static boolean picMode = false;

    boolean needsave = false;
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        content = findViewById(R.id.content);
        content.setTextSize(Tools.getFontSize(App.getInstance().getInt(c.FONT_SZ, c.DEF_FONT_PROGRESS)));
        Intent intent = getIntent();
        findViewById(R.id.back).setOnClickListener(this);
        bookmark = findViewById(R.id.bookmark);
        bookmark.setOnClickListener(this);
        share = findViewById(R.id.share);
        share.setOnClickListener(this);
        initCard(CardManager.get(intent.getStringExtra(c.ID)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            content.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
/// Show pic mode
        findViewById(R.id.clear).setOnClickListener(this);
        name = findViewById(R.id.name);
        text_mode = findViewById(R.id.text_mode);
        pic_mode = findViewById(R.id.pic_mode);
        pic = findViewById(R.id.pic);
        left = findViewById(R.id.left);
        left.setOnClickListener(this);
        right = findViewById(R.id.right);
        right.setOnClickListener(this);
        boolean fit = 1==App.getInstance().getInt(c.option2, 0);
        pic.setScaleType(fit ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER_CROP);
        if (!fit)
        pic.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent event) {
                float curX, curY;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mx = event.getX();
                        my = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getX();
                        curY = event.getY();
                        pic.scrollBy((int) (mx - curX), (int) (my - curY));
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = event.getX();
                        curY = event.getY();
                        pic.scrollBy((int) (mx - curX), (int) (my - curY));
                }
                return true;
            }
        });//*/
        if (picMode)
            setPicMode();
    }
    float mx, my; // for picture moving
    //
    void initCard(Card card){
        this.card = card;
        prev = CardManager.get(card.prev);
        next = CardManager.get(card.next);
        bookmark.setImageResource(card.mark ? R.drawable.ic_bookmark_24px : R.drawable.ic_bookmark_outline_24px);
        setClickableText(content, new String[]{"(рис.", "(фото", "(видео"});
        content.scrollTo(0, 0);
    }
    public void onClick(View v){
        int id = v.getId();
        if (R.id.back==id){
            picMode = false;
            finish();
        }else if (R.id.share==id){
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", card.h + "\n" + card.c);
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        }else if (R.id.bookmark==id){
            card.mark = !card.mark;
            bookmark.setImageResource(card.mark ? R.drawable.ic_bookmark_24px : R.drawable.ic_bookmark_outline_24px);
            Tools.showSnackbarTop(content, getString(card.mark ? R.string.add_bookmark : R.string.del_bookmark));
            needsave = true; /// see onDestroy
        }else if (R.id.clear==id){
            setTextMode();
        }else if (R.id.left==id){
            currPic--;
            if (currPic<0)
                currPic = resList.size()-1;
            setPicture();
        }else if (R.id.right==id){
            currPic++;
            if (currPic>=resList.size())
                currPic = 0;
            setPicture();
        }
    }
    private void setClickableText(final TextView tv, String[] templ){
        StringBuffer sb = new StringBuffer();
        int end = 0;
        if (null!=prev){
            sb.append(c.PREV_STRING).append(prev.h).append('\n');
            end = sb.length();
        }
        sb.append(card.h).append('\n').append(card.getTags()).append('\n');
        sb.append(card.c);
        int begin = sb.length();
        if (null!=next){
            sb.append('\n').append(next.h).append(c.NEXT_STRING);
        }
        String text = sb.toString();
        SpannableString spannableString = new SpannableString(text);
        if (null!=prev)
            spannableString.setSpan(new CustomSpan(c.PREV), 0, end, 0);
        if (null!=next)
            spannableString.setSpan(new CustomSpan(c.NEXT), begin, text.length(), 0);
        for(int j=0; j<templ.length; j++) {
            int i = 0;
            while (true) {
                begin = text.indexOf(templ[j], i);
                if (-1 != begin) {
                    end = text.indexOf(')', begin);
                    spannableString.setSpan(new CustomSpan(text.substring(begin+1, end)), begin + 1, end, 0);
                    i = end + 1;
                } else break;
            }
        }
        tv.setText(spannableString);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (needsave)
            CardManager.saveBookmarks();
    }
    public class CustomSpan extends ClickableSpan {
        String act;
        public CustomSpan(String act) {
            this.act = act;
        } //

        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
        }
        public void onClick(View view) {
                if (act.equals(c.PREV)){
                    initCard(prev);
                } else if (act.equals(c.NEXT)){
                    initCard(next);
                } else if (act.startsWith(c.VIDEO)) { // todo call show video (youtube & rutube)
                    DialogInterface.OnClickListener goVideo = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            try {
                                ArrayList<Resource> video = ResourceManager.getResources(act);
                                Tools.watchVideo(ShowCardActivity.this, video.get(0).path);
                            } catch (Exception exc) {
                                exc.printStackTrace();
                            }
                        }
                    };
                    Tools.okCancelDialog(ShowCardActivity.this, R.string.video_hint, R.string.look_video, goVideo);
                } else {
                    try {
                        resList = ResourceManager.getResources(act);
                        currPic = 0;
                        picMode = true;
                        setPicMode();
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
        }
    }
    /**
     * Нажатие кнопки Back
     */
    @Override
    public void onBackPressed() {
        if (picMode)
            setTextMode();
        else {
            super.onBackPressed();
        }
    }
    private void setPicMode(){
        pic_mode.setVisibility(View.VISIBLE);
        text_mode.setVisibility(View.GONE);
        int vis = 1==resList.size() ? View.GONE : View.VISIBLE; /// if one pic - hide arrows
        left.setVisibility(vis);
        right.setVisibility(vis);
        setPicture();
    }
    private void setTextMode(){
        pic_mode.setVisibility(View.GONE);
        text_mode.setVisibility(View.VISIBLE);
        picMode = false;
    }
    private void setPicture(){
        Resource res = resList.get(currPic);;
        try {
            InputStream ims = Tools.getFileStream("gfx/"+res.path);
            Drawable d = Drawable.createFromStream(ims, null);
            pic.setImageDrawable(d);
            name.setText(res.caption);
            ims.close();
        } catch(IOException ex) {ex.printStackTrace();}
    }
}
