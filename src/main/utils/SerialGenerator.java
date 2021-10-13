package main.utils;

import main.java.objects.Patient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public final class SerialGenerator {
    private final ArrayList<Patient> serials = new ArrayList<>();
    private volatile boolean adding = false;

    private static SerialGenerator generator = null;

    private SerialGenerator() {}

    private synchronized int getSerial(Patient patient) {
        if (adding)
            doWait();

        adding = true;
        serials.add(patient);
        int serial = serials.size();
        adding = false;
        notify();
        return serial;
    }

    private void doWait() {
        try {
            wait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private static void initialize() {
        if (generator == null) {
            generator = new SerialGenerator();
            System.out.println("created generator" + generator);
        }
    }

    public static void main(String[] args) throws Exception {
        initialize();

        ServerSocket server = new ServerSocket(8080);

        while (true) {
            Socket clientSocket = server.accept();

            BufferedWriter clientSerial = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); // write to client socket
            ObjectInputStream patientReader = new ObjectInputStream(clientSocket.getInputStream()); // object reader

            clientSerial.write(generator.getSerial((Patient) patientReader.readObject()));

            clientSerial.close();
            patientReader.close();
        }
    }
}
