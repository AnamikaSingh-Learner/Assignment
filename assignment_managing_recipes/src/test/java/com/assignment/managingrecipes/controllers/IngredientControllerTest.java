package com.assignment.managingrecipes.controllers;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.services.IngredientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class IngredientControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IngredientServiceImpl ingredientServiceImpl;

	@InjectMocks
	private IngredientController ingredientController;

	private ObjectMapper objectMapper;
	private ObjectWriter objectWriter;
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@BeforeEach
	public void beforeTest() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).setControllerAdvice().build();
		this.objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		this.objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
	}

	@DisplayName("Test Method for getIngredientRecipeId")
	@Test
	void Should_Return_200_When_getIngredientRecipeId() throws Exception {
		List<Ingredients> searchResult = new ArrayList<Ingredients>();
		int recipeid = 1;

		Mockito.when(ingredientServiceImpl.findByRecipeIngredientId(recipeid)).thenReturn(searchResult);
		mockMvc.perform(MockMvcRequestBuilders.get("/recipes/v1/" + recipeid + "/ingredients")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@DisplayName("Test Method for deleteRecipeIngredient")
	@Test
	void Should_Return_200_When_deleteRecipeIngredient() throws Exception {
		int recipeid = 1;

		Mockito.when(ingredientServiceImpl.deleteByRecipeId(recipeid)).thenReturn(1);

		mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/v1/" + recipeid + "/ingredients")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
