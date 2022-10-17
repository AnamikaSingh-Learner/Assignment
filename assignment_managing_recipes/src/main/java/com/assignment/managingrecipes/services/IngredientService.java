package com.assignment.managingrecipes.services;

import java.util.List;

import com.assignment.managingrecipes.dto.IngredientRequest;
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;

public interface IngredientService {

	List<Ingredients> findByRecipeIngredientId(int recipeId) throws RecipeIdnotFoundException;

	Ingredients saveIngredient(int id, IngredientRequest ingredientRequest) throws RecipeIdnotFoundException;

	int deleteByRecipeId(int recipeId);

}
