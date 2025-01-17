package com.example.practice.Server;

import com.example.practice.Database.club;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketHandler {
    private final Socket clientSocket;
    private club clubObject;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;


    public ClientSocketHandler(String host, int port) throws IOException {
        this.clientSocket = new Socket(host, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public ClientSocketHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    synchronized public void write(Object obj) throws IOException
    {
        out.writeObject(obj);
    }

    public Object read() throws IOException, ClassNotFoundException {
        return in.readObject();
    }

    public void setClubObject(club clubObject) {
        this.clubObject = clubObject;
    }
    public club getClubObject() {
        return clubObject;
    }

    public void reset() throws IOException {
        out.reset();
    }

    public void close() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }
}
