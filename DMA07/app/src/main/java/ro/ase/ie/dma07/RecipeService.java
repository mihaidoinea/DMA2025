package ro.ase.ie.dma07;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("b/OCIE")
    Call<List<Recipe>> getRecipes();
}
