package com.liferay.portal.kernel.templateparser;

import java.util.LinkedHashMap;
import java.util.List;

public class TemplateNode extends LinkedHashMap<String, Object> {

    public List<TemplateNode> getSiblings() {
        return null;
    }

}