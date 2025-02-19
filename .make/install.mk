install: clean
	@mvn install -DskipTests -U -fae -DskipSurefireReport

javadoc:
	@mvn javadoc:aggregate