package sdf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cookie {
    
    String dirPath = "data2";
    String fileName = "cookie.txt";

    List<String> cookieItems = null;

    public void readCookieFile() throws IOException {
        cookieItems = new ArrayList<String>();

        File file = new File(dirPath + File.separator + fileName);

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String readString;

        try {
            while ((readString = br.readLine()) != null) {
                cookieItems.add(readString);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
            fr.close();
        }


        Cookie cookie = new Cookie();
        cookie.readCookieFile();
        cookie.showCookies();

        ServerSocket ss = new ServerSocket(7000);
        Socket s = ss.accept(); //establish connection, wait for client to connect

        try (InputStream is = s.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            String msgReceived = "";

            while (!msgReceived.equals("close")) {
                msgReceived = dis.readUTF();

                if (msgReceived.equalsIgnoreCase("get-cookie")) {
                    String cookieValue = cookie.returnCookie();
                }
            }
        } catch (EOFException ex) {
            s.close();
            ss.close();
        }
    }

    public String returnCookie() {
        Random rand = new Random();

        if (cookieItems != null) {
            return cookieItems.get(rand.nextInt(cookieItems.size()));
        } else {
            return "There is no cookie found.";
        }
    }

    public void showCookies() {

        if (cookieItems != null) {
            // cookieItems.forEach(ci -> System.out.println(ci));
            
            for (String s: cookieItems) {
                System.out.println(s);
            }
        }
    }

}
