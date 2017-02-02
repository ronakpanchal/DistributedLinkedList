package com.nio.serevr_client;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by ronak on 2/2/2017.
 */
@SuppressWarnings("unused")
public class NIOServer implements  Runnable{

    private String address;
    private int port;
    private InetSocketAddress listenAddress;
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private ByteBuffer buffer;
    private LinkedList<Integer> list;


    public NIOServer(String address, int port) {
        this.address=address;
        this.port=port;
        this.listenAddress=new InetSocketAddress(this.address,this.port);
        this.buffer=ByteBuffer.allocate(2048);
        list=new LinkedList<>();
    }



    @Override
    public void run() {

        try {
            // All the BoilerPlat code to initialize a ServerSocket and bind a selector to it
            this.selector = Selector.open();
            this.serverChannel = ServerSocketChannel.open();
            this.serverChannel.configureBlocking(false);
            this.serverChannel.socket().bind(this.listenAddress);
            this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

            // wait for a client to connect to this ServerSocket
            while(true){
                this.selector.select();
                Iterator<SelectionKey> itr=this.selector.selectedKeys().iterator();
                while(itr.hasNext()){
                    SelectionKey key=itr.next();
                    //remove this key so it does not come up again
                    itr.remove();
                    if(!key.isValid()){
                        continue;
                    }
                    if(key.isAcceptable()){
                        this.accept(key);
                    }
                    if(key.isReadable()){
                        this.read(key);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
        Some more BoilerPlat code.Enjoy!!
     */
    @SuppressWarnings("unused")
    private void accept(SelectionKey key){
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel;
        Socket socket;

        try {
            socketChannel = channel.accept();
            socket = socketChannel.socket();
            //Print some information about the remote client
            System.out.println("The address of remote client is "+socket.getLocalSocketAddress());
            System.out.println("The port on  remote client is "+socket.getPort());

            socketChannel.configureBlocking(false);
            socketChannel.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void read(SelectionKey key){
        this.buffer.clear();
        int numRead;
        SocketChannel channel = (SocketChannel) key.channel();
        Socket socket=channel.socket();

        try {
            numRead = channel.read(this.buffer);
            // remote client shut down the connection
            if(numRead==-1){
                //check if the connection is closed
                System.out.println("Is it bound? "+socket.isBound());
                System.out.println("Is the connection closed "+socket.isClosed());
                key.channel().close();
                key.cancel();
                return;
            }
            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            String response=new String(data);
            String[] arr=response.split(" ");

                list.add(Integer.parseInt(arr[1]));

            System.out.println("List Of Elements");
            System.out.println("--------------------------------------------");
            for(Integer i:list){
                System.out.println(i);
            }
            System.out.println("--------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
