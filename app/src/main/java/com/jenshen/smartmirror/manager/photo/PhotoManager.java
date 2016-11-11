package com.jenshen.smartmirror.manager.photo;


import static com.jenshen.smartmirror.manager.photo.IPhotoPresenter.SOURCE_CAMERA;
import static com.jenshen.smartmirror.manager.photo.IPhotoPresenter.SOURCE_GALLERY;

public class PhotoManager implements IPhotoManager {

    @Override
    public void takePhoto(@PhotoMode String mode) {

    }

    @Override
    public void takePhotoWithCrop(@PhotoMode String mode) {
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
}
