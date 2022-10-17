package com.assignment.managingrecipes.dto;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequest {
	
	@NotNull(message = "Ingredient Name shouldn't be null")
	@ApiModelProperty(notes = "Recipe Ingredient Name")
    private String inName;

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

}
