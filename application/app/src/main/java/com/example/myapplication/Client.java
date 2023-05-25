package com.example.myapplication;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.*;
import java.net.Socket;


public class Client extends AsyncTask<String, Void, String> {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {
    }

    // sendFile function define here
    public static void sendFile(String path)
            throws Exception {
        int bytes = 0;
        // Open the File where he located in your pc
        File file = new File(path);
        FileInputStream fileInputStream
                = new FileInputStream(file);

        // Here we send the File to Server
        dataOutputStream.writeLong(file.length());
        // Here we  break file into chunks
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer))
                != -1) {
            // Send the file to Server Socket
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        // close the file here
        fileInputStream.close();
    }

    private static void receiveFile(String fileName)
            throws Exception {
        int bytes;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong();
        byte[] buffer = new byte[4 * 1024];
        while (size > 0
                && (bytes = dataInputStream.read(
                buffer, 0,
                (int) Math.min(buffer.length, size)))
                != -1) {
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes;
        }
        System.out.println("File is Received");
        fileOutputStream.close();
    }

    @Override
    protected String doInBackground(String... strings) {
        try (Socket socket = new Socket("192.168.50.168", 800)) {

            dataInputStream = new DataInputStream(
                    socket.getInputStream());
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            System.out.println(
                    "connecting");

            sendFile(strings[0]);

            receiveFile(strings[1]);

            dataInputStream.close();
            dataInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

