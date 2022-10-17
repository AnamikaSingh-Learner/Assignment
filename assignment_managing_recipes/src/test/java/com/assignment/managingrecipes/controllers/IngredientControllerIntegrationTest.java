package com.assignment.managingrecipes.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
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
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.repositories.IngredientRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IngredientControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private IngredientRepository ingredientRepository;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/recipes/v1/{recipeId}/ingredients");
	}
	
	@DisplayName("Integration Test Method for getIngredientRecipeId")
	@Test
	@Sql(statements = "INSERT INTO Recipe (recipe_Id,category, recipe_Name, servings) VALUES (225,'Veg', 'Veg', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO Ingredients (ID,IN_NAME, RECIPE_ID) VALUES (55,'Corianders', 225)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testgetIngredientRecipeId() {

		Ingredients[] ingredients = restTemplate.getForObject(baseUrl, Ingredients[].class, 225);
		assertAll(() -> assertNotNull(ingredients), () -> assertEquals(1, ingredients.length));

	}

	@DisplayName("Integration Test Method for deleteRecipeIngredien")
	@Test
	@Sql(statements = "INSERT INTO Recipe (recipe_Id,category, recipe_Name, servings) VALUES (11,'Veg', 'Veg', 2)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "INSERT INTO Ingredients (ID,IN_NAME, RECIPE_ID) VALUES (11,'Veg', 11)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testdeleteRecipeIngredient() {
		int recordCount = ingredientRepository.findAll().size();
		assertEquals(true, recordCount>0);
		restTemplate.delete(baseUrl, 11);
		assertEquals(recordCount -1, ingredientRepository.findAll().size());

	}

}
