# Use the official MySQL image as the base
FROM mysql:8.0

# Set environment variables for the MySQL image
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=iamauthorization
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypassword

# Copy the base SQL script to the Docker image
COPY mysql_init.sql /docker-entrypoint-initdb.d/

# Set the file permissions (readable and executable) for the mysql_init.sql script
RUN chmod 755 /docker-entrypoint-initdb.d/mysql_init.sql
