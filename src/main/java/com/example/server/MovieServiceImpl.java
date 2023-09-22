package com.example.server;

import com.example.CreateOrUpdateMovieRequest;
import com.example.MovieReply;
import com.example.MovieRequest;
import com.example.MovieServiceGrpc;
import com.example.entity.Movie;
import com.example.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@Singleton
public class MovieServiceImpl extends MovieServiceGrpc.MovieServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class.getName());
//    Inject CRUD repo
    @Inject
    private MovieRepository movieRepository;


    @Override
    public void addMovieDetails(CreateOrUpdateMovieRequest request, StreamObserver<MovieReply> responseObserver) {
        MovieReply movieReply = MovieReply.newBuilder()
                .setTitle(request.getTitle())
                .setReview(request.getReview())
                .setRating(request.getRating())
                .setImdb(request.getImdb())
                .build();
        Movie movie = new Movie();
        movie.setTitle(movieReply.getTitle());
        movie.setReview(movieReply.getReview());
        movie.setRating(movieReply.getRating());
        movie.setImdb(movieReply.getImdb());
        movieRepository.save(movie);
        logger.info("Student Created and Stored in Db");
        responseObserver.onNext(movieReply);
        responseObserver.onCompleted();
    }

    @Override
    public void getMovieDetails(MovieRequest request, StreamObserver<MovieReply> responseObserver) {
        long id = request.getId();
        logger.info(String.valueOf(id));
        Optional<Movie> movie = movieRepository.findById(id);
        Movie movie1 = movie.get();
        MovieReply movieReply = MovieReply.newBuilder()
                .setTitle(movie1.getTitle())
                .setReview(movie1.getReview())
                .setRating(movie1.getRating())
                .setImdb(movie1.getImdb())
                .build();
        responseObserver.onNext(movieReply);
        responseObserver.onCompleted();
    }

    @Override
    public void editMovieDetails(CreateOrUpdateMovieRequest request, StreamObserver<MovieReply> responseObserver) {
        long id = request.getId();
        Optional<Movie> movie = movieRepository.findById(id);
        Movie movie1 = movie.get();
        MovieReply movieReply = MovieReply.newBuilder()
                .setTitle(movie1.getTitle())
                .setReview(movie1.getReview())
                .setRating(movie1.getRating())
                .setImdb(movie1.getImdb())
                .build();
        movie1.setTitle(request.getTitle());
        movie1.setReview(request.getReview());
        movie1.setRating(request.getRating());
        movie1.setImdb(request.getImdb());

        movieRepository.update(movie1);
        responseObserver.onNext(movieReply);
        responseObserver.onCompleted();
    }

    @Override
    public void updateMovieDetails(MovieRequest request, StreamObserver<MovieReply> responseObserver) {
        long id = request.getId();

        Optional<Movie> movie = movieRepository.findById(id);
        Movie movie1 = movie.get();
        movieRepository.delete(movie1);
        MovieReply movieReply = MovieReply.newBuilder()
                .setTitle(movie1.getTitle())
                .setReview(movie1.getReview())
                .setRating(movie1.getRating())
                .setImdb(movie1.getImdb())
                .build();
        responseObserver.onNext(movieReply);
        responseObserver.onCompleted();

    }
}
