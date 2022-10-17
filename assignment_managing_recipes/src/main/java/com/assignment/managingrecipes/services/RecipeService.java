package com.assignment.managingrecipes.services;

import java.util.List;

import com.assignment.managingrecipes.dto.RecipeRequest;
import com.assignment.managingrecipes.entities.Recipe;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;
import com.assignment.managingrecipes.exceptions.NoRecordFoundException;

public interface RecipeService {

	List<Recipe> getRecipes() throws NoRecordFoundException;

	Recipe saveOrUpdate(RecipeRequest recipe);

	Recipe getRecipeById(int recipeId) throws NoRecordFoundException;

	int deleteRecipe(int recipeId) throws RecipeIdnotFoundException;

	public List<Recipe> searchRecipes(String category, int servings, String ingredients, boolean included);

}
