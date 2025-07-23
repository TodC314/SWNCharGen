# SWN Char Gen by Tod Casasent

This project is a Stars Without Number character generator and manager.

> **Begin Programmer Geek Speech**
> 
> I started this both to create a char gen application and to brush up on React front-ends, Python Flask back-ends, and Java Spring Boot back-ends.
> The software is designed so the same front-end can use either the Flask or the Spring back-end.
> The application also is intended to be privacy-friendly by not requiring cookies.
> 
> **End Programmer Geek Speech**

# Features

The current features are:

- Assigning a character name.
- Rolling and assigning the six attributes in order.
- Calculate the attribute modifiers when the attributes change.
- Assign a single attribute value to 14 - old value is preserved to allow reassignment.
- Download and upload JSON to preserve a character.

Future planned features are:

- More tests are needed. This will likely take priority over features.
- Rolling for background and skills.
- Future features will follow the flow on page 4 of the Revised free book: A Summary of Character Creation.
- I also plan to add support for downloading and uploading form-fillable PDF files containing the character data.
- Providing a back-end database that is shared between Flask and Spring Boot is also planned.
- Along with the database is some form of authentication, method to be determined.
- Once this application is actually minimally useful, I will also provide it live online in a Docker container.
- Long-term I plan to use Perplexity (or other AI) to add a natural language interface to allow character design.

# Design Decisions

In designing this, I've leaned towards low maintenance, robustness, and privacy.

The robustness is reflected in the method of passing character information between the front and back-ends.
The Dict/Map being passes uses the member variable names for the keys, so additional variables do not mean more code.

I've also eschewed using cookies or features like local storage, while striving for server-side security and business (game system) logic enforcement. 

Some of the extra compartmentalization that may seem out of place is there to provide future options to create other "system" classes for other similar RPGs.

# IDE and Application Setup

The SWN Char Gen software is setup for the IntelliJ IDE. There are four run Configurations for this application.

For the Flask application, first start the Flask Backend configuration and then the React Frontend Flask configuration.

For the Spring application, first start the Spring Backend configuration and then the React Frontend Spring configuration.

Both can be run simultaneously.

# Copyright

SWN Char Gen © 2025. by Tod Casasent is licensed under CC BY-NC-SA 4.0. To view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/.

Rules and values associated with the SWN system are the copyright of Kevin Crawford, Sine Nominee Publishing (https://sine-nomine-publishing.myshopify.com/).

SWN system data is used in accordance with Kevin Crawford's Discord message of limiting content to the free version of the rules.
Specifically, the SWNSystem.java and swn_system.py game strings, values, and relationships are the copyright of Kevin Crawford, Sine Nominee Publishing.