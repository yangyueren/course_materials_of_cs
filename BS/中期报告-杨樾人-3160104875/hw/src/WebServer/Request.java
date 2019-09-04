package WebServer;


import java.io.InputStream;
import java.io.IOException;


public class Request {

    public InputStream input;
    private String uri;
    private String method;
    public PostMethodInfo postInfo;


    public Request(InputStream input) {
        this.input = input;
        postInfo = new PostMethodInfo();

    }
    public String getMethod(){
        return this.method;
    }
    //从InputStream中读取request信息，并从request中获取uri值
    public void parse() {

        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        if (request.substring(0,3).equals("GET")){
            this.method = "GET";
        }
        else {
            this.method = "POST";
            parsePost(request);
//            System.out.println(request);
        }
        uri = parseUri(request.toString());

    }
    private void parsePost(StringBuffer str){
        System.out.println(str);
        String [] arr = str.toString().split("\\s+");
        postInfo.exefile = arr[1];
        arr = str.toString().split("\r\n\r\n");

        if(arr.length > 1){
            postInfo.variables = arr[1];
        }
        int a = str.indexOf("Referer: ")+9;
        String temp = str.substring(a);
        arr = temp.split("\r\n");
        postInfo.referer = arr[0];
    }
    /**
     *
     * requestString形式如下：
     * GET /index.html HTTP/1.1
     * Host: localhost:8080
     * Connection: keep-alive
     * Cache-Control: max-age=0
     * ...
     * 该函数目的就是为了获取/index.html字符串
     */
    private String parseUri(String requestString) {
        int index1, index2;
        String s = "";
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1)

                s = requestString.substring(index1 + 1, index2).replace("#", "");
                if (s.equals("/")){
                    return "index.html";
                }
                else return s;
        }
        return "index.html";
//        return null;
    }

    public String getUri() {
        return uri;
    }
    public String getExeFile(){
        return postInfo.exefile;
    }
    public String getVariables(){
        return postInfo.variables;
    }
    public String getReferer(){
        return postInfo.referer;
    }

}

class PostMethodInfo{
    public String exefile;
    public String variables;
    public String referer;
}