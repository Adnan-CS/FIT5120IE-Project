const { getNearestHospital } = require("./hospital.controller");
const { getNearestSevenEleven } = require("./hospital.controller");

const express = require("express");
const hospitalRouter = express.Router();
const sevenElevRouter = express.Router();

hospitalRouter.post("/",getNearestHospital);
module.exports = hospitalRouter;