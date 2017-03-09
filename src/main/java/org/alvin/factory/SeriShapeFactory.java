package org.alvin.factory;

import java.util.Map;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.bean.shape.data.BaseDataShape;
import org.alvin.bean.shape.data.BaseLineShape;
import org.alvin.bean.shape.data.seri.SeriActiveShape;
import org.alvin.bean.shape.data.seri.SeriConstrainShape;
import org.alvin.bean.shape.data.seri.SeriLifelineShape;
import org.alvin.bean.shape.data.seri.line.BaseSeriLineShape;
import org.alvin.bean.shape.data.seri.line.SeriAsynchronousMessageLineShape;
import org.alvin.bean.shape.data.seri.line.SeriMessageCallLineShape;
import org.alvin.bean.shape.data.seri.line.SeriMessageLineShape;
import org.alvin.bean.shape.data.seri.line.SeriReturnMessageBackLineShape;
import org.alvin.bean.shape.data.seri.line.SeriReturnMessageLineShape;
import org.alvin.biz.seri.SeriDragPanelBiz;
import org.alvin.biz.seri.SeriDrawPanelBiz;
import org.alvin.sys.Application;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * 序列图的简单工厂
 * 
 * @author 唐植超
 * 
 */
public class SeriShapeFactory extends AbstractShapeFactory {

	public SeriShapeFactory() {
		Application.drawPanelBiz = new SeriDrawPanelBiz();
		Application.dragBiz = new SeriDragPanelBiz();
	}

	public BaseDataShape createMenuShapeByType(String type) {
		BaseDataShape shape = null;
		// 序列图
		if (type.equals(ShapeHelper.SERI_ACTIVE)) {
			SeriActiveShape active = new SeriActiveShape();
			active.setH(40);
			shape = active;
		} else if (type.equals(ShapeHelper.SERI_CONSTRAIN)) {
			SeriConstrainShape active = new SeriConstrainShape();
			active.setH(30);
			active.setW(60);
			shape = active;
		} else if (type.equals(ShapeHelper.SERI_LIFE)) {
			SeriLifelineShape life = new SeriLifelineShape();
			life.setH(30);
			life.setW(40);
			life.setTitleHeight(10);
			shape = life;
		} else if (type.equals(ShapeHelper.SERI_MESSAGE)) {
			SeriMessageLineShape msg = new SeriMessageLineShape();
			msg.setX(0);
			msg.setY(40);
			shape = msg;
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_CALL)) {
			SeriMessageCallLineShape callMsg = new SeriMessageCallLineShape();
			callMsg.setX(0);
			callMsg.setY(15);
			callMsg.setX2(0);
			callMsg.setY2(60);
			shape = callMsg;
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_RETURN)) {
			SeriReturnMessageLineShape msg = new SeriReturnMessageLineShape();
			msg.setX(0);
			msg.setY(40);
			shape = msg;
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_CALL_RETURN)) {
			SeriReturnMessageBackLineShape callMsg = new SeriReturnMessageBackLineShape();
			callMsg.setX(0);
			callMsg.setY(15);
			callMsg.setX2(0);
			callMsg.setY2(60);
			shape = callMsg;
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_ASY)) {
			SeriAsynchronousMessageLineShape msg = new SeriAsynchronousMessageLineShape();
			msg.setX(0);
			msg.setY(40);
			shape = msg;
		}
		if (shape != null) {
			shape.setModel(BaseDataShape.MODE_MENU);
		}
		return shape;
	}

	public BaseDataShape createShapeFactory(String type) {
		BaseDataShape shape = null;
		if (type.equals(ShapeHelper.SERI_ACTIVE)) {
			shape = new SeriActiveShape();
		} else if (type.equals(ShapeHelper.SERI_CONSTRAIN)) {
			shape = new SeriConstrainShape();
		} else if (type.equals(ShapeHelper.SERI_LIFE)) {
			shape = new SeriLifelineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE)) {
			shape = new SeriMessageLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_CALL)) {
			shape = new SeriMessageCallLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_RETURN)) {
			shape = new SeriReturnMessageLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_CALL_RETURN)) {
			shape = new SeriReturnMessageBackLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_ASY)) {
			shape = new SeriAsynchronousMessageLineShape();
		}
		if (shape != null && shape instanceof BaseSeriLineShape) {
			shape.setText("");
		}
		return shape;
	}

	public BaseDataShape toShape(Node node, Map<String, BaseDataShape> map) {
		// <id tyep x y w h>text</shape>
		// <id type bid bw eid ew>text</shape>
		NamedNodeMap nodeMap = node.getAttributes();
		Node att = nodeMap.getNamedItem("type");
		String type = att.getNodeValue();
		BaseDataShape shape = null;
		if (type.equals(ShapeHelper.SERI_ACTIVE)) {
			shape = new SeriActiveShape();
		} else if (type.equals(ShapeHelper.SERI_CONSTRAIN)) {
			shape = new SeriConstrainShape();
		} else if (type.equals(ShapeHelper.SERI_LIFE)) {
			shape = new SeriLifelineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE)) {
			shape = new SeriMessageLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_CALL)) {
			shape = new SeriMessageCallLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_RETURN)) {
			shape = new SeriReturnMessageLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_CALL_RETURN)) {
			shape = new SeriReturnMessageBackLineShape();
		} else if (type.equals(ShapeHelper.SERI_MESSAGE_ASY)) {
			shape = new SeriAsynchronousMessageLineShape();
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
			String[] p;
			if (way.equals("-1")) {
				p = id.split(",");
				shape.setX(Double.parseDouble(p[0]));
				shape.setY(Double.parseDouble(p[1]));
			} else {
				line.setBeginShape(map.get(id), way);
			}
			att = nodeMap.getNamedItem("eid");
			id = att.getNodeValue();
			att = nodeMap.getNamedItem("eway");
			way = att.getNodeValue();
			if (way.equals("-1")) {
				p = id.split(",");
				((BaseLineShape) shape).setX2(Double.parseDouble(p[0]));
				((BaseLineShape) shape).setY2(Double.parseDouble(p[1]));
			} else {
				line.setEndShape(map.get(id), way);
			}
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
