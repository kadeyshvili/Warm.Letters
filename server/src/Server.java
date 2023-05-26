import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {
        try (ServerSocket serverSocket
                     = new ServerSocket(8000)) {
            System.out.println("Server is Starting in Port 8000");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected");
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                receiveFile("NewFile.jpg");

//                Удаляем погрешности (блики)
                ProcessBuilder processBuilder_shadows = new ProcessBuilder("/bin/sh", "-c", "python remove_shadows.py NewFile.jpg");
                processBuilder_shadows.inheritIO();
                Process process_shadows = processBuilder_shadows.start();
                process_shadows.waitFor();
                System.out.println("Removed shadows");

//                Конвертируем в html
                ProcessBuilder processBuilder_converting = new ProcessBuilder("/bin/sh", "-c", "convert NewFile.jpg  example.pbm && potrace example.pbm -s -o NewFile.html");
                processBuilder_converting.inheritIO();
                Process process_converting = processBuilder_converting.start();
                process_converting.waitFor();
                System.out.println("File is converted");

//                Анимируем в html
                ProcessBuilder processBuilder_animation = new ProcessBuilder("/bin/sh", "-c", "python animation.py NewFile.html");
                processBuilder_animation.inheritIO();
                Process process_animation = processBuilder_animation.start();
                process_animation.waitFor();
                System.out.println("File is animated");

                sendFile("NewFile.html");
                dataInputStream.close();
                dataOutputStream.close();
                clientSocket.close();
                System.out.println("File is sent");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(String path)
            throws Exception {
        int bytes;
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
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
}
