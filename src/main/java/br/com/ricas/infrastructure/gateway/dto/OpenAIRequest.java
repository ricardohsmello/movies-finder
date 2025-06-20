package br.com.ricas.infrastructure.gateway.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record OpenAIRequest(
        String input,
        String model){}