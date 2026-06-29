package com.example.amanakk;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import androidx.documentfile.provider.DocumentFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {
    public static File getFile(Context context, Uri uri) {
        File file = null;
        DocumentFile documentFile = DocumentFile.fromSingleUri(context, uri);
        if (documentFile != null && documentFile.getName() != null) {
            file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), documentFile.getName());
            try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
                 FileOutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
