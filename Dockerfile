FROM hseeberger/scala-sbt

# extra layer to avoid re-install of SBT 0.13.17
RUN sbt -sbt-version 0.13.17

RUN mkdir /build
WORKDIR /build

# extra layers to fetch all dependencies
RUN mkdir /build/project
ADD build.sbt /build
ADD project /build/project
RUN sbt compile

ADD . /build
RUN sbt jsontranslate/assembly

FROM openjdk:8
COPY --from=0 /build/app/target/jsontranslate*assembly*.jar /jsontranslate.jar
ENTRYPOINT ["java", "-jar", "/jsontranslate.jar"]

