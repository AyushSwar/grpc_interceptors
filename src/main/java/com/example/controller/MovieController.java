package com.example.controller;


import com.example.MovieReply;
import com.example.entity.Movie;
import com.example.server.MovieClientImpl;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;


@Controller( "/movie")
@RequiredArgsConstructor
public class MovieController {
    @Inject
    private MovieClientImpl movieclientImpl;

    @Get("{id}")
    public Movie getMovie(@PathVariable int id) {
        Movie movie = new Movie();
        MovieReply movieReply = movieclientImpl.getMovie(id);
        movie.setTitle(movieReply.getTitle());
        movie.setRating(movieReply.getRating());
        movie.setImdb(movieReply.getImdb());
        movie.setReview(movieReply.getReview());
        return movie;

    }

    @Post("/create")
    public Movie createMovie(@Body Movie movie) {
        MovieReply movieReply = movieclientImpl.addMovie(movie);
        Movie movie1 = new Movie();
        movie1.setTitle(movieReply.getTitle());
        movie1.setRating(movieReply.getRating());
        movie1.setImdb(movieReply.getImdb());
        movie1.setReview(movieReply.getReview());
        return movie1;
    }

    @Post("/update")
    public Movie updateMovie(@Body Movie movie) {
        MovieReply movieReply = movieclientImpl.updateMovie(movie);
        Movie movie1 = new Movie();

        movie1.setTitle(movieReply.getTitle());
        movie1.setRating(movieReply.getRating());
        movie1.setImdb(movieReply.getImdb());
        movie1.setReview(movieReply.getReview());
        return movie1;
    }

    @Delete("delete/{id}")
    public Movie deleteMovie(@PathVariable int id){
        MovieReply movieReply = movieclientImpl.deleteMovie(id);
        Movie movie1 = new Movie();

        movie1.setTitle(movieReply.getTitle());
        movie1.setRating(movieReply.getRating());
        movie1.setImdb(movieReply.getImdb());
        movie1.setReview(movieReply.getReview());
        return movie1;
    }


}
