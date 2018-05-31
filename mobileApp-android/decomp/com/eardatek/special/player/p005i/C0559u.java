package com.eardatek.special.player.p005i;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.util.List;

public class C0559u {
    private WifiManager f492a;
    private WifiInfo f493b = this.f492a.getConnectionInfo();
    private List<ScanResult> f494c;
    private List<WifiConfiguration> f495d;

    public C0559u(Context context) {
        this.f492a = (WifiManager) context.getSystemService("wifi");
    }

    private WifiConfiguration m732a(String str) {
        for (WifiConfiguration wifiConfiguration : this.f492a.getConfiguredNetworks()) {
            if (wifiConfiguration.SSID.equals("\"" + str + "\"")) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    public WifiConfiguration m733a(String str, String str2, int i, String str3) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        if (str3.equals("wifi")) {
            wifiConfiguration.SSID = "\"" + str + "\"";
            C0539i.m643b("EardatekVersion2", wifiConfiguration.SSID);
            WifiConfiguration a = m732a(str);
            if (a != null) {
                C0539i.m643b("", "network is exist");
                this.f492a.removeNetwork(a.networkId);
            }
            if (i == 1) {
                wifiConfiguration.wepKeys[0] = "\"\"";
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.wepTxKeyIndex = 0;
            } else if (i == 2) {
                wifiConfiguration.hiddenSSID = true;
                wifiConfiguration.wepKeys[0] = "\"" + str2 + "\"";
            } else {
                wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
                wifiConfiguration.hiddenSSID = true;
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
            }
        } else {
            wifiConfiguration.SSID = str;
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
            if (i == 1) {
                wifiConfiguration.wepKeys[0] = "\"\"";
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.wepTxKeyIndex = 0;
            } else if (i == 2) {
                wifiConfiguration.hiddenSSID = true;
                wifiConfiguration.wepKeys[0] = str2;
            } else if (i == 3) {
                wifiConfiguration.preSharedKey = str2;
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
            }
        }
        return wifiConfiguration;
    }

    public void m734a() {
        if (!this.f492a.isWifiEnabled()) {
            this.f492a.setWifiEnabled(true);
        }
    }

    public void m735a(int i) {
        this.f492a.disableNetwork(i);
        this.f492a.disconnect();
    }

    public boolean m736a(WifiConfiguration wifiConfiguration) {
        return this.f492a.enableNetwork(this.f492a.addNetwork(wifiConfiguration), true);
    }

    public List<WifiConfiguration> m737b() {
        return this.f495d;
    }

    public void m738c() {
        this.f492a.startScan();
        this.f494c = this.f492a.getScanResults();
        this.f495d = this.f492a.getConfiguredNetworks();
    }

    public void m739d() {
        int networkId = this.f493b.getNetworkId();
        C0539i.m643b("EardatekVersion2", "remove networkdid:" + networkId);
        m735a(networkId);
        this.f492a.removeNetwork(networkId);
    }
}
