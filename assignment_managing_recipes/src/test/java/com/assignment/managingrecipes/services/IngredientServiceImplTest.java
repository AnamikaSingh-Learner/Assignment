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
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;
import com.assignment.managingrecipes.repositories.IngredientRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class IngredientServiceImplTest {

	@Mock
	private IngredientRepository ingredientRepository;

	@InjectMocks
	private IngredientServiceImpl ingredientServiceImpl;

	@Test
	void Should_Return_List_findByRecipeId() throws RecipeIdnotFoundException {

		List<Ingredients> values = new ArrayList<>();

		int recipeId = 1;
		Mockito.when(ingredientRepository.findByRecipeId(recipeId)).thenReturn(values);
		Assert.assertTrue(ingredientServiceImpl.findByRecipeIngredientId(1) instanceof List<?>);
	}

	@Test
	void Should_Return_Int_deleteByRecipeId() {
		ingredientServiceImpl.deleteByRecipeId(1);
		Mockito.verify(ingredientRepository).deleteByRecipeId(1);
	}

}
