package utils;


import com.google.gson.Gson;
import model.Error;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

/**
 * This class is responsible for handling all the exceptions in the application
 */
public class CustomExceptionHandler implements spark.ExceptionHandler {

    @Override
    public void handle(Exception e, Request request, Response response) {

        response.type("application/json");
        if (e instanceof  IllegalArgumentException){
            response.status(HttpStatus.BAD_REQUEST_400);
        } else{
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        response.body(new Gson().toJson(new Error(e.getMessage())));
    }
}
