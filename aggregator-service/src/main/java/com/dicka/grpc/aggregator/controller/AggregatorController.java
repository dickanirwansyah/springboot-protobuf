package com.dicka.grpc.aggregator.controller;

import com.dicka.grpc.aggregator.dto.RecomendedMovie;
import com.dicka.grpc.aggregator.dto.UserGenre;
import com.dicka.grpc.aggregator.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class AggregatorController {

    @Autowired
    private UserMovieService userMovieService;

    @GetMapping(value = "/recomende-movies/{loginId}")
    public ResponseEntity<List<RecomendedMovie>> getRecomendedMovies(@PathVariable("loginId")String loginId){
        List<RecomendedMovie> recomendedMovies = userMovieService.getUserMovieSugestions(loginId);
        return new ResponseEntity<>(recomendedMovies, HttpStatus.OK);
    }

    @PutMapping(value = "/set-user-genre")
    public void setUserGenre(@RequestBody UserGenre userGenre){
        this.userMovieService.setUserGenre(userGenre);
    }
}
