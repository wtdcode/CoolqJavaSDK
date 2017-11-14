package cqjsdk.server;

import cqjsdk.msg.*;
import cqjsdk.*;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Receiver extends Thread{

    private BlockingQueue<Msg> msgq;
    private Sender sender;
    private DatagramSocket server;
    private Dispatcher dispatcher;

    public Receiver(DatagramSocket server) {
        try{
            this.msgq = new ArrayBlockingQueue<Msg>(4096);
            this.server = server;
            this.sender = null;
            this.dispatcher = null;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean initialized(){
        return this.dispatcher != null;
    }

    private void run_dispatcher(){
        this.dispatcher = Dispatcher.getDispatcher(msgq);
        this.dispatcher.start();
    }

    public void run(){
        run_dispatcher();
        byte[] buf = new byte[4096];
        Formatter formatter = Formatter.getFormatter();
        Msg msg;
        try {
            while(true){
                DatagramPacket msgpacket = new DatagramPacket(buf, buf.length);
                server.receive(msgpacket);
                msg = formatter.FormatRecv(msgpacket.getData(), msgpacket.getLength());
                msgq.put(msg);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}