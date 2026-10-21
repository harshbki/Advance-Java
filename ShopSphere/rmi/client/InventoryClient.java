package com.shopsphere.rmi.client;

import com.shopsphere.rmi.remote.InventoryService;import java.rmi.Naming;

public class InventoryClient {
    public static void main(String[] args)throws Exception{
        int id=args.length==0?1:Integer.parseInt(args[0]);
        InventoryService service=(InventoryService)Naming.lookup("rmi://localhost/InventoryService");
        System.out.println(service.getProduct(id));System.out.println("Stock: "+service.getStock(id));
        if(args.length>1)System.out.println("Stock updated: "+service.updateStock(id,Integer.parseInt(args[1])));
    }
}
