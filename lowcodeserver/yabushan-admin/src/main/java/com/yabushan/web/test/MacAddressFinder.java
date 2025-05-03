package com.yabushan.web.test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Formatter;

public class MacAddressFinder {

    public static void main(String[] args) {
        try {
            // 获取所有网络接口
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // 跳过没有硬件地址（MAC地址）或者回环接口（localhost）
                if (networkInterface.isLoopback() || !networkInterface.isUp() || networkInterface.getHardwareAddress() == null) {
                    continue;
                }

                // 获取MAC地址
                byte[] macBytes = networkInterface.getHardwareAddress();

                // 打印MAC地址
                StringBuilder macAddress = new StringBuilder();
                for (int i = 0; i < macBytes.length; i++) {
                    macAddress.append(String.format("%02X%s", macBytes[i], (i < macBytes.length - 1) ? "-" : ""));
                }

                // 获取网络接口名称
                String interfaceName = networkInterface.getName();
                // 获取并打印网络接口关联的IP地址（可选）
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    System.out.println("Interface: " + interfaceName);
                    System.out.println("MAC Address: " + macAddress.toString());
                    System.out.println("IP Address: " + inetAddress.getHostAddress());
                    System.out.println("-------------------------");
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}