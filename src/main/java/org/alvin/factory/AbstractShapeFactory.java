package org.alvin.factory;

import java.util.Map;

import org.alvin.bean.shape.data.BaseDataShape;
import org.w3c.dom.Node;

/**
 */
public abstract class AbstractShapeFactory {
	/**
	 *
	 * @param type
	 * @return
	 */
	public abstract BaseDataShape createMenuShapeByType(String type);

	/**
	 *
	 * @param type
	 * @return
	 */
	public abstract BaseDataShape createShapeFactory(String type);

	/**
	 *
	 * @param node
	 * @param map
	 * @return
	 */
	public abstract BaseDataShape toShape(Node node,
			Map<String, BaseDataShape> map);
	

}
