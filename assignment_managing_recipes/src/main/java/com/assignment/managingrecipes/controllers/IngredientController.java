package com.assignment.managingrecipes.controllers;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.assignment.managingrecipes.dto.IngredientRequest;
import com.assignment.managingrecipes.entities.Ingredients;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;
import com.assignment.managingrecipes.services.IngredientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/recipes/v1/{recipeId}/ingredients")
@Api(value = "Controller to Manage Recipe Ingredients Details")
public class IngredientController {

	@Autowired
	IngredientService ingredientService;

	private Logger logger = LoggerFactory.getLogger(IngredientController.class);

	/**
	 * 
	 * @apiNote Submit Ingredient Details
	 * 
	 */
	@PostMapping
	@ApiOperation(value = "Add Ingredients using Recipe Id", httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 201, message = "Ingredient Details Added"),
			@ApiResponse(code = 400, message = "Already Present"),
			@ApiResponse(code = 500, message = "Internal Server error") })
	public ResponseEntity<Ingredients> saveIngredient(@PathVariable("recipeId") int id,
			@RequestBody @Valid IngredientRequest ingredientRequest) throws RecipeIdnotFoundException {

		try {
			logger.info("Saving Ingredients");
			return new ResponseEntity<>(ingredientService.saveIngredient(id, ingredientRequest), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("ERROR occured while saving Ingredients");

			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 
	 * @apinote Get All Ingredients Details by Recipe Id
	 * @param Recipe Id
	 * @return List Of Ingredients
	 * 
	 */
	@GetMapping
	@ApiOperation(value = "Fetching All Ingredients based on Recipe Id", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "Recipe Details Fetched Successfully"),
			@ApiResponse(code = 500, message = "Internal Server error") })
	public List<Ingredients> getIngredientRecipeId(@PathVariable("recipeId") int id) throws RecipeIdnotFoundException {
		try {
			logger.info("Fetching All Ingredients based on Recipe Id");
			return ingredientService.findByRecipeIngredientId(id);
		} catch (Exception e) {
			logger.error("ERROR occured while Fetching Ingredients based on Recipe Id");
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * 
	 * @apinote Delete Ingredients
	 * @param Recipe Id
	 * 
	 */
	@DeleteMapping
	@ApiOperation(value = "Delete", httpMethod = "DELETE")
	@ApiResponses({ @ApiResponse(code = 200, message = "Recipe Deleted Successfully"),
			@ApiResponse(code = 404, message = "Value not found"),
			@ApiResponse(code = 500, message = "Internal Server error") })
	public int deleteRecipeIngredient(@PathVariable("recipeId") int id) {
		try {
			logger.info("Ingredients Deleted Successfully based on Recipe Id");
			ingredientService.deleteByRecipeId(id);
			return id;
		} catch (Exception e) {
			logger.error("ERROR occured while Deleting Ingredients based on Recipe Id");
			e.printStackTrace();
			throw e;
		}
	}

}
