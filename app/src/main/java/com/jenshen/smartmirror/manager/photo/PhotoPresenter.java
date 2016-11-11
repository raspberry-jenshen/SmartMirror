package com.jenshen.smartmirror.manager.photo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.giantivy.data.io.IFileCreator;
import com.giantivy.domain.util.bitmap.BitmapUtils;
import com.giantivy.presentation.presenter.base.impl.RxBasePresenter;
import com.jelvix.support.io.FileHelpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PhotoPresenter extends RxBasePresenter<IPhotoView> implements IPhotoPresenter {

    private static final String KEY_SOURCE = "KEY_SOURCE";
    private static final String KEY_PHOTO_FILE = "KEY_PHOTO_FILE";
    private static final String KEY_CROP_PHOTO_FILE = "KEY_CROP_PHOTO_FILE";

    private static final String FULL_PHOTO_NAME = "full_photo";
    @SuppressWarnings("FieldCanBeLocal")
    private static final String PREVIEW_PHOTO_NAME = "preview_photo";

    private static final int MAX_PHOTO_SIZE = 720;

    private final IFileCreator fileCreator;

    private final Config config;

    private String source;
    private boolean cropPhoto;

    private File photoFile;
    private File cropPhotoFile;

    public PhotoPresenter(IFileCreator fileCreator) {
        this.fileCreator = fileCreator;
        this.config = new Config();
    }


    /* lifecycle */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_SOURCE))
                source = savedInstanceState.getString(KEY_SOURCE);
            photoFile = (File) savedInstanceState.getSerializable(KEY_PHOTO_FILE);
            cropPhotoFile = (File) savedInstanceState.getSerializable(KEY_CROP_PHOTO_FILE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (source != null)
            outState.putString(KEY_SOURCE, source);
        outState.putSerializable(KEY_PHOTO_FILE, photoFile);
        outState.putSerializable(KEY_CROP_PHOTO_FILE, cropPhotoFile);
    }


    /* activity result */

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == config.requestCode_camera) {
            onActivityResult_camera(resultCode);
            return true;
        } else if (requestCode == config.requestCode_gallery) {
            onActivityResult_gallery(resultCode, data);
            return true;
        } else if (requestCode == config.requestCode_crop) {
            onActivityResult_crop(resultCode);
            return true;
        }

        return super.onActivityResult(requestCode, resultCode, data);
    }

    private void onActivityResult_camera(int resultCode) {
        if (resultCode != Activity.RESULT_OK) {
            canceled();
            return;
        }
        performCrop();
    }

    private void onActivityResult_gallery(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            canceled();
            return;
        }

        try {
            Uri uri = data.getData();
            copyToLocalFile(uri, photoFile);
            performCrop();
        } catch (Exception e) {
            canceled();
            getView().handleError(e);
        }
    }

    private void onActivityResult_crop(int resultCode) {
        if (resultCode != Activity.RESULT_OK) {
            canceled();
            return;
        }
        photoTaken();
    }


    // public methods

    @Override
    public void takePhoto(@Source String source, boolean cropPhoto) {
        try {
            if (!state_beginOperation(source, null, cropPhoto))
                return;

            switch (source) {
                case SOURCE_CAMERA:
                    takePhotoFromCamera();
                    break;
                case SOURCE_GALLERY:
                    takePhotoFromGallery();
                    break;
            }
        } catch (Exception e) {
            getView().handleError(e);
        }
    }

    @Override
    public void takePhoto(@Source String source) {
        takePhoto(source, true);
    }

    @Override
    public void cropPhoto(File photoFile) {
        try {
            if (!state_beginOperation(SOURCE_FILE, photoFile, true))
                return;

            performCrop();
        } catch (Exception e) {
            getView().handleError(e);
        }
    }

    @Override
    public IConfig config() {
        return config;
    }


    /* photo operations */

    private void takePhotoFromCamera() throws IOException {
        IPhotoRouter router = getView().getRouter();

        photoFile = fileCreator.createTempFile(FULL_PHOTO_NAME);
        router.launchCamera(photoFile, config.requestCode_camera);
    }

    private void takePhotoFromGallery() throws IOException {
        IPhotoRouter router = getView().getRouter();

        photoFile = fileCreator.createTempFile(FULL_PHOTO_NAME);
        router.launchGallery(config.requestCode_gallery);
    }

    private void performCrop() {
        try {
            IPhotoRouter router = getView().getRouter();

            state_setCropPhotoFile(fileCreator.createTempFile(PREVIEW_PHOTO_NAME));

            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFile(photoFile.getPath(), MAX_PHOTO_SIZE, MAX_PHOTO_SIZE);
            bitmap = BitmapUtils.rotateBitmap(bitmap, BitmapUtils.getImageOrientation(photoFile));
            photoFile = BitmapUtils.saveBitmap(bitmap, photoFile.getPath());

            router.launchCropper(photoFile, config.requestCode_crop, cropPhotoFile, config.crop_width, config.crop_height);
        } catch (IOException e) {
            getView().handleError(e);
        }
    }


    /* state methods */

    private boolean state_beginOperation(String source, File photoFile, boolean cropPhoto) {
        if (this.source != null)
            return false;

        state_finishOperation();

        this.source = source;
        this.photoFile = photoFile;
        this.cropPhoto = cropPhoto;

        return true;
    }

    private void state_finishOperation() {
        source = null;
        cropPhoto = false;
        cropPhotoFile = null;
    }

    private void state_setCropPhotoFile(File cropPhotoFile) {
        this.cropPhotoFile = cropPhotoFile;
    }

    private void canceled() {
        state_finishOperation();
    }

    private void photoTaken() {
        getView().photoTaken(source, cropPhotoFile, cropPhoto);
        state_finishOperation();
    }


    // util methods

    private void copyToLocalFile(Uri uri, File photoFile) throws Exception {
        ContentResolver contentResolver = getView().getContext().getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
            if (inputStream != null) { // TODO: 18.05.2016 refactor to handle null value variant
                FileHelpers.write(inputStream, photoFile);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


    /* inner types */

    private class Config implements IConfig {
        private int requestCode_camera = 666;
        private int requestCode_gallery = 66;
        private int requestCode_crop = 6;

        private int crop_width = 360;
        private int crop_height = 360;

        @Override
        public IConfig requestCode_camera(int value) {
            this.requestCode_camera = value;
            return this;
        }

        @Override
        public IConfig requestCode_gallery(int value) {
            this.requestCode_gallery = value;
            return this;
        }

        @Override
        public IConfig requestCode_crop(int value) {
            this.requestCode_crop = value;
            return this;
        }

        @Override
        public IConfig crop_height(int crop_height) {
            this.crop_height = crop_height;
            return this;
        }

        @Override
        public IConfig crop_width(int crop_width) {
            this.crop_width = crop_width;
            return this;
        }
    }

}
