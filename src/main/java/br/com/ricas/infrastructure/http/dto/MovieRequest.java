package br.com.ricas.infrastructure.http.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record MovieRequest(
    String input){}