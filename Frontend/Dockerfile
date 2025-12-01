# Imagen base con Nginx
FROM nginx:alpine

# Copia tu frontend dentro del directorio público de Nginx
COPY . /usr/share/nginx/html

# Usar Login.html como página de inicio
RUN mv /usr/share/nginx/html/Login.html /usr/share/nginx/html/index.html

# Expone el puerto 80
EXPOSE 80
