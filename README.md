# REST API using Spring Boot
- CRUD operations & other functionalities
- Test At **http://13.200.253.82/employee/**

## Techstack
- Java
- Spring Boot
- Amazon DynamoDB

## Data Model
**Employee Table**
   - id: string
   - name: string
   - phone: string
   - email: string
   - reportsTo: string
   - profileImage: string


  
## API Endpoints and Usage Examples
- ### Save Employee: http://localhost:8080/employee/save
  - Request Body [POST]:
    ```json
    {
      "name": "GenuinePotion",
      "email": "raon92001@gmail.com",
      "phone": "7021004271",
      "profileImage": "www.urls.img.profile.com/genuine.jpeg",
      "reportsTo": "7f4bb2f1-c9d2-4df0-9473-b1f16d09b515"
    }
    ```
  - Response Body:
    ```json
    {
      "timeStamp": "01:12:43.043863384",
      "statusCode": 201,
      "status": "CREATED",
      "message": "Item created successfully",
      "data": {
          "location": "http://localhost:8080/employee/get/b6a41ca6-9e0d-424b-b65a-cd7a2e7ce8b2",
          "Created": {
              "id": "b6a41ca6-9e0d-424b-b65a-cd7a2e7ce8b2",
              "name": "GenuinePotion",
              "phone": "7021004271",
              "email": "raon92001@gmail.com.com",
              "reportsTo": "7f4bb2f1-c9d2-4df0-9473-b1f16d09b515",
              "profileImage": "www.urls.img.profile.com/genuine.jpeg"
          }
      }
    }
    ```
- ### Update Employee : http://localhost:8080/employee/update/7bba83dd-d297-4974-9ed3-66060773f1d6
  - Request Body [PUT]:
    ```json
    {
      "name": "LightYear",
      "email": "raon92001@gmail.com",
      "phone": "7021004271",
      "profileImage": "www.urls.img.profile.com/genuine.jpeg",
      "reportsTo": "7f4bb2f1-c9d2-4df0-9473-b1f16d09b515"
    }
    ```
  - Response Body:
    ```json
    {
      "timeStamp": "07:07:33.520734864",
      "statusCode": 200,
      "status": "OK",
      "message": "Updated employee successfully",
      "data": {
          "updated_employee": {
              "id": "7bba83dd-d297-4974-9ed3-66060773f1d6",
              "name": "LightYear",
              "phone": "7021004271",
              "email": "raon92001@gmail.com",
              "reportsTo": "7f4bb2f1-c9d2-4df0-9473-b1f16d09b515",
              "profileImage": "www.urls.img.profile.com/genuine.jpeg"
          }
      }
    }
    ```
- ### Delete Employee: http://localhost:8080/employee/delete/7bba83dd-d297-4974-9ed3-66060773f1d6
  - Request Body [DELETE]: N/A
  - Response Body: No Content, 204
