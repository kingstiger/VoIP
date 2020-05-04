package com.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    public static String getLocalIpAddr() throws
                                          UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }
}
