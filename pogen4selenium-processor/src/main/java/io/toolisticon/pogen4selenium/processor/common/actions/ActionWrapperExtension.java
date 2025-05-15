package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.Arrays;
import java.util.stream.Collectors;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.api.AnnotationToClassMapper;
import io.toolisticon.aptk.tools.AnnotationToClassMapperHelper;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationValueWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.pogen4selenium.api.Action;
import io.toolisticon.pogen4selenium.processor.pageobject.ActionWrapper;

public class ActionWrapperExtension {

	
	@CustomCodeMethod(Action.class)
	public static String getAttributeValuesString(ActionWrapper actionWrapper, AnnotationMirrorWrapper annotation) {
	
		
		if (actionWrapper.attributeNameToConstructorMapping().length > 0) {
			
			return Arrays.stream(actionWrapper.attributeNameToConstructorMapping())
					.map( attributeName -> {

		            	return getValidatorExpressionAttributeValueStringRepresentation(
	            			annotation.getAttributeWithDefault(attributeName), 
	            			annotation.getAttributeTypeMirror(attributeName).get());
			            
					})
			    	
		    	.collect(Collectors.joining(", "));
		    	

		    }
		
		
		return "";
		
		
		
	}
	
    private static String getValidatorExpressionAttributeValueStringRepresentation(AnnotationValueWrapper annotationValueWrapper, TypeMirrorWrapper annotationAttributeTypeMirror) {

        if (annotationValueWrapper.isArray()) {
            return annotationValueWrapper.getArrayValue().stream().map(e -> getValidatorExpressionAttributeValueStringRepresentation(e, annotationAttributeTypeMirror.getWrappedComponentType())).collect(Collectors.joining(", ", "new " + annotationAttributeTypeMirror.getWrappedComponentType().getQualifiedName() + "[]{", "}"));
        } else if (annotationValueWrapper.isString()) {
            return "\"" + annotationValueWrapper.getStringValue() + "\"";
        } else if (annotationValueWrapper.isClass()) {
            return annotationValueWrapper.getClassValue().getQualifiedName() + ".class";
        } else if (annotationValueWrapper.isInteger()) {
            return annotationValueWrapper.getIntegerValue().toString();
        } else if (annotationValueWrapper.isLong()) {
            return annotationValueWrapper.getLongValue() + "L";
        } else if (annotationValueWrapper.isBoolean()) {
            return annotationValueWrapper.getBooleanValue().toString();
        } else if (annotationValueWrapper.isFloat()) {
            return annotationValueWrapper.getFloatValue() + "f";
        } else if (annotationValueWrapper.isDouble()) {
            return annotationValueWrapper.getDoubleValue().toString();
        } else if (annotationValueWrapper.isEnum()) {
            return TypeElementWrapper.toTypeElement(annotationValueWrapper.getEnumValue().getEnclosingElement().get()).getQualifiedName() + "." + annotationValueWrapper.getEnumValue().getSimpleName();
        } else {
            throw new IllegalStateException("Got unsupported annotation attribute type : USUALLY THIS CANNOT HAPPEN.");
        }

    }
	
}
