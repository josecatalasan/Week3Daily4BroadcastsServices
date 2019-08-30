package com.example.background.workers;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.background.Constants;
import com.example.background.R;

import java.io.FileNotFoundException;
import java.net.URI;

public class BlurWorker extends Worker {

    public BlurWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        WorkerUtils.makeStatusNotification("Doing <WORK_NAME>", context);
        WorkerUtils.sleep();
        String resourceUri = getInputData().getString(Constants.KEY_IMAGE_URI);
        try{
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e("TAG", "Invalid input uri");
                throw new IllegalArgumentException("Invalid input uri");
            }
            ContentResolver resolver = context.getContentResolver();
            Bitmap picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)));

            Bitmap blurredPicture = WorkerUtils.blurBitmap(picture, context);
            Uri uri = WorkerUtils.writeBitmapToFile(context, blurredPicture);
            WorkerUtils.makeStatusNotification(uri.toString(), context);

            Data outputData = new Data.Builder().putString(Constants.KEY_IMAGE_URI, uri.toString()).build();
            return Result.success(outputData);
        }catch(Throwable throwable){
            Log.e("TAG", "Error applying blur", throwable);
            return Result.failure();
        }
    }
}
