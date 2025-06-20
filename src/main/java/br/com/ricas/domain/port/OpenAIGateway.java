package br.com.ricas.domain.port;

import java.util.List;

public interface OpenAIGateway {
  List<Double> getEmbedding(String input);
}
