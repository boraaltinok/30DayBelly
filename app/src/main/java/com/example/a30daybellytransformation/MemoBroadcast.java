package com.example.a30daybellytransformation;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MemoBroadcast extends BroadcastReceiver {
    User user;




    @Override
    public void onReceive(Context context, Intent intent) {

        loadData(context);
        Intent repeating_Intent = new Intent(context, program_main.class);
        repeating_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bitmap bitmapIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.banana_img), 128, 128, false);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_Intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.girly_background)
                .setLargeIcon(bitmapIcon)
                .setContentText( user.getName() + "...make the body you dream about")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
    public void loadData(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);

        if(user == null)
        {
            user = new User();
        }
    }


}

