package com.example;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class HelloDataFetcher implements DataFetcher<Hello> {
    @Override
    public Hello get(DataFetchingEnvironment environment) throws Exception {
        return new Hello("Hello!");
    }
}
