/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginRequest(@NotBlank String email, @NotBlank String password)
        implements Serializable {}