- ### Read All Employees: http://localhost:8080/employee/get/all
  - Request Body [GET]: N/A
  - Response Body:
    ```json
    {
      "timeStamp": "07:10:04.502057848",
      "statusCode": 200,
      "status": "OK",
      "message": "Employee List",
      "data": {
          "employees": [
              {
                  "id": "e154f366-57db-48c3-afb0-a310aaed726a",
                  "name": "Sanji",
                  "phone": "1237465982",
                  "email": "cook@onepiece.com",
                  "reportsTo": "68da009d-2aca-4aa5-8fa0-61cce31d0964",
                  "profileImage": "www.urls.img.profile.com/cook.jpeg"
              },
              {
                  "id": "7d3d70dd-96ec-4d85-9c5e-8835b0f27407",
                  "name": "Ussop",
                  "phone": "98776565431",
                  "email": "ussop@onepiece.com",
                  "reportsTo": "68da009d-2aca-4aa5-8fa0-61cce31d0964",
                  "profileImage": "www.urls.img.profile.com/ussop.jpeg"
              },
              {
                  "id": "f33af80d-c093-4bca-af21-510e797cd8d2",
                  "name": "Robin",
                  "phone": "9998887776",
                  "email": "robin@onepiece.com",
                  "reportsTo": "273f5a32-327a-4bb1-ac4c-36eb145d4045",
                  "profileImage": "www.urls.img.profile.com/robin.jpeg"
              },
              {
                  "id": "e1adffee-ff9b-486f-9991-bff63b9812d6",
                  "name": "Nami",
                  "phone": "1237465982",
                  "email": "nami@onepiece.com",
                  "reportsTo": "7d3d70dd-96ec-4d85-9c5e-8835b0f27407",
                  "profileImage": "www.urls.img.profile.com/nami.jpeg"
              },
              {
                  "id": "ff029334-9e79-4084-b8b4-2f7cdbf2e314",
                  "name": "Brooks",
                  "phone": "98723487654",
                  "email": "brooks@onepiece.com",
                  "reportsTo": "e1adffee-ff9b-486f-9991-bff63b9812d6",
                  "profileImage": "www.urls.img.profile.com/brooks.jpeg"
              },
              {
                  "id": "7f4bb2f1-c9d2-4df0-9473-b1f16d09b515",
                  "name": "Nayan",
                  "phone": "8879898578",
                  "email": "nayan92001@gmail.com",
                  "reportsTo": null,
                  "profileImage": "www.urls.img.profile.com/nayan.jpeg"
              },
              {
                  "id": "30163100-8634-474d-bcd7-3927c997a1c3",
                  "name": "Franky",
                  "phone": "98723487654",
                  "email": "cyborg@onepiece.com",
                  "reportsTo": "273f5a32-327a-4bb1-ac4c-36eb145d4045",
                  "profileImage": "www.urls.img.profile.com/franky.jpeg"
              },
              {
                  "id": "ea1fd38b-5a2a-4e0a-8ae6-f40a41492100",
                  "name": "Luffy",
                  "phone": "6666666666",
                  "email": "luffy@onepiece.com",
                  "reportsTo": null,
                  "profileImage": "www.urls.img.profile.com/luffy.jpeg"
              },
              {
                  "id": "68da009d-2aca-4aa5-8fa0-61cce31d0964",
                  "name": "Jimbei",
                  "phone": "98723487654",
                  "email": "fishman@onepiece.com",
                  "reportsTo": "ea1fd38b-5a2a-4e0a-8ae6-f40a41492100",
                  "profileImage": "www.urls.img.profile.com/jimbei.jpeg"
              },
              {
                  "id": "89fd7815-3ece-4124-a869-64bf6f43fee3",
                  "name": "Chopper",
                  "phone": "98723487654",
                  "email": "chopper@onepiece.com",
                  "reportsTo": "f33af80d-c093-4bca-af21-510e797cd8d2",
                  "profileImage": "www.urls.img.profile.com/chopper.jpeg"
              },
              {
                  "id": "273f5a32-327a-4bb1-ac4c-36eb145d4045",
                  "name": "Zoro",
                  "phone": "9999999999",
                  "email": "zoro@onepiece.com",
                  "reportsTo": "ea1fd38b-5a2a-4e0a-8ae6-f40a41492100",
                  "profileImage": "www.urls.img.profile.com/zoro.jpeg"
              }
          ]
      }
    }
    ```
- ### Read Single Employee: http://localhost:8080/employee/get/ff029334-9e79-4084-b8b4-2f7cdbf2e314
  - Request Body [GET]: N/A
  - Response Body:
    ```json
    {
      "timeStamp": "07:12:08.782229161",
      "statusCode": 200,
      "status": "OK",
      "message": "Single Record Fetch",
      "data": {
          "employee": {
              "id": "ff029334-9e79-4084-b8b4-2f7cdbf2e314",
              "name": "Brooks",
              "phone": "98723487654",
              "email": "brooks@onepiece.com",
              "reportsTo": "e1adffee-ff9b-486f-9991-bff63b9812d6",
              "profileImage": "www.urls.img.profile.com/brooks.jpeg"
          }
      }
    }
    ```
    
