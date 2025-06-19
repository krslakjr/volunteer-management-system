package com.example.systemevents;

import com.example.systemevents.proto.Ack;
import com.example.systemevents.proto.SystemEvent;
import com.example.systemevents.proto.SystemEventsGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;

@GrpcService // iz grpc-spring-boot-starter
public class SystemEventsServiceImpl extends SystemEventsGrpc.SystemEventsImplBase {

    private final EventRepository repo;

    public SystemEventsServiceImpl(EventRepository repo) {
        this.repo = repo;
    }

    @Override
    public void logEvent(SystemEvent request, StreamObserver<Ack> responseObserver) {
        // Sačuvaj događaj u bazu
        EventEntity entity = new EventEntity();
        entity.setTimestamp(Instant.parse(request.getTimestamp()));
        entity.setMicroservice(request.getMicroservice());
        entity.setUserId(request.getUserId());
        entity.setActionType(request.getActionType().name());
        entity.setResourceName(request.getResourceName());
        entity.setOutcome(request.getOutcome().name());
        entity.setErrorMessage(request.getErrorMessage());

        repo.save(entity);

        Ack ack = Ack.newBuilder().setOk(true).build();
        responseObserver.onNext(ack);
        responseObserver.onCompleted();
    }
}
