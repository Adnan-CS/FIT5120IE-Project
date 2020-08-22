const pool = require("../../config/database");

module.exports = {
    getAllCenters: (data, callBack) => {
        pool.query(
          `select center_name,latitude,longitude,address,contact_details,suburbortown from legalcenters`,
          [],
          (error, results, fields) => {
            if (error) {
              callBack(error);
            }
            return callBack(null, results);
          }
        );
      }
}