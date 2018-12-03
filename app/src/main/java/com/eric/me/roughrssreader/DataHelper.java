package com.eric.me.roughrssreader;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

class DataHelper {

    /**
     *
     * @param mContext current context
     * @param anyString any string to write in the file
     * @param anyFileName file name
     * @param overWrite if true, overwrite the file; else, append at the end (no new line)
     * @return success or not
     */
    static boolean writeFile(final Context mContext, final String anyString, final String anyFileName, final boolean overWrite) {
        Writer writer = null;
        try {
            OutputStream outputStream;
            if (overWrite) {
                outputStream = mContext.openFileOutput(anyFileName, Context.MODE_PRIVATE);
            } else {
                outputStream = mContext.openFileOutput(anyFileName, Context.MODE_APPEND);
            }
            writer = new OutputStreamWriter(outputStream);
            writer.write(anyString);
            return true;
        } catch (Exception e) {
            Log.d("ERICSEXCEPTION", e.toString());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    Log.d("ERICSEXCEPTION", e.toString());
                }
            }
        }
        return false;
    }

    static String readFile(final Context mContext, final String anyFilename) {
        BufferedReader bufferedReader = null;
        try {
            InputStream in = mContext.openFileInput(anyFilename);
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            Log.d("ERICSEXCEPTION", e.toString());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    Log.d("ERICSEXCEPTION", e.toString());
                }
            }
        }
        return null;
    }
}
