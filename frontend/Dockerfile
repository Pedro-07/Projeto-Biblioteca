# Etapa 1 – Construir o frontend Vue.js
FROM node:18 as build-stage
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# Etapa 2 – Servir a aplicação com nginx
FROM nginx:alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
