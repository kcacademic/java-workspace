docker pull b2wdigital/restql-http
docker run --rm -p 9000:9000 -e cards=http://api.magicthegathering.io/v1/cards b2wdigital/restql-http
curl -d "from cards" -H "Content-Type: text/plain" localhost:9000/run-query