FROM mongo

COPY users.json /users.json
COPY clients.json /clients.json

COPY ./seed.sh .

RUN chmod +x seed.sh

CMD ["sh", "-c", "/seed.sh"]