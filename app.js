var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var nodemailer = require('nodemailer');
var Twitter = require('twitter');
var mongoose = require('mongoose');
var sentiment = require('sentiment');

var app = express();

mongoose.connect('mongodb://localhost/tweets');

app.use(bodyParser.urlencoded({'extended':'true'}));
app.use(bodyParser.json());
app.use(bodyParser.json({ type: 'application/vnd.api+json' })); 

app.set('views',path.join(__dirname,'views'));
app.set('view engine','jade');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(express.static(path.join(__dirname,'public')));

app.get('/',function(req,res){
res.render('index',{title: 'Welcome'});
});

app.get('/about',function(req,res){
res.render('about');
});

app.get('/projects',function(req,res){
res.render('projects');
});

app.get('/resume',function(req,res){
res.render('resume');
});

app.post('/twt',function(req,res){
var wsearch = req.body.seword;
console.log(req.body.seword);
//var wsearch = "Real Madrid";
var params = {q: wsearch,count: "100"};
var hash = new Object();
client.get('search/tweets', params, function(error, tweets, response) {
      if (!error) {
        //preProcess(tweets.statuses[i],temp, word);
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
      
      //res.redirect('/contact');
    });
});

app.get('/twt',function(req,res){
var wsearch = "Ronaldo";
console.log(req.body.seword);
//var wsearch = "Real Madrid";
var params = {q: wsearch,count: "25"};
var hash = new Object();
client.get('search/tweets', params, function(error, tweets, response) {
      if (!error) {
        //preProcess(tweets.statuses[i],temp, word);
        console.log("Fetched", tweets.statuses.length, "tweets for", params.q);
        hash["VS"] = 0;hash["S"] = 0;hash["VH"] = 0;hash["H"] = 0;hash["NU"] = 0;
         for(i=0; i<tweets.statuses.length; i++)
         {
            var stm = sentiment(tweets.statuses[i].text).score;
            var s = tweets.statuses[i].source.match(">([^<]*)<")[0];
            s = s.substring(1,s.length -1);
            if(hash[s] == null)
            {
              hash[s] = 1;
            }
            else{
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
      
      //res.redirect('/contact');
    });
});

app.get('/ftwt',function(req,res){

	Tweet.findOneAndUpdate({tweet_id : "879192989435809800"},{$set:{source : "NodeJS"}},{new: true},function (err, docs) {
		if(err){
			res.send(err);
			return handleError(err);;
		}
		else
		{
			console.log("No Error");
		}
	});
	
	Tweet.findOne({tweet_id : "879192989435809800"}, function (err, tweets) {
		if(err){
			res.send(err);
			return handleError(err);;
		}
		else
		{
			console.log("No Error");
			var resp = {"Output": tweets.lang};
    		res.json(tweets);
		}
	});	
    
    //res.redirect('/contact');

});

app.get('/contact',function(req,res){
res.render('contact');
});

var Tweet = mongoose.model('Tweet',{
    tweet_id: String,
    source: String,
    lang: String
});

var client = new Twitter({
  consumer_key: 'eNPo01RTk8fG9aKTzFHxY5L1B',
  consumer_secret: '8xdv6UxQTxBoSg7bQPzbjgjBG2XDZCYtnwiHkFMIWbjZ4DLFNA',
  access_token_key: '828809012347023362-pHzS57WGO2I0ogcH4KMhHqwL7jZmGqY',
  access_token_secret: 'lSpFFh57DMxu6BhUjFUFBPL8Y9QHcfJTMATVBqZrW7SmE'
});


app.post('/contact/send',function(req,res){
//var wsearch = req.body.name;
var wsearch = "Real Madrid";
var params = {q: wsearch,count: "100"};
client.get('search/tweets', params, function(error, tweets, response) {
      if (!error) {
        //preProcess(tweets.statuses[i],temp, word);
        console.log("Fetched", tweets.statuses.length, "tweets for", params.q);
         /*for(i=0; i<tweets.statuses.length; i++){
            var temp = new Tweet;
            temp.tweet_id = tweets.statuses[i].id;
            var s = tweets.statuses[i].source.match(">([^<]*)<")[0];
    		s = s.substring(1,s.length -1);
    		temp.source = s;
    		temp.lang = tweets.statuses[i].lang;
    		temp.save();
        }*/
      }else{
        console.log(error);
      }
      //var resp = {"Output": tweets.statuses[0].lang};
      //res.json(resp);
      //res.redirect('/contact');
    });
});

app.listen(3000);
console.log('Server is running on port 3000');
