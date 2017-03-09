package org.alvin.factory;

import java.util.Map;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.bean.shape.data.active.ActiveActionShape;
import org.alvin.bean.shape.data.active.ActiveConditionShape;
import org.alvin.bean.shape.data.active.ActiveEndShape;
import org.alvin.bean.shape.data.active.ActiveInterfaceShape;
import org.alvin.bean.shape.data.active.ActiveLaneShape;
import org.alvin.bean.shape.data.active.ActiveStartShape;
import org.alvin.bean.shape.data.active.line.ActiveLineShape;
import org.alvin.biz.active.ActiveDragPanelBiz;
import org.alvin.biz.active.ActiveDrawPanelBiz;
import org.alvin.sys.Application;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * 活动图的简单工厂
 * 
 * @author 唐植超
 * 
 */
public class ActiveShapeFactory extends AbstractShapeFactory {

	public ActiveShapeFactory() {
		Application.drawPanelBiz = new ActiveDrawPanelBiz();
		Application.dragBiz = new ActiveDragPanelBiz();
	}

	public BaseDataShape createMenuShapeByType(String type) {
		BaseDataShape shape = null;
		if (type.equals(ShapeHelper.ACTIVE_START)) {
			shape = new ActiveStartShape();
		} else if (type.equals(ShapeHelper.ACTIVE_END)) {
			shape = new ActiveEndShape();
		} else if (type.equals(ShapeHelper.ACTIVE_ACTION)) {
			ActiveActionShape action = new ActiveActionShape();
			action.setW(60);
			action.setH(30);
			shape = action;
		} else if (type.equals(ShapeHelper.ACTIVE_CONDITION)) {
			ActiveConditionShape condition = new ActiveConditionShape();
			condition.setW(60);
			condition.setH(30);
			shape = condition;
		} else if (type.equals(ShapeHelper.ACTIVE_INTEGERFACE)) {
			ActiveInterfaceShape inter = new ActiveInterfaceShape();
			inter.setW(60);
			inter.setH(30);
			inter.setTitleHeight(10);
			shape = inter;
		} else if (type.equals(ShapeHelper.ACTIVE_LANE)) {
			ActiveLaneShape lane = new ActiveLaneShape();
			lane.setW(20);
			lane.setH(40);
			lane.setTitleHeight(10);
			shape = lane;
		}
		if (shape != null) {
			shape.setModel(BaseDataShape.MODE_MENU);
		}
		return shape;
	}

	public BaseDataShape createShapeFactory(String type) {
		if (type.equals(ShapeHelper.ACTIVE_START)) {
			return new ActiveStartShape();
		} else if (type.equals(ShapeHelper.ACTIVE_END)) {
			return new ActiveEndShape();
		} else if (type.equals(ShapeHelper.ACTIVE_ACTION)) {
			return new ActiveActionShape();
		} else if (type.equals(ShapeHelper.ACTIVE_CONDITION)) {
			return new ActiveConditionShape();
		} else if (type.equals(ShapeHelper.ACTIVE_INTEGERFACE)) {
			return new ActiveInterfaceShape();
		} else if (type.equals(ShapeHelper.ACTIVE_LANE)) {
			return new ActiveLaneShape();
		}
		return null;
	}

	public BaseDataShape toShape(Node node, Map<String, BaseDataShape> map) {
		// <id tyep x y w h>text</shape>
		// <id type bid bw eid ew>text</shape>
		NamedNodeMap nodeMap = node.getAttributes();
		Node att = nodeMap.getNamedItem("type");
		String type = att.getNodeValue();
		BaseDataShape shape = null;
		if (type.equals(ShapeHelper.ACTIVE_ACTION)) {
			shape = new ActiveActionShape();
		} else if (type.equals(ShapeHelper.ACTIVE_CONDITION)) {
			shape = new ActiveConditionShape();
		} else if (type.equals(ShapeHelper.ACTIVE_START)) {
			shape = new ActiveStartShape();
		} else if (type.equals(ShapeHelper.ACTIVE_END)) {
			shape = new ActiveEndShape();
		} else if (type.equals(ShapeHelper.ACTIVE_INTEGERFACE)) {
			shape = new ActiveInterfaceShape();
		} else if (type.equals(ShapeHelper.ACTIVE_LANE)) {
			shape = new ActiveLaneShape();
		} else if (type.equals(ShapeHelper.ACTIVE_LINE)) {
			shape = new ActiveLineShape();
		}
		att = nodeMap.getNamedItem("id");
		shape.setId(att.getNodeValue());
		shape.setText(node.getTextContent());
		if (shape instanceof BaseLineShape) {
			BaseLineShape line = (BaseLineShape) shape;
			String way;
			String id;
			att = nodeMap.getNamedItem("bid");
			id = att.getNodeValue();
			att = nodeMap.getNamedItem("bway");
			way = att.getNodeValue();
			line.setBeginShape(map.get(id), way);
			att = nodeMap.getNamedItem("eid");
			id = att.getNodeValue();
			att = nodeMap.getNamedItem("eway");
			way = att.getNodeValue();
			line.setEndShape(map.get(id), way);
		} else {
			att = nodeMap.getNamedItem("x");
			shape.setX(Double.parseDouble(att.getNodeValue()));
			att = nodeMap.getNamedItem("y");
			shape.setY(Double.parseDouble(att.getNodeValue()));
			att = nodeMap.getNamedItem("w");
			shape.setW(Double.parseDouble(att.getNodeValue()));
			att = nodeMap.getNamedItem("h");
			shape.setH(Double.parseDouble(att.getNodeValue()));
			map.put(shape.getId(), shape);
		}
		return shape;
	}

}
