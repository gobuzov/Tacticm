package ru.tacticm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
//
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class Tools {
    /**
     * Получить из assets файл как строку
     *
     * @param fname имя файла
     * @return string
     */
    public static String loadAssetString(String fname) {
        byte[] array = getFile(fname);
        try {
            String result = new String(array, "UTF-8");
            return result;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }
    /**
     * получить из assets файл как массив байтов
     *
     * @param fname имя файла
     * @return byte [ ]
     */
    public static byte[] getFile(String fname) {
        byte[] result = null;
        try {
            InputStream in = App.getInstance().getAssets().open(fname);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[16384];
            while (true) {
                int read = in.read(buffer);
                if (-1 == read)
                    break;
                baos.write(buffer, 0, read);
            }
            result = baos.toByteArray();
            baos.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * получить файл из assets как входной поток
     *
     * @param fname the fname
     * @return input stream
     */
    public static InputStream getFileStream(String fname) {
        InputStream in = null;
        try {
            in = App.getInstance().getAssets().open(fname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static void okDialog(Context ctx, String title, int mid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.Theme_TacticM));
        if (null != title)
            builder.setTitle(title);
        builder.setMessage(ctx.getString(mid)); // сообщение
        builder.setPositiveButton(ctx.getString(R.string.ok), null);
        final AlertDialog ad = builder.create();
        DialogInterface.OnClickListener positive = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ad.hide();
            }
        };
        builder.setPositiveButton(ctx.getString(R.string.ok), positive);
        ad.setCancelable(false);
        ad.show();
    }
    public static String glueString(String[] array, String delim){
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<array.length; i++){
            sb.append(array[i].trim());
            if (i!= array.length-1)
                sb.append(delim);
        }
        return sb.toString();
    }
    /**
     * Метод для устранения двойных нажатий на элементах UI
     * @param view the view
     */
    public static void onlyClick(final View view){
        view.setClickable(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setClickable(true);
            }
        }, 500);
    }
    public static float getFontSize(int progress){
        return c.MIN_FONT + (c.MAX_FONT-c.MIN_FONT)*progress/100;
    }
    public static void watchVideo(Context ctx, String url) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        try {
            ctx.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    // todo remove? 28.07.2023
    public static void watchYoutubeVideo(Context ctx, String id) { // https://www.youtube.com/watch?v=nj3ufJSN2jo
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            ctx.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            ctx.startActivity(webIntent);
        }
    }
    public static void showSnackbar(View view, String string){
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show();
    }
    public static void showSnackbarTop(View view, String string){
        Snackbar sb = Snackbar.make(view, string, Snackbar.LENGTH_SHORT);
        View snackbarLayout = sb.getView();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        snackbarLayout.setLayoutParams(lp);
        sb.show();
    }
    /**
     * Ok cancel dialog.
     * @param ctx      the ctx
     * @param msgId    the msg
     * @param posTextId  text for positiveButton
     * @param positive the positive

     */
    public static void okCancelDialog(Context ctx, int msgId, int posTextId, DialogInterface.OnClickListener positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.Theme_TacticM));
        builder.setMessage(ctx.getString(msgId)); // сообщение
        builder.setPositiveButton(ctx.getString(posTextId), positive);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {//cancel
                System.out.println("*** ok");
            }
        });
        final AlertDialog ad = builder.create();
       /* ad.setCancelable(true);
        if (null==cancel)
            cancel = new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    System.out.println("*** cancel");
                }
            };
        ad.setOnCancelListener(cancel);//*/
        ad.show();
    }
}


