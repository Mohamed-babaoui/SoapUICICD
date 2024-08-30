# Start with a Maven image with OpenJDK 17
FROM maven:3.8.5-openjdk-17-slim

# Define the SoapUI version
ARG SOAPUI_VERSION=5.6.1

# Install necessary dependencies, download SoapUI, and add JARs to the ext folder
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget unzip ca-certificates && \
    wget --no-check-certificate https://s3.amazonaws.com/downloads.eviware/soapuios/${SOAPUI_VERSION}/SoapUI-${SOAPUI_VERSION}-linux-bin.tar.gz && \
    tar -xzf SoapUI-${SOAPUI_VERSION}-linux-bin.tar.gz -C /usr/local && \
    ln -s /usr/local/SoapUI-${SOAPUI_VERSION} /usr/local/SoapUI && \
    rm SoapUI-${SOAPUI_VERSION}-linux-bin.tar.gz && \
    apt-get remove -y wget unzip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy the JAR files to the SoapUI ext directory
COPY jars/*.jar /usr/local/SoapUI-${SOAPUI_VERSION}/bin/ext/

# Copy the CA certificate into the container
COPY pvcp-Issuing-CA.crt .

# Import the CA certificate into the Java cacerts
RUN keytool -import -trustcacerts -alias pvcpissuing2024 -storepass changeit -noprompt -file pvcp-Issuing-CA.crt -cacerts

# Add SoapUI to the PATH
ENV PATH="$PATH:/usr/local/SoapUI/bin"

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY pom.xml /app

# Run Maven build by default
CMD ["mvn", "clean", "install"]
