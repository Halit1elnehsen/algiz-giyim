@REM Maven Wrapper batch script for Windows
@echo off
set MAVEN_PROJECTBASEDIR=%~dp0
set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"

if exist %WRAPPER_JAR% (
    java %MAVEN_OPTS% -classpath %WRAPPER_JAR% org.apache.maven.wrapper.MavenWrapperMain %*
) else (
    echo Maven Wrapper JAR not found, downloading...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar' -OutFile '%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar'"
    java %MAVEN_OPTS% -classpath %WRAPPER_JAR% org.apache.maven.wrapper.MavenWrapperMain %*
)
