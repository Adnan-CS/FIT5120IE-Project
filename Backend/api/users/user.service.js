const pool = require("../../config/database");

module.exports = {
    create: (data, callBack) => {
        pool.query(
            `insert into registration(firstname, lastname, age, email, password, number) 
            values(?,?,?,?,?,?)`,
            [
                data.firstname,
                data.lastname,
                data.age,
                data.email,
                data.password,
                data.number                    
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