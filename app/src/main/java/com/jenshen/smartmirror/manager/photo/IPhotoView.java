package com.jenshen.smartmirror.manager.photo;

import com.giantivy.presentation.presenter.base.view.IView;

import java.io.File;

public interface IPhotoView extends IView {

    void photoTaken(String source, File photoFile, boolean isCropped);
    IPhotoRouter getRouter();

}
