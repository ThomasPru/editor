package org.ulco;

import java.util.Vector;

public class Layer {
    private Vector<GraphicsObject> m_list;
    private int m_ID;

    public Layer() {
        m_list = new Vector<GraphicsObject>();
        m_ID=ID.getGeneratorId().getID();
    }

    public Layer(String json) {
        m_list= new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int endIndex = str.lastIndexOf("}");
        int groupsIndex = str.indexOf("groups");
        if(groupsIndex > 0) {
            parseGroups(str.substring(objectsIndex + 8, endIndex - 1));
        }
        parseObjects(str.substring(objectsIndex + 9, endIndex - 1));
    }

    public Vector<GraphicsObject> getObjectList(){
        return m_list;
    }

    public void add(GraphicsObject o) {
        m_list.add(o);
    }

    public GraphicsObject get(int index) {
        return m_list.elementAt(index);
    }

    public int getObjectNumber() {
        return m_list.size();
    }

    public int getID() {
        return m_ID;
    }

    private void parseGroups(String groupsStr) {
        String groupStr;
        while (!groupsStr.isEmpty()) {
            int separatorIndex = searchSeparator(groupsStr);
            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            m_list.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private void parseObjects(String objectsStr) {
        String objectStr;
        while (!objectsStr.isEmpty()) {
            int separatorIndex = searchSeparator(objectsStr);
            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            m_list.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
    }

    private int searchSeparator(String str) {
        int index = 0;
        int level = 0;
        boolean found = false;

        while (!found && index < str.length()) {
            if (str.charAt(index) == '{') {
                ++level;
                ++index;
            } else if (str.charAt(index) == '}') {
                --level;
                ++index;
            } else if (str.charAt(index) == ',' && level == 0) {
                found = true;
            } else {
                ++index;
            }
        }
        if (found) {
            return index;
        } else {
            return -1;
        }
    }


    public String toJson() {
        String str = "{ type: layer, objects : { ";

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);

            str += element.toJson();
            if (i < m_list.size() - 1) {
                str += ", ";
            }
        }
        return str + " } }";
    }


}
