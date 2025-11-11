package ro.ase.ie.dma07;

import androidx.annotation.Nullable;

public class Recipe
{
    private String denumire;
    private String descriere;
    private String dataAdaugarii;
    private int calorii;
    private String categorie;

    @Override
    public boolean equals(@Nullable Object obj) {
        Recipe recipe = (Recipe) obj;
        return (this.denumire.equals(recipe.denumire) && this.calorii == recipe.calorii);
    }

    public Recipe(String denumire, String descriere, String dataAdaugarii, int calorii, String categorie) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.dataAdaugarii = dataAdaugarii;
        this.calorii = calorii;
        this.categorie = categorie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getDataAdaugarii() {
        return dataAdaugarii;
    }

    public void setDataAdaugarii(String dataAdaugarii) {
        this.dataAdaugarii = dataAdaugarii;
    }

    public int getCalorii() {
        return calorii;
    }

    public void setCalorii(int calorii) {
        this.calorii = calorii;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "denumire='" + denumire + '\'' +
                ", descriere='" + descriere + '\'' +
                ", dataAdaugarii='" + dataAdaugarii + '\'' +
                ", calorii=" + calorii +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}