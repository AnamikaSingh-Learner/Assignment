package com.assignment.managingrecipes.dto;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.assignment.managingrecipes.entities.Ingredients;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RecipeRequest {
	
	@NotNull(message = "category shouldn't be null")
	@ApiModelProperty(notes = "Recipe Category Veg OR Non Veg")
	private String category;
	
    @NotNull(message = "Recipe Name cannot be null")
	@ApiModelProperty(notes = "Recipe Name")
	private String recipeName;
    
    @Min(02)
    @Max(06)
    @NotNull(message = "Servings  cannot be null")
    @ApiModelProperty(notes = "No. of Servings")
    private int servings;
    
    @ApiModelProperty(notes = "Recipe Ingredients")
    private List<Ingredients> ingredients;


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getServings() {
		return servings;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public List<Ingredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}
	
}
