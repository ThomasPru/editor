package org.ulco;

import java.util.Vector;

public class Utility {
    public static GraphicsObjects select(Point pt, double distance,Layer layer) {
        GraphicsObjects list = new GraphicsObjects();
        Vector <GraphicsObject> go_list = layer.getObjectList();
        for (GraphicsObject object : go_list) {
            if (object.isClosed(pt, distance)) {
                list.add(object);
            }
        }
        return list;
    }

    public static GraphicsObjects select(Point pt, double distance, Document document) {
        GraphicsObjects list = new GraphicsObjects();
        Vector <Layer> go_list = document.getObjectList();
        for (Layer layer : go_list) {
            list.addAll(Utility.select(pt, distance,layer));
        }
        return list;
    }
}
