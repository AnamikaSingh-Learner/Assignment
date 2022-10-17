package com.assignment.managingrecipes.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.assignment.managingrecipes.dto.IngredientRequest;
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.entities.Recipe;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;
import com.assignment.managingrecipes.repositories.IngredientRepository;
import com.assignment.managingrecipes.repositories.RecipeRepository;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	RecipeRepository recipeRepository;

	/**
	 * 
	 * @apiNote Find Recipe Ingredients
	 * @param Recipe Id
	 * @return List of Ingredients
	 * 
	 */
	@Override
	public List<Ingredients> findByRecipeIngredientId(int recipeId) throws RecipeIdnotFoundException {
		List<Ingredients> ingredientList = ingredientRepository.findByRecipeId(recipeId);

		if (!ingredientList.isEmpty() || ingredientList != null)
			return ingredientList;
		else
			throw new RecipeIdnotFoundException("Ingredient not found for given recipe Id: " + recipeId);
	}

	/**
	 * 
	 * @apiNote Save Recipe Ingredients
	 * @param Recipe      Id
	 * @param Ingredients
	 * @return Ingredients
	 * 
	 */
	@Override
	public Ingredients saveIngredient(int id, IngredientRequest ingredientR) throws RecipeIdnotFoundException {

		List<Ingredients> ingredientList = new ArrayList<>();
		Ingredients ingredients = new Ingredients(0, id, ingredientR.getInName());
		Recipe recipe = recipeRepository.findByRecipeId(id);
		ingredientList = recipe.getIngredientsList();
		ingredientList.add(ingredients);

		recipe.setIngredientsList(ingredientList);
		ingredients.setRecipe(recipe);
		return ingredientRepository.save(ingredients);
	}

	/**
	 * 
	 * @apiNote Delete Ingredients by Recipe Id
	 * @param Recipe Id
	 * @return 0
	 * 
	 */
	@Override
	public int deleteByRecipeId(int recipeId) {
		ingredientRepository.deleteByRecipeId(recipeId);
		return 0;
	}
}
