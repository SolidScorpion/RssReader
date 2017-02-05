package com.example.antony.rssreader.models;

/**
 * Created by Pripachkin on 05.02.2017.
 */

public class MenuItem {
    private String serviceName;
    private String serviceUrl;

    public MenuItem(String serviceName, String serviceUrl) {
        this.serviceName = serviceName;
        this.serviceUrl = serviceUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItem menuItem = (MenuItem) o;

        if (!serviceName.equals(menuItem.serviceName)) return false;
        return serviceUrl.equals(menuItem.serviceUrl);

    }

    @Override
    public int hashCode() {
        int result = serviceName.hashCode();
        result = 31 * result + serviceUrl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                '}';
    }
}
