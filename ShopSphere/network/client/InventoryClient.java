package com.shopsphere.network.client;

import com.shopsphere.network.model.Product;
import java.io.*;import java.net.Socket;import java.nio.charset.StandardCharsets;

public class InventoryClient {
    public static void main(String[] args)throws Exception{
        if(args.length<1){System.out.println("Usage: InventoryClient GET_PRODUCT <id> | GET_ORDER <id> | GET_STOCK <id> | SEARCH_PRODUCT <text>");return;}
        String command=args[0].toUpperCase();String payload=String.join(" ",java.util.Arrays.copyOfRange(args,1,args.length));
        try(Socket socket=new Socket("localhost",5050)){
            socket.getOutputStream().write((command+(payload.isBlank()?"":" "+payload)+"\n").getBytes(StandardCharsets.UTF_8));socket.getOutputStream().flush();
            if("GET_PRODUCT".equals(command)){
                try(ObjectInputStream in=new ObjectInputStream(socket.getInputStream())){Product product=(Product)in.readObject();System.out.println(product==null?"Product not found.":product);}
            }else if("GET_ORDER".equals(command)){
                try(ObjectInputStream in=new ObjectInputStream(socket.getInputStream())){com.shopsphere.network.model.Order order=(com.shopsphere.network.model.Order)in.readObject();System.out.println(order==null?"Order not found.":order);}
            }else{BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));System.out.println(in.readLine());}
        }
    }
}
