package com.assignment.managingrecipes.services;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.assignment.managingrecipes.dto.RecipeRequest;
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.entities.Recipe;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;
import com.assignment.managingrecipes.repositories.RecipeRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RecipeServiceImplTest {

	@Mock
	private RecipeRepository recipeRepository;

	@InjectMocks
	private RecipeServiceImpl recipeServiceImpl;

	@Test
	void Should_Return_List_getRecipes() throws RecipeIdnotFoundException {
		List<Recipe> values = new ArrayList<>();
		Recipe recipe = new Recipe();
		recipe.setRecipeId(10);
		recipe.setCategory("Veg");
		recipe.setIngredientsList(null);
		recipe.setRecipeName("Veg");
		recipe.setServings(2);
		values.add(recipe);
		Mockito.when(recipeRepository.findAll()).thenReturn(values);
		Assert.assertTrue(recipeServiceImpl.getRecipes() instanceof List<?>);
	}

	@Test
	void Should_Return_List_getRecipeById() throws RecipeIdnotFoundException {
		List<Recipe> values = new ArrayList<>();
		Recipe recipe = new Recipe();
		recipe.setRecipeId(10);
		recipe.setCategory("Veg");
		recipe.setIngredientsList(null);
		recipe.setRecipeName("Veg");
		recipe.setServings(2);
		values.add(recipe);
		Mockito.when(recipeRepository.findByRecipeId(1)).thenReturn(recipe);
		Assert.assertTrue(recipeServiceImpl.getRecipeById(1) instanceof Recipe);
	}

	@Test
	void Should_Return_deleteRecipeById() throws RecipeIdnotFoundException {

		Mockito.when(recipeRepository.findById(1)).thenReturn(java.util.Optional.empty());
		Assert.assertTrue(recipeServiceImpl.deleteRecipe(1) > 0);
	}

	@Test
	 void Should_Return_Recipe_saveOrUpdate() throws RecipeIdnotFoundException {
		List<Ingredients> values = new ArrayList<>();
		Ingredients ing = new Ingredients();
		ing.setInName("Veg");

		values.add(ing);

		Recipe recipe = new Recipe();
		recipe.setRecipeId(10);
		recipe.setCategory("Veg");
		recipe.setIngredientsList(values);
		recipe.setRecipeName("Veg");
		recipe.setServings(2);

		RecipeRequest recipeRequest = new RecipeRequest();
		recipeRequest.setCategory("Veg");
		recipeRequest.setIngredients(values);
		recipeRequest.setRecipeName("Veg");
		recipeRequest.setServings(2);

		Mockito.when(recipeRepository.save(recipe)).thenReturn(recipe);
		Assert.assertTrue(recipeServiceImpl.saveOrUpdate(recipeRequest) instanceof Recipe);
	}

	@Test
	void Should_Return_List_searchRecipesInclude() throws RecipeIdnotFoundException {

		List<Recipe> values = new ArrayList<>();
		Mockito.when(recipeRepository.searchINRecipe("Veg", 2, null)).thenReturn(values);
		Assert.assertTrue(recipeServiceImpl.searchRecipes(null, 2, null, false) instanceof List<?>);
	}

	@Test
	void Should_Return_List_searchRecipesTrue() throws RecipeIdnotFoundException {

		List<Recipe> values = new ArrayList<>();
		Mockito.when(recipeRepository.searchINRecipe("Veg", 2, null)).thenReturn(values);
		Assert.assertTrue(recipeServiceImpl.searchRecipes(null, 2, null, true) instanceof List<?>);
	}

}
