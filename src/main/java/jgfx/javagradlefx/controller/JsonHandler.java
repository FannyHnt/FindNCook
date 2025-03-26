package jgfx.javagradlefx.controller;

import jgfx.javagradlefx.model.Recette;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    public List<Recette> jsonToRecipe(JSONObject obj) {
        List<Recette> recettes = new ArrayList<Recette>();
        for (int i = 0; i < obj.getJSONArray("results").length(); i++) {
            JSONObject recette = obj.getJSONArray("results").getJSONObject(i);
            Recette rec = new Recette(recette.getLong("id"), recette.getString("title"), recette.getString("image"));
            recettes.add(rec);
        }
        return recettes;
    }
}
