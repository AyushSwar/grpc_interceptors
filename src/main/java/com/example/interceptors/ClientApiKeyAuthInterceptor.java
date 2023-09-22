package com.example.interceptors;

import io.grpc.*;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j /* Used for logging */
public class ClientApiKeyAuthInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        // Define a logger for logging information about intercepted calls.
        final Logger log = LoggerFactory.getLogger(ClientApiKeyAuthInterceptor.class);

        // Create a gRPC client call with added "x-api-key" header for API key-based authentication.
        return new ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(methodDescriptor, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // Add an "x-api-key" header with the value "aayushjungswar" to the request metadata.
                headers.put(Metadata.Key.of("x-api-key", Metadata.ASCII_STRING_MARSHALLER), "aayush");
                super.start(responseListener, headers);
            }
        };
    }
}

// Note:
/*
In summary, this Java code defines a gRPC client interceptor (`ClientApiKeyAuthInterceptor`) that
intercepts outgoing gRPC calls and adds an "x-api-key" header with the value "aayushjungswar" to the
request metadata headers. This interceptor is used for adding API key-based authentication to gRPC client
requests. It logs information about intercepted calls and modifies the request headers to include an
API key before forwarding the call to its destination.
*/
