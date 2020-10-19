require("dotenv").config();
const express = require("express");
const app = express();
const centersRoute = require("./api/legalcenters/center.router");
const counsellingRoute = require("./api/counsellingcenters/counselling.router");
const dayNightgRoute = require("./api/hospitalservices/hospital.router");

//To convert Json object to javascript object
app.use(express.json());

app.use("/api/centers",centersRoute);
app.use("/api/counselling",counsellingRoute);
app.use("/api/nearestdaynight",dayNightgRoute);

app.listen(process.env.port || 8080,()=>{
    console.log("Server up and running port ");
});
