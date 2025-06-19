package com.example.feedbackservice.logging;

import com.example.systemevents.proto.SystemEvent;
import com.example.systemevents.proto.ActionType;
import com.example.systemevents.proto.Outcome;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
public class SystemEventLoggerAspect {

    private SystemEventsClient systemEventsClient;

    @Autowired
    public void setSystemEventsClient(SystemEventsClient systemEventsClient) {
        this.systemEventsClient = systemEventsClient;
    }

    @Around("@annotation(com.example.feedbackservice.logging.LoggableAction)")
    public Object logSystemEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();

        SystemEvent.Builder eventBuilder = SystemEvent.newBuilder()
                .setTimestamp(Instant.now().toString())
                .setMicroservice("feedback-service")
                .setUserId("unknown") // zamijeni ako imaš korisnički kontekst
                .setActionType(mapToActionType(methodName))
                .setResourceName(className);

        try {
            Object result = joinPoint.proceed();
            Outcome outcome = Outcome.SUCCESS;
            if (result instanceof ResponseEntity<?> response) {
                HttpStatus status = (HttpStatus) response.getStatusCode();
                if (!status.is2xxSuccessful()) {
                    outcome = Outcome.ERROR;
                }
            }
            systemEventsClient.logEvent(eventBuilder.setOutcome(outcome).build());
            return result;
        } catch (Throwable t) {
            systemEventsClient.logEvent(eventBuilder
                    .setOutcome(Outcome.ERROR)
                    .setErrorMessage(t.getMessage())
                    .build());
            throw t;
        }
    }

    private ActionType mapToActionType(String methodName) {
        methodName = methodName.toLowerCase();
        if (methodName.contains("save")) return ActionType.CREATE;
        if (methodName.contains("delete")) return ActionType.DELETE;
        if (methodName.contains("update")) return ActionType.UPDATE;
        if (methodName.contains("patch")) return ActionType.UPDATE;
        return ActionType.GET;
    }
}
