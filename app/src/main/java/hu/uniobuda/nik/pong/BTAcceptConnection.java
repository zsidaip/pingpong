package hu.uniobuda.nik.pong;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;


public class BTAcceptConnection {
    public final BluetoothServerSocket mmServerSocket;
    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothSocket BTSocket;

    public BTAcceptConnection(BluetoothAdapter BTAdapter) {
        mBluetoothAdapter = BTAdapter;
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("PONGGAME", UUID.fromString("5b9f32a0-d126-4fc0-a91f-244fdbc94834"));
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    public boolean run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                return false;
            }
            // If a connection was accepted
            if (socket != null) {
                BTSocket = socket;
                return true;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
}