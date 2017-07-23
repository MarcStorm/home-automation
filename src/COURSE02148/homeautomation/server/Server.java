package COURSE02148.homeautomation.server;

import COURSE02148.homeautomation.server.serverhandler.ServerHandleClient;
import org.apache.commons.logging.LogFactory;
import org.cmg.resp.behaviour.Agent;
import org.cmg.resp.comp.Node;
import org.cmg.resp.knowledge.ts.TupleSpace;
import org.cmg.resp.topology.SocketPort;

import java.io.IOException;
import java.net.Inet4Address;

public class Server {

    String serverName;
    SocketPort serverSocket;
    Node serverNode;
    Agent serverAgent;
    public TupleSpace serverTupleSpace;
    ServerHandleClient serverHandleClient;
    public String serverHost;
    public int serverPort;
    FailureDetectorAgent failureDetectorAgent;

    public Server(int serverPort) throws Exception {
        serverName = "Server";
        serverHost = Inet4Address.getLocalHost().getHostAddress();
        this.serverPort = serverPort;
        this.serverSocket = new SocketPort(this.serverHost, this.serverPort);
        System.out.println("SERVER STARTED ON: " + serverHost);
        serverTupleSpace = new TupleSpace();
        serverNode = new Node(serverName, serverTupleSpace);
        serverNode.addPort(this.serverSocket);
        serverAgent = new ServerAgent(serverTupleSpace);
        serverNode.addAgent(serverAgent);
        failureDetectorAgent = new FailureDetectorAgent(this);
        serverNode.addAgent(failureDetectorAgent);
        serverHandleClient = new ServerHandleClient(this);
    }

    public void startServer(){
        serverNode.start();
        serverHandleClient.startClient();
    }

}