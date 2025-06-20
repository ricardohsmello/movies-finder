package br.com.ricas.infrastructure.gateway.dto;

import java.util.List;

public record OpenAIResponse(
        List<DataItem> data
) {
    public record DataItem(
            List<Double> embedding
    ){}
}