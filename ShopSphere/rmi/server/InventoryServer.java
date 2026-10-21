package com.shopsphere.rmi.server;

import com.shopsphere.rmi.remote.InventoryService;import java.rmi.Naming;import java.rmi.registry.LocateRegistry;import java.rmi.registry.Registry;import java.rmi.server.UnicastRemoteObject;

public class InventoryServer {
    public static void main(String[] args)throws Exception{
        try{LocateRegistry.createRegistry(Registry.REGISTRY_PORT);}catch(java.rmi.server.ExportException alreadyRunning){/* reuse local registry */}
        InventoryService service=new InventoryServiceImpl();InventoryService stub=(InventoryService)UnicastRemoteObject.exportObject(service,0);
        Naming.rebind("rmi://localhost/InventoryService",stub);System.out.println("ShopSphere RMI InventoryService is ready.");
    }
}
