package com.shopsphere.network.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/** Educational URL/URLConnection protocol demonstration; pass a URL as the first argument. */
public class UrlConnectionDemo {
    public static void main(String[] args)throws Exception{
        if(args.length==0){System.out.println("Usage: UrlConnectionDemo <http(s)://... or file:... URL>");return;}
        URL url=new URL(args[0]);URLConnection connection=url.openConnection();connection.setConnectTimeout(5000);connection.setReadTimeout(5000);
        System.out.println("Protocol: "+url.getProtocol());System.out.println("Content type: "+connection.getContentType());
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8))){
            for(int i=0;i<5;i++){String line=reader.readLine();if(line==null)break;System.out.println(line);}
        }
    }
}
