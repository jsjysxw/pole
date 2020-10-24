package cn.com.avatek.pole.utils;

import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import cn.com.avatek.pole.SvaApplication;

/**
 * Created by shenxw on 2016/12/26.
 */

public class FilePathTool {
    private static String basePath;
    public static String videoBasePath;
    public static String photoBasePath;
    public static String audioBasePath;
    static {
        basePath= Environment.getExternalStorageDirectory().getPath()+"/avatek/";
        videoBasePath=basePath+"video/";
        photoBasePath=basePath+"photo/";
        audioBasePath=basePath+"audio/";
        testPath(videoBasePath);
        testPath(photoBasePath);
        testPath(audioBasePath);
    }
    private static void testPath(String path){
        File file = new File(path);
        if (!file.exists()){
            if(!file.mkdirs()){
                Toast.makeText(SvaApplication.getContext(), "目录创建失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String createImgPath(){
        return photoBasePath + TimeFormatTool.getTimeFormat() + ".jpg";
    }
    public static String createAudioPath(){
        return audioBasePath + TimeFormatTool.getTimeFormat() + ".amr";
    }
    public static String createVideoPath(){
        return videoBasePath + TimeFormatTool.getTimeFormat() + ".mp4";
    }

}