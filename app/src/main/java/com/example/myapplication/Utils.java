package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WIFI_SERVICE;

public class Utils {
    public static String Root_Path = "";
    public static String DB_PATH = "";
    public static String IMAGE_FOLDER_PATH = "";
    public static String IMAGE_FOLDER_NAME = "ProGamerImages";
    public static String DB_FOLDER_NAME = "ProGamerDB";
    public static String DB_Name = "ProGamer.db";
    public static String LogFolder = "ProGamerLogs.db";
    public static String MobilePattern = "[6-9][0-9]{9}";
    public static boolean iEnableLog = true;


    static void setCustomTitle(AppCompatActivity activity) {
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(activity);
        View v = inflator.inflate(R.layout.titleview, null);
        ((TextView) v.findViewById(R.id.title)).setText(activity.getTitle());
        activity.getSupportActionBar().setCustomView(v);
    }

    public static String creRowId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    @SuppressLint("ResourceType")
    public static String getId(View view) {
        if (view.getId() == 0xffffffff) return "no-id";
        else return view.getResources().getResourceName(view.getId());
    }

    @SuppressLint("HardwareIds")
    public static String getMacAddress(Context context) {
        String result = "";
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
            result = wifiInfo.getMacAddress();
        } catch (Exception ex) {
            Utils.WriteLog(ex.getMessage());
        }
        if (!"".equals(result)) {
            SharedPreferences settings = context.getSharedPreferences("DeviceInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("MAC_ADDRESS", result);
            editor.apply();
        } else {
            SharedPreferences settings = context.getSharedPreferences("DeviceInfo", MODE_PRIVATE);
            result = settings.getString("MAC_ADDRESS", "");
        }
        return result;
    }

    static void showMsg(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    static String getExceptionRootMessage(Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(throwable.getMessage()).append("\r\n");
        StackTraceElement[] stackTraceElementsList = throwable.getStackTrace();
        if (stackTraceElementsList != null) {
            for (StackTraceElement stackTraceElement : stackTraceElementsList) {
                if (stackTraceElement != null) {
                    stringBuilder.append(stackTraceElement.getClassName());
                    stringBuilder.append(".");
                    stringBuilder.append(stackTraceElement.getMethodName());
                    stringBuilder.append("()-");
                    stringBuilder.append(stackTraceElement.getLineNumber()).append("\r\n");
                }
            }
        }
        return stringBuilder.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static int WriteLog(String message) {
        try {
            if (Utils.iEnableLog) {
                Calendar currentDate = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
                String dateNow = formatter.format(currentDate.getTime());
                File dir = new File(Root_Path + "/" + LogFolder);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(Root_Path + "/" + LogFolder + "/" + dateNow + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                new SimpleDateFormat("hh:mm:ss.SSS");
                Date dat1 = new Date();
                FileWriter fstream = new FileWriter(file, true);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(dat1.toString());
                out.write(":");
                out.write(message + "\n");
                out.newLine();
                out.close();
            }
        } catch (Exception var10) {
            System.out.println("Exception in writelog fn" + var10.getMessage());
        }

        return 0;
    }

    public void lodImage(Context context, String URL, ImageView view) {
        Glide.with(context).load(URL).into(view);
    }


}
