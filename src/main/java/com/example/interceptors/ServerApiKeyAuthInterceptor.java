package com.example.interceptors;

import io.grpc.*;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Singleton
@Slf4j
public class ServerApiKeyAuthInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("Server interceptor: Intercepted call for method '{}'", serverCall.getMethodDescriptor().getFullMethodName());

        // Extract the "x-api-key" header from the request metadata
        Metadata.Key<String> apiKeyMetadata = Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER);
        String apiKey = metadata.get(apiKeyMetadata);
        log.info("x-api-key from client: '{}'", apiKey);

        if (Objects.nonNull(apiKey) && apiKey.equals("aayush")) {
            // If the API key is valid, allow the call to proceed
            return serverCallHandler.startCall(serverCall, metadata);
        } else {
            // If the API key is invalid, close the call with an "UNAUTHENTICATED" status
            Status status = Status.UNAUTHENTICATED.withDescription("Invalid api-key");
            serverCall.close(status, metadata);
            // Return an empty listener to terminate the call
            return new ServerCall.Listener<>() {};
        }
    }
}
