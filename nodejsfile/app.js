var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var nodemailer = require('nodemailer');
var Twitter = require('twitter');
var mongoose = require('mongoose');
var sentiment = require('sentiment');

var app = express();

app.use(bodyParser.urlencoded({'extended':'true'}));
app.use(bodyParser.json());
app.use(bodyParser.json({ type: 'application/vnd.api+json' })); 

app.set('views',path.join(__dirname,'views'));
app.set('view engine','jade');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(express.static(path.join(__dirname,'public')));

app.post('/twt',function(req,res){
var wsearch = req.body.seword;
console.log(req.body.seword);
var params = {q: wsearch,count: "100"};
var hash = new Object();
client.get('search/tweets', params, function(error, tweets, response) {
      if (!error) {
        console.log("Fetched", tweets.statuses.length, "tweets for", params.q);
        hash["VS"] = 0;hash["S"] = 0;hash["VH"] = 0;hash["H"] = 0;hash["NU"] = 0;
        hash["Twitter for Android"] = 0;hash["Twitter Web Client"] = 0;hash["Twitter for iPhone"] = 0;hash["Google"] = 0;
         for(i=0; i<tweets.statuses.length; i++)
         {
            var stm = sentiment(tweets.statuses[i].text).score;
            var s = tweets.statuses[i].source.match(">([^<]*)<")[0];
            s = s.substring(1,s.length -1);
            if(s == "Twitter for Android" || s == "Twitter Web Client" || s == "Twitter for iPhone" || s == "Google")
            {
              hash[s] = hash[s] + 1;
            }
            
            if(stm<0)
            {
              if(stm < -2)
              {
                hash["VS"] = hash["VS"] + 1;
              }
              else
              {
                hash["S"] = hash["S"] + 1;  
              }
            }
            else if(stm > 0)
            {
              if(stm > 2)
              {
                hash["VH"] = hash["VH"] + 1;
              }
              else
              {
                hash["H"] = hash["H"] + 1
              }
            }
            else
            {
              hash["NU"] = hash["NU"] + 1
            }
          }

          for (var key in hash) 
          {
            if (hash.hasOwnProperty(key)) {
            console.log(key + " -> " + hash[key]);
          }
        }

        res.json(hash);
      }

      else{
        console.log(error);
      }
      
    });
});

//Enter your twitter OAuth credentials here
var client = new Twitter({
  consumer_key: '',
  consumer_secret: '',
  access_token_key: '',
  access_token_secret: ''
});

app.listen(3000);
console.log('Server is running on port 3000');
