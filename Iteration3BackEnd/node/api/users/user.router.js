const { createUser } = require("./user.controller");

const express = require("express");
const router = express.Router();

router.post("/",createUser);
module.exports = router;