package WebServer;

import java.io.*;

/*
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/

public class Response {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            //将web文件写入到OutputStream字节流中
            File file = new File(MultiThreadWebServer.WEB_ROOT, request.getUri());
//            System.out.println(request.getUri());

            if (file.exists()) {
                output.write("HTTP/1.0 200 OK".getBytes());
                output.write("MIME_version:1.0".getBytes());
                output.write("Content_Type:text/html".getBytes());
                int len = (int) file.length();
                output.write(("Content_Length:" + len).getBytes());
                output.write("\n\n".getBytes());//报文头和信息之间要空一行

                int byteread = 0;
                InputStream in = new FileInputStream(file);

                while ((byteread = in.read(bytes)) != -1) {
                    output.write(bytes, 0, byteread);
                }
                    output.flush();

            } else {
                // file not found
                System.out.println("not found");
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (fis != null)
                fis.close();
        }
    }
    public void handledPost(){
        String []env = {
                "REDIRECT_STATUS=true",
                "GATEWAY_INTERFACE=CGI/1.1",
                "CONTENT_TYPE=application/x-www-form-urlencoded; charset=UTF-8",
                "HTTP_HOST=127.0.0.1:8080",
                "HTTP_USER_AGENT=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36",
                "REQUEST_METHOD=POST",
                "SCRIPT_FILENAME="+MultiThreadWebServer.WEB_ROOT+request.getExeFile(),
                "CONTENT_LENGTH="+String.valueOf(request.postInfo.variables.length()),
        };
//        System.out.println("handlpost");
        try {
            Process p = Runtime.getRuntime().exec("php-cgi", env);
            OutputStream out = p.getOutputStream();
//            System.out.println(request.postInfo.variables);
            out.write(request.postInfo.variables.getBytes());
            out.flush();
            InputStream is = p.getInputStream();
//            System.out.println(fromStream(is));

            byte[] bytes = new byte[2048];
            int readlength = is.read(bytes,0,2048);

            output.write("HTTP/1.0 200 OK".getBytes());
//            output.write("MIME_version:1.0".getBytes());
            output.write("Content_Type:text/html".getBytes());

            output.write(("Content_Length:" + readlength).getBytes());
            output.write("\n\n".getBytes());//报文头和信息之间要空一行
            output.write(bytes, 66, readlength);
//            String s = new String(bytes);

            output.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static String fromStream(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }

}