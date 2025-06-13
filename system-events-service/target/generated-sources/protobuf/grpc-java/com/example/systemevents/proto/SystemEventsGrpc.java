package com.example.systemevents.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Servis u system-events aplikaciji
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.0)",
    comments = "Source: system_events.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SystemEventsGrpc {

  private SystemEventsGrpc() {}

  public static final java.lang.String SERVICE_NAME = "systemevents.SystemEvents";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.systemevents.proto.SystemEvent,
      com.example.systemevents.proto.Ack> getLogEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LogEvent",
      requestType = com.example.systemevents.proto.SystemEvent.class,
      responseType = com.example.systemevents.proto.Ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.systemevents.proto.SystemEvent,
      com.example.systemevents.proto.Ack> getLogEventMethod() {
    io.grpc.MethodDescriptor<com.example.systemevents.proto.SystemEvent, com.example.systemevents.proto.Ack> getLogEventMethod;
    if ((getLogEventMethod = SystemEventsGrpc.getLogEventMethod) == null) {
      synchronized (SystemEventsGrpc.class) {
        if ((getLogEventMethod = SystemEventsGrpc.getLogEventMethod) == null) {
          SystemEventsGrpc.getLogEventMethod = getLogEventMethod =
              io.grpc.MethodDescriptor.<com.example.systemevents.proto.SystemEvent, com.example.systemevents.proto.Ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LogEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.systemevents.proto.SystemEvent.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.systemevents.proto.Ack.getDefaultInstance()))
              .setSchemaDescriptor(new SystemEventsMethodDescriptorSupplier("LogEvent"))
              .build();
        }
      }
    }
    return getLogEventMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SystemEventsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemEventsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemEventsStub>() {
        @java.lang.Override
        public SystemEventsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemEventsStub(channel, callOptions);
        }
      };
    return SystemEventsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SystemEventsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemEventsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemEventsBlockingStub>() {
        @java.lang.Override
        public SystemEventsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemEventsBlockingStub(channel, callOptions);
        }
      };
    return SystemEventsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SystemEventsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemEventsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemEventsFutureStub>() {
        @java.lang.Override
        public SystemEventsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemEventsFutureStub(channel, callOptions);
        }
      };
    return SystemEventsFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Servis u system-events aplikaciji
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void logEvent(com.example.systemevents.proto.SystemEvent request,
        io.grpc.stub.StreamObserver<com.example.systemevents.proto.Ack> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLogEventMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SystemEvents.
   * <pre>
   * Servis u system-events aplikaciji
   * </pre>
   */
  public static abstract class SystemEventsImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SystemEventsGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SystemEvents.
   * <pre>
   * Servis u system-events aplikaciji
   * </pre>
   */
  public static final class SystemEventsStub
      extends io.grpc.stub.AbstractAsyncStub<SystemEventsStub> {
    private SystemEventsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemEventsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemEventsStub(channel, callOptions);
    }

    /**
     */
    public void logEvent(com.example.systemevents.proto.SystemEvent request,
        io.grpc.stub.StreamObserver<com.example.systemevents.proto.Ack> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLogEventMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SystemEvents.
   * <pre>
   * Servis u system-events aplikaciji
   * </pre>
   */
  public static final class SystemEventsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SystemEventsBlockingStub> {
    private SystemEventsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemEventsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemEventsBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.systemevents.proto.Ack logEvent(com.example.systemevents.proto.SystemEvent request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLogEventMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SystemEvents.
   * <pre>
   * Servis u system-events aplikaciji
   * </pre>
   */
  public static final class SystemEventsFutureStub
      extends io.grpc.stub.AbstractFutureStub<SystemEventsFutureStub> {
    private SystemEventsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemEventsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemEventsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.systemevents.proto.Ack> logEvent(
        com.example.systemevents.proto.SystemEvent request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLogEventMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOG_EVENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOG_EVENT:
          serviceImpl.logEvent((com.example.systemevents.proto.SystemEvent) request,
              (io.grpc.stub.StreamObserver<com.example.systemevents.proto.Ack>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getLogEventMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.systemevents.proto.SystemEvent,
              com.example.systemevents.proto.Ack>(
                service, METHODID_LOG_EVENT)))
        .build();
  }

  private static abstract class SystemEventsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SystemEventsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.systemevents.proto.SystemEventsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SystemEvents");
    }
  }

  private static final class SystemEventsFileDescriptorSupplier
      extends SystemEventsBaseDescriptorSupplier {
    SystemEventsFileDescriptorSupplier() {}
  }

  private static final class SystemEventsMethodDescriptorSupplier
      extends SystemEventsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SystemEventsMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SystemEventsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SystemEventsFileDescriptorSupplier())
              .addMethod(getLogEventMethod())
              .build();
        }
      }
    }
    return result;
  }
}
