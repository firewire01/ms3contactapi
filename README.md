# ms3contactapi
- This is for MS3 Contact API

- GitHub: https://github.com/firewire01/ms3contactapi
	- Used gulp and docker to setup CI/CD and dockerize deployment.
	- Only have a test lint in Angular. Remove spring side for errors in deployment.
- AWS Url Link:
  http://ms3contactapi-env.eba-i5amy7xm.ap-southeast-1.elasticbeanstalk.com/
 


# Assumptions
* Valid json schema 
    {
	"Identification": {
		"id": 1,
		"firstName": "Jose",
		"lastName": "Ancajas",
		"dob": "09/12/2020",
		"gender": "M",
		"title": "Librarian"
	},
	"Address": [
		{
			"id": 2,
			"type": "gg",
			"number": 0,
			"street": "dsd",
			"unit": "asd",
			"city": "sd",
			"state": "ads",
			"zipcode": "ads"
		}
	],
	"Communication": [
		{
			"preferred": true,
			"id": 3,
			"type": "email",
			"value": "asd"
		}
	]
}

* All data fields are clean and conform to json naming standards.
* All capital and Pascal casing is tranforms to all lower case expect for the main class,
* This is an Spring boot back end and an Angular front end. using spring boot h2 database.
* Data base schema:
    - Contact:
	    - id - auto gen - pk
		- first_name - string not null
		- last_name - string not null
		- dob - Date - not null
		- gender - string - not null
		- title - string - not null
	- Address
		- id - auto gen - pk
		- contact_id - fk -> Contact id pk
		- type - string - not null
		- number - int
		- street - string - not null
		- unit - string 
		- state - string
		- city - string - not null
		- zipcode - string
	- Communication
		- id - auto gen - pk
		- contact_id - fk -> Contact id pk
		- type - string - not null
		- value - string - not null 
		- preferred - string - not null
# SQL
* CREATE TABLE Contact
(
id int NOT NULL,
first_name varchar(125) NOT NULL,
last_name varchar(125) NOT NULL,
dob date NOT NULL,
gender char(5) NOT NULL,
title  varchar(40) NOT NULL,
PRIMARY KEY (id)
);

* CREATE TABLE Address
(
id int NOT NULL,
contact_id int NOT NULL,
type varchar(125) NOT NULL,
number int,
street char(150) NOT NULL,
unit  varchar(150),
state  varchar(150),
city  varchar(150) NOT NULL,
zipcode  varchar(40),
PRIMARY KEY (id),
FOREIGN KEY (contact_id) REFERENCES Contact(id)
);

* CREATE TABLE Communication
(
id int NOT NULL,
contact_id int NOT NULL,
type varchar(125) NOT NULL,
value char(150) NOT NULL,
preferred  boolean NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (contact_id) REFERENCES Contact(id)
);
     
# API
	-  /contacts -> GET - return the list of contacts
	- /contacts/{id} -> GET - return contact by ID.
	- /contacts -> POST - creation of contact
	- /contacts -> PUT - Update of contact
	- /contacts/{id} -> DELETE -> deletion per contact id.
# How to run app. 
- This lunch in the AWS - Elastic Beanstalk
- Url: http://ms3contactapi-env.eba-i5amy7xm.ap-southeast-1.elasticbeanstalk.com/
  *Note: this is just a free tier not sure if the dns will change. please contact me if the link is not accessable.
*First: 
   - Go to this link first to initilze the site http://ms3contactapi-env.eba-i5amy7xm.ap-southeast-1.elasticbeanstalk.com/
* There is two menu in the top left part: 
	* Contact List -> Will have a all contact list view. initially its empty.
		- Button navigation:
			- Delete -> Delete contact by id
			- Update -> all fields will be populated.
				- Supply all required fields:
				- First Name - because this sa vital info of contact.
				- Last Name - because this sa vital info of contact.
				- Date of Birth - because this sa vital info of contact.
				- Gender - because this sa vital info of contact.
				- Title - because this sa vital info of contact.
				- Address: at least one
					- Type
					- Street
					- City
				- Communication: at least one
					- Type
					- Value
					- Preferred: if you dont supply this it will be default as False.
				- Button navigation:
					- Back to Contact List -> redirect to Contact List
					- Reset -> Reset fields.
					- Update -> Update Contact if all fields are valid.
			- Details -> Display details of the contact by id
	- Add Contact -> This will create the contact.
		- Supply all required fields: All fields are considered vital info for the contact.
			- First Name
			- Last Name
			- Date of Birth
			- Gender
			- Title
			- Address: at least one, you can Press Add More Address for another entry
				- Type
				- Street
				- City
			- Communication: at least one, you can Press Add More Communication for another entry
				- Type
				- Value
				- Preferred: if you dont supply this it will be default as False.
		- Button navigation:
			- Back to Contact List -> redirect to Contact List
			- Submit -> Create Contact if all fields are valid.