package xyz.dicedpixels.hardcover.config;

import com.google.gson.annotations.SerializedName;

public class Config {
    @SerializedName("alternative-recipe-button")
    public boolean alternativeRecipeButton;

    @SerializedName("dark-mode")
    public boolean darkMode;

    @SerializedName("ungroup-recipes")
    public boolean ungroupRecipes;

    @SerializedName("recipe-book")
    public boolean recipeBook = true;

    @SerializedName("unlock-all-recipes")
    public boolean unlockAllRecipes;

    @SerializedName("bounce")
    public boolean bounce = true;

    @SerializedName("circular-scrolling")
    public boolean circularScrolling;

    @SerializedName("centered-inventory")
    public boolean centeredInventory;

    @SerializedName("mouse-wheel-scrolling")
    public boolean mouseWheelScrolling;
}
