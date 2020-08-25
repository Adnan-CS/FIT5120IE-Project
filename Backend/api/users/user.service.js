const pool = require("../../config/database");

module.exports = {
    create: (data, callBack) => {
        pool.query(
            `insert into registration(firstname, lastname, dob, email, password) 
            values(?,?,?,?,?)`,
            [
                data.firstname,
                data.lastname,
                data.dob,
                data.email,
                data.password                  
            ],
            (error,results,fields) => {
                if(error){
                    callBack(error)
                }
                return callBack(null,results);
            }
        );
    }
}