package org.alvin.bean.shape.data.seri.line;

import org.alvin.bean.shape.ShapeHelper;
import org.alvin.sys.Language;

/**
 * µ÷ÓÃ
 * 
 * @author ÌÆÖ²³¬
 * 
 */
public class SeriMessageCallLineShape extends BaseCallLineShape {

	private static final long serialVersionUID = 1L;

	public SeriMessageCallLineShape() {
		type = ShapeHelper.SERI_MESSAGE_CALL;
		text = Language.getValue("SeriMsgCall");
	}

}
