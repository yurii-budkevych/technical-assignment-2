## Table of contents
* [General info](#general-info)
* [Links](#links)
* [Paths](#paths)
* [Examples](#examples)

## General info
This project is a simple service which fetches data from https://api.hnpwa.com, preserve it into DB and returns it.
So far it has 1 GET endpoint which returns newest stories from hacker rank.

## Links
Raw data set:
https://api.hnpwa.com/v0/newest/{1}.json

Description of Hacker Rank API:
https://github.com/tastejs/hacker-news-pwas/blob/master/docs/api.md

		
### Paths


|  URL |  Method |  Description |
|----------|--------------|----------------|
|`http://localhost:8080/service/new-stories`  | GET | Returns newest stories from HR|

## Examples

Input:
  - http://localhost:8080/service/new-stories

Output:
  - Ordered stories.


```json
[ {
    "timeAgo": "2 minutes ago",
    "id": "22316384",
    "title": "Fundamental user onboarding lessons from classic Nintendo games",
    "url": "https://www.appcues.com/blog/3-fundamental-user-onboarding-lessons-from-classic-nintendo-games"
  },
  {
    "timeAgo": "2 minutes ago",
    "id": "22316379",
    "title": "The Byzantine Generals Problem [pdf]",
    "url": "http://lamport.azurewebsites.net/pubs/byz.pdf"
  } 
]
```
