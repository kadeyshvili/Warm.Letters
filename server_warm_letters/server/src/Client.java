//import java.io.*;
//import java.net.Socket;
//
//public class Client {
//    private static DataOutputStream dataOutputStream = null;
//    private static DataInputStream dataInputStream = null;
//
//    public static void main(String[] args) {
//        try (Socket socket = new Socket("localhost", 900)) {
//
//            dataInputStream = new DataInputStream(socket.getInputStream());
//            dataOutputStream = new DataOutputStream(socket.getOutputStream());
//            System.out.println("Sending the File to the Server");
//            sendFile("/Users/antonina/PycharmProjects/change_letter/example.png");
//            System.out.println("file was sent to a server");
//            receiveFile("/Users/antonina/Downloads/server_java_make_html/server/changed.html");
//            System.out.println("file was received from a server");
//            dataInputStream.close();
//            dataOutputStream.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static void sendFile(String path)
//            throws Exception {
//        int bytes;
//        File file = new File(path);
//        FileInputStream fileInputStream = new FileInputStream(file);
//        dataOutputStream.writeLong(file.length());
//        System.out.println(" Intelligi");
//        byte[] buffer = new byte[4 * 1024];
//        while ((bytes = fileInputStream.read(buffer)) != -1) {
//            dataOutputStream.write(buffer, 0, bytes);
//            dataOutputStream.flush();
//        }
//        fileInputStream.close();
//    }
//
//
//    private static void receiveFile(String fileName)
//            throws Exception {
//        int bytes;
//        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//
//        long size = dataInputStream.readLong();
//        byte[] buffer = new byte[4 * 1024];
//        while (size > 0
//                && (bytes = dataInputStream.read(
//                buffer, 0,
//                (int) Math.min(buffer.length, size)))
//                != -1) {
//            fileOutputStream.write(buffer, 0, bytes);
//            size -= bytes;
//        }
//        System.out.println("File is Received from Intelligi");
//        fileOutputStream.close();
//    }
//}
