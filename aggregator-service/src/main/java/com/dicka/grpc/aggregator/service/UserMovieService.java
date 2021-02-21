package com.dicka.grpc.aggregator.service;

import com.dicka.grpc.aggregator.dto.RecomendedMovie;
import com.dicka.grpc.aggregator.dto.UserGenre;
import com.dicka.grpc.common.Genre;
import com.dicka.grpc.movie.MovieSearchResponse;
import com.dicka.grpc.movie.MovieSearcherRequest;
import com.dicka.grpc.movie.MovieServiceGrpc;
import com.dicka.grpc.user.UserGenreUpdateRequest;
import com.dicka.grpc.user.UserResponse;
import com.dicka.grpc.user.UserSearchRequest;
import com.dicka.grpc.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecomendedMovie> getUserMovieSugestions(String loginId){
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder()
                .setLoginId(loginId)
                .build();

        UserResponse userResponse = this.userStub.geUserGenre(userSearchRequest);

        MovieSearcherRequest movieSearcherRequest = MovieSearcherRequest.newBuilder()
                .setGenre(userResponse.getGenre())
                .build();

        MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(movieSearcherRequest);
        return movieSearchResponse.getMovieList().stream()
                .map(movieDto -> new RecomendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre){
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build();

        UserResponse userResponse = this.userStub.updateUserGenre(userGenreUpdateRequest);

    }
}
