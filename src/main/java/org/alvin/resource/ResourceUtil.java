package org.alvin.resource;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.alvin.util.LogUtil;

/**
 *
 */
public class ResourceUtil {

    private ResourceUtil() {
    }

    public static Image right_img;
    public static Image wrong_img;
    public static Image myface_img;
    public static Icon new_icon;
    public static Icon line_icon;
    public static Icon open_icon;
    public static Icon save_Icon;
    public static Icon saveAs_Icon;
    public static Icon copy_icon;
    public static Icon cut_icon;
    public static Icon paste_icon;
    public static Icon del_icon;
    public static Icon redo_icon;
    public static Icon selectAll_icon;
    public static Icon undo_icon;
    public static Icon help_icon;
    public static Icon property_icon;
    public static Icon defaultCurs_icon;
    public static Icon zoomIn_icon;
    public static Icon zoomOut_icon;
    public static Icon zoomReset_icon;

    public static Image logo_img = getImage("logo.gif");
    public static Icon bg_icon = getIcon("bg.gif");
    // ���
    public static Cursor rightCursor;
    public static Cursor wrongCursor;

    public static void loadResource() {
        LogUtil.info("��Դ�����С���");
        // cursor
        right_img = getImage("right.gif");
        wrong_img = getImage("wrong.gif");

        rightCursor = Toolkit.getDefaultToolkit().createCustomCursor(right_img,
                new Point(0, 0), "move_right");
        wrongCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                ResourceUtil.wrong_img, new Point(0, 0), "move_wrong");
        // about
        myface_img = getImage("myface.bmp");
        // file menu
        new_icon = getIcon("new.gif");
        open_icon = getIcon("open.gif");
        save_Icon = getIcon("save.gif");
        saveAs_Icon = getIcon("saveas.gif");
        // toolbar button
        line_icon = getIcon("line.gif");
        defaultCurs_icon = getIcon("mouse.gif");
        // edit menu
        copy_icon = getIcon("copy.gif");
        cut_icon = getIcon("cut.gif");
        paste_icon = getIcon("paste.gif");
        del_icon = getIcon("delete.gif");
        redo_icon = getIcon("redo.gif");
        selectAll_icon = getIcon("selectAll.gif");
        undo_icon = getIcon("undo.gif");

        zoomIn_icon = getIcon("zoomin.png");
        zoomOut_icon = getIcon("zoomout.png");
        zoomReset_icon = getIcon("zoomreset.png");
        // help menu
        help_icon = getIcon("help.gif");
        property_icon = getIcon("property.gif");
    }

    private static Image getImage(String name) {
        return Toolkit.getDefaultToolkit().getImage(
                ResourceUtil.class.getResource("/" + name));
    }

    private static Icon getIcon(String fileName) {
        return new ImageIcon(getImage(fileName));
    }
}
