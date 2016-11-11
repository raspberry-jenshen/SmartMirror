package com.jenshen.smartmirror.manager.photo;

import android.support.annotation.StringDef;

import com.giantivy.presentation.presenter.base.IPresenter;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IPhotoPresenter extends IPresenter<IPhotoView> {

    String SOURCE_CAMERA = "SOURCE_CAMERA";
    String SOURCE_GALLERY = "SOURCE_GALLERY";
    String SOURCE_FILE = "SOURCE_FILE";

    void takePhoto(@Source String source, boolean cropPhoto);
    void takePhoto(@Source String source);
    void cropPhoto(File photoFile);

    IConfig config();

    @StringDef({SOURCE_CAMERA, SOURCE_GALLERY})
    @Retention(RetentionPolicy.SOURCE)
    @interface Source {
    }

}
