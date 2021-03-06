package Logic.Menu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntFunction;

public class MenuSection implements Serializable {
    private String section;
    private String[] addons;
    private boolean maxOne;

    public MenuSection(String section, String[] addons, boolean maxOne){
        this.section = section;
        this.addons = addons;
        this.maxOne = maxOne;
    }

    public MenuSection(JSONObject menuSection){
        String section = (String) menuSection.get("section");
        boolean maxOne = (boolean) menuSection.get("maxOne");
        JSONArray addons = (JSONArray) menuSection.get("addons");

        this.addons = new String[addons.size()];
        for(int i=0;i<addons.size();i++){
            this.addons[i] = (String)addons.get(i);
        }

        this.section = section;
        this.maxOne = maxOne;

    }

    public void setAddons(String[] addons) {
        this.addons = addons;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setMaxOne(boolean maxOne) {
        this.maxOne = maxOne;
    }

    public String getSection() {
        return section;
    }

    public String[] getAddons() {
        return addons;
    }

    public boolean isMaxOne() {
        return maxOne;
    }

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("section", section);
        o.put("addons", new LinkedList(List.of(addons)));
        o.put("maxOne", maxOne);
        return o;
    }
}
