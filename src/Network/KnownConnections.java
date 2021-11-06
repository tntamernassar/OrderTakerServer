package Network;

import Network.NetworkMessages.Out.OutGoingNetworkMessage;

import java.util.HashMap;

public class KnownConnections {


    private HashMap<String, HashMap<String, ConnectionHandler>> knownConnection;

    public KnownConnections(){
        this.knownConnection = new HashMap<>();
    }


    public void addConnection(String restaurant, String serialNumber, ConnectionHandler connectionHandler){
        if(!knownConnection.containsKey(restaurant)){
            knownConnection.put(restaurant, new HashMap<>());
        }

        knownConnection.get(restaurant).put(serialNumber, connectionHandler);
    }

    public void sendToAllBut(String restaurant, String serialNumber, OutGoingNetworkMessage outGoingNetworkMessage){
        if(knownConnection.containsKey(restaurant)){
            for(String sn: knownConnection.get(restaurant).keySet()){
                if(!sn.equals(serialNumber)){
                    knownConnection.get(restaurant).get(sn).send(outGoingNetworkMessage);
                }
            }
        }
    }



}
