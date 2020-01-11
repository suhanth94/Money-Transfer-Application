package utils;

import spark.Filter;

/**
 * Response Interceptor to append content type to the HTTP response
 */
public class ResponseInterceptor {
    public Filter getFilter() {
        return (request, response) -> response.type("application/json");
    }
}