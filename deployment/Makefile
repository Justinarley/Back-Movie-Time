build-image:
        @ docker build -f Dockerfile -t movitime:1 .

deploy:
        @ docker stack deploy --with-registry-auth -c docker-compose.yml movitime_backend

rm:
        @ docker stack rm movitime_backend

crear-imagen:
        @ docker build -f Dockerfile -t movitime:1 .

crear-red:
        @ docker network create --driver overlay --scope swarm movitime_net || true

borrar-red:
        @ docker network rm movitime_net

crear-volumen:
        @ docker volume create pg_movitime_data

borrar-volumen:
        @ docker volume rm pg_movitime_data