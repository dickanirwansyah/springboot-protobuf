package com.dicka.grpc.movie.service;

import com.dicka.grpc.movie.MovieDto;
import com.dicka.grpc.movie.MovieSearchResponse;
import com.dicka.grpc.movie.MovieSearcherRequest;
import com.dicka.grpc.movie.MovieServiceGrpc;
import com.dicka.grpc.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearcherRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movies =  this.movieRepository.findByGenre(request.getGenre().toString())
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setYear(movie.getYear())
                        .setRating(movie.getRating())
                        .build())
                .collect(Collectors.toList());
        System.out.println("REQUEST : "+request.getGenre());
        System.out.println("DATA : " + movies);
        responseObserver.onNext(MovieSearchResponse.newBuilder().addAllMovie(movies).build());
        responseObserver.onCompleted();
    }
}
