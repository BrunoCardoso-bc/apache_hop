# Use uma imagem base do OpenJDK
FROM openjdk:11-jre-slim

# Instalar dependências necessárias
RUN apt-get update && apt-get install -y \
    curl \
    unzip

# Baixar o Apache Hop
RUN curl -L https://dlcdn.apache.org/hop/2.4.0/apache-hop-2.4.0.zip -o /tmp/hop.zip

# Descompactar o arquivo baixado
RUN unzip /tmp/hop.zip -d /opt/

# Definir diretório de trabalho
WORKDIR /opt/apache-hop-2.4.0

# Expor a porta necessária (geralmente 8080 para a interface web do Apache Hop)
EXPOSE 8080

# Comando para iniciar o Apache Hop
CMD ["bin/hop-server.sh"]
