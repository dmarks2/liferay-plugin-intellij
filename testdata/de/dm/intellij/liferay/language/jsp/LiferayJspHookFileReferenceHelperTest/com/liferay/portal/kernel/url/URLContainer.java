package com.liferay.portal.kernel.url;

import java.net.URL;

import java.util.Set;

public interface URLContainer {

    public URL getResource(String name);

    public Set<String> getResources(String path);

}