package com.user.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.user.dto.RoleDto.NonEmptyLinksFilter;
import com.user.entity.Attachments;
import com.user.entity.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
	
	@JsonInclude(Include.NON_EMPTY)
	private long id;
	@NotBlank(message = "Name should not be empty")
	@Size(min=5, message = "Name must contain atleast 5 characters")
	private String name;
	@NotBlank(message = "Email should not be empty")
	@Email(message = "Enter valid email")
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotEmpty(message="RoleId should not be null")
	private List <Long> roleId;
	@JsonProperty(access=JsonProperty.Access.READ_ONLY)
	private List <RoleDto> roles;
	private Attachments attachments;
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
