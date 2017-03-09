package org.alvin.factory;

import java.util.Map;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.bean.shape.data.usecase.UseCaseActorShape;
import org.alvin.bean.shape.data.usecase.UseCaseShape;
import org.alvin.bean.shape.data.usecase.UseCaseSysBorderShape;
import org.alvin.bean.shape.data.usecase.line.UseCaseLineShape;
import org.alvin.biz.usecase.UseCaseDrageBiz;
import org.alvin.biz.usecase.UseCaseDrawPanelBiz;
import org.alvin.sys.Application;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * ÓÃÀý¹¤³§
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class UseCaseShapeFactory extends AbstractShapeFactory {

	public UseCaseShapeFactory() {
		Application.drawPanelBiz = new UseCaseDrawPanelBiz();
		Application.dragBiz = new UseCaseDrageBiz();
	}

	public BaseDataShape createMenuShapeByType(String type) {
		BaseDataShape shape = null;
		if (type.equals(ShapeHelper.USECASE_CASE)) {
			UseCaseShape usecase = new UseCaseShape();
			usecase.setText("");
			usecase.setW(60);
			usecase.setH(30);
			shape = usecase;
		} else if (type.equals(ShapeHelper.USECASE_ACTOR)) {
			UseCaseActorShape actor = new UseCaseActorShape();
			actor.setW(40);
			actor.setH(60);
			actor.setText("");
			shape = actor;
		} else if (type.equals(ShapeHelper.USECASE_SYS_BORDER)) {
			UseCaseSysBorderShape sys = new UseCaseSysBorderShape();
			sys.setW(60);
			sys.setH(60);
			sys.setText("");
			shape = sys;
		}
		if (shape != null) {
			shape.setModel(BaseDataShape.MODE_MENU);
		}
		return shape;
	}

	public BaseDataShape createShapeFactory(String type) {
		if (type.equals(ShapeHelper.USECASE_CASE)) {
			return new UseCaseShape();
		} else if (type.equals(ShapeHelper.USECASE_ACTOR)) {
			return new UseCaseActorShape();
		} else if (type.equals(ShapeHelper.USECASE_SYS_BORDER)) {
			return new UseCaseSysBorderShape();
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
		if (type.equals(ShapeHelper.USECASE_ACTOR)) {
			shape = new UseCaseActorShape();
		} else if (type.equals(ShapeHelper.USECASE_CASE)) {
			shape = new UseCaseShape();
		} else if (type.equals(ShapeHelper.USECASE_SYS_BORDER)) {
			shape = new UseCaseSysBorderShape();
		} else if (type.equals(ShapeHelper.USECASE_LINE)) {
			shape = new UseCaseLineShape();
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
