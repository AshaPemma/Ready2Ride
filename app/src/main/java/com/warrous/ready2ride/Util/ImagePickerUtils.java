package com.warrous.ready2ride.Util;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;


import com.warrous.ready2ride.BuildConfig;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.framework.Ready2RideLog;

import java.io.File;


public class ImagePickerUtils { public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1886;
    public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 1887;
    public static final int SELECT_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    public static final int SELECT_MULTIPLE_IMAGE_ACTIVITY_REQUEST_CODE = 1885;
    public static final int SELECT_LIBRARY_IMAGE_ACTIVITY_REQUEST_CODE = 1884;

    public static final int SELECT_VIDEO_ACTIVITY_REQUEST_CODE = 1889;

    public File photoFile;
    private Uri fileUri;

    public ImagePickerUtils() {

    }

    public File getPhotoFile() {
        return photoFile;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void broadcastMediaScanner(AppCompatActivity activity) {
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, fileUri));
    }

    public void broadcastMediaScanner(AppCompatActivity activity, Uri uri) {
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

//    public void dispatchTakePhotoIntent(Fragment fragment) {
//        if (!PermissionUtils.verifyCameraStoragePermissions(fragment)) {
//            return;
//        }
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File photoFile = getImagePath();
//        if (photoFile != null) {
//            this.photoFile = photoFile;
//            fileUri = FileProvider.getUriForFile(fragment.getContext(),
//                    BuildConfig.APPLICATION_ID + ".provider",
//                    photoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//            fragment.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//    }

    public void dispatchTakePhotoIntent(AppCompatActivity activity) {
        if (!PermissionUtils.verifyCameraStoragePermissions(activity)) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = getImagePath();
        if (photoFile != null) {
            this.photoFile = photoFile;
            Ready2RideLog.getInstance().info("Picur",""+photoFile);
            fileUri = FileProvider.getUriForFile(activity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            activity.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public void dispatchTakeVideoIntent(Fragment fragment) {
        if (!PermissionUtils.verifyCameraStoragePermissions(fragment)) {
            return;
        }
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fragment.startActivityForResult(takeVideoIntent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    public void dispatchTakeVideoIntent(AppCompatActivity activity) {
        if (!PermissionUtils.verifyCameraStoragePermissions(activity)) {
            return;
        }
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        activity.startActivityForResult(takeVideoIntent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    public void selectImageFromGallery(Fragment fragment) {
        if (!PermissionUtils.verifyStoragePermissions(fragment)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        fragment.startActivityForResult(
                Intent.createChooser(intent, "Select Photo"),
                SELECT_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void selectImageFromGallery(AppCompatActivity activity) {
        if (!PermissionUtils.verifyStoragePermissions(activity)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(
                Intent.createChooser(intent, "Select Photo"),
                SELECT_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void selectMultipleImagesFromGallery(AppCompatActivity activity) {
        if (!PermissionUtils.verifyStoragePermissions(activity)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activity.startActivityForResult(
                Intent.createChooser(intent, "Select Photo"),
                SELECT_MULTIPLE_IMAGE_ACTIVITY_REQUEST_CODE);
    }



    public void selectVideoFromGallery(Fragment fragment) {
        if (!PermissionUtils.verifyStoragePermissions(fragment)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        fragment.startActivityForResult(
                Intent.createChooser(intent, "Select Video"),
                SELECT_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    public void selectVideoFromGallery(AppCompatActivity activity) {
        if (!PermissionUtils.verifyStoragePermissions(activity)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        activity.startActivityForResult(
                Intent.createChooser(intent, "Select Video"),
                SELECT_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    private File getImagePath() {
        File gallery = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        return new File(gallery.getPath() + File.separator +
                "cards_" + System.currentTimeMillis() + ".jpg");
    }

    public void showDialog(final AppCompatActivity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_upload_asset);
        dialog.setTitle("Upload Asset");
        RelativeLayout ivCamera =  dialog.findViewById(R.id.rl_capture_image);
        RelativeLayout ivGallery =  dialog.findViewById(R.id.rl_upload_gallery);
      //  RelativeLayout ivLibrary=dialog.findViewById(R.id.rl_upload_library);
      //  ivLibrary.setVisibility(View.GONE);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePhotoIntent(activity);
                dialog.dismiss();

            }
        });
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromGallery(activity);
                dialog.dismiss();

            }
        });
//        ivLibrary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtils.startActivity(activity,LibraryImagesDialodActivity.class,null);
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }
    public void showDialogEmail(final AppCompatActivity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_upload_asset);
        dialog.setTitle("Upload Asset");
        RelativeLayout ivCamera =  dialog.findViewById(R.id.rl_capture_image);
        RelativeLayout ivGallery =  dialog.findViewById(R.id.rl_upload_gallery);
       // RelativeLayout ivLibrary=dialog.findViewById(R.id.rl_upload_library);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePhotoIntent(activity);
                dialog.dismiss();

            }
        });
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromGallery(activity);
                dialog.dismiss();

            }
        });
//        ivLibrary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtils.startActivityForResult(activity,LibraryImagesDialodActivity.class,SELECT_LIBRARY_IMAGE_ACTIVITY_REQUEST_CODE,null);
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }


//    public void showDialog(final Fragment fragment) {
//        final Dialog dialog = new Dialog(fragment.getActivity());
//        dialog.setContentView(R.layout.image_picker_dialog);
//        ImageView ivCamera = (ImageView) dialog.findViewById(R.id.iv_camera);
//        ImageView ivGallery = (ImageView) dialog.findViewById(R.id.iv_gallery);
//        ivCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dispatchTakePhotoIntent(fragment);
//                dialog.dismiss();
//
//            }
//        });
//        ivGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImageFromGallery(fragment);
//                dialog.dismiss();
//
//            }
//        });
//        dialog.show();
//    }
//
//    public void showDialog(final AppCompatActivity activity) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.image_picker_dialog);
//        ImageView ivCamera = (ImageView) dialog.findViewById(R.id.iv_camera);
//        ImageView ivGallery = (ImageView) dialog.findViewById(R.id.iv_gallery);
//        ivCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dispatchTakePhotoIntent(activity);
//                dialog.dismiss();
//
//            }
//        });
//        ivGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImageFromGallery(activity);
//                dialog.dismiss();
//
//            }
//        });
//        dialog.show();
//    }
}
