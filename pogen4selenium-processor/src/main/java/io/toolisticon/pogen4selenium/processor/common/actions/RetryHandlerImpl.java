package io.toolisticon.pogen4selenium.processor.common.actions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.pogen4selenium.runtime.RetryImpl;

public class RetryHandlerImpl implements RetryHandler{
	
	private final RetryImpl.RetryConfigType retryConfigType;
	private final int nrOfTries;
	private final int intervallInMs;
	private final List<TypeMirrorWrapper> retryCauseTypeMirrors;
	
	RetryHandlerImpl(RetryImpl.RetryConfigType retryConfigType, int nrOfTries, int intervallInMs, List<TypeMirrorWrapper> retryCauseTypeMirrors) {

		this.retryConfigType = retryConfigType;
		this.nrOfTries = nrOfTries;
		this.intervallInMs = intervallInMs;
		this.retryCauseTypeMirrors = retryCauseTypeMirrors;
		
	}
	

	@Override
	public String getRetryConfigType() {
		return this.retryConfigType.name();
	}

	@Override
	public int getNrOfTries() {
		return this.nrOfTries;
	}

	@Override
	public int getIntervalInMs() {
		return this.intervallInMs;
	}

	@Override
	public String getRetryCauseClassNames() {
		
		
		return retryCauseTypeMirrors.size() == 0 ? "" :  ", " +retryCauseTypeMirrors.stream().map(e -> e.getSimpleName() + ".class").collect(Collectors.joining(","));
	}

	@Override
	public Set<String> getImports() {
		return retryCauseTypeMirrors.stream().map(TypeMirrorWrapper::getQualifiedName).collect(Collectors.toSet());
	}
	
}
