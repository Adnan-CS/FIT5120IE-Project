const { getNearestCounselling } = require("./counselling.controller");

const express = require("express");
const counsellingRouter = express.Router();

counsellingRouter.post("/",getNearestCounselling);
module.exports = counsellingRouter;