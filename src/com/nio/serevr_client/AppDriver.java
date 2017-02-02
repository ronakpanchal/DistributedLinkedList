package com.nio.serevr_client;

/**
 * Created by ronak on 2/2/2017.
 */
public class AppDriver {

    public static void main(String[] args){
        String address="localhost";
        int port=8065;
        NIOServer server=new NIOServer(address,port);
        new Thread(server).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // create 5 clients
        for(int i=0;i<5;i++){
            NIOClient client=new NIOClient(address,port,new String(i+""));
            new Thread(client).start();
        }

    }
}
