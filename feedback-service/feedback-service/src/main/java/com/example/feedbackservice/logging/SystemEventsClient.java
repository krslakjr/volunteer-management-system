package com.example.feedbackservice.logging;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.systemevents.proto.SystemEvent;
import com.example.systemevents.proto.SystemEventsGrpc;
import com.example.systemevents.proto.Ack;
import org.springframework.stereotype.Component;

@Component
public class SystemEventsClient {

    private final SystemEventsGrpc.SystemEventsBlockingStub stub;

    public SystemEventsClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.stub = SystemEventsGrpc.newBlockingStub(channel);
    }

    public boolean logEvent(SystemEvent event) {
        Ack ack = stub.logEvent(event);
        return ack.getOk();
    }
}
