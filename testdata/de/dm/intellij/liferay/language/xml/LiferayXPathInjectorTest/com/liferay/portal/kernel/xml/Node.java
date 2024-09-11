package com.liferay.portal.kernel.xml;

import java.io.Serializable;
import java.util.List;

public interface Node extends Serializable {

	public List<Node> selectNodes(String xPathExpression);

}
