# CBD Lab 2

Sample workspace for completing the CBD Lab 2.

This workspace provides a docker-compose file to run the MongoDB server, and it's companions, in a dockerized enviromnment.

The [resources folder](resources) is automatically mounted to `/resources` in the MongoDB container.
It contains some assets required to complete the Lab.

Open `mongosh` on the container:
`docker-compose exec -it mongodb mongosh --db cbd`

Import restaurants: 
`docker-compose exec -it mongodb mongoimport --db cbd --collection restaurants --drop --file /resources/restaurants.json`


## Additional Notes

* Make sure you have previously installed Docker Desktop, or at least Docker Engine.
// TODO: Add Links
sudo docker-compose up -d
sudo docker cp /home/ttabelhaxd/3ano/CBD/lab2/resources/restaurants.json lab2_mongodb_1:/tmp/test.json
sudo docker exec lab2_mongodb_1 mongoimport -d cbd -c restaurants --drop --file /tmp/test.json
sudo docker-compose exec mongodb mongosh --db cbd