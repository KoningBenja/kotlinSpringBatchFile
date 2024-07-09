# kotlinSpringBatchFile
A kotlin application that makes use of Spring Batch to read and write files.

## Setup
1. You need to run the docker compose file to setup the batch database. This is where information about the jobs and steps are written to.
   1. Make sure you have Docker Desktop running (the docker service should be accessible by your IDE)
   2. Open a terminal in the root directory of the project (where the docker-compose.yaml file is located).
   3. run: `docker-compose up -d`
   4. This should now have created the container and image, you should be able to see them in Docker Desktop's Container tab.
2. Spring Batch should create the tables you require, but to be safe, you could set them up yourself.
   1. Go to the docker container you just created, open a terminal to the image using exec. 
   2. You now have to login to the MySql running on the image, the username and password will be in the application-local.yaml file. The login command is:
   `mysql -u <username> -p<password>`
   3. Next you'll have to create the database, for this project I called it: bank. The command to creat the database is:
   `CREATE DATABASE bank;`
   4. Then you have to tell MySql to use this database. The command for this is:
   `use bank;`
   5. Now you'll be able to copy the sql queries from the createTableScripts.txt file and paste them in the MySql terminal.
   6. All tables should now have been created, to see them you can run:
   `SHOW TABLES;`
3. Next you need to create a file to use for the validation job.
   1. First go to the BatchWriteTestConfig class. You should see generateTransactionsJob with a JobBuilder instance being created in it. This instance will have a name, copy that name.
   2. Go to the TransactionFileApplication.kt file, you'll see a Qualifier annotation set for a jobToRun parameter in the TransactionFileApplication class. Paste the name of the job you copied there.
   3. Now run the application. I found that writing a 100 000 records takes about 4.3 seconds.
   4. Once done, the file should be in src/main/resources/gen
   5. You can copy this file to the src/main/resources directory, that where the reader for the validation will read from.


