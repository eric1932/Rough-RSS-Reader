package com.eric.me.roughrssreader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

class IOHelper {

    private Context mContext;

    IOHelper(Context c) {
        mContext = c;
    }

    boolean isExist(final String fileName) {
        File file = new File(mContext.getFilesDir(), fileName);
        return file.exists();
    }

    boolean DeleteFile(final String fileName) {
        File file = new File(mContext.getFilesDir(), fileName);
        return file.delete();
    }

    /**
     *
     * @param anyFilename name of the file
     * @return file as string
     * @throws IOException simple io exception
     */
    String readFile(final String anyFilename) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            InputStream in = mContext.openFileInput(anyFilename);
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (FileNotFoundException fnfe) {
            return null;
        } catch (IOException ioe) {
            Log.e("IOException", ioe.toString());
            throw new IOException();
        }
    }

    //来自 https://blog.csdn.net/it_liuwei/article/details/52333642
    /**
     *
     * @param anyString any string to write in the file
     * @param anyFileName file name
     * @param overWrite if true, overwrite the file; else, append at the end (no new line)
     * @return success or not
     */
    boolean writeFile(final String anyString, final String anyFileName, final boolean overWrite) {
        //加入恢复默认的提示
        if (anyString.equals("[]\n")) {
            Toast.makeText(mContext, "All sites deleted. Default site will be applied.", Toast.LENGTH_LONG).show();
        }
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
            Log.d("WRITE EXCEPTION", e.toString());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    Log.d("WRITE EXCEPTION", e.toString());
                }
            }
        }
        return false;
    }

    //another implementation
    /*boolean writeFileData(final String fileName, final String message) {
        try {
            FileOutputStream fout = mContext.openFileOutput(fileName, mContext.MODE_PRIVATE);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    //自带OverWrite，而且是外置存储
    //来自 https://blog.csdn.net/csdnzouqi/article/details/75333266
    /*boolean saveFile(final String str, final String fileName) {
        //创建String对象保存文件名路径
        try {
            //创建指定路径的文件
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            //如果文件不存在
            if (file.exists()) {
                //创建新的空文件
                file.delete();
            }
            file.createNewFile();
            //获取文件的输出流对象
            FileOutputStream outStream = new FileOutputStream(file);
            //获取字符串对象的byte数组并写入文件流
            outStream.write(str.getBytes());
            //最后关闭文件输出流
            outStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    /*String readFileData(final String fileName) {
        String res = "";
        try {
            FileInputStream fin = mContext.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }*/

    //这个适合外置存储
    /*String getFile(String fileName) {
        try {
            //创建文件
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            //创建FileInputStream对象
            FileInputStream fis = new FileInputStream(file);
            //创建字节数组 每次缓冲1M
            byte[] b = new byte[1024];
            int len = 0;// 一次读取1024字节大小，没有数据后返回-1.
            //创建ByteArrayOutputStream对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //一次读取1024个字节，然后往字符输出流中写读取的字节数
            while ((len = fis.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            //将读取的字节总数生成字节数组
            byte[] data = baos.toByteArray();
            //关闭字节输出流
            baos.close();
            //关闭文件输入流
            fis.close();
            //返回字符串对象
            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /*public static void deletefile(String fileName) {
        try {
            // 找到文件所在的路径并删除该文件
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
