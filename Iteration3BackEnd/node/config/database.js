const { createPool } = require("mysql");

const pool = createPool({
    port: 3306,
    host: "aws-simplified.cpkevmigz47j.us-east-1.rds.amazonaws.com",
    user: "admin",
    password: "1234adnan1234",
    database: "Safewomen",
    connectionLimit: 10
});

module.exports = pool;