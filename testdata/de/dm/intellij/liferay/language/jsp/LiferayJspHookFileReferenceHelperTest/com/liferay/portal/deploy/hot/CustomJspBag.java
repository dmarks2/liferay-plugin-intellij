package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.url.URLContainer;

import java.util.List;

public interface CustomJspBag {

    public String getCustomJspDir();

    public List<String> getCustomJsps();

    public URLContainer getURLContainer();

    public boolean isCustomJspGlobal();

}