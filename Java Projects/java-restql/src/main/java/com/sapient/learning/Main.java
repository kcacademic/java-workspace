package com.sapient.learning;

import restql.core.RestQL;
import restql.core.config.ClassConfigRepository;
import restql.core.response.QueryResponse;

public class Main {

	public static void main(String[] args) throws Exception {

		ClassConfigRepository config = new ClassConfigRepository();
		config.put("cards", "http://api.magicthegathering.io/v1/cards");
		config.put("card", "http://api.magicthegathering.io/v1/cards/:id");
		RestQL restQL = new RestQL(config);

		// Simple Query
		String query = "from cards as cardslist params type = ?";
		QueryResponse response = restQL.executeQuery(query, "Artifact");
		// The JSON String
		String jsonString = response.toString();
		System.out.println("Simple Query Response: " + jsonString);

		// Chained Query
		String queryCardsAndDetails = "from cards as cardsList params type = ? \n"
				+ "from card as cardWithDetails params id = cardsList.id";
		QueryResponse chainedResponse = restQL.executeQuery(queryCardsAndDetails, "Artifact");
		//The JSON String
		String chainedJsonString = chainedResponse.toString();
		System.out.println("Chained Query Response: " + chainedJsonString);
	}
}