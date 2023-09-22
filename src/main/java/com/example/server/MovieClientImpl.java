package com.example.server;
import com.example.CreateOrUpdateMovieRequest;
import com.example.MovieReply;
import com.example.MovieRequest;
import com.example.MovieServiceGrpc;
import com.example.entity.Movie;
import com.example.interceptors.ClientApiKeyAuthInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.inject.Singleton;


@Singleton
public class MovieClientImpl {
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("localhost", 8081)
                    .usePlaintext()
                    .intercept(new ClientApiKeyAuthInterceptor())
                    .build();

            MovieServiceGrpc.MovieServiceBlockingStub client = MovieServiceGrpc.newBlockingStub(channel);


           public MovieReply getMovie(int id){
               MovieRequest movieRequest = MovieRequest.newBuilder().setId(id).build();
               return client.getMovieDetails(movieRequest);

            }

            public MovieReply addMovie( Movie movie) {
                CreateOrUpdateMovieRequest createOrUpdateMovieRequest = CreateOrUpdateMovieRequest.newBuilder()
                        .setTitle(movie.getTitle())
                        .setRating(movie.getRating())
                        .setImdb(movie.getImdb())
                        .setReview(movie.getReview())
                        .build();
                return client.addMovieDetails(createOrUpdateMovieRequest);
            }
            public MovieReply updateMovie(Movie movie){
                CreateOrUpdateMovieRequest createOrUpdateMovieRequest = CreateOrUpdateMovieRequest.newBuilder()
                        .setTitle(movie.getTitle())
                        .setRating(movie.getRating())
                        .setImdb(movie.getImdb())
                        .setReview(movie.getReview())
                        .setId(movie.getId().intValue())
                        .build();
                return client.editMovieDetails(createOrUpdateMovieRequest);
            }
            public MovieReply deleteMovie(int id){
               MovieRequest movieRequest = MovieRequest.newBuilder().setId(id).build();
               return client.updateMovieDetails(movieRequest);
            }


    }