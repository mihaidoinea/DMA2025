package ro.ase.ie.dma07;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeJsonParser {

    public static List<Recipe> fromJson(String recipeJSONArray) {
        List<Recipe> recipes = null;
        if(recipeJSONArray != null)
        {
            recipes = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(recipeJSONArray);
                for(int index =0; index<jsonArray.length(); index++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    Recipe recipe = readRecipe(jsonObject);
                    recipes.add(recipe);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recipes;
    }

    private static Recipe readRecipe(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("denumire");
        String description = jsonObject.getString("descriere");
        String date = jsonObject.getString("dataAdaugarii");
        int calories = jsonObject.getInt("calorii");
        String category = jsonObject.getString("categorie");
        return new Recipe(name, description, date,calories, category);
    }

    public static String toJson(List<Recipe> recipes) {
        JSONArray array = new JSONArray();
        try {
            for (Recipe recipe:recipes) {
                JSONObject jsonObject = writeRecipe(recipe);
                array.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }



    protected static JSONObject writeRecipe(Recipe recipe) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("denumire", recipe.getDenumire());
        jsonObject.put("descriere", recipe.getDescriere());
        jsonObject.put("dataAdaugarii", recipe.getDataAdaugarii());
        jsonObject.put("calorii", recipe.getCalorii());
        jsonObject.put("categorie", recipe.getCategorie());
        return jsonObject;
    }
}