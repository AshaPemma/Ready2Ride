package com.warrous.ready2ride.framework;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class GlideManager {
    static Context contextD;


    public static void loadImage(Context context, String file, ImageView imageView) {

        Glide.with(context)
                .load(file)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }



    public static void loadFullScreenImage(Context context, String file, ImageView imageView) {

        Glide.with(context)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    public static void loadImageUrl(Context context, String url, ImageView imageView) {

        Glide.with(context)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    public static void loadFullImage(Context context, String file, ImageView imageView) {


        Glide.with(context)
                .load("" + file)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);


    }

//    public static void downloadImage(Context context, String file){
//        contextD=context;
//        Glide.with(context)
//                .load(file)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>(100,100) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation)  {
//                        saveImage(resource);
//                    }
//                });
//    }


    public static String saveImage(Bitmap image) {
        String savedImagePath = null;

        String imageFileName = "JPEG_" + "FILE_NAME" + ".jpg";
        File storageDir = new File(            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/AVAIL IMAGES");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(contextD, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    public static void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        contextD.sendBroadcast(mediaScanIntent);
    }


}
