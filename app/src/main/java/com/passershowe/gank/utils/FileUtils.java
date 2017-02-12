package com.passershowe.gank.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.passershowe.gank.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hao809395357 on 2016/10/9.
 */

public class FileUtils {

    private static final String TAG = "FileUtils";
    private static FileUtils INSTANCE;
    private static final int MB = 1024 * 1024;
    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;
    private static final String WHOLESALE_CONV = ".thumb";

    public static FileUtils getInstance() {
        if (null == INSTANCE) {
            synchronized (FileUtils.class) {
                if (null == INSTANCE) {
                    INSTANCE = new FileUtils();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 动态创建多级文件目录
     *
     * @param filePath
     */
    public String buildFilePath(Context context, String filePath) {
        File file = null;
        //截取filePath第一个字符串
        String oneFilePath = new StringBuffer(filePath).substring(0, 1);
        //判断SD卡是否存在
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            String newFilePath;
            //判断第一个字符串是否带"/"，如果不是则添加
            if (!TextUtils.equals(oneFilePath, "/")) {
                filePath = "/" + filePath;
            }
            //创建指定目录
            file = new File(Environment.getExternalStorageDirectory() + filePath);
            if (!file.exists())//如果不存在
            {
                file.mkdirs();//创建多级目录结构
            }
        } else {
            Toast.makeText(context, "SD卡不存在", Toast.LENGTH_LONG).show();
        }
        return file.getPath();
    }


    public File buildFile(Context context, String fileName) {

        Log.e(TAG,"fileName:"+fileName);

        File buildFile = new File(buildFilePath(context, context.getResources().getString(R.string.app_name)) +"/"+ fileName);

        boolean bFlag = false;
        try {
            if (buildFile.exists()) {
                bFlag = buildFile.delete();
            } else {
                bFlag = true;
            }

            if (bFlag) {

                buildFile.createNewFile();

            }

        } catch (IOException io) {
            io.printStackTrace();
        }

        return buildFile;

    }


    /**
     * 获取SD卡下指定文件夹的图片
     *
     * @param fileAll
     * @return
     */
    public List<String> obtainImageListBySD(File fileAll) {
        List<String> filePathList = new ArrayList<>();
        File[] files = fileAll.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                filePathList.add(file.getName());
            }
        }
        return filePathList;
    }

    /**
     * 获取SD卡下指定文件夹的文件
     *
     * @param fileAll
     * @return
     */
    public List<String> obtainFileListBySD(File fileAll) {
        List<String> filePathList = new ArrayList<>();
        File[] files = fileAll.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            filePathList.add(file.getPath());
        }
        return filePathList;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     *
     * @param fName 文件名
     * @return
     */
    public boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * 获取指定的SD卡目录
     *
     * @param filePath
     * @return
     */
    public String getFileSDPath(String filePath) {
        String sdPath = "";
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory() +"/"+ filePath;
        }
        return sdPath;
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public void deleteFolder(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFolder(f.getPath());
            }
            file.delete();
        }
    }

    /**
     * 将图片存入文件缓存
     **/
    public void saveBitmapToThumb(Bitmap bm, String imagePath, int quality) {
        if (bm == null) {
            return;
        }
        //判断sdcard上的空间
        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
        }
        String filename = convertUrlToFileName(imagePath);
        File dirFile = new File(filename);
        try {
            dirFile.createNewFile();
            OutputStream outStream = new FileOutputStream(dirFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将图片存入文件缓存
     **/
    public void saveBitmapToImage(Bitmap bm, File imageFile, int quality) {
        if (bm == null) {
            return;
        }
        //判断sdcard上的空间
        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
        }
        try {
            imageFile.createNewFile();
            OutputStream outStream = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化文件名
     **/
    private String convertUrlToFileName(String path) {
        String dest = "";
        dest = removeJpgToThumb(path);
        return dest;
    }

    /**
     * jpg后缀替换成thumb后缀
     *
     * @param str
     * @return
     */
    private String removeJpgToThumb(String str) {
        String dest = "";
        dest = str.replaceAll(".jpg", WHOLESALE_CONV);
        return dest;
    }

    /**
     * 计算sdcard上的剩余空间
     **/
    private int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
        return (int) sdFreeMB;
    }

    /**
     * 缩放图片
     *
     * @param oriFile
     * @return
     */
    public Bitmap createBitmapToThumb(String oriFile) {
        Bitmap bitmap = null;
        if (oriFile != null && !oriFile.equals("")) {
            String thubmFile = removeJpgToThumb(oriFile);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(thubmFile, options);
            options.inJustDecodeBounds = false;
            int be = options.outWidth / 210;
            if (be <= 0) {
                be = 10;
            }
            options.inSampleSize = be;
            bitmap = BitmapFactory.decodeFile(thubmFile, options);
        }
        return bitmap;
    }

    /**
     * 设置图片压缩值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 计算Bitmap的大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {     //API 19
            return bitmap.getAllocationByteCount() / 1024;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount() / 1024;
        }
        return bitmap.getRowBytes() * bitmap.getHeight() / 1024;                //earlier version
    }
}



