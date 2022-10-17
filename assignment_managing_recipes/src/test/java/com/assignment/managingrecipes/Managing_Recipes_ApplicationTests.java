package com.assignment.managingrecipes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.managingrecipes.controllers.RecipeController;
import com.assignment.managingrecipes.services.RecipeService;

@SpringBootTest
class Managing_Recipes_ApplicationTests {

	@Autowired
	private RecipeController recipeController;

	@Autowired
	private RecipeService recipeService;

	@Test
	void contextLoads() throws Exception {
		assertThat(recipeController).isNotNull();
		assertThat(recipeService).isNotNull();
	}

}
