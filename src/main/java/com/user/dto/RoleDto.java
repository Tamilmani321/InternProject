package com.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RoleDto extends RepresentationModel<RoleDto> {
	
	@JsonInclude(Include.NON_EMPTY)
	private int id;
	@Size(min = 3, message = "Rolename must contain atleast 3 character")
	@NotBlank(message = "RoleName should not be empty")
	private String name;
	@JsonProperty("links")
	@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NonEmptyLinksFilter.class)
	@NonNull
	@Override
	public Links getLinks() {
		return super.getLinks();
	}

	static class NonEmptyLinksFilter {

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Links)) {
				return false;
			}
			Links links = (Links) obj;
			return links.isEmpty();
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
}
