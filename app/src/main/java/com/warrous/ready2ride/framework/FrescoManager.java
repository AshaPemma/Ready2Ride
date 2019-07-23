package com.warrous.ready2ride.framework;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;


public class FrescoManager {
    private static FrescoManager instance;
    private Context mContext;

    private FrescoManager(Context context) {
        mContext = context;
    }

    public static FrescoManager getInstance(Context context) {
        if (instance == null) {
            instance = new FrescoManager(context);
        }
        return instance;
    }

    public void displayImageFromResource(SimpleDraweeView view, int drawableId) {
        Uri localImageUri = Uri.parse("res:/" + drawableId);
        view.setImageURI(localImageUri);
//        view.setImageResource(drawableId);
    }

    public void displayRoundedImageFromResource(SimpleDraweeView view, int drawableId) {
        setCircleImage(view, 0);
        Uri localImageUri = Uri.parse("res:/" + drawableId);
        view.setImageURI(localImageUri);
    }

    public void displayImageFromFile(SimpleDraweeView view, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            Uri imageUri = Uri.fromFile(new File(filePath));
//            view.destroyDrawingCache();
//            Fresco.getImagePipeline().evictFromMemoryCache(imageUri);
            view.setImageURI(imageUri);
        }
    }

    public void displayImageFromNetwork(SimpleDraweeView view, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            setDraweeController(view, filePath);
        }
    }

    public void setCircleImage(SimpleDraweeView view, int borderColor) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(borderColor, 1.0f);
        roundingParams.setRoundAsCircle(true);
        roundingParams.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        view.getHierarchy().setRoundingParams(roundingParams);

    }

    public void displayCircleImageFromFile(SimpleDraweeView view, String filePath) {
        setCircleImage(view, 0);
        displayImageFromFile(view, filePath);
    }

    public void displayCircleImageFromNetwork(SimpleDraweeView draweeView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        setCircleImage(draweeView, 0);
        setDraweeController(draweeView, url);
    }


    public void setRoundCornersImage(SimpleDraweeView view, int borderColor) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(borderColor, 1.0f);
        roundingParams.setRoundAsCircle(false);
        view.getHierarchy().setRoundingParams(roundingParams);

    }

    public void displayRoundCornerImageFromFile(SimpleDraweeView view, String filePath) {
        setRoundCornersImage(view, 0);
        displayImageFromFile(view, filePath);
    }

    public void displayRoundCornerImageFromNetwork(SimpleDraweeView draweeView, String url) {
        setRoundCornersImage(draweeView, 0);
        setDraweeController(draweeView, url);
    }


    public void displayImageFromFile(SimpleDraweeView view, File file) {
        if(file != null){
            Uri imageUri = Uri.fromFile(file);
            view.destroyDrawingCache();
            Fresco.getImagePipeline().evictFromMemoryCache(imageUri);
            view.setImageURI(imageUri);
        }
    }

    public void displayImageFromFileUri(SimpleDraweeView view, Uri uri) {
        if(uri != null){
            view.destroyDrawingCache();
            Fresco.getImagePipeline().evictFromMemoryCache(uri);
            view.setImageURI(uri);
        }
    }
    private void setDraweeController(SimpleDraweeView draweeView, String url) {
        Uri imageUri = Uri.parse(url);
        ImageRequest request = ImageRequest.fromUri(imageUri);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController()).build();
        draweeView.setController(controller);
    }

    public void displayGIFImageFromResource(SimpleDraweeView view, int drawableId) {
        Uri localImageUri = Uri.parse("res:/" + drawableId);
        DraweeController controller =
                Fresco.newDraweeControllerBuilder()
                        .setUri(localImageUri)
                        .setAutoPlayAnimations(true)
                        .build();
        view.setController(controller);
    }

}
