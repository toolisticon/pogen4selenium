/**
 * This package contains the seleniumap annotation processor.
 */
@AnnotationWrapper(value={PageObject.class, PageObjectElement.class, ActionClick.class, ActionMoveToAndClick.class, ActionWrite.class, ExtractData.class,Pause.class}
,bindCustomCode = {PageObjectWrapperExtension.class, ExtractDataWrapperExtension.class}
,usePublicVisibility = true)
package io.toolisticon.pogen4selenium.processor.pageobject; 


import io.toolisticon.pogen4selenium.api.ActionClick;
import io.toolisticon.pogen4selenium.api.ActionMoveToAndClick;
import io.toolisticon.pogen4selenium.api.ActionWrite;
import io.toolisticon.pogen4selenium.api.ExtractData;
import io.toolisticon.pogen4selenium.api.PageObject;
import io.toolisticon.pogen4selenium.api.PageObjectElement;
import io.toolisticon.pogen4selenium.api.Pause;
import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;