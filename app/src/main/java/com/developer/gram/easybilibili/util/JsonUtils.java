package com.developer.gram.easybilibili.util;

import android.content.res.AssetManager;
import android.support.annotation.Nullable;

import com.developer.gram.easybilibili.EasyBiliApp;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/31 11:15
 * 描述:
 */

public class JsonUtils {
    public static String readAssetsJson(String fileName) {
        AssetManager assetManager = EasyBiliApp.getInstance().getAssets();
        try {
            InputStream is = assetManager.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "文件不存在";
        }
    }
}
