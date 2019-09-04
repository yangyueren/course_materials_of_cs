package WebServer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadWebServer implements Runnable{
    protected int          serverPort   = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "web";

    public MultiThreadWebServer(int port){
        this.serverPort = port;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(true){
            Socket clientSocket = new Socket();
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(
                    new WorkThread(clientSocket)
            ).start();
        }
    }
    private void openServerSocket() {
        this.serverSocket = null;
        int port = 8080;
        try {
            //服务器套接字对象
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        MultiThreadWebServer multiThreadWebServer = new MultiThreadWebServer(8080);
        new Thread(multiThreadWebServer).start();
    }

}