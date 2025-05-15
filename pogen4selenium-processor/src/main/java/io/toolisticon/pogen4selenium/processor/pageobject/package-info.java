/**
 * This package contains the seleniumap annotation processor.
 */
@AnnotationWrapper(value={PageObject.class, PageObjectElement.class, Action.class, ExtractData.class, ExtractDataValue.class ,Pause.class}
,bindCustomCode = {PageObjectWrapperExtension.class, ExtractDataWrapperExtension.class, ExtractDataValueWrapperExtension.class, io.toolisticon.pogen4selenium.processor.common.actions.ActionWrapperExtension.class}
,usePublicVisibility = true)
package io.toolisticon.pogen4selenium.processor.pageobject; 


import io.toolisticon.pogen4selenium.api.Action;
import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.ExtractData;
import io.toolisticon.pogen4selenium.api.ExtractDataValue;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.Pause;
import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;