package com.jenshen.smartmirror.manager.photo;

public interface IConfig {
    IConfig requestCode_camera(int requestCode_camera);
    IConfig requestCode_gallery(int requestCode_gallery);
    IConfig requestCode_crop(int requestCode_crop);

    IConfig crop_height(int crop_height);
    IConfig crop_width(int crop_width);
}
