/**
 * This package contains the seleniumap annotation processor.
 */
@AnnotationWrapper(value={ DataObject.class, ExtractDataValue.class}
,bindCustomCode = {DataObjectWrapperExtension.class,ExtractDataValueWrapperExtension.class}
,usePublicVisibility = true)
package io.toolisticon.pogen4selenium.processor.datatoextract; 


import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.pogen4selenium.api.DataObject;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;