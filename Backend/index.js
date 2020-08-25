require("dotenv").config();
const express = require("express");
const app = express();
const userRoute = require("./api/users/user.router");
const centersRoute = require("./api/legalcenters/center.router");
const counsellingRoute = require("./api/counsellingcenters/counselling.router");

//To convert Json object to javascript object
app.use(express.json());

app.use("/api/users",userRoute);
app.use("/api/centers",centersRoute);
app.use("/api/counselling",counsellingRoute);

app.listen(process.env.SERVER_PORT,()=>{
    console.log("Server up and running port ",process.env.SERVER_PORT);
});
