FROM node:20-alpine AS angular-app-builder
WORKDIR /app
COPY package*.json ./
RUN npm install -g @angular/cli
RUN npm install
COPY ./ .
RUN npm run build 

FROM debian:bullseye-slim AS selfsigned-certificat-builder
RUN apt-get update && apt-get install -y openssl
WORKDIR /cert/
COPY ./docker/self-signed-certificate.conf . /cert/
RUN openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout localhost.key -out localhost.crt -config self-signed-certificate.conf


FROM nginx:1.24-alpine-slim
RUN  mkdir /app
RUN apk add curl

COPY --from=angular-app-builder /app/dist/open-pp/ /app
COPY --from=selfsigned-certificat-builder /cert/localhost.crt /etc/ssl/certs/localhost.crt
COPY --from=selfsigned-certificat-builder /cert/localhost.key /etc/ssl/certs/localhost.key
RUN chown nginx:nginx /etc/ssl/certs/localhost.crt /etc/ssl/certs/localhost.key
COPY ./docker/nginx.conf /etc/nginx/nginx.conf
USER nginx
