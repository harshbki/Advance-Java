package com.shopsphere.network.server;

import com.shopsphere.network.model.Product;
import java.io.*;import java.net.ServerSocket;import java.net.Socket;import java.nio.charset.StandardCharsets;

public class InventoryServer {
    private static final int PORT=5050;
    public static void main(String[] args)throws IOException{
        InventoryDAO dao=new InventoryDAO();
        try(ServerSocket server=new ServerSocket(PORT)){
            System.out.println("ShopSphere inventory server listening on port "+PORT);
            while(true){Socket client=server.accept();new Thread(()->handle(client,dao),"shopsphere-inventory-client").start();}
        }
    }
    private static void handle(Socket socket,InventoryDAO dao){
        try(Socket client=socket){client.setSoTimeout(5000);
            BufferedReader input=new BufferedReader(new InputStreamReader(client.getInputStream(),StandardCharsets.UTF_8));
            String request=input.readLine();if(request==null)return;String[] parts=request.trim().split("\\s+",2);String command=parts[0];
            switch(command){
                case "GET_PRODUCT"->{int id=Integer.parseInt(parts[1]);Product product=dao.findProduct(id);ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream());out.writeObject(product);out.flush();}
                case "GET_ORDER"->{int id=Integer.parseInt(parts[1]);com.shopsphere.network.model.Order order=dao.findOrder(id);ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream());out.writeObject(order);out.flush();}
                case "GET_STOCK"->{int id=Integer.parseInt(parts[1]);int stock=dao.getStock(id);write(client,stock<0?"NOT_FOUND":"STOCK "+stock);}
                case "SEARCH_PRODUCT"->{String q=parts.length>1?parts[1]:"";write(client,dao.search(q).toString());}
                default->write(client,"ERROR Unknown command");
            }
        }catch(Exception ex){System.err.println("Inventory request failed: "+ex.getMessage());}
    }
    private static void write(Socket socket,String value)throws IOException{socket.getOutputStream().write((value+"\n").getBytes(StandardCharsets.UTF_8));socket.getOutputStream().flush();}
}
