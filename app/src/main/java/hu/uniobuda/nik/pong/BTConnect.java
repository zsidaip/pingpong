package hu.uniobuda.nik.pong;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Dani on 5/8/2016.
 */
public class BTConnect {

    private BluetoothAdapter mBluetoothAdapter;
    public final BluetoothSocket BTSocket;

    public BTConnect(BluetoothDevice device,BluetoothAdapter BTAdapter) {
        mBluetoothAdapter = BTAdapter;
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("5b9f32a0-d126-4fc0-a91f-244fdbc94834"));
        } catch (IOException e) { }
        BTSocket = tmp;
    }

    public boolean run() {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            BTSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                BTSocket.close();
            } catch (IOException closeException) { }
            return false;
        }

        // Do work to manage the connection (in a separate thread)
        return true;
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            BTSocket.close();
        } catch (IOException e) { }
    }
}