package privious.utils;

import java.io.*;

public class IOUtils {

    public static String readAll (InputStream inputStream) {
        StringBuilder sb = new StringBuilder(); //Immutable
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void readFullyByBytes (InputStream is) {
        int oneByte;
        try {
            while ((oneByte = is.read()) != -1) {
                System.out.print((char) oneByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OutputStream copy(InputStream in, OutputStream out) {


        byte[]buff = new byte[64*1024];
        int count;

        try {
            while ((count = in.read(buff))!=-1){
                out.write(buff,0,count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;

    }

    public static void closeOut(OutputStream out) {
        if (out != null) {
            try {
                out.flush();
            } catch (IOException e) {
               e.printStackTrace();

            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
    public static void closeIn(InputStream in) {
        if (in != null) {
            try {
                in.close();
                System.out.println("Stream closed");
                //System.exit(-1);
            } catch (IOException e) {
               e.printStackTrace();

            }
        }
    }

    public static String fileInBytes(InputStream in) {


        byte[]buff = new byte[64*1024];
        int count;
        StringBuilder sb= new StringBuilder("");
        String line;

        try {
            while ((count = in.read(buff))!=-1){
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <buff.length; i++) {
            sb.append((char) buff[i]);
        }
        line = sb.toString();

        return line;

    }

}
