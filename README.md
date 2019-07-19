# objectionable-content-filter
## Description
This application filters out comments which has objectionable words. To achieve this, application matches each word of comment against the application's objectionable words data source. Objectionable words data source is a contract (named  `ObjectionableDatasource`) in this application. This data source  can be implemented in a variety of ways internally depending on use cases e.g. TrieDataSource (which has underlaying data structure as Trie), SetDataSource (which has underlaying data structure as immutable Set), LifoCacheDataSource (which is a cache following LIFO strategy for purging) etc. TrieDataSource and SetDataSource implementation is currently available in this app. <br /><br />
Flow is as follows: <br />Controller receives the request -> Controller calls DataSourceFactory to get objectionable data source implementation -> Controller creates CommentsFilter by injecting objectionable data source -> CommentsFilter filter method is  called and result is returned.<br /><br />

Rest API:<br />
Endpoint: http://localhost:9000/comments?filterStrategy=trie <br />
`filterStrategy` can be trie, set or lifo_cache (lifo_cache not implemented yet)<br />
HTTP method:  POST<br />

Request Body:<br />
`{"comment":"Hell is crap"}`<br />

Response  Body: <br />
In case of 500<br />
`{
    "message": "Comment rejected. Comment contains objectionable words.",
    "detectedObjectionalWords": [
        "hell",
        "crap"
    ]
}`
<br />
or <br />
In case of 201<br />
`{
    "message": "Comment successfully posted"
}`
<br /><br />
Use `sbt run` to run the application (default port 9000). <br />
Use  `sbt test` to run the test cases.
