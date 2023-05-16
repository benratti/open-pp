FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install -g @angular/cli
RUN npm install
COPY ./ .
RUN npm run build 

FROM nginx:latest
RUN  mkdir /app
COPY --from=builder /app/dist/open-pp/ /app
COPY ./docker/nginx.conf /etc/nginx/nginx.conf
USER nginx