/*
 * This file is part of AndProx, an application for using Proxmark3 on Android.
 *
 * Copyright 2016-2018 Michael Farrell <micolous+git@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Under section 7 of the GNU General Public License v3, the following additional
 * terms apply to this program:
 *
 *  (b) You must preserve reasonable legal notices and author attributions in
 *      the program.
 *  (c) You must not misrepresent the origin of this program, and need to mark
 *      modified versions in reasonable ways as different from the original
 *      version (such as changing the name and logos).
 *  (d) You may not use the names of licensors or authors for publicity
 *      purposes, without explicit written permission.
 */
package au.id.micolous.andprox.serial;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hoho.android.usbserial.driver.UsbSerialPort;

import java.io.IOException;

import au.id.micolous.andprox.natives.NativeSerialWrapper;
import au.id.micolous.andprox.natives.SerialInterface;

/**
 * UsbSerialAdapter acts as an interface to wrap a {@link UsbSerialPort} into a
 * {@link SerialInterface}.
 */
public class UsbSerialAdapter implements SerialInterface {
    private static final int TIMEOUT = NativeSerialWrapper.TIMEOUT;

    @Nullable
    private UsbSerialPort mPort;

    public UsbSerialAdapter(@NonNull UsbSerialPort port) {
        mPort = port;
    }

    @Override
    public int send(@NonNull byte[] pbtTx) throws IOException {
        if (mPort == null) {
            return 0;
        }

        return mPort.write(pbtTx, TIMEOUT);
    }

    @Override
    public int receive(@NonNull byte[] pbtRx) throws IOException {
        if (mPort == null) {
            return -1;
        }

        return mPort.read(pbtRx, TIMEOUT);
    }

    @Override
    public void close() {
        if (mPort == null) {
            return;
        }

        try {
            mPort.close();
        } catch (IOException ignored) {
            // ignored
        }

        mPort = null;
    }
}
