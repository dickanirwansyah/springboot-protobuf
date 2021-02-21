package com.dicka.grpc.movie;

import com.dicka.grpc.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SpringApplicationMovieService {

    public static void main(String[] args){
        SpringApplication.run(SpringApplicationMovieService.class, args);
    }
}

@Component
class TestData implements CommandLineRunner{

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MOVIES : "+movieRepository.findAll());
    }
}
