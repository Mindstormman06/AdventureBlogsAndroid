package com.aidenadzich.adventureblogs;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    public static String getPathFromUri(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String fileName = getFileName(contentResolver, uri);

        if (fileName == null) {
            Log.e("FILE_UTILS", "File name is null for URI: " + uri.toString());
            return null;
        }

        File tempFile = new File(context.getCacheDir(), fileName);

        try (InputStream inputStream = contentResolver.openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(tempFile)) {

            if (inputStream == null) {
                Log.e("FILE_UTILS", "Failed to open input stream for URI: " + uri.toString());
                return null;
            }

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            Log.e("FILE_UTILS", "Error copying file: " + e.getMessage());
        }

        return null;
    }

    private static String getFileName(ContentResolver contentResolver, Uri uri) {
        String fileName = null;
        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex);
                }
            }
        }
        return fileName;
    }
}

