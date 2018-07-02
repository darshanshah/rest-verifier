Project Title

rest-Verifier:  fully test the REST API using any programming language and test framework.
                The solution should contain tests that you think are appropriate for testing the
                above requirements, API docs, as well as behaviors and expectations of a typical REST service.

Getting Started :
        Need to install all the packages to get started.

        Java 7,Maven,Junit,httpclient,jackson-core
        Java 7 + Junit: to run the whole project and run all the test cases.
        Maven :  to build and install dependency.
        httpclient : to get all access of the http response.
        


Running the tests :
                    1) Maven clean
                    2) Maven install
                    3) Build whole project
                    4) Run all test cases which are present in the BusinessRequirement file.
                    It will execute all the test cases which are requirement to test this rest api.

List Of Bug:

1. Bugs on Post service:
    1.  Post is not working. like its not updating data.(Since this Mocklab so it will not going to
        update any data)(P.S. :Not able to test boundary and edge cases since its not working
        like: multiple blank moive)
2. Bugs on Get Service
    1.  when you ask for specific movie record.
        It is not giving you specific movie record instead it gives all movies collection.
    2.  when you ask for Count of movie then its not giving proper count of data.
    3.  Sorting requirement is not satisfying(i.e
        1. Movies with genre_ids == null should be first in response.
        2. if multiple movies have genre_ids == null,
        then sort by id (ascending). For movies that have non-null genre_ids,
        results should be sorted by id (ascending)


