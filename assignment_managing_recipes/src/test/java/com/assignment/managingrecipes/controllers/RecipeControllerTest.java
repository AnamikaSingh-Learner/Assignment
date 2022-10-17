package com.assignment.managingrecipes.controllers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.assignment.managingrecipes.dto.RecipeRequest;
import com.assignment.managingrecipes.entities.Recipe;
import com.assignment.managingrecipes.exceptions.ApplicationExceptionHandler;
import com.assignment.managingrecipes.exceptions.InternalServerException;
import com.assignment.managingrecipes.exceptions.InvalidArgumentException;
import com.assignment.managingrecipes.exceptions.NoRecordFoundException;
import com.assignment.managingrecipes.services.RecipeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RecipeControllerTest {
	private MockMvc mockMvc;

	@Mock
	private RecipeServiceImpl recipeServiceImpl;

	@InjectMocks
	private RecipeController recipeController;

	/*
	 * These will be required for sending data to controller in JSON
	 */
	private ObjectMapper objectMapper;
	private ObjectWriter objectWriter;
	String baseURL = "/recipes/v1";
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@BeforeEach
	public void beforeTest() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
				.setControllerAdvice(new ApplicationExceptionHandler()).build();
		this.objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		this.objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
	}

	@DisplayName("Test Method for getAllRecipes")
	@Test
	void Should_Return_200_When_getAllRecipes() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setRecipeId(1);
		recipe.setCategory("Veg");
		recipe.setRecipeName("Veg");
		recipe.setServings(02);

		List<Recipe> searchResult = new ArrayList<Recipe>();

		Mockito.when(recipeServiceImpl.getRecipes()).thenReturn(searchResult);

		mockMvc.perform(MockMvcRequestBuilders.get(baseURL).contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}

	@DisplayName("Test Method for getAllRecipes method throwing NoRecordFoundException")
	@Test
	void Should_Return_NoRecordFoundException_When_getAllRecipes() throws Exception {

		when(recipeServiceImpl.getRecipes()).thenThrow(new NoRecordFoundException("ERROR"));

		mockMvc.perform(MockMvcRequestBuilders.get(baseURL)).andDo(MockMvcResultHandlers.print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NoRecordFoundException));

	}

	@DisplayName("Test Method for getAllRecipes method throwing InternalServerError")
	@Test
	void Should_Return_InternalServerError_When_getAllRecipes() throws Exception {

		when(recipeServiceImpl.getRecipes()).thenThrow(new InternalServerException("INTERNAL SERVER ERROR"));

		mockMvc.perform(MockMvcRequestBuilders.get(baseURL)).andDo(MockMvcResultHandlers.print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InternalServerException));

	}

	@DisplayName("Test Method for getRecipeById. ")
	@Test
	void Should_Return_200_When_getRecipeById() throws Exception {
		Recipe recipe = new Recipe();
		int recipeid = 1;

		Mockito.when(recipeServiceImpl.getRecipeById(recipeid)).thenReturn(recipe);
		mockMvc.perform(MockMvcRequestBuilders.get(baseURL + "/" + recipeid).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@DisplayName("Test Method for saveRecipe. ")
	@Test
	void Should_Return_201_When_saveRecipe() throws Exception {

		Recipe recipe = new Recipe();

		RecipeRequest recipeRequest = new RecipeRequest();
		recipeRequest.setCategory("Veg");
		recipeRequest.setRecipeName("Veg");
		recipeRequest.setIngredients(null);
		recipeRequest.setServings(06);

		Mockito.when(recipeServiceImpl.saveOrUpdate(recipeRequest)).thenReturn(recipe);
		String requestJson = objectWriter.writeValueAsString(recipeRequest);

		mockMvc.perform(MockMvcRequestBuilders.post(baseURL).contentType(APPLICATION_JSON_UTF8)
				.content(requestJson)).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@DisplayName("Test Method for saveRecipe. ")
	@Test
	void Should_Return_400_When_saveRecipe() throws Exception {

		RecipeRequest recipeRequest = new RecipeRequest();
		Mockito.when(recipeServiceImpl.saveOrUpdate(recipeRequest))
				.thenThrow(new InvalidArgumentException("Invalid Argument Exception"));

		String requestJson = objectWriter.writeValueAsString(recipeRequest);
		mockMvc.perform(MockMvcRequestBuilders.post(baseURL).contentType(APPLICATION_JSON_UTF8)
				.content(requestJson)).andDo(MockMvcResultHandlers.print()).andExpect(status().is(400));
	}

	@DisplayName("Test Method for deleteRecipe. ")
	@Test
	void Should_Return_200_When_deleteRecipe() throws Exception {
		int recipeid = 1;

		Mockito.when(recipeServiceImpl.deleteRecipe(recipeid)).thenReturn(recipeid);
		mockMvc.perform(MockMvcRequestBuilders.delete(baseURL + "/" + recipeid).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