- ### Get Nth Level Manager: http://localhost:8080/employee/getManager/{id:String}/{level:int}
  - Request Body [GET]: N/A
  - Response Body:
    - http://localhost:8080/employee/getManager/89fd7815-3ece-4124-a869-64bf6f43fee3/1
      ```json
      {
        "timeStamp": "07:15:23.549896367",
        "statusCode": 200,
        "status": "OK",
        "message": "Manager found successfully",
        "data": {
            "manager": {
                "id": "f33af80d-c093-4bca-af21-510e797cd8d2",
                "name": "Robin",
                "phone": "9998887776",
                "email": "robin@onepiece.com",
                "reportsTo": "273f5a32-327a-4bb1-ac4c-36eb145d4045",
                "profileImage": "www.urls.img.profile.com/robin.jpeg"
            }
        }
      }
      ```
    - http://localhost:8080/employee/getManager/89fd7815-3ece-4124-a869-64bf6f43fee3/4
      ```json
      {
        "timeStamp": "07:16:36.532827313",
        "statusCode": 404,
        "status": "NOT_FOUND",
        "message": "Manager not found for employee ID: 89fd7815-3ece-4124-a869-64bf6f43fee3 at level 4",
        "data": null
      }
      ```
      
- ### Read Employees Paginated: http://localhost:8080/employee/get
  - Request Params [GET]: 
    - sortBy: name
    - orderBy: asc
    - lastEvaluatedKey: 273f5a32-327a-4bb1-ac4c-36eb145d4045
    - pageSize: 5
  - Response Body:
    - http://localhost:8080/employee/getManager/89fd7815-3ece-4124-a869-64bf6f43fee3/1
      ```json
      {
        "timeStamp": "07:23:49.538110397",
        "statusCode": 200,
        "status": "OK",
        "message": "Employee List",
        "data": {
            "lastEvaluatedKey": "89fd7815-3ece-4124-a869-64bf6f43fee3",
            "employees": [
                {
                    "id": "89fd7815-3ece-4124-a869-64bf6f43fee3",
                    "name": "Chopper",
                    "phone": "98723487654",
                    "email": "chopper@onepiece.com",
                    "reportsTo": "f33af80d-c093-4bca-af21-510e797cd8d2",
                    "profileImage": "www.urls.img.profile.com/chopper.jpeg"
                },
                {
                    "id": "30163100-8634-474d-bcd7-3927c997a1c3",
                    "name": "Franky",
                    "phone": "98723487654",
                    "email": "cyborg@onepiece.com",
                    "reportsTo": "273f5a32-327a-4bb1-ac4c-36eb145d4045",
                    "profileImage": "www.urls.img.profile.com/franky.jpeg"
                },
                {
                    "id": "68da009d-2aca-4aa5-8fa0-61cce31d0964",
                    "name": "Jimbei",
                    "phone": "98723487654",
                    "email": "fishman@onepiece.com",
                    "reportsTo": "ea1fd38b-5a2a-4e0a-8ae6-f40a41492100",
                    "profileImage": "www.urls.img.profile.com/jimbei.jpeg"
                },
                {
                    "id": "ea1fd38b-5a2a-4e0a-8ae6-f40a41492100",
                    "name": "Luffy",
                    "phone": "6666666666",
                    "email": "luffy@onepiece.com",
                    "reportsTo": null,
                    "profileImage": "www.urls.img.profile.com/luffy.jpeg"
                },
                {
                    "id": "7f4bb2f1-c9d2-4df0-9473-b1f16d09b515",
                    "name": "Nayan",
                    "phone": "8879898578",
                    "email": "nayan92001@gmail.com",
                    "reportsTo": null,
                    "profileImage": "www.urls.img.profile.com/nayan.jpeg"
                }
            ]
        }
      }
      ```

## Amazon DynamoDB Setup
- Create AWS account
- Login to AWS Console and go to IAM
- Create an IAM Group and attach AmazonDynamoDBFullAccess policy
- Create a an IAM user in that group and download the Access Key and Secret Access Key for further use in the code
- Go to Amazon DynamoDB
- Create a table named 'Employee' with 'id - string' as the partition key
