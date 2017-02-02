package com.nio.serevr_client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by ronak on 2/2/2017.
 */
public class NIOClient implements Runnable{

    private InetSocketAddress address;
    private int port;
    private String remoteServerAddress;
    private SocketChannel socketchannel;
    private String clientName;


    public NIOClient(String address, int port,String name) {
        this.remoteServerAddress=address;
        this.port=port;
        this.address=new InetSocketAddress(this.remoteServerAddress,this.port);
        this.clientName=name;
    }

    @Override
    public void run() {
        try {
            socketchannel=SocketChannel.open(this.address);

                String message="ADD "+this.clientName;
                byte[] msg_byte=message.getBytes();
                ByteBuffer buffer=ByteBuffer.wrap(msg_byte);
                this.socketchannel.write(buffer);
                buffer.clear();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            try {

                Thread.sleep(10000);
                System.out.println("Closing the connection to remote host");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            socketchannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

