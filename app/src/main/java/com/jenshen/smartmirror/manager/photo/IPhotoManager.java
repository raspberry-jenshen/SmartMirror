package com.jenshen.smartmirror.manager.photo;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.jenshen.smartmirror.manager.photo.IPhotoManager.PhotoMode.PHOTO_FROM_CAMERA;
import static com.jenshen.smartmirror.manager.photo.IPhotoManager.PhotoMode.PHOTO_FROM_GALLERY;

public interface IPhotoManager {

    void takePhoto(@PhotoMode String mode);

    void takePhotoWithCrop(@PhotoMode String mode);

    @StringDef({PHOTO_FROM_CAMERA, PHOTO_FROM_GALLERY})
    @Retention(RetentionPolicy.SOURCE)
     @interface PhotoMode {
        String PHOTO_FROM_CAMERA = "PHOTO_FROM_CAMERA";
        String PHOTO_FROM_GALLERY = "PHOTO_FROM_GALLERY";
    }
}
