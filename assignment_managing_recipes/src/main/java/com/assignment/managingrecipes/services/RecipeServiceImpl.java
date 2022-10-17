package com.assignment.managingrecipes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.assignment.managingrecipes.dto.RecipeRequest;
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.entities.Recipe;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;
import com.assignment.managingrecipes.exceptions.NoRecordFoundException;
import com.assignment.managingrecipes.repositories.IngredientRepository;
import com.assignment.managingrecipes.repositories.RecipeRepository;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	IngredientRepository ingredientRepository;

	private final RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	/**
	 * 
	 * @apiNote Delete Recipe
	 * @param Recipe Id
	 * @return Recipe Id
	 * 
	 */
	@Override
	public int deleteRecipe(int id) throws RecipeIdnotFoundException {

		Optional<Recipe> check = recipeRepository.findById(id);
		
		if (check != null) {
			recipeRepository.deleteById(id);
		} else
			throw new NoRecordFoundException("No Records Found with: " + id);
		return id;
	}

	/**
	 * 
	 * @apiNote Get Recipe by Recipe ID
	 * @param Recipe Id
	 * @return Recipe
	 * @throws NoRecordFoundException
	 * 
	 */
	@Override
	public Recipe getRecipeById(int id) throws NoRecordFoundException {
		Recipe recipe = recipeRepository.findByRecipeId(id);
		if (recipe != null) {
			return recipe;
		} else {
			throw new NoRecordFoundException("No Records Found with Recipe ID: " + id);
		}
	}

	/**
	 * 
	 * @apiNote Get All Recipes
	 * @return List Recipe
	 * @throws NoRecordFoundException
	 * 
	 */
	@Override
	public List<Recipe> getRecipes() throws NoRecordFoundException {

		List<Recipe> listRecipe = recipeRepository.findAll();
		boolean listRecipe_isEmpty = listRecipe.isEmpty();
		if (!listRecipe_isEmpty) {
			return listRecipe;
		} else {
			throw new NoRecordFoundException("No Records Found");
		}
	}

	/**
	 * 
	 * @apiNote Add new Recipe
	 * @return Recipe
	 * 
	 */
	@Override
	public Recipe saveOrUpdate(RecipeRequest recipeRequest) {

		Recipe recipe = new Recipe(recipeRequest.getCategory(), recipeRequest.getRecipeName(),
				recipeRequest.getServings(), recipeRequest.getIngredients());
		ArrayList<Ingredients> ingredientList = new ArrayList<>();
		recipeRequest.getIngredients().stream().forEach(e -> {
			ingredientList.add(e);
			e.setRecipe(recipe);
		});

		recipe.setIngredientsList(ingredientList);
		recipeRepository.save(recipe);
		return recipe;
	}

	/**
	 * 
	 * @apiNote Search Recipe
	 * @return List of Recipes matching search criteria.
	 * 
	 */
	@Override
	public List<Recipe> searchRecipes(String category, int servings, String ingredients, boolean included) {
		if (included)
			return recipeRepository.searchINRecipe(category, servings, ingredients);
		else
			return recipeRepository.searchEXRecipe(category, servings, ingredients);
	}
}
