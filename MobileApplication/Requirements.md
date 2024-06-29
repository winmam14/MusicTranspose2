# Requirements Mobile App

## Settings

## Download/Admin
User gets proposed a selection of parts based on his register and the masters active score.

### Admin
List of all music pieces  
select active  
confirmation popup  
when success, list available scores  
select score/request score  
display pdf  

### Download
refresh available scores  
list available scores  
select score/request score  
display pdf 

## Import
This Page is for uploading/importing Documents. 

- import PDF files
- set metadata (on short press):
    - title -> string
    - description -> string
    - dateAdded -> unix timestamp
    - register -> dropdown ENUM (1 for trombone)
    - part -> string
- upload file (on long press)
    - multy select
- POST to Backend:
    - multipart/form-data
        - name, filename, content-type
        - document, score1.pdf, application/pdf
        - document, score2.pdf, application/pdf
        - metadata, {...}, application/json

- metadata: 
    ```json 
  { "metadata": [
        {
          "id": 1,
          "title": "Example Title",
          "description": "Description of the document",
          "dateAdded": 1715368281,
          "register": 1,
          "part": "1 Pos in C"
        },
        {
          "id": 2,
          "title": "Example Title",
          "description": "Description of the document",
          "dateAdded": 1715368281,
          "register": 2,
          "part": "1 Tuba in C"
        },
        {
          // Add more metadata objects as needed
        }
      ] 
  }  
  
    
