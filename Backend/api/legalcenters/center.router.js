const { getNearestCenters } = require("./center.controller");

const express = require("express");
const centerRouter = express.Router();

centerRouter.post("/",getNearestCenters);
module.exports = centerRouter;