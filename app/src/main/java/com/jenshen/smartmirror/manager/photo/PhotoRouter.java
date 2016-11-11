package com.jenshen.smartmirror.manager.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

public class PhotoRouter implements IPhotoRouter {

    public static IPhotoRouter create(Activity activity) {
        return new PhotoRouter(ExternalResourcesHelper.create(activity));
    }

    public static IPhotoRouter create(Fragment fragment) {
        return new PhotoRouter(ExternalResourcesHelper.create(fragment));
    }

    private final ExternalResourcesHelper externalResourcesHelper;
    
    private PhotoRouter(ExternalResourcesHelper externalResourcesHelper) {
        this.externalResourcesHelper = externalResourcesHelper;
    }

    @Override
    public void launchCamera(File photoFile, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        externalResourcesHelper.startActivityForResult(intent, requestCode);
    }

    @Override
    public void launchGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        externalResourcesHelper.startActivityForResult(intent, requestCode);
    }

    @Override
    public void launchCropper(File photoFile, int requestCode, File cropPhotoFile, int crop_width, int crop_height) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.putExtra("aspectX", crop_width);
        cropIntent.putExtra("aspectY", crop_height);
        cropIntent.putExtra("outputX", crop_width);
        cropIntent.putExtra("outputY", crop_height);
        cropIntent.setDataAndType(Uri.fromFile(photoFile), "image/*");
        cropIntent.putExtra("return-data", false);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropPhotoFile));

        externalResourcesHelper.startActivityForResult(cropIntent, requestCode);
    }
    
    private static abstract class ExternalResourcesHelper {
        public static ExternalResourcesHelper create(Activity activity) {
            return new ActivityHelper(activity);
        }

        public static ExternalResourcesHelper create(Fragment fragment) {
            return new FragmentHelperV4(fragment);
        }

        public abstract void startActivityForResult(Intent intent, int requestCode);

        private static class ActivityHelper extends ExternalResourcesHelper {
            private final Activity activity;

            public ActivityHelper(Activity activity) {
                this.activity = activity;
            }

            @Override
            public void startActivityForResult(Intent intent, int requestCode) {
                activity.startActivityForResult(intent, requestCode);
            }
        }

        private static class FragmentHelperV4 extends ExternalResourcesHelper {
            private final Fragment fragment;

            public FragmentHelperV4(Fragment fragment) {
                this.fragment = fragment;
            }

            @Override
            public void startActivityForResult(Intent intent, int requestCode) {
                fragment.startActivityForResult(intent, requestCode);
            }
        }
    }
    
}
