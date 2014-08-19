

Background
---
Knowledge as a Service (KaaS for short) is a system that can help you quickly find solutions from the existing knowledge base (like AnwserHub, Wiki etc.) based on the analysis of your failed build or a stack trace.

Here is the current situation, think about the scenario happened everyday. when you assembly your project with Assembler Maven Plugin, your build failed with bunch of stack trace or error messages, but you still can not figure out how to resolve the issue. and you start search wiki page or answerhub, but still very hard to find a solution, then you filed a ticket to L1 support, after the triage and couple round of emails back and forth, finally you resolve the problem. and couple day later, another engineer from another team had the same issue, and same thing happen again and again, it's quite time consuming process.

So, KaaS here is to reduce the triage time, and help users easily find solutions to those known issues.

What you have is a problem (with log or stacktrace), What we already have is a solution somewhere in wiki/answerhub, and What KaaS doing is just to find solutions for the problem from the existing KB. Fundamentally, KaaS is a matching system.

Birdview
---
![enter image description here][1]

Work Flow
---
A typical problem should contain four basic elements, 1. context, 2. raw data 3. sourceurl, 4. timestamp

KaaS provides RESTful API to find solutions if you provide those basic elements.

KaaS use Redis as the cache DB, if we can find solution with the context you provided, and KaaS return the solutions back to you right away.

If KaaS can not find the solutions in the cache DB, then KaaS will file the queries against AnswerHub, Wiki, and then store in Redis Cache.

KaaS use the full text match to calculate the similarity for the problem against the solution db. it will ordered by the rate of similarity. And key message, or keyword, or label, will be taken into consideration to calculate the similarity. It's a formulation, not just one single factor.

KaaS will cache the solutions, and Users Application use Ajax call to asynchronously get back the solutions.

Use Case
---
Here we will take Assembler as an example.
 
 
 
When user get a failed assembly job, assembly jenkins plugin will invoke KaaS RESTful service to submit a problem, KaaS service will store the problem into Redis DB.

The problem is submit to a Problem Queue, KaaS will read the problems from Queue to match the solutions.
Another way to fill up the problem tank is to use a back-end batch job to load the failed assembly jobs from Tracking DB which stores all the failed assembly jobs and the full stack trace.

The Assembler Log URL will be used to produce a MD5 key as a unique id in redis.
For example:  
For this [url][2], will produce a

Unique ID: 277fc287593875b2714c3365f64bab26


![enter image description here][3]

![enter image description here][4]

A Solution Finder Batch job is run scheduled to find solutions for problems that stored in redis DB that don't have any solutions yet.

It loads the find workers (WikiFinders, AHFinders, etc.) to parallel find the solutions. And store the solutions into the redis DB.

The solution has a score attached with it, so we can sort the solutions by the the score desc.
A on-demand solution finder might be considered to implement as well through RESTful API.

![enter image description here][5] 

![enter image description here][6]

From the client side, the Assembler Log View page will call KaaS RESTful API to fetch the solutions.

And this notes will be used as a solution to this problem stored in redis DB.

![enter image description here][7]

So basically the KaaS provide three major RESTful API

![enter image description here][8]

Here is the mock up Assembler Log View page
![enter image description here][9]

Data Model
---

```java
problems  LIST  // it's a queue to store the not-processed problems
```
```java
problem:id  HASH
-------------------
context
sourceurl
timestamp
rawdata
```
problem:id is the md5 (sourceurl)
```java
problem:id:similarity  SORTED SET // to store the solutions for the specific problem order by similarity by desc
```
```java
problem:tokenized:id SET // to store the tokenized rawdata of the problem
```
```java
answered_problems SET // to store all the answered problem:id
```

```java
solutions:context  SET              // to store the solutions for a specific context
----------------------
solution:id
```
solution_id  is md5(solution sourceurl)
```java
solution:id   HASH
--------------------
title
summary
rawdata
context
sourceurl
timestamp
score
```
```java
solution:tokenized:id SET // to store the tokenized rawdata of the solution
```
```java
context SET // to store all the known applications, e.g Assembly, Sonar etc.
```

API
---
###Inbound API
#####Problem POST
```java
http://kaas/problem/post
Content-Type : application/json
POST
{   
    "context": "assembler",
    "sourceurl" : "http://....",
    "rawdata" : "exception messages and stacktrace",
    "timestamp" : "yyyy-MM-dd_HH:mm:ss"
}
 
// RESPONSE
200 OK                  accepted, the problem-id will be returned in the body
406 NOT_ACCEPTABLE      Same problem already exists.
406 NOT_ACCEPTABLE      Source URL will be generated as the ID for the problem, it can not be empty.
406 NOT_ACCEPTABLE      Context can not be empty.
 
// Sample
public void post(Problem problem) {
    String target = "http://localhost:7070/myapp";
    String path = "/problem/post";
    Response response = client.target(target).path(path).request().post(Entity.entity(problem, MediaType.APPLICATION_JSON));
    System.out.println(response.readEntity(String.class));
}
```

#####Solution POST
```java

http://kaas/solution/post
Content-Type : application/json
POST
{   
    "context": "assembler",
    "sourceurl" : "http://....",
    "title" : "",
    "summary" : "",
    "rawdata" : "exception messages and stacktrace",
    "timestamp" : "yyyy-MM-dd_HH:mm:ss",
    "score" : "xxx"
}
 
RESPONSE
200 OK                  accepted, the solution-id will be returned in the body
406 NOT_ACCEPTABLE      Source URL will be generated as the ID for the problem, it can not be empty.
406 NOT_ACCEPTABLE      Context can not be empty.
```

###Outbound API
#####AnswerHub API
```java
API URL http://answerhub.corp.ebay.com/services/v2/question.json?q="KEY"
Method GET
Content-Type application/json
Authorization Basic Z3BmX3NlcnZpY2U6R1BG
```
AH API requirements:

- API to get all posts with answers, we need to store those as solution into our db. (post include the title, question, author, time) answer should also be stored along with the author, date, votes, authors score etc.

Design
---
###How score calculated?
###Problems Queue
![enter image description here][10]

Tech Stack
---
[Jersey][11]

[quartz][12] 

[Redis][13]

[Jedis][14] 

[Lucene][15]

Road Map
---

Phase I
---
- Basic functions described above. 
- Apply to Assembler Jenkins Plugin

Phase II
---
- More applications on board
- A [http://stackoverflow.com/][16] like web application, with ElasticSearch as backend search engine. Tag, - - Vote etc. functions.
- KaaS on mesos (Scale-ability)
- KaaS in docker
- Monitoring (Problems Queue etc.)

Code Base
---
[https://github.corp.ebay.com/DevExTech/kaas][17]


  [1]: docs/images/birdview.png
  [2]: http://ebayci.qa.ebay.com/productbundlesserv-ci-001/AssemblerService/viewDeployLog?jobName=productbundlesserv-ci-001&buildNo=5
  [3]: docs/images/submit_a_problem.png
  [4]: docs/images/problem_model.png
  [5]: docs/images/find_solution.png
  [6]: docs/images/solution_model.png
  [7]: docs/images/fetch_solutions.png
  [8]: docs/images/kaas_api.png
  [9]: docs/images/mockup.png
  [10]: docs/images/problem_queue.png
  [11]: https://jersey.java.net/
  [12]: http://quartz-scheduler.org/
  [13]: http://redis.io/
  [14]: https://github.com/xetorthio/jedis
  [15]: http://lucene.apache.org/
  [16]: http://stackoverflow.com/
  [17]: https://github.corp.ebay.com/DevExTech/kaas
