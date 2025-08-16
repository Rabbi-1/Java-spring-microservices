package com.rabbi.billingservice.grpc;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request received {}", billingRequest.toString());
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}


// This BillingGrpcService class implements the gRPC BillingService defined in the .proto file.
// - Annotated with @GrpcService, it registers the service with the gRPC server in a Spring Boot context.
// - Extends BillingServiceImplBase to provide the implementation for createBillingAccount.
// - When a request is received, it logs the incoming BillingRequest.
// - Builds a BillingResponse with a hardcoded accountId ("12345") and status ("ACTIVE").
// - Sends the response back to the client using responseObserver.onNext() and finalizes the call with onCompleted().
// This class demonstrates how the backend processes billing requests via gRPC and responds with structured data.
