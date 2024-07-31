/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlogRequest {

    @Schema(example = "My First Blog")
    @NotBlank(message = "Blog name is required")
    @Size(max = 100, message = "Blog name must be less than 100 characters")
    private String blogName;

    @Schema(example = "This is the content of my first blog.")
    @NotBlank(message = "Blog content is required")
    private String blogContent;

    @Schema(example = "http://example.com/photo.jpg")
    @Pattern(regexp = "link", message = "Link must be a valid URL")
    private String blogPhoto;

    @Schema(example = "http://example.com")
    @Pattern(regexp = "link", message = "Link must be a valid URL")
    private String link;

}
