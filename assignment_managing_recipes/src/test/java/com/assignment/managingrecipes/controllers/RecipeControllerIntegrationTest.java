package com.assignment.managingrecipes.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import com.assignment.managingrecipes.entities.Recipe;
import com.assignment.managingrecipes.repositories.RecipeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RecipeControllerIntegrationTest {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private RecipeRepository recipeRepository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/recipes/v1");
	}

	@DisplayName("Integration Test Method for getAllRecipes")
	@Test
	@Sql(statements = "INSERT INTO Recipe (recipe_Id,category, recipe_Name, servings) VALUES (44,'Veg', 'Veg', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testgetAllRecipes() {
		List<Recipe> recipes = restTemplate.getForObject(baseUrl, List.class);
		assertEquals(true, recipes.size() > 0);
	}

	@DisplayName("Integration Test Method for getRecipeById")
	@Test
	@Sql(statements = "INSERT INTO Recipe (recipe_Id,category, recipe_Name, servings) VALUES (88,'Veg', 'Veg', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM Recipe WHERE recipe_Id=88", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testgetRecipeById() {
		Recipe recipe = restTemplate.getForObject(baseUrl + "/{id}", Recipe.class, 88);
		assertAll(() -> assertNotNull(recipe), () -> assertEquals(88, recipe.getRecipeId()));
	}

	@DisplayName("Integration Test Method for deleteRecipe")
	@Test
	@Sql(statements = "INSERT INTO Recipe (recipe_Id,category, recipe_Name, servings) VALUES (99,'Veg', 'Veg', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testdeleteRecipe() {
		int recordCount = recipeRepository.findAll().size();
		restTemplate.delete(baseUrl + "/{id}", 99);
		assertEquals(recordCount - 1, recipeRepository.findAll().size());
	}
}
