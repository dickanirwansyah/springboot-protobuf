package com.dicka.grpc.user.service;

import com.dicka.grpc.common.Genre;
import com.dicka.grpc.user.UserGenreUpdateRequest;
import com.dicka.grpc.user.UserResponse;
import com.dicka.grpc.user.UserSearchRequest;
import com.dicka.grpc.user.UserServiceGrpc;
import com.dicka.grpc.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void geUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setName(user.getName())
                            .setLoginId(user.getLoginId())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()))
                            .build();
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    builder.setName(user.getName())
                            .setLoginId(user.getLoginId())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()))
                            .build();
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
