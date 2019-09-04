package WebServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkThread implements Runnable {
    protected Socket clientSocket = null;


    public WorkThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

    }


    @Override
    public void run() {
        try {
            InputStream input  = this.clientSocket.getInputStream();
            OutputStream output = this.clientSocket.getOutputStream();
            // 创建Request对象并解析
            Request request = new Request(input);
            request.parse();

            // 创建 Response 对象
            Response response = new Response(output);
            response.setRequest(request);
            if(request.getMethod().equals("GET")){
//                System.out.println("myget");
                response.sendStaticResource();
            }
            else {
//                System.out.println("mypost");
                response.handledPost();
            }
            // 关闭 socket 对象
            clientSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}