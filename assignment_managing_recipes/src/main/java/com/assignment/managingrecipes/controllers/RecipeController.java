package com.assignment.managingrecipes.controllers;

import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.assignment.managingrecipes.dto.RecipeRequest;
import com.assignment.managingrecipes.entities.Recipe;
import com.assignment.managingrecipes.exceptions.InternalServerException;
import com.assignment.managingrecipes.exceptions.InvalidArgumentException;
import com.assignment.managingrecipes.exceptions.NoRecordFoundException;
import com.assignment.managingrecipes.exceptions.RecipeIdnotFoundException;
import com.assignment.managingrecipes.services.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/recipes/v1")
@Api(value = "Controller to Manage Recipe Details")
public class RecipeController {

	@Autowired
	RecipeService recipeService;

	private Logger logger = LoggerFactory.getLogger(RecipeController.class);

	/**
	 * 
	 * @apinote Fetch All Rcipes
	 * @return List Of Recipes
	 * @throws NoRecordFoundException
	 * 
	 */
	@GetMapping
	@ApiOperation(value = "Fetch All Recipes", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "Recipe Details Fetched Successfully"),
			@ApiResponse(code = 404, message = "Already Present"),
			@ApiResponse(code = 500, message = "Internal Server error") })
	public ResponseEntity<List<Recipe>> getAllRecipes() {
		logger.info("Fetching all Recipes");
		try {
			return ResponseEntity.ok(recipeService.getRecipes());
		} catch (NoRecordFoundException e) {
			throw new NoRecordFoundException("NO RECORDS FOUND");
		} catch (Exception e) {
			throw new InternalServerException("Internal Server ERROR");
		}
	}

	/**
	 * 
	 * @apiNote Add New Recipe Details
	 * @param category
	 * @param ingredients
	 * @param Recipe      Name
	 * @param Servings
	 * 
	 */
	@PostMapping(consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Add New Recipe", httpMethod = "POST")
	@ApiResponses({ @ApiResponse(code = 201, message = "Recipe Details Submitted"),
			@ApiResponse(code = 400, message = "Invalid Arguments"),
			@ApiResponse(code = 500, message = "Internal Server error") })

	public ResponseEntity<Recipe> saveRecipe(@RequestBody @Valid RecipeRequest saveRecipe) {
		try {
			logger.info("Saving Recipe");
			return new ResponseEntity<>(recipeService.saveOrUpdate(saveRecipe), HttpStatus.CREATED);
		} catch (InvalidArgumentException e) {
			throw new InvalidArgumentException("Invalid Argument");
		} catch (InternalServerException e) {
			throw new InternalServerException("Internal Server Error");
		}
	}

	/**
	 * 
	 * @apinote Fetch All Recipe Details by Recipe Id
	 * @param Recipe Id
	 * @return List Of Recipes
	 * @throws NoRecordFoundException
	 * 
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "Fetch Recipe based on Recipe Id", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "Recipe Details Fetched Successfully"),
			@ApiResponse(code = 500, message = "Internal Server error") })

	public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") int id) throws NoRecordFoundException {
		try {
			logger.info("Fetching All Recipes based on Recipe Id");
			return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
		} catch (InternalServerException e) {
			logger.error("ERROR occured while Fetching All Recipes based on Recipe Id");
			throw new InternalServerException("Internal Server Error");
		}
	}

	/**
	 * 
	 * @apinote Delete Recipe
	 * @param Recipe Id
	 * 
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete", httpMethod = "DELETE")
	@ApiResponses({ @ApiResponse(code = 200, message = "Recipe Deleted Successfully"),
			@ApiResponse(code = 404, message = "Value not found"), })
	public void deleteRecipe(@PathVariable("id") int id) throws RecipeIdnotFoundException {
		try {
			logger.info("Recipe Deleted Successfully based on Recipe Id");

			recipeService.deleteRecipe(id);
		} catch (RecipeIdnotFoundException e) {
			logger.error("ERROR occured while Deleting Recipes based on Recipe Id");
			throw new RecipeIdnotFoundException("ID NOT FOUND");
		}
	}

	/**
	 * 
	 * @apinote Search API for Recipes *
	 */
	@GetMapping("/search")
	@ApiOperation(value = "Search", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "Value Fetched Successfully"),
			@ApiResponse(code = 404, message = "Value not found"),
			@ApiResponse(code = 500, message = "Internal Server error") })
	public List<Recipe> searchRecipes(@RequestParam(required = false) String category,
			@RequestParam(required = false) Optional<Integer> servings,
			@RequestParam(required = false) String ingredients,
			@RequestParam(required = false, defaultValue = "false") boolean included) {
		int s = servings.isPresent() ? servings.get() : -1;
		try {
			logger.info("Search Operation Successfull");
			return recipeService.searchRecipes(category, s, ingredients, included);
		} catch (Exception e) {
			logger.error("Search Operation Failed");
			e.printStackTrace();
			throw e;
		}
	}

}
