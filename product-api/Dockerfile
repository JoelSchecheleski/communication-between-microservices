FROM gradle:7.4.2-jdk11-alpine AS build
COPY --chown=gradle:gradle . .
WORKDIR .
RUN gradle build -x test --no-daemon
EXPOSE 8082
CMD ["gradle", "bootRun"]