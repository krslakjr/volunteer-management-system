package com.example.activitymanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JsonPatchHelper {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T> T applyPatch(JsonPatch patch, T targetObject, Class<T> targetClass)
            throws JsonPatchException, JsonProcessingException {
        JsonNode patchedNode = patch.apply(objectMapper.convertValue(targetObject, JsonNode.class));
        T patchedObject = objectMapper.treeToValue(patchedNode, targetClass);

        Set<ConstraintViolation<T>> violations = validator.validate(patchedObject);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + violations);
        }
        return patchedObject;
    }
}
